<%@ page session="true" import="erland.webapp.stocks.StockServletEnvironmentInterface,
                 erland.webapp.stocks.StockAccountViewInterface,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                                java.util.Iterator,
                                erland.webapp.stocks.StockAccountTransaction"%>
<% StockServletEnvironmentInterface environment = (StockServletEnvironmentInterface)request.getAttribute("environment");%>
<%
    StockAccountViewInterface account = environment.getStockAccountFactory().getAccount(request);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if(account!=null) {
        Iterator it = environment.getBrokers().getBrokers();
        %>
        <table class="no-border">
        <%
        while(it.hasNext()) {
            String broker = (String) it.next();
            String brokerString = environment.getBrokers().getBrokerName(broker);
            %>
            <tr><td><a class="bold-link" href="portal?do=stockaccountnewsingle&number=&broker=<%=broker%>&addcmd=stockaccountaddsingle"><%=brokerString%> : Lägg till (antal aktier)</a></td></tr>
            <tr><td><a class="bold-link" href="portal?do=stockaccountnewsingle&number=&value=&broker=<%=broker%>&addcmd=stockaccountaddsingle"><%=brokerString%> : Lägg till (antal aktier för värde)</a></td></tr>
            <tr><td><a class="bold-link" href="portal?do=stockaccountnewsingle&value=&broker=<%=broker%>&addcmd=stockaccountaddsingle"><%=brokerString%> : Lägg till (för värde)</a></td></tr>
            <%
        }
        %>
        </table>
        <table class="no-border">
        <tr><td><p class="bold">Mäklare</p></td><td><p class="bold">Aktie</p></td><td><p class="bold">Datum</p></td><td><p class="bold">Antal</p></td><td><p class="bold">Värde</p></td></tr>
        <%
        it = account.getPurchaseOnceEntries().iterator();
        while(it.hasNext()) {
            StockAccountTransaction entry = (StockAccountTransaction) it.next();
            %>
            <tr>
            <td><%=environment.getBrokers().getBrokerName(entry.getBroker())%></td>
            <td><%=environment.getBrokers().getStockName(entry.getBroker(),entry.getStock())%></td>
            <td><%=dateFormat.format(entry.getDate())%></td>
            <td><%=entry.getNumber()!=0?""+entry.getNumber():""%></td>
            <td><%=entry.getValue()!=0?""+entry.getValue():""%></td>
            <td>
            <a class="bold-link" href="portal?do=stockaccountremovesingle&broker=<%=entry.getBroker()%>&stock=<%=entry.getStock()%>&purchasedate=<%=dateFormat.format(entry.getDate())%>" onClick="return confirm('Är du säker på att du vill ta bort denna ?')">Ta bort</a>
            </td>
            </tr>
<%      } %>
        </table>
        <%
    }
%>