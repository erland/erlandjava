<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="no-border" style="bold-link" styleSelected="bold-link-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="galleries" titleKey="dirgallery.menu.galleries" page="/do/user/menu?id={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="dirgallery.menu.galleries.new" page="/do/user/menu?id={menuItemId}" />
        <erland-common:beanmenuitem bean="menuGalleriesPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="preferences" titleKey="dirgallery.menu.preferences" page="/do/user/menu?id={menuItemId}" />
    <erland-common:menuitem id="users" titleKey="dirgallery.menu.users" page="/do/user/menu?id={menuItemId}" roles="manager">
        <erland-common:menuitem id="new" titleKey="dirgallery.menu.users.new" page="/do/user/menu?id={menuItemId}" roles="manager" />
    </erland-common:menuitem>
    <erland-common:menuitem id="logout" titleKey="dirgallery.menu.logout" page="/do/logout" />
</erland-common:menu>
