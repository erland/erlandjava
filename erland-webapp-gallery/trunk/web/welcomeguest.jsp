<%@ page import="erland.webapp.common.WebAppEnvironmentInterface,
                 erland.webapp.usermgmt.User"%>
<%
    String username = request.getParameter("user");
    WebAppEnvironmentInterface env = (WebAppEnvironmentInterface)request.getAttribute("environment");
    User template = (User)env.getEntityFactory().create("user");
    template.setUsername(username);
    User user = (User)env.getEntityStorageFactory().getStorage("user").load(template);
%>
<h3>Welcome to the gallery page of <%=user.getFirstName()%> <%=user.getLastName()%></h3>