<%@ page session="true" import="java.util.Date,
                 java.text.SimpleDateFormat,
                 java.text.DateFormat,
                 erland.webapp.usermgmt.User,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.ViewEntriesInterface,
                                erland.webapp.diary.appendix.ViewAppendixEntriesInterface,
                                erland.webapp.diary.purchase.ViewPurchaseEntriesInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntriesInterface,
                                erland.webapp.diary.diary.ViewDiariesInterface,
                                erland.webapp.diary.gallery.ViewGalleriesInterface"%>

<jsp:useBean id="user" scope="session" class="User" />

<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    CommandInterface originalCmd = cmd;
%>
<table class="no-border">
<tr><td class="left-margin"></td>
<td colspan="3"><jsp:include page="menudiary.jsp"/></td>
</tr>
<%
    if(!(cmd instanceof ViewEntriesInterface)) {
        cmd = (CommandInterface)request.getSession().getAttribute("searchentriescmd");
    }
    if(cmd instanceof ViewEntriesInterface) {
        String diary = request.getParameter("diary");
        %>
        <tr><td class="left-margin"></td><td colspan="3">&nbsp</td></tr>
        <tr>
        <td class="left-margin"></td>
        <form name="newEntry" action="portal" method="POST">
            <td>
            <input type="text" name="date" value="<%=dateFormat.format(new Date())%>">
            </td>
            <td>
            <a href="javascript: window.location='portal?do=newentry<%=diary!=null?"&diary="+diary:""%>&date='+newEntry.date.value" class="bold-link">Lägg till</a>
            </td>
        </form>
        </tr>
        <%
    }
%>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchpurchaseentries" class="bold-link">Inköpslista</a>
    </td>
    </tr>
    <%
        if(originalCmd instanceof ViewPurchaseEntriesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td colspan="3"><jsp:include page="menupurchase.jsp" /></td>
            </tr>
            <%
        }
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchactiveinventoryentries" class="bold-link">Fiskar och Växter</a>
    </td>
    </tr>
    <%
        if(originalCmd instanceof ViewInventoryEntriesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td colspan="3"><jsp:include page="menuinventory.jsp" /></td>
            </tr>
            <%
        }
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchgalleries" class="bold-link">Bildarkiv</a>
    </td>
    </tr>
    <%
        if(originalCmd instanceof ViewGalleriesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td colspan="3"><jsp:include page="menugalleries.jsp" /></td>
            </tr>
            <%
        }
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=searchdiaries" class="bold-link">Dagböcker</a>
    </td>
    </tr>
    <%
        if(originalCmd instanceof ViewDiariesInterface) {
            %>
            <tr>
            <td class="left-margin"></td>
            <td colspan="3"><jsp:include page="menudiaries.jsp" /></td>
            </tr>
            <%
        }
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3">
    <a href="portal?do=viewuseraccount" class="bold-link">Inställningar</a>
    </td>
    </tr>
    <%
    if(user.hasRole("manager")) {
        %>
        <tr>
        <td class="left-margin"></td>
        <td colspan="3">
        <a href="portal?do=searchuseraccounts" class="bold-link">Användare</a>
        </td>
        </tr>
        <tr>
        <td class="left-margin"></td>
        <td colspan="3">
        <a href="portal?do=searchappendixentries" class="bold-link">Appendix</a>
        </td>
        </tr>
        <%
            if(originalCmd instanceof ViewAppendixEntriesInterface) {
                %>
                <tr>
                <td class="left-margin"></td>
                <td colspan="3"><jsp:include page="menuappendix.jsp" /></td>
                </tr>
                <%
            }
        %>
        <%
    }
    %>
    <tr>
    <td class="left-margin"></td>
    <td colspan="3"><jsp:include page="menulogout.jsp"/></td>
    </tr>
</table>
</form>
