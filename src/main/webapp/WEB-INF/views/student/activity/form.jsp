<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="student.activities.form.label.title" path="title"/>
	<acme:input-select code="student.activities.form.label.aType" path="aType" choices="${activities}"/>
	<acme:input-textarea code="student.activities.form.label.abstrat" path="abstrat"/>		
	<acme:input-moment code="student.activities.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="student.activities.form.label.finalDate" path="finalDate"/>
	<acme:input-url code="student.activities.form.label.link" path="link"/>
	
	<acme:hidden-data path="id"/>
	<acme:hidden-data path="draftMode"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="student.activities.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activities.form.button.delete" action="/student/activity/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.activities.form.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>