<%@ page session="true" %>
Om du loggar in kan du komma åt privata bilder och privata bildarkiv hos den person som äger dessa bildarkiv, observera att du kommer inte att komma åt dessa
om inte ägaren till bildarkivet först har satt upp dig som gäst användare.

<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="loginguest"/>
</jsp:include>