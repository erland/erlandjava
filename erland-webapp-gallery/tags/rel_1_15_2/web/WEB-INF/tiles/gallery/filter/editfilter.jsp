<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.gallery.filter.edit.title"/></div>
<erland-common:helplink style="propertypage-button" context="gallery.filter.edit" target="_blank"><bean:message key="gallery.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editfilter" method="POST">
    <table class="propertypage-body">
    <html:hidden property="id"/>
    <tr><td><bean:message key="gallery.gallery.filter.edit.name"/></td><td>
    <html:text property="name" size="40"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.filter.edit.description"/></td><td>
    <html:textarea property="description" rows="5" cols="60" />
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.filter.edit.cls"/></td><td>
    <html:text property="cls" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.filter.edit.parameters"/></td><td>
    <html:text property="parameters" size="80"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
