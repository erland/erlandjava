<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<jsp:include page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editcategory" method="POST">
    <html:hidden property="gallery"/>
    <html:hidden property="category"/>
    <table>
        <tr><td><bean:message key="gallery.gallery.category.edit.name"/></td><td>
        <html:text property="name"/>
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.visible"/></td><td>
        <html:checkbox property="officialVisible" value="true"/>
        (<bean:message key="gallery.gallery.category.edit.forced"/>
        <html:checkbox property="forcedOfficial" value="true" />
        )
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.official"/></td><td>
        <html:checkbox property="official" value="true"/>
        (<bean:message key="gallery.gallery.category.edit.forced"/>
        <html:checkbox property="forcedOfficial" value="true" />
        )
        </td></tr>
        <tr><td><bean:message key="gallery.gallery.category.edit.official-always"/></td><td>
        <html:checkbox property="officialAlways" value="true"/>
        (<bean:message key="gallery.gallery.category.edit.forced"/>
        <html:checkbox property="forcedOfficial" value="true" />
        )
        </td></tr>
        <tr><td></td><td>
        <html:submit titleKey="gallery.buttons.save"/>
        </td></tr>
    <table>
</html:form>
