<%@ page session="true" import="erland.webapp.usermgmt.User"%>
<jsp:useBean id="user" scope="session" class="User" />
<h3><%=user.getFirstName()%> <%=user.getLastName()%> welcome to your gallery page</h3>