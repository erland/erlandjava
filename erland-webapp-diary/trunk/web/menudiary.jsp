<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                java.text.SimpleDateFormat,
                                java.text.DateFormat,
                                erland.webapp.diary.diary.DiaryEntryInterface,
                                java.util.Calendar,
                                java.util.Date,
                                erland.webapp.diary.diary.ViewEntriesInterface,
                                erland.webapp.diary.inventory.ViewInventoryEntriesInterface"%>
<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    String user = request.getParameter("user");
    if(user!=null && user.length()==0) {
        user=null;
    }
    String diary = request.getParameter("diary");
    String month = request.getParameter("month");
    String queryAttributes = "do="+(user!=null?"searchentriesguest":"searchentries")+(user!=null?"&user="+user:"")+(diary!=null?"&diary="+diary:"")+(month!=null?"&month="+month:"");
    if(!(cmd instanceof ViewEntriesInterface)) {
        cmd = (CommandInterface)request.getSession().getAttribute("searchentriescmd");
    }
    if(cmd instanceof ViewEntriesInterface) {
        %>
        <table class="no-border">
        <tr>
        <td colspan="3">
        <jsp:include page="calendar.jsp"/>
        </td>
        </tr>
        <%
        DiaryEntryInterface[] entries = ((ViewEntriesInterface)cmd).getEntries();
        for(int i=0;i<entries.length&&i<5;i++) {
            %>
            <tr>
            <td nowrap><p class="normal"><%=dateFormat.format(entries[i].getDate())%></p></td>
            <td><p class="normal">&nbsp</p></td>
            <td nowrap>
            <a class="bold-link" href="portal?<%=queryAttributes%>&date=<%=dateFormat.format(entries[i].getDate())%>">
                <%=entries[i].getTitle()%>
            </a>
            </td>
            </tr>
            <%
        }
        %>
        </table>
    <%
    }
%>