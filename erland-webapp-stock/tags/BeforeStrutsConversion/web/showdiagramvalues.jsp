<%@ page session="true" import="erland.webapp.diagram.DateValueSeriesContainerInterface,
                 java.util.Iterator,
                 erland.webapp.diagram.DateValueSerieInterface,
                 erland.webapp.diagram.DateValueInterface,
                 java.text.SimpleDateFormat,
                 java.text.DateFormat,
                 erland.webapp.common.CommandInterface"%>
<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    String showValues = request.getParameter("showvalues");
    if(showValues!=null && showValues.length()>0) {
        %>
        <br>
        <a class="bold-link" href="portal?<%=request.getQueryString()!=null?request.getQueryString()+"&showvalues=":""%>">Göm värden</a>
        <br>
        <%
        if(cmd instanceof DateValueSeriesContainerInterface) {
            DateValueSeriesContainerInterface valueSeries = (DateValueSeriesContainerInterface) cmd;
            Iterator series = valueSeries.getSeries();
            while(series.hasNext()) {
                DateValueSerieInterface serie = (DateValueSerieInterface) series.next();
                Iterator it = serie.getSerie(null);
                %>
                <p><%=serie.getName()%>
                <table>
                <%
                while(it.hasNext()) {
                    DateValueInterface dateValue = (DateValueInterface) it.next();
                    %>
                    <tr><td><%=dateFormat.format(dateValue.getDate())%></td><td><%=dateValue.getValue()%></td></tr>
                    <%
                }
                %>
                </table>
                <%
            }
        }
    }else {
        %>
        <br>
        <a class="bold-link" href="portal?<%=request.getQueryString()!=null?request.getQueryString()+"&showvalues=true":""%>">Visa värden</a>
        <br>
        <%
    }
%>
