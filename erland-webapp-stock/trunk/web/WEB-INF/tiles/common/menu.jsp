<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<erland-common:menu id="mainMenu" menuStyle="menu" style="menuitem" styleSelected="menuitem-selected" indentWidth="10" indentImage="/images/transparent.gif" >
    <erland-common:menuitem id="brokerstocks" titleKey="stock.menu.brokerstocks" page="/do/viewbrokerstocks" />
    <erland-common:menuitem id="stocks" titleKey="stock.menu.stocks" page="/do/expandcollapsemenu?menuName={menuId}&menuItemId={menuItemId}">
        <erland-common:menuitem id="permanent" titleKey="stock.menu.permanent" page="/do/viewaccountpermanententries"/>
        <erland-common:menuitem id="purchaseonce" titleKey="stock.menu.purchaseonce" page="/do/viewaccountpurchaseonceentries"/>
        <erland-common:menuitem id="continously" titleKey="stock.menu.continously" page="/do/viewaccountcontinouslyentries"/>
    </erland-common:menuitem>
    <erland-common:menuitem id="users" titleKey="stock.menu.diagrams" page="/do/viewaccountdiagramwithoutdiagram" />
    <erland-common:menuitem id="logout" titleKey="stock.menu.logout" page="/do/logout" />
</erland-common:menu>
