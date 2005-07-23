<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>
<categories>
    <logic:iterate id="category" name="categoriesPB">
        <category>
            <id><bean:write name="category" property="categoryDisplay"/></id>
            <title><bean:write name="category" property="name"/></title>
        </category>
    </logic:iterate>
</categories>
