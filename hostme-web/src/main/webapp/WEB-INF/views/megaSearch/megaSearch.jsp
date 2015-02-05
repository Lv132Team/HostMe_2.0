<%@ page language="java" contentType="text/html; charset=Utf-8"
         pageEncoding="Utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<title><spring:message code="label.search"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=Utf-8">

<script src="<c:url value="/resources/js/search/search.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.11.2/jquery-ui.js"/>"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.11.2/jquery-ui.min.js"/>"></script>
<link href="<c:url value="/resources/css/search/searchResults.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/search/searchOptions.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/jquery-ui-themes-1.11.2/themes/smoothness/jquery-ui.css"/>" rel="stylesheet">

<section class="content-header">
  <h1>
    <spring:message code="label.search" />
  </h1>
</section>

<section class="content">
    <div class="box box-primary container-fluid">
        <div id="searchBox" class="col-md-offset-2">
            <select id="searchType" class="col-md-4 btn btn-default dropdown-toggle" style="height: 4%"></select>
            <input id="searchCity" class="col-md-6" placeholder="<spring:message code='label.citySelect'/>" style="height: 4%"/>
            <input id="search" class="col-md-6" placeholder="<spring:message code='search.searchUser'/>" style="height: 4%">
            <div id="searchOptions" class="searchOptionBox col-md-12"></div>
            <div id="showSearchOptions" class="btn btn-primary col-md-10">
                Advanced options
            </div>
        </div>
        <div id="searchResult" class="col-md-12"></div>
    </div>
</section>


