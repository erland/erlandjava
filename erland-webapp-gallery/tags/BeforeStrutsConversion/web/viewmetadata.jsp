<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.loader.ViewMetadataInterface,
                 erland.webapp.common.ServletParameterHelper"%>
<%
    CommandInterface cmd = (CommandInterface) request.getAttribute("cmd");
    if(cmd instanceof ViewMetadataInterface) {
        %>
        <table>
        <%
        String[] names = ((ViewMetadataInterface)cmd).getMetadataNames();
        int noOfItems = names.length/2;
        if(noOfItems*2<names.length) {
            noOfItems++;
        }
        for (int i = 0; i < noOfItems; i++) {
            String value1 = ((ViewMetadataInterface)cmd).getMetadataValue(names[i]);
            String description1 =((ViewMetadataInterface)cmd).getMetadataDescription(names[i]);
            String value2 = null;
            String description2 = null;
            if(i+noOfItems<names.length) {
                value2 = ((ViewMetadataInterface)cmd).getMetadataValue(names[i+noOfItems]);
                description2 =((ViewMetadataInterface)cmd).getMetadataDescription(names[i+noOfItems]);
            }
            %>
            <tr><td><%=description1%></td><td>&nbsp:&nbsp</td><td><%=value1%></td><td>&nbsp&nbsp&nbsp&nbsp&nbsp</td><td><%=description2!=null?description2:""%></td><td>&nbsp:&nbsp</td><td><%=value2!=null?value2:""%></td></tr>
            <%
        }
        %>
        </table>
        <%
        String showAll = request.getParameter("showall");
        if(showAll!=null && showAll.equalsIgnoreCase("true")) {
            %>
            <br><a class="bold-link" href="portal?<%=ServletParameterHelper.removeParameter(request.getAttribute("cmdparameters").toString(),"showall")%>">Visa utvald bildinformation</a>
            <%
        }else {
            %>
            <br><a class="bold-link" href="portal?<%=ServletParameterHelper.replaceParameter(request.getAttribute("cmdparameters").toString(),"showall","true")%>">Visa all bildinformation</a>
            <%
        }
        %>
        <%
    }
%>
