<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.dirgallery.account.UserAccount,
                                erland.webapp.dirgallery.account.ViewUserAccountInterface,
                                erland.webapp.dirgallery.gallery.GalleryInterface,
                                erland.webapp.dirgallery.gallery.picture.ViewPictureCommentInterface,
                                erland.webapp.dirgallery.gallery.picture.PictureComment"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewPictureCommentInterface) {
        String comment = ((ViewPictureCommentInterface)cmd).getComment();
        %>
        <form name="editEntry" action="portal" method="POST">
            <input type="hidden" name="do" value="editpicturecomment">
            <input type="hidden" name="gallery" value="<%=request.getParameter("gallery")%>">
            <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
            <table>
            <tr><td>Beskrivning</td><td>
            <textarea class="normal" name="comment" cols="80" rows="5" wrap="virtual"><%=comment!=null?comment:""%></textarea>
            </td></tr>
            <tr><td></td><td>
            <input type="submit" value="Spara">
            <input type="button" value="Avbryt" onClick="window.location='portal?do=home'">
            </td></tr>
            <table>
        </form>
        <%
    }
%>
