<%@ page import="java.util.Date"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<logic:notEmpty name="diaryEntriesPB">
    <tiles:insert page="/WEB-INF/tiles/common/calendar.jsp"/>
    <br>
    <table class="no-border-tight">
        <html:form action="/user/newdiaryentry" method="POST" type="" >
        <tr>
        <td><html:text property="dateDisplay" size="10" /></td>
        <td><a href="javascript: window.location='<html:rewrite page="/do/user/newdiaryentry"/>?diary=<bean:write name="diary"/>&dateDisplay='+editDiaryEntryFB.dateDisplay.value" class="bold-link"><bean:message key="diary.diary.entry.new"/></a></td>
        </tr>
        </html:form>
    </table>
</logic:notEmpty>
<erland-common:menu id="mainMenu" menuStyle="no-border" style="bold-link" styleSelected="bold-link-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="purchase" titleKey="diary.menu.purchase" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="diary.menu.purchase.new" page="/do/user/newpurchaseentry" />
        <erland-common:menuitem id="all" titleKey="diary.menu.purchase.all" page="/do/user/viewpurchaseentries" />
        <erland-common:menuitem id="stats" titleKey="diary.menu.purchase.stats" page="/do/user/viewpurchasestats" />
    </erland-common:menuitem>
    <erland-common:menuitem id="containers" titleKey="diary.menu.containers" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="diary.menu.containers.new" page="/do/user/newcontainer" />
        <erland-common:beanmenuitem bean="menuContainersPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="inventory" titleKey="diary.menu.inventory" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="diary.menu.inventory.new" page="/do/user/newinventoryentry" />
        <erland-common:menuitem id="current" titleKey="diary.menu.inventory.current" page="/do/user/viewcurrentinventory" />
        <erland-common:menuitem id="whole" titleKey="diary.menu.inventory.whole" page="/do/user/viewwholeinventory" />
    </erland-common:menuitem>
    <erland-common:menuitem id="galleries" titleKey="diary.menu.galleries" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="diary.menu.galleries.new" page="/do/user/newgallery" />
        <erland-common:beanmenuitem bean="menuGalleriesPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="diaries" titleKey="diary.menu.diaries" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" >
        <erland-common:menuitem id="new" titleKey="diary.menu.diaries.new" page="/do/user/newdiary" />
        <erland-common:beanmenuitem bean="menuDiariesPB" id="idDisplay" title="name" page="path" childs="childs" />
    </erland-common:menuitem>
    <erland-common:menuitem id="preferences" titleKey="diary.menu.preferences" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" />
    <erland-common:menuitem id="users" titleKey="diary.menu.users" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" roles="manager" />
    <erland-common:menuitem id="appendix" titleKey="diary.menu.appendix" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" roles="manager" >
        <erland-common:menuitem id="new" titleKey="diary.menu.appendix.new" page="/do/user/newappendixentry" />
    </erland-common:menuitem>
    <erland-common:menuitem id="sourceappendix" titleKey="diary.menu.sourceappendix" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" roles="manager" >
        <erland-common:menuitem id="new" titleKey="diary.menu.sourceappendix.new" page="/do/user/newsourceappendixentry" />
    </erland-common:menuitem>
    <erland-common:menuitem id="logout" titleKey="diary.menu.logout" page="/do/user/menu?menuName={menuId}&menuItemId={menuItemId}" />
</erland-common:menu>
