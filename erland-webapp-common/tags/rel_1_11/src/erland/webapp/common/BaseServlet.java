package erland.webapp.common;
/*
 * Copyright (C) 2003 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

import erland.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public abstract class BaseServlet extends HttpServlet implements WebAppEnvironmentInterface {
    private ParameterValueStorageExInterface resources=null;
    private ParameterValueStorageExInterface configurableResources=null;
    private EntityFactoryInterface entityFactory;
    private EntityStorageFactoryInterface entityStorageFactory;
    private ServiceFactoryInterface serviceFactory;
    private CommandFactoryInterface commandFactory;
    private StorageInterface storage;
    private static final String SESSION = "session";
    private static final String REQUEST = "request";

    class Pages {
        private String[] supportPageNames;
        private Map pages = new HashMap();
        private Map pageTypes = new HashMap();
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
        public String getPageType(String page) {
            return (String) pageTypes.get(page);
        }

        public void setPage(String page, String value, String type) {
            Log.println(this,"setPage "+page+" in "+type+"="+value);
            if(value!=null) {
                pages.put(page,value);
                if(type!=null) {
                    pageTypes.put(page,type);
                }
            }else {
                pages.remove(page);
                pageTypes.remove(page);
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
            Log.println(this,"Loading configuration from: "+getServletContext().getRealPath("/")+"WEB-INF/resources.xml");
            storage = new FileStorage(getServletContext().getRealPath("/")+"WEB-INF/resources.xml");
        }
        return storage;
    }

    public ParameterValueStorageExInterface getResources() {
        if(resources==null) {
            resources = new ParameterStorageChild("resources.",new ParameterStorageTree(getStorage(),new JarFileStorageFactory()));
        }
        return resources;
    }
    protected String getConfigurableResourcesEntityName() {
        return "common-resource";
    }
    protected boolean getConfigurableResourcesCache() {
        return true;
    }
    public ParameterValueStorageExInterface getConfigurableResources() {
        if(configurableResources==null) {
            configurableResources = new ParameterStorageChild("resources.",new ResourceStorage(getEnvironment(),getApplicationName(),getConfigurableResourcesEntityName(),getConfigurableResourcesCache()));
        }
        return configurableResources;
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

    public ServiceFactoryInterface getServiceFactory() {
        if(serviceFactory==null) {
            serviceFactory = new ServiceFactory(getEnvironment());
        }
        return serviceFactory;
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
        InputStream input = getClass().getResourceAsStream("/"+getApplicationName()+"_log.xml");
        if(input!=null) {
            System.out.println("Loading log configuration from: "+getApplicationName()+"_log.xml");
            Log.setLogConfig(new ParameterStorageString(new StreamStorage(input,null),null,"log"));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected boolean isCommandAllowed(HttpServletRequest request) {
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandInterface cmd = getCommandFactory().create(getCommandClassName(request));
        request.setAttribute("environment",getEnvironment());
        String page;
        if(cmd!=null) {
            if(isCommandAllowed(request)) {
                setCommandClass(request,cmd);
                String saveParameters = getEnvironment().getResources().getParameter("commands."+getCommandClassName(request)+".saveparameters");
                if(saveParameters!=null) {
                    String type = getEnvironment().getResources().getParameter("commands."+getCommandClassName(request)+".saveparameters.type");
                    saveParameter(request,type,REQUEST,saveParameters,getParameters(request));
                }
                Log.println(this,cmd.getClass().getName()+"::execute("+request.getQueryString()+")");
                if(cmd instanceof CommandOptionsInterface) {
                    ((CommandOptionsInterface)cmd).setOptions(new ParameterStorageChild("commands."+getCommandClassName(request)+".options.",getResources()));
                }
                page = cmd.execute(request);
                String save = getEnvironment().getResources().getParameter("commands."+getCommandClassName(request)+".save");
                if(save!=null) {
                    String type = getEnvironment().getResources().getParameter("commands."+getCommandClassName(request)+".save.type");
                    saveParameter(request, type, SESSION, save, cmd);
                }
                String delete = getEnvironment().getResources().getParameter("commands."+getCommandClassName(request)+".delete");
                if(delete!=null) {
                    String type = getEnvironment().getResources().getParameter("commands."+getCommandClassName(request)+".delete.type");
                    saveParameter(request, type, SESSION, delete, null);
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

    protected void saveParameter(HttpServletRequest request, String type, String defaultType, String name, Object value) {
        if(type==null || (!type.equalsIgnoreCase(SESSION) && !type.equalsIgnoreCase(REQUEST))) {
            type=defaultType;
        }
        name = decodeParameter(request,name);
        Log.println(this,"Save "+(value!=null?value.getClass().getName():"null")+" in "+type+" as "+name);
        if(name.length()>0) {
            if(type.equalsIgnoreCase(SESSION)) {
                if(value!=null) {
                    request.getSession().setAttribute(name,value);
                }else {
                    request.getSession().removeAttribute(name);
                }
            }else {
                if(value!=null) {
                    request.setAttribute(name,value);
                }else {
                    request.removeAttribute(name);
                }
            }
        }
    }

    private void setPage(HttpServletRequest request, String page, String value, String type) {
        if(value!=null && value.length()>0) {
            if(type==null || type.length()==0 || type.equalsIgnoreCase("session")) {
                Log.println(this,"Set "+page+"="+value+" in session");
                request.getSession().setAttribute(page,value);
            }
            Log.println(this,"Set "+page+"="+value+" in request");
            request.setAttribute(page,value);
        }else {
            if(type==null || type.length()==0 || type.equalsIgnoreCase("session")) {
                Log.println(this,"Remove "+page+"=from session");
                request.getSession().removeAttribute(page);
            }
            Log.println(this,"Remove "+page+"=from request");
            request.removeAttribute(page);
        }
    }
    private void setPages(HttpServletRequest request, Pages pages) {
        String[] supportPages = pages.getSupportPageNames();
        for(int i=0;i<supportPages.length;i++) {
            setPage(request,supportPages[i],pages.getPage(supportPages[i]),pages.getPageType(supportPages[i]));
        }
        setPage(request,pages.getMainPageName(),pages.getPage(pages.getMainPageName()),pages.getPageType(pages.getMainPageName()));
        setPage(request,pages.getContentPageName(),pages.getPage(pages.getContentPageName()),pages.getPageType(pages.getContentPageName()));
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
    private String getPageType(String page) {
        return getEnvironment().getResources().getParameter(page+".type");
    }
    private Pages getPages(HttpServletRequest request, String page) {
        Pages p = new Pages("main","content",getSupportPages());
        if(page!=null) {
            boolean bForward = true;
            String content = getEnvironment().getResources().getParameter(page+".forward");
            String contentType = getEnvironment().getResources().getParameter(page+".forward.type");
            Log.println(this,"getPages("+page+".forward,"+contentType+")="+content);
            if(content==null) {
                content = getEnvironment().getResources().getParameter(page+".page");
                contentType = getEnvironment().getResources().getParameter(page+".page.type");
                Log.println(this,"getPages("+page+".page,"+contentType+")="+content);
                if(content!=null) {
                    content = replaceDynamicParameters(request,content);
                }
                bForward = false;
            }else {
                content = replaceDynamicParameters(request,content);
            }
            Log.println(this,"getPages("+page+","+contentType+")="+content);
            if(content!=null) {
                p.setForward(bForward);
                p.setPage(p.getContentPageName(),"/"+content,contentType);
                p.setPage(p.getMainPageName(),getPage(request,page,p.getMainPageName()),getPageType(page+"."+p.getMainPageName()));
                String[] supportPages = p.getSupportPageNames();
                for(int i=0;i<supportPages.length;i++) {
                    p.setPage(supportPages[i],getPage(request,page,supportPages[i]),getPageType(supportPages[i]));
                }
                return p;
            }
        }else {
            String content = (String) request.getSession().getAttribute(p.getContentPageName());
            if(content!=null) {
                p.setPage(p.getContentPageName(),content,null);
                p.setPage(p.getMainPageName(),(String)request.getSession().getAttribute(p.getMainPageName()),null);
                String[] supportPages = p.getSupportPageNames();
                for(int i=0;i<supportPages.length;i++) {
                    p.setPage(supportPages[i],(String) request.getSession().getAttribute(supportPages[i]),null);
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
    protected String decodeParameter(HttpServletRequest request, String parameter) {
        int startPos = parameter.indexOf('{');
        if(startPos>=0) {
            int endPos = parameter.indexOf('}',startPos+1);
            if(endPos>=0) {
                StringBuffer sb = new StringBuffer(parameter);
                String replaceValue = request.getParameter(parameter.substring(startPos+1,endPos));
                if(replaceValue==null) {
                    replaceValue="";
                }
                sb.replace(startPos,endPos+1,replaceValue);
                parameter = sb.toString();
                return decodeParameter(request,parameter);
            }
        }
        return parameter;
    }

    protected String[] getPreferedParameterOrder() {
        return new String[] {"do"};
    }

    protected String getParameters(HttpServletRequest request) {
        Map map = request.getParameterMap();
        StringBuffer sb = new StringBuffer();
        boolean bFirst = true;
        String[] order = getPreferedParameterOrder();
        Collection orderCollection = Arrays.asList(order);
        for (int i = 0; i < order.length; i++) {
            String name = order[i];
            String values[] = request.getParameterValues(name);
            for (int j = 0; values!=null && j < values.length; j++) {
                if(!bFirst) {
                    sb.append("&");
                }
                bFirst= false;
                sb.append(name);
                sb.append("=");
                sb.append(values[j]);
            }
        }
        for(Iterator it=map.keySet().iterator();it.hasNext();) {
            String name = (String) it.next();
            if(!orderCollection.contains(name)) {
                String[] values = (String[]) map.get(name);
                for (int i = 0; i < values.length; i++) {
                    if(!bFirst) {
                        sb.append("&");
                    }
                    bFirst = false;
                    sb.append(name);
                    sb.append("=");
                    sb.append(values[i]);
                }
            }
        }
        return sb.toString();
    }

    protected String replaceDynamicParameters(HttpServletRequest request,String address) {
        StringBuffer sb = new StringBuffer(address);
        int startPos = sb.indexOf("{");
        while(startPos>=0) {
            int endPos = sb.indexOf("}",startPos+1);
            if(endPos>=0) {
                String attribute = sb.substring(startPos+1,endPos);
                Object value = request.getParameter(attribute);
                if(value==null) {
                    value = request.getAttribute(attribute);
                }
                if(value==null) {
                    HttpSession session = request.getSession(false);
                    if(session!=null) {
                        value = session.getAttribute(attribute);
                    }
                }
                if(value!=null) {
                    sb.replace(startPos,endPos+1,value.toString());
                    endPos = startPos+value.toString().length();
                }else {
                    sb.replace(startPos,endPos+1,"");
                    endPos = startPos;
                }
                startPos = sb.indexOf("{",endPos);
            }else {
                startPos = -1;
            }
        }
        return sb.toString();
    }

    protected abstract String getApplicationName();
}
