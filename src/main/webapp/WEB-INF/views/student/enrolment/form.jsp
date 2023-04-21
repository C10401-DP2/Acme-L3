<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
	<acme:input-textarea code="student.enrolment.form.label.motivation" path="motivation"/>	
	<acme:input-textarea code="student.enrolment.form.label.goals" path="goals"/>
	<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textarea code="student.enrolment.form.label.creditCardNumber" path="creditCardNumber"/>	
	<acme:input-textarea code="student.enrolment.form.label.holder" path="holder"/>
	<acme:hidden-data path="draftMode"/>
	<acme:hidden-data path="id"/>
	<acme:hidden-data path="totalTime"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show'}">
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:submit code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise"/>
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>