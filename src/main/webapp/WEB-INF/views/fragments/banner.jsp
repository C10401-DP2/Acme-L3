<%--
- banner.jsp
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


<div class="rounded" style="background: <acme:message code='master.banner.background'/>">
	<img src="images/banner.png" alt="<acme:message code='master.banner.alt'/>" class="img-fluid rounded"/>
	
	<jstl:if test="${banner.documentLink != null}">
		<a href="${banner.documentLink}">
	</jstl:if>
	<jstl:choose>
		<jstl:when test="${banner.image != null}">
			<img src="${banner.image}" alt="${banner.slogan}" class="img-fluid rounded" style="max-width: 300px;max-height: 120px;"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:print value="${banner.slogan}"/>
		</jstl:otherwise>
	</jstl:choose>
	<jstl:if test="${banner.documentLink != null}">
	</jstl:if>
	

</div>

