<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="today" titleKey="tvguide.menu.today" page="/do/guest/menu?user={user}&menuItemId={menuItemId}" />
    <erland-common:menuitem id="subscribed" titleKey="tvguide.menu.subscribed" page="/do/guest/menu?user={user}&menuItemId={menuItemId}" />
    <erland-common:menuitemseparator id="separator1" />
    <erland-common:menuitem id="subscriptions" titleKey="tvguide.menu.subscriptions" page="/do/guest/menu?user={user}&menuItemId={menuItemId}" />
    <erland-common:beanmenuitem bean="menuFavoriteChannelsPB" id="idDisplay" title="name" page="path" />
</erland-common:menu>
