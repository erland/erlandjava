<%@ page session="true" import="erland.webapp.stocks.StockServletEnvironmentInterface,
                                java.util.Iterator,
                                erland.webapp.stocks.BrokersStockEntry" %>
<% StockServletEnvironmentInterface environment = (StockServletEnvironmentInterface)request.getAttribute("environment");%>
<%  String broker = request.getParameter("broker");
    String stocklist = request.getParameter("stocks");
    String type = request.getParameter("type");
    if(type==null) {
        type="";
    }
    if(broker!=null && stocklist!=null) { %>
        <img src="portal?do=datevaluediagram&dataproducer=xmlkurser&broker=<%=broker%>&stocks=<%=stocklist%>&type=<%=type%>"></img>
<%  }%>
<br>

<%
    Iterator brokers = environment.getBrokers().getBrokers();
    while(brokers.hasNext()) {
        String brokerCode = (String) brokers.next();
%>      <h3><%=environment.getBrokers().getBrokerName(brokerCode)%></h3>
        <form name="<%=brokerCode%>Form" action="portal" method="POST">
        <select name="stocks" size="1">
        <%
        Iterator stocks = environment.getBrokers().getStocks(brokerCode);
        while(stocks.hasNext()) {
            BrokersStockEntry stock = (BrokersStockEntry) stocks.next();
        %>
            <option value=<%=stock.getCode()%><% if(stock.getCode().startsWith(request.getParameter("stocks")!=null?request.getParameter("stocks"):"")) {%> selected<%}%>><%=stock.getName()%></option>
        <%
        }
        %>
        </select>
        <a class="bold-link" href="javascript: window.location='portal?do=datevaluediagramservices&broker=<%=brokerCode%>&stocks='+<%=brokerCode%>Form.stocks.value">Diagram</a>
        </form>
<%
    }
%>
