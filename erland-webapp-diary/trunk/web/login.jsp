<%@ page session="true" %>
Logga in f�r att uppdatera din dagbok eller klicka
<a class="bold-link" href="portal?do=searchentriesguest&user=erland">h�r</a> f�r att l�sa min dagbok.
Du kan ocks� v�lja att titta p� n�gon annans dagbok genom att klicka p� l�nkarna nedan.<br>
Eller registrera din egen dagbok genom att klicka <a class="bold-link" href="portal?do=registernewuseraccount">h�r</a><br><br>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="searchentriesguest" />
</jsp:include>
<jsp:include page="loginform.jsp"/>