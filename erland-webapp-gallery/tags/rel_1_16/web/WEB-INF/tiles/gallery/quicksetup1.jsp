<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="gallery.gallery.quicksetup.windowtitle"/></div>
<erland-common:helplink style="propertypage-button" context="gallery.edit" target="_blank"><bean:message key="gallery.help.button"/></erland-common:helplink>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/quicksetup1" method="POST">
    <html:hidden property="galleryDisplay"/>
    <logic:iterate id="category" name="quicksetupGalleryFB" property="hiddenCategoriesDisplay">
        <bean:define id="currentCategory" name="category" type="String"/>
        <html:hidden property="hiddenCategoriesDisplay" value="<%=currentCategory%>" />
    </logic:iterate>
    <html:hidden property="officialGuestCategoryDisplay"/>
    <html:hidden property="officialCategoryDisplay"/>
    <html:hidden property="topCategoryDisplay"/>
    <table class="propertypage-body">
    <tr><td><bean:message key="gallery.gallery.quicksetup.publish-categories"/></td><td>
    <html:radio property="publishCategoriesDisplay" value="true"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.quicksetup.protected-categories"/></td><td>
    <html:radio property="publishCategoriesDisplay" value="false"/>
    </td></tr>
    <tr><td></td><td align="right">
    <html:submit><bean:message key="gallery.buttons.next"/></html:submit>
    </td></tr>
</html:form>
