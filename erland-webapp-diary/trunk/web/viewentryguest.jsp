<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.DiaryEntryInterface,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.diary.HTMLEncoder,
                                erland.webapp.diary.diary.ViewEntryInterface,
                                erland.webapp.usermgmt.User"%>

<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if(cmd instanceof ViewEntryInterface) {
        DiaryEntryInterface entry = ((ViewEntryInterface)cmd).getEntry();
        if(entry!=null) {
            %>

            <table>
            <tr>
                <td><p class="title"><%=dateFormat.format(entry.getDate())%> <%=entry.getTitle()%></p></td>
            </tr>
            <tr>
                <td><font class="normal"><%=HTMLEncoder.encode(entry.getDescription())%></font></td>
            </tr>
            </table>

            <%
        }
    }
%>
