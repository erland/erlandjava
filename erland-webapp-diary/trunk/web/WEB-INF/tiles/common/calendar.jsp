<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://erland.homeip.net/tags/erland-common" prefix="erland-common" %>

<bean:define id="year" name="year" type="Integer"/>
<bean:define id="month" name="month" type="Integer"/>

<table class="calendar-header">
    <tr>
        <td class="calendar-year" id="calendar-year" colspan="2"><erland-common:beanlink style="calendar-year-button" styleSelected="calendar-year-button-selected" name="calendarYearMonthsPB" property="years[0].link" propertySelected="years[0].selected"><bean:write name="calendarYearMonthsPB" property="years[0].key"/></erland-common:beanlink></td>
        <td class="calendar-year" id="calendar-year" colspan="2"><erland-common:beanlink style="calendar-year-button" styleSelected="calendar-year-button-selected" name="calendarYearMonthsPB" property="years[1].link" propertySelected="years[1].selected"><bean:write name="calendarYearMonthsPB" property="years[1].key"/></erland-common:beanlink></td>
        <td class="calendar-year" id="calendar-year" colspan="2"><erland-common:beanlink style="calendar-year-button" styleSelected="calendar-year-button-selected" name="calendarYearMonthsPB" property="years[2].link" propertySelected="years[2].selected"><bean:write name="calendarYearMonthsPB" property="years[2].key"/></erland-common:beanlink></td>
    </tr>
    <tr>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[0].link" propertySelected="months[0].selected"><bean:message name="calendarYearMonthsPB" property="months[0].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[1].link" propertySelected="months[1].selected"><bean:message name="calendarYearMonthsPB" property="months[1].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[2].link" propertySelected="months[2].selected"><bean:message name="calendarYearMonthsPB" property="months[2].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[3].link" propertySelected="months[3].selected"><bean:message name="calendarYearMonthsPB" property="months[3].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[4].link" propertySelected="months[4].selected"><bean:message name="calendarYearMonthsPB" property="months[4].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[5].link" propertySelected="months[5].selected"><bean:message name="calendarYearMonthsPB" property="months[5].key"/></erland-common:beanlink></td>
    </tr>
    <tr>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[6].link" propertySelected="months[6].selected"><bean:message name="calendarYearMonthsPB" property="months[6].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[7].link" propertySelected="months[7].selected"><bean:message name="calendarYearMonthsPB" property="months[7].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[8].link" propertySelected="months[8].selected"><bean:message name="calendarYearMonthsPB" property="months[8].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[9].link" propertySelected="months[9].selected"><bean:message name="calendarYearMonthsPB" property="months[9].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[10].link" propertySelected="months[10].selected"><bean:message name="calendarYearMonthsPB" property="months[10].key"/></erland-common:beanlink></td>
        <td class="calendar-month" id="calendar-month"><erland-common:beanlink style="calendar-month-button" styleSelected="calendar-month-button-selected" name="calendarYearMonthsPB" property="months[11].link" propertySelected="months[11].selected"><bean:message name="calendarYearMonthsPB" property="months[11].key"/></erland-common:beanlink></td>
    </tr>
</table>

<erland-common:beancalendar days="diaryEntriesPB" year="<%=String.valueOf(year)%>" month="<%=String.valueOf(month)%>" dateProperty="date" descriptionProperty="title" linkProperty="viewLink" descriptions="titlestrings" image="/images/{day}s.gif" imageEmpty="/images/{day}.gif" styleCell="calendar-cell" styleTable="calendar-body" styleLink="calendar-cell-link" />

<table class="calendar-description-current-body" width="*">
<tr><td><b><DIV id=descriptions class="calendar-description-current">&nbsp</DIV></b></td></tr>
</table>

<jsp:include page="textchangescript.jsp">
    <jsp:param name="textchangescriptstringlist" value="titlestrings" />
    <jsp:param name="textchangescriptchangetag" value="descriptions"/>
</jsp:include>

<table class="calendar-description-body" width="*">
<logic:iterate name="diaryEntriesPB" id="entry" length="5">
    <tr><td nowrap><p class="calendar-description"><bean:write name="entry" property="dateDisplay"/></p></td><td><p class="calendar-description">&nbsp;</p></td><td nowrap><erland-common:beanlink name="entry" property="viewLink" style="bold-link"><bean:write name="entry" property="title"/></erland-common:beanlink></td></tr>
</logic:iterate>
</table>
