<%@ page session="true" import="erland.webapp.usermgmt.User"%>
<jsp:useBean id="user" scope="session" class="User" />
<h3>Välkommen <%=user.getFirstName()%> <%=user.getLastName()%> till din aktiesida</h3>
