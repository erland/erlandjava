<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="applications" titleKey="help.menu.applications" page="/do/user/menu?id={menuItemId}">
        <erland-common:menuitem id="new" titleKey="help.menu.applications.new" page="/do/user/newapplication"/>
        <erland-common:beanmenuitem bean="menuUserApplicationsPB" id="id" title="name" titleKey="nameKey" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="logout" titleKey="help.menu.logout" page="/do/logout" />
</erland-common:menu>
