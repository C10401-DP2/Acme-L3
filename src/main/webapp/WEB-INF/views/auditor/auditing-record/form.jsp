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
	<acme:input-textbox code="auditor.auditing-record.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditing-record.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditing-record.form.label.initialDate" path="initialDate"/>
	<acme:input-moment code="auditor.auditing-record.form.label.finalDate" path="finalDate"/>
	<acme:input-select code="auditor.auditing-record.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-url code="auditor.auditing-record.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditing-record.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditing-record.form.button.delete" action="/auditor/auditing-record/delete"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:hidden-data path="correction"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:hidden-data path="correction"/>
			<acme:submit code="auditor.auditing-record.form.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-correction'}">
			<acme:input-select code="auditor.auditing-record.form.label.correction" path="correction" choices="${corrections}"/>
			<acme:submit code="auditor.auditing-record.form.button.correction" action="/auditor/auditing-record/create-correction?masterId=${masterId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>