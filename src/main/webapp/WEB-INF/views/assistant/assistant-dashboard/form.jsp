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

<%@page language="java" import="acme.datatypes.ActivityType"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="assistant.dashboard.form.title.general-indicators"/>
</h2>

<h3>
	<acme:message code="assistant.dashboard.form.label.title-sessions"/>
</h3>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-sessions"/>
		</th>
		<td>
			<acme:print value="${averageTimeTutorialSessions}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-time-sessions"/>
		</th>
		<td>
			<acme:print value="${standardDesviationTimeTutorialSessions}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-time-sessions"/>
		</th>
		<td>
			<acme:print value="${minTimeTutorialSessions}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-time-sessions"/>
		</th>
		<td>
			<acme:print value="${maxTimeTutorialSessions}"/>
		</td>
	</tr>
</table>

<h3>
	<acme:message code="assistant.dashboard.form.label.title-tutorial"/>
</h3>	
	
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.average-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${averageTimeTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.deviation-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${standardDesviationTimeTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.min-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${minTimeTutorials}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.max-time-tutorials"/>
		</th>
		<td>
			<acme:print value="${maxTimeTutorials}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="assistant.dashboard.form.label.number-of-tutorials-by-type"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"THEORY", "HANDSON", "BALANCED"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${totalTutorials[ActivityType.THEORY]}"/>,
						<jstl:out value="${totalTutorials[ActivityType.HANDSON]}"/>,
						<jstl:out value="${totalTutorials[ActivityType.BALANCED]}"/>
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							// suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>

