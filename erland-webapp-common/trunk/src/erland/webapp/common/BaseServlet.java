package erland.webapp.common;

import erland.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseServlet extends HttpServlet implements WebAppEnvironmentInterface {
    private ParameterValueStorageExInterface resources=null;
    private EntityFactoryInterface entityFactory;
    private EntityStorageFactoryInterface entityStorageFactory;
    private CommandFactoryInterface commandFactory;
    private StorageInterface storage;

    class Pages {
        private String[] supportPageNames;
        private Map pages = new HashMap();
        private String mainPageName;
        private String contentPageName;
        private boolean forward;

        public Pages(String mainPageName, String contentPageName, String[] supportPageNames) {
            this.supportPageNames = supportPageNames;
            this.mainPageName = mainPageName;
            this.contentPageName = contentPageName;
        }
        public String[] getSupportPageNames() {
            return supportPageNames;
        }

        public String getContentPageName() {
            return contentPageName;
        }

        public String getMainPageName() {
            return mainPageName;
        }

        public String getPage(String page) {
            return (String) pages.get(page);
        }

        public void setPage(String page, String value) {
            System.out.println("setPage "+page+"="+value);
            if(value!=null) {
                pages.put(page,value);
            }else {
                pages.remove(page);
            }
        }

        public void setForward(boolean forward) {
            this.forward = forward;
        }

        public boolean isForward() {
            return this.forward;
        }
    }

    protected StorageInterface getStorage() {
        if(storage==null) {
            storage = new FileStorage(getServletContext().getRealPath("/")+"resources.xml");
        }
        return storage;
    }

    public ParameterValueStorageExInterface getResources() {
        if(resources==null) {
            resources = new ParameterStorageTree(getStorage());
        }
        return resources;
    }

    public EntityFactoryInterface getEntityFactory() {
        if(entityFactory==null) {
            entityFactory = new EntityFactory(getEnvironment());
        }
        return entityFactory;
    }

    public EntityStorageFactoryInterface getEntityStorageFactory() {
        if(entityStorageFactory==null) {
            entityStorageFactory = new EntityStorageFactory(getEnvironment());
        }
        return entityStorageFactory;
    }

    public CommandFactoryInterface getCommandFactory() {
        if(commandFactory==null) {
            commandFactory = new CommandFactory(getEnvironment());
        }
        return commandFactory;
    }

    protected void initStart() {

    }
    protected void initEnd() {

    }

    protected WebAppEnvironmentInterface getEnvironment() {
        return this;
    }

    public void init() throws ServletException {
        initStart();
        XMLParser.setInstance(new SAXXMLParser());
        initEnd();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected boolean isCommandAllowed(HttpServletRequest request) {
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandInterface cmd = getEnvironment().getCommandFactory().create(getCommandClassName(request));
        request.setAttribute("environment",getEnvironment());
        String page;
        if(cmd!=null) {
            if(isCommandAllowed(request)) {
                setCommandClass(request,cmd);
                Log.println(this,cmd.getClass().getName()+"::execute("+request.getQueryString()+")");
                page = cmd.execute(request);
                String save = getEnvironment().getResources().getParameter("commands."+getCommandClassName(request)+".save");
                if(save!=null) {
                    if(save.length()>0) {
                        request.getSession().setAttribute(save,cmd);
                    }else {
                        request.getSession().removeAttribute(save);
                    }
                }
            }else {
                page = getNotAllowedPage(request);
                setCommandClass(request,null);
            }
        }else {
            if(getCommandClassName(request)==null) {
                page = getDefaultPage(request);
            }else {
                page = getErrorPage(request);
            }
        }
        Pages pages = null;
        if(page!=null && page.length()>0) {
            pages = getPages(request,"pages."+getCommandClassName(request)+"."+page);
            if(pages==null) {
                pages = getPages(request,"pages."+page);
                if(pages==null) {
                    pages = getPages(request,null);
                }
            }
        }else if(page==null && cmd instanceof CommandResponseInterface) {
            ((CommandResponseInterface)cmd).makeResponse(request,response);
        }else {
            pages = getPages(request,"pages."+getCommandClassName(request));
            if(pages==null) {
                pages = getPages(request,null);
            }
        }
        if(pages!=null) {
            setPages(request,pages);
            RequestDispatcher dispatcher =null;
            String main = pages.getPage(pages.getMainPageName());
            if(!pages.isForward() && main!=null && main.length()>0) {
                Log.println(this,"Opening "+main);
                dispatcher = request.getRequestDispatcher(main);
            }else {
                String content = pages.getPage(pages.getContentPageName());
                Log.println(this,"Opening "+content);
                dispatcher = request.getRequestDispatcher(content);
            }
            dispatcher.forward(request,response);
            Log.println(this,"Opened page");
        }else {
            if(!(cmd instanceof CommandResponseInterface)) {
                Log.println(this,cmd.getClass().getName()+"::execute -> no page returned for "+page);
            }
        }
    }

    private void setPage(HttpServletRequest request, String page, String value) {
        if(value!=null && value.length()>0) {
            request.getSession().setAttribute(page,value);
        }else {
            request.getSession().removeAttribute(page);
        }
    }
    private void setPages(HttpServletRequest request, Pages pages) {
        String[] supportPages = pages.getSupportPageNames();
        for(int i=0;i<supportPages.length;i++) {
            setPage(request,supportPages[i],pages.getPage(supportPages[i]));
        }
        setPage(request,pages.getMainPageName(),pages.getPage(pages.getMainPageName()));
        setPage(request,pages.getContentPageName(),pages.getPage(pages.getContentPageName()));
    }


    protected String[] getSupportPages() {
        final String[] supportPages = {"menu","header","footer"};
        return supportPages;
    }

    private String getPage(HttpServletRequest request, String contentPage, String page) {
        String val = getEnvironment().getResources().getParameter(contentPage+"."+page);
        if(val==null) {
            val = (String) request.getSession().getAttribute(page);
        }else if(val.length()>0) {
            val = "/"+val;
        }else {
            val = null;
        }
        return val;
    }
    private Pages getPages(HttpServletRequest request, String page) {
        Pages p = new Pages("main","content",getSupportPages());
        if(page!=null) {
            boolean bForward = true;
            String content = getEnvironment().getResources().getParameter(page+".forward");
            Log.println(this,"getPages("+page+".forward)="+content);
            if(content==null) {
                content = getEnvironment().getResources().getParameter(page+".page");
                Log.println(this,"getPages("+page+".page)="+content);
                bForward = false;
            }
            Log.println(this,"getPages("+page+")="+content);
            if(content!=null) {
                p.setForward(bForward);
                p.setPage(p.getContentPageName(),"/"+content);
                p.setPage(p.getMainPageName(),getPage(request,page,p.getMainPageName()));
                String[] supportPages = p.getSupportPageNames();
                for(int i=0;i<supportPages.length;i++) {
                    p.setPage(supportPages[i],getPage(request,page,supportPages[i]));
                }
                return p;
            }
        }else {
            String content = (String) request.getSession().getAttribute(p.getContentPageName());
            if(content!=null) {
                p.setPage(p.getContentPageName(),content);
                p.setPage(p.getMainPageName(),(String)request.getSession().getAttribute(p.getMainPageName()));
                String[] supportPages = p.getSupportPageNames();
                for(int i=0;i<supportPages.length;i++) {
                    p.setPage(supportPages[i],(String) request.getSession().getAttribute(supportPages[i]));
                }
                return p;
            }
        }
        return null;
    }
    protected void setCommandClass(HttpServletRequest request,CommandInterface cmd) {
        if(cmd!=null) {
            request.setAttribute("cmd",cmd);
            HttpSession session = request.getSession(false);
            if(session!=null) {
                session.setAttribute("cmd",cmd);
            }
        }else {
            HttpSession session = request.getSession(false);
            if(session!=null) {
                request.setAttribute("cmd",session.getAttribute("cmd"));
            }
        }
    }
    protected String getCommandClassName(HttpServletRequest request) {
        return request.getParameter("do");
    }
    protected String getErrorPage(HttpServletRequest request) {
        return "error";
    }
    protected String getDefaultPage(HttpServletRequest request) {
        return "default";
    }

    protected String getNotAllowedPage(HttpServletRequest request) {
        return "notallowed";
    }
}
