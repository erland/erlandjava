<%@ page session="true" %>
<p class="normal">Logga in f�r att uppdatera ditt bildarkiv eller klicka
<a class="bold-link" href="portal?do=home&user=erland">h�r</a> f�r att titta i mitt bildarkiv.
Du kan ocks� titta p� n�gon annans bildarkiv genom att klicka p� l�nkarna nedan.<br>
Du kan ocks� registrera ditt eget bildarkiv <a class="bold-link" href="portal?do=registernewuseraccount">h�r</a><br><br>
<center>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="home" />
</jsp:include>
<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="login"/>
</jsp:include>
</center>