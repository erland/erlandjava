<%@ page session="true" import="java.util.Iterator,
                                erland.webapp.stocks.BrokersStockEntry,
                                erland.webapp.stocks.StockServletEnvironmentInterface"%>

<%
    String broker = request.getParameter("broker");
    StockServletEnvironmentInterface environment = (StockServletEnvironmentInterface)request.getAttribute("environment");
%>
    <table class="no-border">
    <form name="editForm" action="portal" method="POST">
    <input type="hidden" name="do" value="<%=request.getParameter("addcmd")%>">
    <input type="hidden" name="broker" value="<%=broker%>">
    <tr><td>Aktie</td><td>
    <select name="stock" size="1">
    <%
    Iterator stocks = environment.getBrokers().getStocks(broker);
    while(stocks.hasNext()) {
        BrokersStockEntry stock = (BrokersStockEntry) stocks.next();
    %>
        <option value=<%=stock.getCode()%><% if(stock.getCode().startsWith(request.getParameter("stock")!=null?request.getParameter("stock"):"")) {%> selected<%}%>><%=stock.getName()%></option>
    <%
    }
    %>
    </select>
    </td></tr>
    <tr><td>Datum</td><td>
    <input type="text" name="purchasedate" value="<%=request.getParameter("purchasedate")!=null?request.getParameter("purchasedate"):"2003-01-01"%>">
    <%
    if(request.getParameter("number")!=null) {
        %>
        </td></tr>
        <tr><td>Antal</td><td>
        <input type="text" name="number" value="<%=request.getParameter("number")!=null?request.getParameter("number"):"10"%>">
        <%
    }
    if(request.getParameter("value")!=null) {
        %>
        </td></tr>
        <tr><td>Värde</td><td>
        <input type="text" name="value" value="<%=request.getParameter("value")!=null?request.getParameter("value"):"10"%>">
        <%
    }
    %>
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="Spara">
    </td></tr>
    </form>
    </table>