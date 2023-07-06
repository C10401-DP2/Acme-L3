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

	<acme:list>
		<acme:list-column code="student.activities.list.label.title" path="title"/>
		<acme:list-column code="student.activities.list.label.abstrat" path="abstrat"/>
		<acme:list-column code="student.activities.list.label.aType" path="aType"/>
		<acme:list-column code="student.activities.list.label.initialDate" path="initialDate"/>
		<acme:list-column code="student.activities.list.label.finalDate" path="finalDate"/>
	</acme:list>
	
	<jstl:if test="${_command == 'list-mine'}">
		<jstl:if test="${draftMode}">
			<acme:button code="student.activities.list.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
		</jstl:if>
	</jstl:if>

	