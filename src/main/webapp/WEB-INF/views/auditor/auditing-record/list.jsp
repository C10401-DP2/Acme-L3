<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="auditor.auditing-record.list.label.subject" path="subject" width="30%"/>	
	<acme:list-column code="auditor.auditing-record.list.label.assessment" path="assessment" width="30%"/>
	<acme:list-column code="auditor.auditing-record.list.label.period" path="period" width="20%"/>
	<acme:list-column code="auditor.auditing-record.list.label.mark" path="mark" width="20%"/>
	<acme:hidden-data path="isCorrection"/>
</acme:list>

<acme:button test="${showCreate}" code="auditor.auditing-record.list.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>
<acme:button test="${showCorrection}" code="auditor.auditing-record.list.button.correction" action="/auditor/auditing-record/create-correction?masterId=${masterId}"/>

<acme:menu-separator></acme:menu-separator>
<acme:message code="auditor.auditing-record.list.message.asterisk.info"></acme:message>