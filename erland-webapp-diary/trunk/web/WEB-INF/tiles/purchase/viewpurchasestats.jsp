<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<p class="title"><bean:message key="diary.purchase.stats.stores"/></p>
<table border="0">
    <logic:iterate name="purchaseStoresEntriesPB" id="entry">
        <tr>
        <td nowrap><p class="normal"><bean:write name="entry" property="group"/></p></td>
        <td nowrap><p class="normal">&nbsp&nbsp&nbsp</p></td>
        <td align="right" nowrap><p class="normal"><bean:write name="entry" property="priceDisplay"/> kr</p></td>
        </tr>
    </logic:iterate>
</table>

<p class="title"><bean:message key="diary.purchase.stats.categories"/></p>
<table border="0">
    <logic:iterate name="purchaseCategoriesEntriesPB" id="entry">
        <tr>
        <td nowrap><p class="normal"><bean:write name="entry" property="group"/></p></td>
        <td nowrap><p class="normal">&nbsp&nbsp&nbsp</p></td>
        <td align="right" nowrap><p class="normal"><bean:write name="entry" property="priceDisplay"/> kr</p></td>
        </tr>
    </logic:iterate>
</table>

<p class="title"><bean:message key="diary.purchase.stats.months"/></p>
<table border="0">
    <logic:iterate name="purchaseMonthsEntriesPB" id="entry">
        <tr>
        <td nowrap><p class="normal"><bean:write name="entry" property="group"/></p></td>
        <td nowrap><p class="normal">&nbsp&nbsp&nbsp</p></td>
        <td align="right" nowrap><p class="normal"><bean:write name="entry" property="priceDisplay"/> kr</p></td>
        </tr>
    </logic:iterate>
</table>
