<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="collections" titleKey="cdcollection.menu.collections" page="/do/user/menu?menuItemId={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="cdcollection.menu.collections.new" page="/do/user/menu?menuItemId={menuItemId}" />
        <erland-common:beanmenuitem bean="menuCollectionsPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="medias" titleKey="cdcollection.menu.medias" page="/do/user/menu?menuItemId={menuItemId}" roles="manager">
        <erland-common:menuitem id="new" titleKey="cdcollection.menu.medias.new" page="/do/user/menu?menuItemId={menuItemId}" roles="manager"/>
        <erland-common:menuitem id="import" titleKey="cdcollection.menu.medias.import" page="/do/user/menu?menuItemId={menuItemId}" roles="manager"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="preferences" titleKey="cdcollection.menu.preferences" page="/do/user/menu?menuItemId={menuItemId}" />
    <erland-common:menuitem id="users" titleKey="cdcollection.menu.users" page="/do/user/menu?menuItemId={menuItemId}" roles="manager" />
    <erland-common:beanmenuitem bean="helpMenuPB" id="idText" titleKey="name" page="path" target="_blank"/>
    <erland-common:menuitem id="logout" titleKey="cdcollection.menu.logout" page="/do/user/menu?menuItemId={menuItemId}" />
</erland-common:menu>
