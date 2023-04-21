<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.course.form.label.code" path="code"/>	
	<acme:input-textbox code="any.course.form.label.title" path="title"/>	
	<acme:input-textbox code="any.course.form.label.anAbstract" path="anAbstract"/>	
	<acme:input-textbox code="any.course.form.label.retailPrice" path="retailPrice"/>	
	<acme:input-textbox code="any.course.form.label.link" path="link"/>	
	<acme:input-textbox code="any.course.form.label.activityType" path="activityType"/>

	<acme:check-access test="isAuthenticated()">
		<acme:button code="any.course.form.button.tutorials-list" action="/authenticated/tutorial/list?courseId=${id}"/>
	</acme:check-access>
	<acme:check-access test="hasRole('Assistant')">
		<acme:button code="any.course.form.button.tutorials-create" action="/assistant/tutorial/create?courseId=${id}"/>
	</acme:check-access>
	
</acme:form>