<%@ page session="true" import="erland.webapp.usermgmt.User"%>
<jsp:useBean id="user" scope="session" class="User" />
<h3>V�lkommen <%=user.getFirstName()%> <%=user.getLastName()%> till din aktiesida</h3>
