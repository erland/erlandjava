<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editcontainer" method="POST">
    <table class="propertypage-body">
    <logic:notEmpty name="editContainerFB" property="id">
        <html:hidden property="id"/>
    </logic:notEmpty>
    <tr><td><bean:message key="diary.container.edit.name"/></td><td>
    <html:text property="name"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.model"/></td><td>
    <html:text property="model"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.description"/></td><td>
    <html:textarea property="description" cols="80" rows="15"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.volume"/></td><td>
    <html:text property="volumeDisplay"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.width"/></td><td>
    <html:text property="widthDisplay"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.height"/></td><td>
    <html:text property="heightDisplay"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.length"/></td><td>
    <html:text property="lengthDisplay"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.image"/></td><td>
    <html:text property="image"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.largeimage"/></td><td>
    <html:text property="largeImage"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.link"/></td><td>
    <html:text property="link"/>
    </td></tr>
    <tr><td><bean:message key="diary.container.edit.gallery"/></td><td>
    <html:select property="galleryDisplay" size="1">
        <html:option value="" key="diary.container.edit.gallery.none"/>
        <html:options collection="galleriesPB" property="idDisplay" labelProperty="title" />
    </html:select>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="diary.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
