<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="company.session.list.label.title" path="title" />
	<acme:list-column code="company.session.list.label.startDate" path="initialDate" />
	<acme:list-column code="company.session.list.label.finishDate" path="finalDate" />	
</acme:list>

<acme:button test="${showCreate && !draftMode}" code="company.sessionPracticum.list.button.create" action="/company/practicum-session/create?practicumId=${practicumId}"/>