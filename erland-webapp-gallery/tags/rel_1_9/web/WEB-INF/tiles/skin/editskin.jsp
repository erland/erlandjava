<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editskin" method="POST">
    <table class="propertypage-body">
    <logic:notEmpty name="skinFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <logic:empty name="skinFB" property="id">
        <tr><td><bean:message key="gallery.skin.edit.id"/></td><td>
        <html:text property="id" size="20"/>
        </td></tr>
    </logic:empty>
    <tr><td><bean:message key="gallery.skin.edit.layout"/></td><td>
    <html:text property="layout" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.skin.edit.layoutSingle"/></td><td>
    <html:text property="layoutSingle" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.skin.edit.menu"/></td><td>
    <html:text property="menu" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.skin.edit.header"/></td><td>
    <html:text property="header" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.skin.edit.search"/></td><td>
    <html:text property="search" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.skin.edit.viewpicture"/></td><td>
    <html:text property="viewPicture" size="80"/>
    </td></tr>
    <tr><td><bean:message key="gallery.skin.edit.viewpictures"/></td><td>
    <html:text property="viewPictures" size="80"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="gallery.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
