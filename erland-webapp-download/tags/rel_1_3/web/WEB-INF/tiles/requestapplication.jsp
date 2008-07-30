<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<SCRIPT TYPE="text/javascript" LANGUAGE=JAVASCRIPT>
function checkEmail() {
    <logic:notEmpty name="applicationFilePB" property="mailingList">
	    if (document.applicationFileFB.emailcheckbox.checked == false &&
            document.applicationFileFB.email.value == "") {

    		alert ('<bean:message key="download.mailinglist.email-checkbox-warning"/>');
    		return false;
        } else {
            if(document.applicationFileFB.email.value != "" && !checkEmailAddress(document.applicationFileFB.email.value)) {
                return false;
            }
            return true;
        }
    </logic:notEmpty>
    <logic:empty name="applicationFilePB" property="mailingList">
        return true;
    </logic:empty>
}

function checkEmailAddress(str) {

		var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1){
		   alert("<bean:message key="download.mailinglist.email-invalid"/>")
		   return false
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
            alert("<bean:message key="download.mailinglist.email-invalid"/>")
		   return false
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
            alert("<bean:message key="download.mailinglist.email-invalid"/>")
		    return false
		}

		 if (str.indexOf(at,(lat+1))!=-1){
            alert("<bean:message key="download.mailinglist.email-invalid"/>")
		    return false
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
            alert("<bean:message key="download.mailinglist.email-invalid"/>")
		    return false
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
            alert("<bean:message key="download.mailinglist.email-invalid"/>")
		    return false
		 }

		 if (str.indexOf(" ")!=-1){
            alert("<bean:message key="download.mailinglist.email-invalid"/>")
		    return false
		 }

 		 return true
}
</script>

<html:form action="/downloadapplication" method="GET" onsubmit="return checkEmail();">
    <html:hidden property="name"/>
    <html:hidden property="filename"/>
    <table class="propertypage-body" align="center">
        <tr><td>
            <p class="propertypage-title"><bean:write name="applicationFilePB" property="applicationTitle"/></p>
        </td></tr>
        <tr><td>
            <bean:message key="download.mailinglist.filename"/>: <bean:write name="applicationFilePB" property="filename"/>
        </td></tr>
        <tr><td>&nbsp;</td></tr>
        <tr><td>
            <erland-common:expandhtml><bean:write name="applicationFilePB" property="requestMessage"/></erland-common:expandhtml>
        </td></tr>
        <logic:notEmpty name="applicationFilePB" property="mailingList">
            <tr><td>&nbsp;</td></tr>
            <tr><td>
            <erland-common:expandhtml><bean:write name="applicationFilePB" property="mailingList"/></erland-common:expandhtml>
            </td></tr>
            <tr><td>
                <bean:message key="download.mailinglist.email"/>:
                <html:text property="email"/>
            </td></tr>
            <tr><td>
                <input type="checkbox" id="emailcheckbox" value="1"/> <bean:message key="download.mailinglist.email-checkbox"/>
            </td></tr>
        </logic:notEmpty>
        <tr><td>&nbsp;</td></tr>
        <tr><td>
            <input type="submit" value="<bean:message key="download.mailinglist.download"/>">
        </td></tr>
    </table>
</html:form>
