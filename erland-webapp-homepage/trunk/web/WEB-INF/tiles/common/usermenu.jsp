<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:beanmenuitem bean="menuSectionsPB" id="idDisplay" title="name" titleKey="nameKey" page="path" childs="childs" />
    <erland-common:menuitemseparator id="separator"/>
    <erland-common:menuitem id="services" titleKey="homepage.menu.services" page="/do/user/menu?menuItemId={menuItemId}" roles="manager">
        <erland-common:menuitem id="new" titleKey="homepage.menu.service-new" page="/do/user/menu?menuItemId={menuItemId}" roles="manager"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="preferences" titleKey="homepage.menu.preferences" page="/do/user/menu?menuItemId={menuItemId}" />
    <erland-common:menuitem id="users" titleKey="homepage.menu.users" page="/do/user/menu?menuItemId={menuItemId}" roles="manager" />
    <erland-common:beanmenuitem bean="helpMenuPB" id="idText" titleKey="name" page="path" target="_blank"/>
    <erland-common:menuitem id="logout" titleKey="homepage.menu.logout" page="/do/user/menu?menuItemId={menuItemId}" />
</erland-common:menu>
