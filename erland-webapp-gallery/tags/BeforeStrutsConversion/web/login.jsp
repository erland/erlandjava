<%@ page import="erland.webapp.common.DescriptionTagHelper,
                 erland.webapp.common.html.HTMLEncoder,
                 erland.webapp.common.WebAppEnvironmentInterface"%>
<%@ page session="true" %>
<%
    WebAppEnvironmentInterface env = (WebAppEnvironmentInterface)request.getAttribute("environment");
    String welcomeText = env.getConfigurableResources().getParameter("welcometext");
%>
<p class="normal"><%=welcomeText!=null?HTMLEncoder.encode(welcomeText):""%>
<center>
<jsp:include page="viewuseraccountsguest.jsp">
    <jsp:param name="viewusercmd" value="home" />
</jsp:include>
<jsp:include page="loginform.jsp">
    <jsp:param name="logincmd" value="login"/>
</jsp:include>
</center>