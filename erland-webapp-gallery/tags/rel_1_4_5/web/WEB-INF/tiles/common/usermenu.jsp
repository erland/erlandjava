<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="no-border" style="bold-link" styleSelected="bold-link-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="galleries" titleKey="gallery.menu.galleries" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="gallery.menu.galleries.new" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" />
        <erland-common:beanmenuitem bean="menuGalleriesAndCategoriesPB" id="idDisplay" titleKey="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="guestgalleries" titleKey="gallery.menu.galleries-guest" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:beanmenuitem bean="guestMenuGalleriesAndCategoriesPB" id="idDisplay" titleKey="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="storages" titleKey="gallery.menu.storages" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" />
    <erland-common:menuitem id="preferences" titleKey="gallery.menu.preferences" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" />
    <erland-common:menuitem id="guestusers" titleKey="gallery.menu.guestusers" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" />
    <erland-common:menuitem id="users" titleKey="gallery.menu.users" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" roles="manager" />
    <erland-common:menuitem id="logout" titleKey="gallery.menu.logout" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" />
</erland-common:menu>
