<%@ page session="true" import="erland.webapp.usermgmt.User"%>
<jsp:useBean id="user" scope="session" class="User" />
<p class="title"><%=user.getFirstName()%> <%=user.getLastName()%> welcome to your gallery page</p>