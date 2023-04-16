<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title"/>
	<acme:list-column code="lecturer.lecture.list.label.learningTime" path="learningTime"/>
	<jstl:choose>
		<jstl:when test="${!acme:anyOf(_command, 'list-mine')}">
			<acme:list-column code="lecturer.lecture.list.label.lecturer" path="lecturer"/>
		</jstl:when>
	</jstl:choose>
</acme:list>
<acme:button code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create"/>
