<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="today" titleKey="tvguide.menu.today" page="/do/user/menu?menuItemId={menuItemId}" />
    <erland-common:menuitem id="subscribed" titleKey="tvguide.menu.subscribed" page="/do/user/menu?menuItemId={menuItemId}" />
    <erland-common:menuitemseparator id="separator1" />
    <erland-common:beanmenuitem bean="menuFavoriteChannelsPB" id="idDisplay" title="name" page="path" />
    <erland-common:menuitemseparator id="separator2" />
    <erland-common:menuitem id="newfavorite" titleKey="tvguide.menu.favorites-new" page="/do/user/newfavorite" />
    <erland-common:menuitem id="subscriptions" titleKey="tvguide.menu.subscriptions" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}">
        <erland-common:menuitem id="new" titleKey="tvguide.menu.subscriptions-new" page="/do/user/newsubscription" />
    </erland-common:menuitem>
    <erland-common:menuitem id="channels" titleKey="tvguide.menu.channels" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" roles="manager">
        <erland-common:menuitem id="new" titleKey="tvguide.menu.channels-new" page="/do/user/newchannel" />
        <erland-common:beanmenuitem bean="menuChannelsPB" id="idDisplay" title="name" page="path" />
    </erland-common:menuitem>
    <erland-common:menuitem id="services" titleKey="tvguide.menu.services" page="/do/user/menu?menuItemId={menuItemId}" roles="manager">
        <erland-common:menuitem id="new" titleKey="tvguide.menu.services-new" page="/do/user/menu?menuItemId={menuItemId}"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="preferences" titleKey="tvguide.menu.preferences" page="/do/user/menu?menuItemId={menuItemId}" />
    <erland-common:menuitem id="users" titleKey="tvguide.menu.users" page="/do/user/menu?menuItemId={menuItemId}" roles="manager">
        <erland-common:menuitem id="new" titleKey="tvguide.menu.users.new" page="/do/user/menu?id={menuItemId}" />
    </erland-common:menuitem>
    <erland-common:beanmenuitem bean="helpMenuPB" id="idText" titleKey="name" page="path" target="_blank"/>
    <erland-common:menuitem id="logout" titleKey="tvguide.menu.logout" page="/do/user/menu?menuItemId={menuItemId}" />
</erland-common:menu>
