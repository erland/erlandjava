<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="no-border" style="bold-link" styleSelected="bold-link-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="newissue" titleKey="issuetracking.menu.newissue" page="/do/guest/menu?id={menuItemId}&application={application}"/>
    <erland-common:menuitem id="all" titleKey="issuetracking.menu.all" page="/do/guest/menu?id={menuItemId}&application={application}"/>
    <erland-common:menuitem id="problem" titleKey="issuetracking.menu.problem" page="/do/guest/menu?id={menuItemId}&application={application}">
        <erland-common:menuitem id="new" titleKey="issuetracking.menu.new" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="analyzing" titleKey="issuetracking.menu.analyzing" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="correcting" titleKey="issuetracking.menu.correcting" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="testing" titleKey="issuetracking.menu.testing" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="rejected" titleKey="issuetracking.menu.rejected" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="delivered" titleKey="issuetracking.menu.delivered" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="waiting" titleKey="issuetracking.menu.waiting" page="/do/guest/menu?id={menuItemId}&application={application}"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="feature" titleKey="issuetracking.menu.feature" page="/do/guest/menu?id={menuItemId}&application={application}">
        <erland-common:menuitem id="new" titleKey="issuetracking.menu.new" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="analyzing" titleKey="issuetracking.menu.analyzing" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="correcting" titleKey="issuetracking.menu.correcting" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="testing" titleKey="issuetracking.menu.testing" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="rejected" titleKey="issuetracking.menu.rejected" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="delivered" titleKey="issuetracking.menu.delivered" page="/do/guest/menu?id={menuItemId}&application={application}"/>
        <erland-common:menuitem id="waiting" titleKey="issuetracking.menu.waiting" page="/do/guest/menu?id={menuItemId}&application={application}"/>
    </erland-common:menuitem>
</erland-common:menu>
