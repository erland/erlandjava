<%@ page session="true" %>
<p class="normal">Logga in för att uppdatera ditt bildarkiv eller klicka
<a class="bold-link" href="portal?do=home&user=erland">här</a> för att titta i mitt bildarkiv.
Du kan också titta på någon annans bildarkiv genom att klicka på länkarna nedan.<br>
Du kan också registrera ditt eget bildarkiv <a class="bold-link" href="portal?do=registernewuseraccount">här</a><br><br>
<center>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="home" />
</jsp:include>
<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="login"/>
</jsp:include>
</center>