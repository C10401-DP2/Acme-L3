<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.note.form.label.message" path="message"/>
	<acme:input-email code="authenticated.note.form.label.email" path="email"/>
	<acme:input-url code="authenticated.note.form.label.link" path="link"/>
	<acme:input-checkbox code="authenticate.note.form.button.confirmation" path="confirmation"/>
	<acme:hidden-data path= "id"/>
	<acme:hidden-data path= "instMoment"/>
	<acme:hidden-data path= "author"/>
	
	
	

	<acme:submit test="${_command == 'create'}" code="authenticated.note.form.button.create" action="/authenticated/note/create"/>
	
</acme:form>