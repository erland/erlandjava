<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="newissue" titleKey="issuetracking.menu.newissue" page="/do/guest/menu?id={menuItemId}&application={application}"/>
    <erland-common:menuitem id="issues" titleKey="issuetracking.menu.issues" page="/do/guest/menu?id={menuItemId}&application={application}">
        <erland-common:beanmenuitem bean="menuIssuesApplicationsPB" id="id" title="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="errorissues" titleKey="issuetracking.menu.errorissues" page="/do/guest/menu?id={menuItemId}&application={application}">
        <erland-common:beanmenuitem bean="menuErrorIssuesApplicationsPB" id="id" title="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="featureissues" titleKey="issuetracking.menu.featureissues" page="/do/guest/menu?id={menuItemId}&application={application}">
        <erland-common:beanmenuitem bean="menuFeatureIssuesApplicationsPB" id="id" title="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="deliveredissues" titleKey="issuetracking.menu.deliveredissues" page="/do/guest/menu?id={menuItemId}&application={application}">
        <erland-common:beanmenuitem bean="menuDeliveredIssuesApplicationsPB" id="id" title="name" page="path" childs="childs"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="closedissues" titleKey="issuetracking.menu.closedissues" page="/do/guest/menu?id={menuItemId}&application={application}">
        <erland-common:beanmenuitem bean="menuClosedIssuesApplicationsPB" id="id" title="name" page="path" childs="childs"/>
    </erland-common:menuitem>
</erland-common:menu>
