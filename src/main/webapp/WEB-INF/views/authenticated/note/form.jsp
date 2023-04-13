<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form readonly="${readonly}">
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.note.form.label.author" path="author"/>
	<acme:input-checkbox code="authenticated.note.form.label.message" path="message"/>
	<acme:input-email code="authenticated.note.form.label.email" path="email"/>
	<acme:input-url code="authenticated.note.form.label.link" path="link"/>
	
	<jstl:if test="${!readonly}">
		<acme:input-checkbox code="authenticated.note.form.button.confirmation" path="confirmation"/>
		<acme:submit code="authenticated.note.form.button.create" action="/administrator/note/create"/>
	</jstl:if>
</acme:form>