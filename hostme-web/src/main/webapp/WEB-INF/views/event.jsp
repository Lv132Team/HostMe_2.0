<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html >
<head>
<meta charset="UTF-8">
<link href="resources/css/dataTables.bootstrap.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="resources/css/groups.css">
<link rel="stylesheet" type="text/css" media="all"
	href="resources/css/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="resources/css/bootstrap.css" />
<link
	href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all"
	href="resources/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/oka_slider_model.css"/>" />

<script src="resources/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="resources/js/separateEventPageHandler.js"
	type="text/javascript"></script>
<script src="resources/js/dataTables.bootstrap.js"
	type="text/javascript"></script>
<script type="text/javascript" src="resources/js/jquery.validate.js"></script>
<script src='resources/js/moment.js'></script>
<script type="text/javascript" src="resources/js/daterangepicker.js"></script>
<script type="text/javascript" src="resources/js/rangeCalendar.js"></script>
<script src="<c:url value="/resources/js/oka_slider_model.js"/>"></script>

<title>Events</title>
</head>
<body class="skin-blue  pace-done" >
	<section class="content-header">
		<h1>
			<spring:message code="label.events" />
		</h1>
	</section>
	<!-- Main content -->
	<section class="content">
		<div>
			<div class="box box-primary">
				<div class="box-header">
					<div class="box-title col-md-8">
						<i class="fa fa-calendar-o"></i>
						<c:out value="${event.name}" />
						<security:authorize access="hasRole('USER')">
							<c:if test="${isSubscribed eq false}">
								<a href="<spring:url value="event-subscribe/${event.id}"/>"
									class="btn btn-default btn-sm " style="margin-left: 20px;"><i
									class="fa fa-share"></i> <spring:message code="label.join" />
								</a>
							</c:if>
							<c:if test="${isSubscribed eq true}">
								<a href="<spring:url value="event-subscribe/${event.id}"/>"
									class="btn btn-default btn-sm " style="margin-left: 20px;"><i
									class="fa fa-share"></i> <spring:message code="label.leave" />
								</a>
							</c:if>
						</security:authorize>
					</div>

					<div class="box-title col-md-4">
						<div align="right">

							<security:authorize access="hasRole('MODERATOR')">
								<button type="button"
									class="btn btn-default btn-sm dropdown-toggle"
									data-toggle="dropdown" class="btn btn-default dropdown-toggle">
									<i class="fa fa-gears"></i>
									<spring:message code="label.status" />
								</button>
								<ul id="eventStatusChanger" class="dropdown-menu "
									style="margin-top: -10px; left: 20px;">
									<li><a id="APPROVED" href="#">Approved</a> <a id="PENDING"
										href="#">Pending</a> <a id="REFUSED" href="#">Refused</a></li>
								</ul>
								<button type="button" class="btn btn-default btn-sm"
									data-toggle="modal" data-target="#eventEdit">
									<i class="fa fa-fw fa-edit"></i>
									<spring:message code="label.edit" />
								</button>
								<a href="<spring:url value="event/delete/${event.id}" />"
									class="btn btn-default btn-sm triggerRemove"><i
									class="fa fa-fw fa-trash-o"></i> <spring:message
										code="label.delete" /> </a>
							</security:authorize>

							<security:authorize access="hasRole('USER')">
								<c:if test="${isCreator eq true}">
									<button type="button" class="btn btn-default btn-sm"
										data-toggle="modal" data-target="#eventEdit">
										<i class="fa fa-fw fa-edit"></i>
										<spring:message code="label.edit" />
									</button>
									<a href="<spring:url value="event/delete/${event.id}" />"
										class="btn btn-default btn-sm triggerRemove"><i
										class="fa fa-fw fa-trash-o"></i> <spring:message
											code="label.delete" /> </a>
								</c:if>

							</security:authorize>

						</div>
					</div>
				</div>
				<div class="box-body">
					<div class="row">
						<div class="col-md-6">
							<div class="col-xs-12 .col-sm-6 .col-lg-8">
								<c:forEach var="image" begin="0" end="0" items="${event.image}">
									<a href="${image_url}${image.link}" data-lightbox="images">
										<img src="${image_url}${image.link}" />
									</a>
								</c:forEach>
							</div>
						</div>
						<div class="col-md-6">
							<div class="panel box box-warning col-md-12"
								style="padding-left: 0.3em;">
								<div class="box-header">
									<h4 class="box-title"
										style="padding-top: 0.8em; padding-bottom: 0em;">
										<spring:message code="label.genInfo" />
										:
									</h4>
								</div>
								<div id="collapseOne" class="panel-collapse in"
									style="height: auto;">
									<div class="box-body">
										<div class="row"
											style="padding-top: 0.3em; padding-bottom: 0.3em">
											<div class="col-md-4" style="padding-right: 0em;">
												<spring:message code="label.country" />
												:
											</div>
											<div class="col-md-8 selected">${event.city.country.country}</div>
										</div>
										<div class="row" style="padding-bottom: 0.3em">
											<div class="col-md-4" style="padding-right: 0em;">
												<spring:message code="label.city" />
												:
											</div>
											<div class="col-md-8 selected">${event.city.city}</div>
										</div>
										<div class="row" style="padding-bottom: 0.3em">
											<div class="col-md-4" style="padding-right: 0em;">
												<spring:message code="label.address" />
												:
											</div>
											<div class="col-md-8 selected">${event.address}</div>
										</div>
										<div class="row" style="padding-bottom: 0.3em">
											<div class="col-md-4" style="padding-right: 0em;">
												<spring:message code="label.start/end" />
												:
											</div>
											<div class="col-md-8 selected">${event.startDate}/${event.endDate}</div>
										</div>
										<div class="row" style="padding-bottom: 0.3em">
											<div class="col-md-4" style="padding-right: 0em;">
												<spring:message code="label.price" />
												:
											</div>
											<div class="col-md-8 selected">${event.priceCategory.priceCategory}</div>
										</div>
										<div class="row" style="padding-bottom: 0.3em">
											<div class="col-md-4" style="padding-right: 0em;">
												<spring:message code="label.website" />
												:
											</div>
											<div class="col-md-8 selected">${event.website}</div>
										</div>
										<div class="row" style="padding-bottom: 0.3em">
											<div class="col-md-4" style="padding-right: 0em;">
												<spring:message code="label.owner" />
												:
											</div>
											<div class="col-md-8 selected">${event.owner.firstName}&nbsp;${event.owner.lastName}</div>
										</div>

									</div>
								</div>
							</div>
						</div>
						<div class="modal fade" id="modalRemove" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">
											<spring:message code="label.removeEventConf" />
										</h4>
									</div>
									<div class="modal-body">
										<div
											class="alert alert-info alert-dismissable alertDelteWindowFont"
											id="alert" align="center">
											<i class="fa fa-warning"></i> <font size="5"><spring:message
													code="label.removeCaution" /></font>
										</div>
									</div>
									<div class="modal-footer">
										<a href="" class="btn btn-primary removeBtn"><spring:message
												code="label.delete" /></a>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<spring:message code="label.cancel" />
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="box box-solid">
					<div class="box-header">
						<h3 class="box-title">
							<spring:message code="label.photos" />
						</h3>
					</div>
					<!-- /.box-header -->
					<div class="slider_model demo-8">
						<div class="slider_model_box">
							<c:forEach var="image" items="${event.image}">
								<img src="${image_url}${image.link}" />
							</c:forEach>
						</div>
					</div>
					<!-- /.box-body -->
				</div>
				<div class="box box-solid">
					<dl>
						<dd>
							<form:form method="post" action="addPhotosToEvent"
								modelAttribute="event" enctype="multipart/form-data">
								<input type="hidden" value="${event.id}" name="id" />
								<security:authorize access="hasRole('USER')">
									<c:if test="${isCreator eq true}">
										<div class="form-group">
											<label for="exampleInputFile">
												<div class="box-body">
													<h4 class="box-title">
														<spring:message code="label.addPhotos" />
													</h4>
													<h5>
														(
														<spring:message code="label.savePhotoHotKey" />
														)
													</h5>
											</label> <br> <input type="file" name="file" class="multi"
												multiple accept="gif|jpg|png" data-maxfile="10000"
												data-maxsize="50000" /> <br>
											<button type="submit" class="btn btn-primary">
												<spring:message code="label.savePhotos" />
											</button>
										</div>
									</c:if>
								</security:authorize>
							</form:form>
						</dd>
					</dl>
				</div>
			</div>

			<div class="col-md-6">
				<div class="box box-info">
					<div class="callout callout-danger">
						<h4>
							<spring:message code="label.description" />
							:
						</h4>
						<p>${event.description}</p>
						<h4>
							<spring:message code="label.comment" />
							:
						</h4>
						<p>${event.comment}</p>
					</div>
					<div class="callout callout-warning">
						<h4>
							<spring:message code="label.attendees" />
							:
						</h4>
						<c:set var="leng" value="${fn:length(event.attendee)}" />
						<c:forEach var="attendee" items="${event.attendee}"
							varStatus="counter">
							<c:out value="${attendee.firstName} ${attendee.lastName}"></c:out>
							<c:if test="${counter.count lt leng}">
								<c:out value=","></c:out>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</section>
	<form:form modelAttribute="event" method="post"
		cssClass="form-horizontal groupForm">
		<!-- Modal -->
		<div class="modal fade" id="eventEdit" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">
							<spring:message code="label.edit" />
						</h4>
					</div>
					<div class="modal-body">

						<div class="form-group">
							<label for="name" class="col-sm-2 control-label"><spring:message
									code="label.eventTitle" />: </label>
							<div class="col-sm-10">
								<form:input path="name" cssClass="form-control" />
								<form:errors path="name" />
							</div>
						</div>

						<div class="form-group">
							<label for="name" class="col-sm-2 control-label"> <spring:message
									code="label.dateRange" />
							</label>

							<form class="form-horizontal">
								<div class="col-sm-10" style="margin-left: auto">
									<input type="text" value=${event.startDate}/${event.endDate}
										name="reservation" id="reservation" class="form-control" />
								</div>
							</form>

						</div>

						<div class="form-group" style="display: none;">
							<label for="startDate" class="col-sm-2 control-label"> <spring:message
									code="label.startDate" />:
							</label>
							<div class="col-sm-10">
								<form:input id="startDate" path="startDate"
									cssClass="form-control" />

							</div>
						</div>
						<div class="form-group" style="display: none;">
							<label for="endDate" class="col-sm-2 control-label"><spring:message
									code="label.endDate" />: </label>
							<div class="col-sm-10">
								<form:input id="endDate" path="endDate" cssClass="form-control" />

							</div>
						</div>
						<div class="form-group">
							<form:label path="city.country.country"
								class="col-sm-2 control-label">
								<spring:message code="label.country" />
							</form:label>
							<div class="col-sm-10">
								<form:select id="country" class="dropMenuForModal"
									path="city.country.country">
									<option value="${event.city.country.country}">${event.city.country.country}</option>
									<c:forEach items="${countries}" var="country">
										<option value="${country.country}">${country.country}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<form:label path="city.city" class="col-sm-2 control-label">
								<spring:message code="label.city" />
							</form:label>
							<div class="col-sm-10">
								<form:select id="city" class="dropMenuForModal" path="city.city">
									<option value="${event.city.city}">${event.city.city}</option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="address" class="col-sm-2 control-label"> <spring:message
									code="label.address" />:
							</label>
							<div class="col-sm-10">
								<form:textarea id="group-dscrptn-textarea" path="address"
									cssClass="form-control" />
								<form:errors path="address" />
							</div>
						</div>
						<div class="form-group">
							<label for="description" class="col-sm-2 control-label">
								<spring:message code="label.description" />:
							</label>
							<div class="col-sm-10">
								<form:textarea id="group-dscrptn-textarea" path="description"
									cssClass="form-control" />
								<form:errors path="description" />
							</div>
						</div>
						<div class="form-group">
							<label for="comment" class="col-sm-2 control-label"> <spring:message
									code="label.comment" />:
							</label>
							<div class="col-sm-10">
								<form:textarea id="group-dscrptn-textarea" path="comment"
									cssClass="form-control" />
								<form:errors path="comment" />
							</div>
						</div>
						<div class="form-group">
							<label for="website" class="col-sm-2 control-label"> <spring:message
									code="label.website" />:
							</label>
							<div class="col-sm-10">
								<form:textarea id="group-dscrptn-textarea" path="website"
									cssClass="form-control" />
								<form:errors path="website" />
							</div>
						</div>
						<div class="form-group">
							<label for="priceCategory.priceCategory"
								class="col-sm-2 control-label"> <spring:message
									code="label.priceCategory" />:
							</label>
							<div class="col-sm-10">
								<form:select class="dropMenuForModal"
									path="priceCategory.priceCategory">
									<option value="${event.priceCategory.priceCategory}">${event.priceCategory.priceCategory}</option>
									<c:forEach items="${priceCategories}" var="priceCategory">
										<option value="${priceCategory.priceCategory}">${priceCategory.priceCategory}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary button"
							value=<spring:message code="label.save" />>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<spring:message code="label.close" />
						</button>
					</div>
				</div>

			</div>
		</div>
	</form:form>
</body>
</html>