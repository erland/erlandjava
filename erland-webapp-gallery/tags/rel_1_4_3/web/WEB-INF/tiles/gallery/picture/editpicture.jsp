<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editpicture" method="POST">
    <html:hidden property="gallery"/>
    <logic:notEmpty name="pictureFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty> 
    <table>
        <tr><td><bean:message key="gallery.gallery.picture.edit.title"/></td><td>
        <html:text property="title" size="30"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.picture.edit.description"/></td><td>
        <html:textarea property="description" cols="80" rows="15"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.picture.edit.image"/></td><td>
        <html:text property="image" size="80"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.picture.edit.link"/></td><td>
        <html:text property="link" size="80"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.picture.edit.date"/></td><td>
        <html:text property="dateDisplay" size="19"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.picture.edit.official"/></td><td>
        <html:checkbox property="official" value="true"/>
        </td></tr>
        <tr><td></td><td>
        <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
        </td></tr>
    </table>
</html:form>
