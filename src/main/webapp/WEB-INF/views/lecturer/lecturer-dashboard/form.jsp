<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form> 
	<acme:input-integer code="lecturer.dashboard.form.label.totaltlectures" path="totalTheoryLectures"/>
	<acme:input-integer code="lecturer.dashboard.form.label.totalhlectures" path="totalHandsonLectures"/>
	<h2>
	<acme:message code="lecturer.dashboard.form.label.statslectures"/>
	</h2>
	<table class="table table-sm">
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.averageLecture"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeLectures.get('averageLecture')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.deviationLecture"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeLectures.get('deviationLecture')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.minLecture"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeLectures.get('minLecture')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.maxLecture"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeLectures.get('maxLecture')}"/>
			</td>
		</tr>
	</table>
	<h2>
	<acme:message code="lecturer.dashboard.form.label.statscourses"/>
	</h2>
<table class="table table-sm">
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.averageCourse"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeCourses.get('averageCourse')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.deviationCourse"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeCourses.get('deviationCourse')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.minCourse"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeCourses.get('minCourse')}"/>
			</td>
		</tr>
		<tr>
			<th scope="row">
				<acme:message code="lecturer.dashboard.form.label.maxCourse"/>
			</th>
			<td>
				<acme:print value="${ statsLearningTimeCourses.get('maxCourse')}"/>
			</td>
		</tr>
	</table>

</acme:form> 