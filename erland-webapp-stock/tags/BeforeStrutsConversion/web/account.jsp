<%@ page session="true" import="erland.webapp.stocks.StockServletEnvironmentInterface,
                                erland.webapp.common.CommandInterface,
                                erland.webapp.stocks.StockAccountCommand,
                                java.util.Iterator,
                                erland.webapp.stocks.StockAccountStockEntry,
                                erland.webapp.stocks.StockAccountStockEntryListInterface,
                                erland.webapp.diagram.DateValueSeriesContainerInterface,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                erland.webapp.diagram.DateValueSerieInterface,
                                erland.webapp.diagram.DateValueInterface,
                                java.util.Date,
                                java.text.DecimalFormat,
                                java.text.NumberFormat" %>
<% StockServletEnvironmentInterface environment = (StockServletEnvironmentInterface)request.getAttribute("environment");%>
<%
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = request.getParameter("date")!=null?request.getParameter("date"):dateFormat.format(new Date());
    String purchaseDate = request.getParameter("purchasedate")!=null?request.getParameter("purchasedate"):"2003-01-01";
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof StockAccountCommand) {
        StockAccountCommand account = (StockAccountCommand) cmd;
        NumberFormat numberFormat = new DecimalFormat("#.##");
        %>
        <p>Värdet <%=request.getParameter("date")%> är <%=numberFormat.format(account.getStockValue())%> kr som har köpts in för <%=numberFormat.format(account.getPurchaseValue())%> kr</p>
        <br>
        <%
        String broker = request.getParameter("broker");
        String stock = request.getParameter("stock");
        String type = request.getParameter("type");
        String seriesincluded = request.getParameter("seriesincluded");
        if(type==null) {
            type="";
        }
        if(broker!=null && stock!=null) {
            %>
            <img src="portal?do=datevaluediagram&dataproducer=stockaccount&broker=<%=broker%>&stock=<%=stock%>&date=<%=date%>&purchasedate=<%=purchaseDate%>&purchasevalues=inköp"></img>
            <%
        }else if(broker!=null) {
            %>
            <img src="portal?do=datevaluediagram&dataproducer=stockaccount&broker=<%=broker%>&date=<%=date%>&purchasedate=<%=purchaseDate%>&purchasevalues=inköp&seriesincluded=<%=seriesincluded%>"></img>
            <%
        }else {
            %>
            <img src="portal?do=datevaluediagram&dataproducer=stockaccount&date=<%=date%>&purchasedate=<%=purchaseDate%>&purchasevalues=inköp&seriesincluded=<%=seriesincluded%>"></img>
            <%
        }

        StockAccountStockEntryListInterface stockList = account.getStocks();
        %>
        <br>
        <table class="no-border">
        <%
        for(int i=0;i<stockList.size();i++) {
            StockAccountStockEntry entry = stockList.getStock(i);
            if(broker==null||entry.getBroker().equals(broker)) {
                %>
                <tr><td nowrap>
                <a class="bold-link" href="portal?do=stockaccount&broker=<%=entry.getBroker()%>&stock=<%=entry.getStock()%>&purchasedate=<%=purchaseDate%>&date=<%=date%>&purchasevalues=inköp&stocknumbers=noofstocks&diffvalues=difference"><%=purchaseDate%> - <%=date%> : <%=environment.getBrokers().getBrokerName(entry.getBroker())%> <%=environment.getBrokers().getStockName(entry.getBroker(),entry.getStock())%></a>
                </td></tr>
                <%
            }
        }
        %>
        </table>
        <%
    }
%>
<br>

<table class="no-border">
<form name="showAccount" action="portal" method="POST">
        <tr><td>Första datum</td><td>
        <input type="text" name="purchasedate" value="<%=purchaseDate%>">
        </td></tr>
        <tr><td>Sista datum</td><td>
        <input type="text" name="date" value="<%=date%>">
        </td></tr>
        <tr><td colspan="2" nowrap>
        <a href="javascript: window.location='portal?do=stockaccount&date='+showAccount.date.value+'&purchasedate='+showAccount.purchasedate.value+'&purchasevalues=inköp&stocknumbers=noofstocks&diffvalues=difference&seriesincluded=all'" class="bold-link">Visa alla</a>
        </td></tr>
        <tr><td colspan="2" nowrap>
        <a href="javascript: window.location='portal?do=stockaccount&date='+showAccount.date.value+'&purchasedate='+showAccount.purchasedate.value+'&purchasevalues=inköp&seriesincluded=total'" class="bold-link">Visa total</a>
        </td></tr>
        <%
        Iterator brokers = environment.getBrokers().getBrokers();
        while(brokers.hasNext()) {
            String brokerCode = (String) brokers.next();
            %>
            <tr><td colspan="2" nowrap>
            <a href="javascript: window.location='portal?do=stockaccount&date='+showAccount.date.value+'&purchasedate='+showAccount.purchasedate.value+'&purchasevalues=inköp&stocknumbers=noofstocks&diffvalues=difference&seriesincluded=all&broker=<%=brokerCode%>'" class="bold-link">Visa alla <%=environment.getBrokers().getBrokerName(brokerCode)%></a>
            </td></tr>
            <tr><td colspan="2" nowrap>
            <a href="javascript: window.location='portal?do=stockaccount&date='+showAccount.date.value+'&purchasedate='+showAccount.purchasedate.value+'&purchasevalues=inköp&seriesincluded=total&broker=<%=brokerCode%>'" class="bold-link">Visa total <%=environment.getBrokers().getBrokerName(brokerCode)%></a>
            </td></tr>
            <%
        }
        %>
</form>
</table>
<jsp:include page="showdiagramvalues.jsp"/>