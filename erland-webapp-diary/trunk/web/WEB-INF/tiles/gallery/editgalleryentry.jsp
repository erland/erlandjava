<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editgalleryentry" method="POST">
    <table>
    <html:hidden property="gallery"/>
    <logic:notEmpty name="editGalleryEntryFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <tr><td><bean:message key="diary.gallery.entry.edit.title"/></td><td>
    <html:text property="title"/>
    </td></tr>
    <tr><td><bean:message key="diary.gallery.entry.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="diary.gallery.entry.edit.image"/></td><td>
    <html:text property="image"/>
    </td></tr>
    <tr><td><bean:message key="diary.gallery.entry.edit.link"/></td><td>
    <html:text property="link"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
