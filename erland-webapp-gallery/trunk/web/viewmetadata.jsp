<%@ page import="erland.webapp.common.CommandInterface,
                 erland.webapp.gallery.loader.ViewMetadataInterface"%>
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
    }
%>
