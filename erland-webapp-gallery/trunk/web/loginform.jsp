<%@ page session="true"%>

<form name="loginForm" action="portal" method="POST">
<input type="hidden" name="do" value="login">
<input type="hidden" name="application" value="gallery">
<%
    String user = request.getParameter("user");
    if(user!=null && user.length()==0) {
        user = null;
    }
%>
<table>
<tr><td>Username</td><td>
<input type="text" name="name" value="<%=user!=null?user:""%>">
</td></tr>
<tr><td>Password</td><td>
<input type="password" name="password" value="">
</td></tr>
<tr><td></td><td>
<input type="submit" value="Logga in">
</td></tr>
</table>
</form>
