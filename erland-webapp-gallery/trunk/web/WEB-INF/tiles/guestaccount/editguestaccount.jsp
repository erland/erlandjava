<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="normal"><bean:message key="gallery.guestaccount.add.description"/></p>
<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editguestaccount" method="POST">
    <table>
    <tr><td><bean:message key="gallery.guestaccount.edit.username"/></td><td>
    <html:text property="guestUser" />
    </td></tr>
    <tr><td></td><td>
    <html:submit titleKey="gallery.buttons.save"/>
    </td></tr>
    <table>
</html:form>
