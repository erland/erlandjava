<%@ page import="erland.webapp.common.WebAppEnvironmentInterface,
                 erland.webapp.usermgmt.User"%>
<%
    String username = request.getParameter("user");
    WebAppEnvironmentInterface env = (WebAppEnvironmentInterface)request.getAttribute("environment");
    User template = (User)env.getEntityFactory().create("user");
    template.setUsername(username);
    User user = (User)env.getEntityStorageFactory().getStorage("user").load(template);
%>
<p class="title">Welcome to the photo gallery by<br>
<font class="big-title"><%=user.getFirstName()%> <%=user.getLastName()%></font></p>