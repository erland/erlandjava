<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="tvguide.favorite.edit.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="favorite.edit" target="_blank"><bean:message key="tvguide.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editfavorite" method="POST">
    <logic:notEmpty name="editFavoriteFB" property="idDisplay">
        <html:hidden property="idDisplay"/>
    </logic:notEmpty>
    <table class="propertypage-body">
    <tr><td><bean:message key="tvguide.favorite.edit.channel"/></td><td>
    <html:select property="channelDisplay" size="1">
        <html:option value="" key="tvguide.favorite.edit.channel.none"/>
        <html:options collection="channelsPB" property="idDisplay" labelProperty="name" />
    </html:select>
    </td></tr>
    <tr><td><bean:message key="tvguide.favorite.edit.orderno"/></td><td>
    <html:text property="orderNo"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="tvguide.buttons.save"/></html:submit>
    </td></tr>
</html:form>
