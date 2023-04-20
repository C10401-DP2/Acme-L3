<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.course.list.label.title" path="title" width="50%"/>
	<acme:list-column code="lecturer.course.list.label.retailPrice" path="retailPrice" width="50%"/>
</acme:list>

<acme:button code="lecturer.course.form.button.create" action="/lecturer/course/create"/>