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
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>
	<acme:input-select code="authenticated.tutorial.form.label.course" path="course" choices="${choices}"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.abstraction" path="anAbstract"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.goals" path="goals"/>
	<label>
		<acme:message code="authenticated.tutorial.form.label.assistant"/>
	</label>
	<acme:input-textbox code="authenticated.tutorial.form.label.assistant.supervisor" path="assistant.supervisor"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.assistant.expertises" path="assistant.expertiseFields"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.assistant.resume" path="assistant.resume"/>	
	<acme:input-textbox code="authenticated.tutorial.form.label.assistant.link" path="assistant.link"/>	
</acme:form>
