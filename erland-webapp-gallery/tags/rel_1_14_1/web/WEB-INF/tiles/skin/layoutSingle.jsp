<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<tiles:useAttribute name="title" ignore="true"/>
<tiles:useAttribute name="header" ignore="true"/>
<tiles:useAttribute name="body" ignore="true"/>
<tiles:useAttribute name="menu" ignore="true"/>
<tiles:useAttribute name="footer" ignore="true"/>
<tiles:insert beanName="skinPB" beanProperty="layoutSingle">
    <tiles:put name="title" beanName="title"/>
    <tiles:put name="header" beanName="header"/>
    <tiles:put name="body" beanName="body"/>
    <tiles:put name="menu" beanName="menu"/>
    <tiles:put name="footer" beanName="footer"/>
</tiles:insert>
