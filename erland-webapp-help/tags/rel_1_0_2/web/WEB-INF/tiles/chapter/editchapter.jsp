<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<div class="propertypage-title"><bean:message key="help.chapter.edit.title"/></div>
<tiles:insert page="/WEB-INF/tiles/common/validationerrors.jsp" />
<html:form action="/user/editchapter" method="POST">
    <table class="propertypage-body">
    <logic:notEmpty name="editChapterFB" property="id">
        <html:hidden property="idDisplay"/>
    </logic:notEmpty>
    <html:hidden property="application"/>
    <html:hidden property="version"/>
    <tr><td><bean:message key="help.chapter.edit.chapter"/></td><td>
    <html:text property="chapter"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.edit.orderno"/></td><td>
    <html:text property="orderNoDisplay"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.edit.title-english"/></td><td>
    <html:text property="titleEnglish"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.edit.title-native"/></td><td>
    <html:text property="titleNative"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.edit.header-english"/></td><td>
    <html:textarea property="headerEnglish" cols="60" rows="5" />
    </td></tr>
    <tr><td><bean:message key="help.chapter.edit.header-native"/></td><td>
    <html:textarea property="headerNative" cols="60" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.edit.footer-english"/></td><td>
    <html:textarea property="footerEnglish" cols="60" rows="5"/>
    </td></tr>
    <tr><td><bean:message key="help.chapter.edit.footer-native"/></td><td>
    <html:textarea property="footerNative" cols="60" rows="5"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit><bean:message key="help.buttons.save"/></html:submit>
    </td></tr>
    <table>
</html:form>
