<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.course.list.label.code" path="code"  width="30%"/>
	<acme:list-column code="any.course.list.label.title" path="title" width="30%" />
	<acme:list-column code="any.course.list.label.retailPrice" path="retailPrice" width="20%" />
	<acme:list-column code="any.course.list.label.retailPriceSystem" path="default" width="20%" />
	<acme:list-column code="any.course.list.label.link" path="link" width="20%" />
</acme:list>