<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="brokerstocks" titleKey="stock.menu.brokerstocks" page="/do/viewbrokerstocks" />
    <erland-common:beanmenuitem bean="menuItemsPB" id="idDisplay" childs="childs" title="name" titleKey="nameKey" page="path"/>
    <erland-common:menuitem id="newaccount" titleKey="stock.menu.newaccount" page="/do/newaccount" />
    <erland-common:menuitem id="logout" titleKey="stock.menu.logout" page="/do/logout" />
</erland-common:menu>
