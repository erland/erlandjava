<%@ page import="erland.webapp.common.WebAppEnvironmentInterface,
                 erland.webapp.usermgmt.User"%>
<%
    String username = request.getParameter("user");
    WebAppEnvironmentInterface env = (WebAppEnvironmentInterface)request.getAttribute("environment");
    User template = (User)env.getEntityFactory().create("usermgmt-userinfo");
    template.setUsername(username);
    User user = (User)env.getEntityStorageFactory().getStorage("usermgmt-userinfo").load(template);
%>
<div class="bold">Välkommen till <font class="title"><%=user.getFirstName()%> <%=user.getLastName()%>'s</font> bildarkiv</div>