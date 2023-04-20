<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textarea code="assistant.tutorial-session.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.tutorial-session.form.label.abstract" path="anAbstract"/>
	<acme:input-select path="type" code="assistant.tutorial-session.form.label.type" choices="${types}"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.initial-date" path="initialDate"/>
	<acme:input-moment code="assistant.tutorial-session.form.label.final-date" path="finalDate"/>
	<acme:input-url code="assistant.tutorial-session.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="assistant.tutorial-session.form.button.update" action="/assistant/tutorial-session/update"/>
			<acme:submit code="assistant.tutorial-session.form.button.delete" action="/assistant/tutorial-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorial-session.form.button.create" action="/assistant/tutorial-session/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>

