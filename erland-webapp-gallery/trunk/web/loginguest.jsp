<%@ page session="true" %>
Login to get access to private pictures of this user, observe that you won't be able to login unless the user already has setup you as a guest user

<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="loginguest"/>
</jsp:include>