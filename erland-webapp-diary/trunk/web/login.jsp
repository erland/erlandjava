<%@ page session="true" %>
<p class="normal">Logga in för att uppdatera din dagbok eller klicka
<a class="bold-link" href="portal?do=home&user=erland">här</a> för att läsa min dagbok.
Du kan också välja att titta på någon annans dagbok genom att klicka på länkarna nedan.<br>
Eller registrera din egen dagbok genom att klicka <a class="bold-link" href="portal?do=registernewuseraccount">här</a><br><br>
<center>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="home" />
</jsp:include>
<jsp:include page="loginform.jsp"/>
</center>