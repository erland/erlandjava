<%@ page session="true"%>

<form name="loginForm" action="portal" method="POST">
<input type="hidden" name="do" value="<%=request.getParameter("logincmd")%>">
<input type="hidden" name="application" value="gallery">
<%
    String loginuser = request.getParameter("loginuser");
    if(loginuser!=null && loginuser.length()==0) {
        loginuser = null;
    }
    String user = request.getParameter("user");
    if(user!=null && user.length()==0) {
        user = null;
    }
    if(user!=null) {
        %>
        <input type="hidden" name="user" value="<%=user%>">
        <%
    }
%>
<table>
<tr><td>Användarnamn</td><td>
<input type="text" name="name" value="<%=loginuser!=null?loginuser:""%>">
</td></tr>
<tr><td>Lösenord</td><td>
<input type="password" name="password" value="">
</td></tr>
<tr><td></td><td>
<input type="submit" value="Logga in">
</td></tr>
</table>
</form>
