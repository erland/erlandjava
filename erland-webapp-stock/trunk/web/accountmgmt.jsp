<%@ page session="true" import="erland.webapp.stocks.StockServletEnvironmentInterface,
                                erland.webapp.stocks.StockAccountViewInterface,
                                java.text.DateFormat,
                                java.text.SimpleDateFormat,
                                java.util.Iterator,
                                erland.webapp.stocks.StockAccountTransaction,
                                java.util.Vector,
                                erland.webapp.stocks.BrokersStockEntry"%>
<% StockServletEnvironmentInterface environment = (StockServletEnvironmentInterface)request.getAttribute("environment");%>
<%
    StockAccountViewInterface account = environment.getStockAccountFactory().getAccount(request);
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    if(account!=null) {
        %>
        <h3>Permanent entries</h3>
        <table>
        <tr><td>Broker</td><td>Stock</td><td>Date</td><td>Number</td><td>Value</td></tr>
        <%
        Iterator it = account.getPermanentEntries().iterator();
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
                <form action="portal" method="POST">
                    <input type="hidden" name="do" value="stockaccountremovepermanent">
                    <input type="hidden" name="broker" value="<%=entry.getBroker()%>">
                    <input type="hidden" name="stock" value="<%=entry.getStock()%>">
                    <input type="hidden" name="purchasedate" value="<%=dateFormat.format(entry.getDate())%>">
                    <input type="submit" value="delete">
                </form>
            </td>
            </tr>
<%      } %>
        </table>
        <h3>One time purchase entries</h3>
        <table>
        <tr><td>Broker</td><td>Stock</td><td>Date</td><td>Number</td><td>Value</td></tr>
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
                <form action="portal" method="POST">
                    <input type="hidden" name="do" value="stockaccountremovesingle">
                    <input type="hidden" name="broker" value="<%=entry.getBroker()%>">
                    <input type="hidden" name="stock" value="<%=entry.getStock()%>">
                    <input type="hidden" name="purchasedate" value="<%=dateFormat.format(entry.getDate())%>">
                    <input type="submit" value="delete">
                </form>
            </td>
            </tr>
<%      } %>
        </table>
        <h3>Continously purchase entries</h3>
        <table>
        <tr><td>Broker</td><td>Stock</td><td>Date</td><td>Number</td><td>Value</td></tr>
        <%
            it = account.getPurchaseContinouslyEntries().iterator();
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
                <form action="portal" method="POST">
                    <input type="hidden" name="do" value="stockaccountremovemultiple">
                    <input type="hidden" name="broker" value="<%=entry.getBroker()%>">
                    <input type="hidden" name="stock" value="<%=entry.getStock()%>">
                    <input type="hidden" name="purchasedate" value="<%=dateFormat.format(entry.getDate())%>">
                    <input type="submit" value="delete">
                </form>
            </td>
            </tr>
<%      } %>
        </table>
<%  } %>
<br>
<%
    Vector types = new Vector();
    types.addElement("stockaccountaddsingle");
    types.addElement("stockaccountaddsingle");
    types.addElement("stockaccountaddsingle");
    types.addElement("stockaccountaddmultiple");
    types.addElement("stockaccountaddpermanent");
    types.addElement("stockaccountaddpermanent");

    Iterator brokers = environment.getBrokers().getBrokers();
    while(brokers.hasNext()) {
        int i=0;
        String brokerCode = (String) brokers.next();
%>      <h1><%=environment.getBrokers().getBrokerName(brokerCode)%></h1>
<%      Iterator it = types.iterator();
        while(it.hasNext()) {
            String type = (String) it.next();
            %>
            <form name="<%=brokerCode+type%>Form" action="portal" method="POST">
            <input type="hidden" name="do" value="<%=type%>">
            <input type="hidden" name="broker" value="<%=brokerCode%>">
            <select name="stock" size="1">
            <%
            Iterator stocks = environment.getBrokers().getStocks(brokerCode);
            while(stocks.hasNext()) {
                BrokersStockEntry stock = (BrokersStockEntry) stocks.next();
            %>
                <option value=<%=stock.getCode()%><% if(stock.getCode().startsWith(request.getParameter("stock")!=null?request.getParameter("stock"):"")) {%> selected<%}%>><%=stock.getName()%></option>
            <%
            }
            %>
            </select>
            <input type="text" name="purchasedate" value="<%=request.getParameter("purchasedate")!=null?request.getParameter("purchasedate"):"2003-01-01"%>">
            <%
            if(type.equals("stockaccountaddpermanent") || (type.equals("stockaccountaddsingle")&&i<2)) {
            %>
                <input type="text" name="number" value="<%=request.getParameter("number")!=null?request.getParameter("number"):"10"%>">
            <%
            }
            if(type.equals("stockaccountaddsingle") || type.equals("stockaccountaddpermanent")) {
                i++;
                if(i>=2 && i<=4) {
            %>
                <input type="text" name="value" value="<%=request.getParameter("value")!=null?request.getParameter("value"):"10"%>">
            <%
                }
            }else if(type.equals("stockaccountaddmultiple")) {
            %>
                <input type="text" name="value" value="<%=request.getParameter("value")!=null?request.getParameter("value"):"10"%>">
            <%
            }
            String buttonText = "";
            if(type.equals("stockaccountaddsingle")) {
                if(i==1) {
                    buttonText = "Add single (number of stocks)";
                }else if(i==2) {
                    buttonText = "Add single (number of stocks for price)";
                }else {
                    buttonText = "Add single (stocks for value)";
                }
            }else if(type.equals("stockaccountaddmultiple")) {
                buttonText = "Add multiple (stocks for value)";
            }else {
                if(i==4) {
                    buttonText = "Add permanent (number of stocks for price)";
                }else {
                    buttonText = "Add permanent (number of stocks)";
                }
            }
            %>
            <input type="submit" value="<%=buttonText%>">
            </form>
    <%
        }
    }
%>
