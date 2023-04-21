
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-textbox code="company.session.form.label.title" path="title"/>
	<acme:input-textarea code="company.session.form.label.abstraction" path="anAbstract"/>
	<acme:input-moment code="company.session.form.label.startDate" path="initialDate" />
	<acme:input-moment code="company.session.form.label.finishDate" path="finalDate"/>
	<acme:input-textbox code="company.session.form.label.link" path="link"/>
	
	<acme:submit test="${_command == 'create'}" code="company.practicum.button.create" action="/company/practicum-session/create"/>
	
	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="company.practicum.session.button.update" action="/company/practicum-session/update"/>
		<acme:submit code="company.practicum.button.delete" action="/company/practicum-session/delete"/>		
		<acme:submit code="company.practicum.button.publish" action="/company/practicum-session/publish"/>	
	</jstl:if>
</acme:form>