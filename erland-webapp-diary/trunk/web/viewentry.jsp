<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.ViewEntriesInterface" %>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewEntriesInterface) {
        if(request.getParameter("date")!=null) {
            String diary=request.getParameter("diary");
            %>
            <a href="portal?do=newentry<%=diary!=null?"&diary="+diary:""%>&date=<%=request.getParameter("date")%>" class="bold-link">Uppdatera</a>
            <a href="portal?do=removeentry<%=diary!=null?"&diary="+diary:""%>&date=<%=request.getParameter("date")%>" class="bold-link" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a>
            <br>
            <jsp:include page="viewentryguest.jsp" />
            <%
        }
    }
%>
