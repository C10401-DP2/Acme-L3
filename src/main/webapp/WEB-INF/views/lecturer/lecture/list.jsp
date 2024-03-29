<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title"/>
	<acme:list-column code="lecturer.lecture.list.label.learningTime" path="learningTime"/>
	<acme:list-column code="lecturer.lecture.list.label.activityType" path="activityType"/>
</acme:list>
<jstl:choose>
	<jstl:when test="${!acme:anyOf(_command, 'list-mine')}">
		<acme:button test="${showAddLecture}" code="lecturer.courseLecture.list.button.create" action="/lecturer/course-lecture/create?masterId=${masterId}"/>
		<acme:button test="${showAddLecture}" code="lecturer.courseLecture.list.button.delete" action="/lecturer/course-lecture/delete-lecture?masterId=${masterId}"/>
	</jstl:when>
</jstl:choose>
<acme:button test="${showAddLecture}" code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create"/>
