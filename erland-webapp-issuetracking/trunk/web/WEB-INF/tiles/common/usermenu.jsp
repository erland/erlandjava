<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="no-border" style="bold-link" styleSelected="bold-link-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="newissue" titleKey="issuetracking.menu.newissue" page="/do/user/menu?id={menuItemId}" />
    <erland-common:menuitem id="issues" titleKey="issuetracking.menu.issues" page="/do/user/menu?id={menuItemId}">
        <erland-common:beanmenuitem bean="menuIssuesApplicationsPB" id="id" titleKey="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="errorissues" titleKey="issuetracking.menu.errorissues" page="/do/user/menu?id={menuItemId}">
        <erland-common:beanmenuitem bean="menuErrorIssuesApplicationsPB" id="id" titleKey="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="featureissues" titleKey="issuetracking.menu.featureissues" page="/do/user/menu?id={menuItemId}">
        <erland-common:beanmenuitem bean="menuFeatureIssuesApplicationsPB" id="id" titleKey="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="closedissues" titleKey="issuetracking.menu.closedissues" page="/do/user/menu?id={menuItemId}">
        <erland-common:beanmenuitem bean="menuClosedIssuesApplicationsPB" id="id" titleKey="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="applications" titleKey="issuetracking.menu.applications" page="/do/user/menu?id={menuItemId}">
        <erland-common:menuitem id="new" titleKey="issuetracking.menu.applications.new" page="/do/user/newapplication"/>
        <erland-common:beanmenuitem bean="menuUserApplicationsPB" id="id" titleKey="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="preferences" titleKey="issuetracking.menu.preferences" page="/do/user/menu?id={menuItemId}" />
    <erland-common:menuitem id="users" titleKey="issuetracking.menu.users" page="/do/user/menu?id={menuItemId}" roles="manager" />
    <erland-common:menuitem id="logout" titleKey="issuetracking.menu.logout" page="/do/logout" />
</erland-common:menu>
