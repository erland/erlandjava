<%@ page session="true" import="erland.webapp.usermgmt.User"%>
<jsp:useBean id="user" scope="session" class="User" />
<div class="bold"><%=user.getFirstName()%> <%=user.getLastName()%> v�lkommen till din akvariedagbok</div>