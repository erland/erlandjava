<%@ page session="true" import="java.util.Calendar,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 erland.webapp.common.CommandInterface,
                                erland.webapp.diary.diary.ViewEntriesInterface,
                                erland.webapp.diary.diary.DiaryEntryInterface"%>


<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    String user = request.getParameter("user");
    if(user!=null && user.length()==0) {
        user=null;
    }
    String diary = request.getParameter("diary");
    String queryAttributes = "do="+(user!=null?"searchentriesguest":"searchentries")+(user!=null?"&user="+user:"")+(diary!=null?"&diary="+diary:"");
    if(!(cmd instanceof ViewEntriesInterface)) {
        cmd = (CommandInterface)request.getSession().getAttribute("searchentriescmd");
    }
    if(cmd instanceof ViewEntriesInterface) {
        Calendar cal = Calendar.getInstance();
        if(request.getParameter("month")!=null) {
            int month = Integer.valueOf(request.getParameter("month")).intValue();
            cal.set(Calendar.MONTH,month-1);
        }
        int month=cal.get(Calendar.MONTH)+1;
        %>
        <table class="no-border">
            <tr>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=1" class="bold-link<%=month==1?"-selected":""%>">Jan</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=2" class="bold-link<%=month==2?"-selected":""%>">Feb</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=3" class="bold-link<%=month==3?"-selected":""%>">Mar</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=4" class="bold-link<%=month==4?"-selected":""%>">Apr</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=5" class="bold-link<%=month==5?"-selected":""%>">Maj</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=6" class="bold-link<%=month==6?"-selected":""%>">Jun</a></td>
            </tr>
            <tr>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=7" class="bold-link<%=month==7?"-selected":""%>">Jul</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=8" class="bold-link<%=month==8?"-selected":""%>">Aug</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=9" class="bold-link<%=month==9?"-selected":""%>">Sep</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=10" class="bold-link<%=month==10?"-selected":""%>">Okt</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=11" class="bold-link<%=month==11?"-selected":""%>">Nov</a></td>
                <td id="calendar-month"><a href="portal?<%=queryAttributes%>&month=12" class="bold-link<%=month==12?"-selected":""%>">Dec</a></td>
            </tr>
        </table>
        <%
        cal.set(Calendar.DAY_OF_MONTH,1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int start = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(start==0) {
            start+=7;
        }
        String[] titleStrings = new String[maxDay+1];
        for( int i=1;i<=maxDay;i++) {
            cal.set(Calendar.DAY_OF_MONTH,i);
            DiaryEntryInterface entry = ((ViewEntriesInterface)cmd).getEntry(cal.getTime());
            if(entry!=null) {
                titleStrings[i] = entry.getTitle();
            }
        }
        request.setAttribute("titlestrings",titleStrings);
        %>
        <jsp:include page="textchangescript.jsp">
            <jsp:param name="textchangescriptstringlist" value="titlestrings" />
            <jsp:param name="textchangescriptchangetag" value="descriptions"/>
        </jsp:include>

        <table class="no-border-tight">
        <%
            int day=1;
            while(day<=maxDay) {
                    %><tr><%
                    for(int i=1;i<start;i++) {
                            %><td class="no-border-tight"></td><%
                    }
                    for(int weekday=start;weekday<=7&&day<=maxDay;weekday++,day++) {
                            %><td class="no-border-tight"><%
                        if(titleStrings[day]!=null) {
                            cal.set(Calendar.DAY_OF_MONTH,day);
                                %><a href="portal?<%=queryAttributes%><%=request.getParameter("month")!=null?"&month="+request.getParameter("month"):""%>&date=<%=dateFormat.format(cal.getTime())%>" title="<%=titleStrings[day]%>"><%
                        }
                            %><img src="<%=request.getContextPath()%>/<%=day%><%=titleStrings[day]!=null?"s":""%>.gif" border="0" onmouseover="doChangeText(<%=day%>)" onmouseout="doChangeText(0)"></img><%
                        if(titleStrings[day]!=null) {
                                %></a><%
                        }
                            %></td><%
                    }
                    start=1;
                    %></tr><%
            }
        %>
        </table>
        <table class="no-border" width="*">
        <tr><td><b><DIV id=descriptions class="bold">&nbsp</DIV></b></td></tr>
        </table>
    <%
    }
%>