<%@ page session="true" import="java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                java.text.NumberFormat,
                                java.text.DecimalFormat,
                                java.util.Iterator,
                                erland.webapp.diagram.DateValueSerieInterface,
                                erland.webapp.diagram.DateValueSerieType,
                                erland.webapp.diagram.DateValueInterface,
                                erland.webapp.stocks.GetStockCommand"%>
<jsp:useBean id="cmd" class="GetStockCommand" scope="request"/>

<% response.setContentType("text/xml");%>
<?xml version="1.0" encoding="ISO-8859-1"?>
<fonder>
    <%  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        NumberFormat numberFormat = new DecimalFormat("0.00");
        Iterator it = cmd.getSeries();
        while(it.hasNext()) {
            DateValueSerieInterface serie = (DateValueSerieInterface) it.next(); %>
            <fond name="<%=serie.getName() %>">
        <%  Iterator it2 = serie.getSerie(DateValueSerieType.get(request.getParameter("type")));
            while(it2.hasNext()) {
                DateValueInterface rate = (DateValueInterface) it2.next(); %>
                <kurs date="<%=dateFormat.format(rate.getDate())%>" kurs="<%=numberFormat.format(rate.getValue())%>"/>
        <%  } %>
            </fond>
    <%  } %>
</fonder>
