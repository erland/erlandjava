<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="help.chapter.attribute.edit.title"/></div>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editchapterattribute" method="POST">
    <table class="propertypage-body">
    <logic:notEmpty name="editChapterAttributeFB" property="id">
        <html:hidden property="idDisplay"/>
    </logic:notEmpty>
    <html:hidden property="application"/>
    <html:hidden property="version"/>
    <html:hidden property="chapter"/>
    <tr><td><bean:message key="help.chapter.attribute.edit.orderno"/></td><td>
    <html:text property="orderNoDisplay"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.attribute.edit.name-english"/></td><td>
    <html:text property="nameEnglish"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.attribute.edit.name-native"/></td><td>
    <html:text property="nameNative"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.attribute.edit.description-english"/></td><td>
    <html:textarea property="descriptionEnglish" cols="60" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.attribute.edit.description-native"/></td><td>
    <html:textarea property="descriptionNative" cols="60" rows="5"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="help.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
