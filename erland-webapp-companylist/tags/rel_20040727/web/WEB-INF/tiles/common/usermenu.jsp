<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="no-border" style="bold-link" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="listcompanies" titleKey="companylist.menu.listmycompanies" page="/do/user/listcompanies" roles="user"/>
    <erland-common:menuitem id="managerlistcompanies" titleKey="companylist.menu.listcompanies" page="/do/manager/listcompanies" roles="manager"/>
    <erland-common:menuitem id="registercompany" titleKey="companylist.menu.registercompany" page="/do/user/registercompany" roles="user"/>
    <erland-common:menuitem id="logout" titleKey="companylist.menu.logout" page="/do/logout" roles="user"/>
</erland-common:menu>
