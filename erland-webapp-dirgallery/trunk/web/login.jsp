<%@ page session="true" %>
<p class="normal">Logga in för att uppdatera dina bildarkiv eller klicka
<a class="bold-link" href="portal?do=home&user=erland">här</a> för att titta på mina bildarkiv
Du kan också titta på någon annans bildarkiv genom att klicka på länkarna nedan.<br>
Om du vill ha ett eget bildarkiv kan du höra av dig till <a class="bold-link" href="mailto:erland.i@telia.com">mig</a> eller också gå vidare till
<a class="bold-link" href="http://erland.homeip.net/gallery">stora bildarkivet</a> där du kan registrera dig själv.<br><br>
Vill du se fler bilder än vad som finns här kan du gå till <a class="bold-link" href="http://erland.homeip.net/gallery">stora bildarkivet</a>
<br><br>
<center>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="home" />
</jsp:include>
<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="login"/>
</jsp:include>
</center>