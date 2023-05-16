<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-select code="lecturer.courseLecture.form.label.lecture"
		path="lecture" choices="${lectures}" />		
<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.courseLecture.form.button.create"
				action="/lecturer/course-lecture/create?masterId=${masterId}" />
		</jstl:when>
		<jstl:when test="${_command == 'delete-lecture'}">
			<acme:submit code="lecturer.courseLecture.form.button.delete"
				action="/lecturer/course-lecture/delete-lecture?masterId=${masterId }" />
		</jstl:when>
</jstl:choose>

</acme:form>