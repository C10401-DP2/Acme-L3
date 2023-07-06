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
	<acme:input-textbox code="authenticated.audit.form.label.code" path="code"/>
	<acme:input-textarea code="authenticated.audit.form.label.conclusion" path="conclusion"/>	
	<acme:input-textarea code="authenticated.audit.form.label.strong-points" path="strongPoints"/>
	<acme:input-textarea code="authenticated.audit.form.label.weak-points" path="weakPoints"/>
	<acme:input-textbox code="authenticated.audit.form.label.course" path="course"/>
	<acme:input-textbox code="authenticated.audit.form.label.auditor" path="auditor"/>
	<acme:hidden-data path="id"/>
</acme:form>