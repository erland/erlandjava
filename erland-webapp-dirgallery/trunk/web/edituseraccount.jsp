<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.account.UserAccount,
                                erland.webapp.dirgallery.account.ViewUserAccountInterface,
                                erland.webapp.dirgallery.gallery.GalleryInterface"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewUserAccountInterface) {
        UserAccount account = ((ViewUserAccountInterface)cmd).getAccount();
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
            <tr><td>Copyright text</td><td>
            <input type="text" name="copyright" value="<%=account!=null?account.getCopyrightText():""%>">
            </td></tr>
            <tr><td>Default gallery</td><td>
            <select name="defaultgallery" size="1">
            <option value="" <%=account.getDefaultGallery()==null||account.getDefaultGallery().intValue()==0?"selected":""%>>Ingen</option>
            <%
            GalleryInterface[] galleries = ((ViewUserAccountInterface)cmd).getGalleries();
            for (int i = 0; i < galleries.length; i++) {
                GalleryInterface gallery = galleries[i];
                %>
                <option value=<%=gallery.getId()%> <%=gallery.getId().equals(account.getDefaultGallery())?"selected":""%>><%=gallery.getTitle()%></option>
                <%
            }
            %>
            </select>
            </td></tr>
            <tr><td>Official page</td><td>
            <input type="checkbox" name="official" value="true" <%=(account!=null && account.getOfficial().booleanValue())?"checked":""%>>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Save">
            <input type="button" value="Cancel" onClick="window.location='portal?do=home'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
