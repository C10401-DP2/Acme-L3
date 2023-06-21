<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>


<acme:list>
	<acme:list-column code="company.practicum-session.list.label.title" path="title" width="60%"/>	
	<acme:list-column code="company.practicum-session.list.label.start-date" path="initialDate" width="15%"/>
	<acme:list-column code="company.practicum-session.list.label.end-date" path="finalDate" width="15%"/>
	<acme:list-column code="company.practicum-session.list.label.addendum" path="addendumState" width="10%"/>
</acme:list>

<acme:button test="${showAddendumCreate}" code="company.addendum-session.list.button.create" action="/company/practicum-session/create-addendum?practicumId=${practicumId}"/>
<acme:button test="${showCreate}" code="company.sessionPracticum.list.button.create" action="/company/practicum-session/create?practicumId=${practicumId}"/>