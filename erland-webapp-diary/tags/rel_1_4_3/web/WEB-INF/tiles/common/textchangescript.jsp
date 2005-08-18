<script type="text/javascript">

<!--

function doChangeText(j) {
    changetext(content[j]);
}

var content=new Array()
<%
    String[] strings = (String[])request.getAttribute(request.getParameter("textchangescriptstringlist"));
    for(int i=0;i<strings.length;i++) {
        %>content[<%=i%>]='<%=strings[i]!=null?strings[i]:"&nbsp"%>'
        <%
    }
%>

function changetext(whichcontent){
    if (document.all||document.getElementById) {
        cross_el=document.getElementById? document.getElementById("<%=request.getParameter("textchangescriptchangetag")%>"):document.all.descriptions
        cross_el.innerHTML=whichcontent
    }else if (document.layers){
        document.d1.document.d2.document.write(whichcontent)
        document.d1.document.d2.document.close()
    }
}

// -->
</script>
