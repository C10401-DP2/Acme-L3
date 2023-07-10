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
    <acme:input-textbox code="company.sessionPracticum.form.label.title" path="title"/>
    <acme:input-textbox code="company.sessionPracticum.form.label.overview" path="anAbstract"/>
    <acme:input-moment code="company.sessionPracticum.form.label.startDate" path="initialDate"/>
    <acme:input-moment code="company.sessionPracticum.form.label.endDate" path="finalDate"/>
    <acme:input-checkbox code="company.sessionPracticum.form.label.addendum" path="addendum" readonly="true"/>
    <acme:input-url code="company.sessionPracticum.form.label.moreInfo" path="link"/>
    
    <jstl:if test="${confirmation}">
		<acme:input-checkbox code="company.addendum-session.form.label.accept" path="accept"/>
	</jstl:if>
    
    <jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.sessionPracticum.form.button.update" action="/company/practicum-session/update"/>
			<acme:submit code="company.sessionPracticum.form.button.delete" action="/company/practicum-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create' && draftMode == true}">
			<acme:submit code="company.sessionPracticum.form.button.create" action="/company/practicum-session/create?practicumId=${practicumId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-addendum'}">
			<acme:submit code="company.sessionPracticum.form.button.create" action="/company/practicum-session/create-addendum?practicumId=${practicumId}"/>
		</jstl:when>
					
	</jstl:choose>
</acme:form>