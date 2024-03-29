<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.course.form.label.code" path="code"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.anAbstract" path="anAbstract"/>
	<acme:input-money code="lecturer.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${_command != 'create'}">
			<acme:input-textbox readonly="true" code="lecturer.course.form.label.courseType" path="courseType"/>
		</jstl:when>
	</jstl:choose>

<jstl:choose>
	<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
		<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
		<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
		<acme:submit test="${canPublish}" code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
		<acme:button code="lecturer.course.form.button.lectures" action="/lecturer/lecture/list?masterId=${id}"/>
	</jstl:when>
	
	
	<jstl:when test="${_command == 'create'}">
		<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
	</jstl:when>
	
</jstl:choose>
	
</acme:form>