<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="diaryEntriesPB">
<tiles:insert page="/WEB-INF/tiles/common/calendar.jsp"/>
</logic:notEmpty>
<br>
<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="containers" titleKey="diary.menu.containers" page="/do/guest/menu?user={user}&menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:beanmenuitem bean="menuContainersPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="inventory" titleKey="diary.menu.inventory" page="/do/guest/viewinventory?user={user}" />
    <erland-common:menuitem id="species" titleKey="diary.menu.species" page="/do/guest/viewspecies?user={user}" />
    <erland-common:menuitem id="galleries" titleKey="diary.menu.galleries" page="/do/guest/menu?user={user}&menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:beanmenuitem bean="menuGalleriesPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="diaries" titleKey="diary.menu.diaries" page="/do/guest/menu?user={user}&menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:beanmenuitem bean="menuDiariesPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
</erland-common:menu>
