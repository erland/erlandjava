<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editpicturestorage" method="POST">
    <table>
    <html:hidden property="id"/>
    <tr><td><bean:message key="gallery.picturestorage.edit.name"/></td><td>
    <html:text property="name" size="40"/>
    </td></tr>
    <tr><td><bean:message key="gallery.picturestorage.edit.path"/></td><td>
    <html:text property="path" size="80"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
