<%@ page session="true" %>
Login to update your gallery or click
<a class="bold-link" href="portal?do=home&user=erland">here</a> to look at my gallery
You can choose to look at someones gallery by clicking on the links below.<br>
You can register your own gallery by clicking <a class="bold-link" href="portal?do=registernewuseraccount">here</a><br><br>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="home" />
</jsp:include>
<jsp:include page="loginform.jsp"/>