<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<html:form action="/guest/viewsearchpictures" method="POST">
    <html:hidden property="user"/>
    <html:hidden property="guestUser"/>
    <html:hidden property="start"/>
    <html:hidden property="max"/>
    <html:hidden property="gallery"/>
    <table>
    <tr><td><bean:message key="gallery.gallery.search.first-date"/></td><td>
    <html:text property="dateAfterDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.search.last-date"/></td><td>
    <html:text property="dateBeforeDisplay"/>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.search.categories"/></td><td>
    <html:select property="categoriesDisplay" size="10" multiple="true">
        <html:options collection="categoriesPB" property="categoryDisplay" labelProperty="name"/>
    </html:select>
    </td></tr>
    <tr><td><bean:message key="gallery.gallery.search.all-categories"/></td><td>
    <html:checkbox property="allCategories" value="true"/>
    </td></tr>
    <tr><td></td><td>
    <html:submit titleKey="gallery.buttons.search"/>
    </td></tr>
    </table>
</html:form>
