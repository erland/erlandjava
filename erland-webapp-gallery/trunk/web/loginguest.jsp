<%@ page session="true" %>
Om du loggar in kan du komma �t privata bilder och privata bildarkiv hos den person som �ger dessa bildarkiv, observera att du kommer inte att komma �t dessa
om inte �garen till bildarkivet f�rst har satt upp dig som g�st anv�ndare.

<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="loginguest"/>
</jsp:include>