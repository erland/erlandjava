<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editimagecomment" method="POST">
    <table>
    <html:hidden property="id"/>
    <html:hidden property="gallery"/>
    <tr><td><bean:message key="dirgallery.gallery.comment.edit.comment"/></td><td>
    <html:textarea property="comment" cols="60" rows="5"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="dirgallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
