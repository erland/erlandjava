<%@ page session="true" %>
<p class="normal">Logga in f�r att uppdatera dina bildarkiv eller klicka
<a class="bold-link" href="portal?do=home&user=erland">h�r</a> f�r att titta p� mina bildarkiv
Du kan ocks� titta p� n�gon annans bildarkiv genom att klicka p� l�nkarna nedan.<br>
Om du vill ha ett eget bildarkiv kan du h�ra av dig till <a class="bold-link" href="mailto:erland.i@telia.com">mig</a> eller ocks� g� vidare till
<a class="bold-link" href="http://erland.homeip.net/gallery">stora bildarkivet</a> d�r du kan registrera dig sj�lv.<br><br>
Vill du se fler bilder �n vad som finns h�r kan du g� till <a class="bold-link" href="http://erland.homeip.net/gallery">stora bildarkivet</a>
<br><br>
<center>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="home" />
</jsp:include>
<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="login"/>
</jsp:include>
</center>