<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.account.UserAccount,
                                erland.webapp.diary.account.ViewUserAccountCommand,
                                erland.webapp.diary.diary.Diary,
                                erland.webapp.diary.account.ViewUserAccountInterface"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountCommand) {
        UserAccount account = ((ViewUserAccountCommand)cmd).getAccount();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="edituseraccount">
            <table>
            <tr><td>Användarnamn</td><td>
            <input type="hidden" name="username" value="<%=account.getUsername()%>"><a class="normal"><%=account.getUsername()%></a>
            </td></tr>
            <tr><td>Beskrivning</td><td>
            <textarea name="description" cols="80" rows="5" wrap="virtual"><%=account!=null?account.getDescription():""%></textarea>
            </td></tr>
            <tr><td>Välkomsttext</td><td>
            <textarea name="welcometext" cols="80" rows="15" wrap="virtual"><%=account!=null?account.getWelcomeText():""%></textarea>
            </td></tr>
            <tr><td>Logo</td><td>
            <input type="text" name="logo" value="<%=account!=null?account.getLogo():""%>">
            </td></tr>
            <tr><td>Standarddagbok</td><td>
            <select name="defaultdiary" size="1">
            <option value="" <%=account.getDefaultDiary()==null||account.getDefaultDiary().intValue()==0?"selected":""%>>Ingen</option>
            <%
            Diary[] diaries = ((ViewUserAccountInterface)cmd).getDiaries();
            for (int i = 0; i < diaries.length; i++) {
                Diary diary = diaries[i];
                %>
                <option value=<%=diary.getId()%> <%=diary.getId().equals(account.getDefaultDiary())?"selected":""%>><%=diary.getTitle()%></option>
                <%
            }
            %>
            </select>
            </td></tr>
            <tr><td>Officiell sida</td><td>
            <input type="checkbox" name="official" value="true" <%=(account!=null && account.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=searchentries'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
