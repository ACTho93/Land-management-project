<%@page import="vn.edu.vinaenter.util.SlugUtil"%>
<%@page import="vn.edu.vinaenter.model.bean.Category"%>
<%@page import="java.util.List"%>
<%@page import="vn.edu.vinaenter.model.dao.CategoryDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>  


<div class="clearfix sidebar_container floatright">
	<div class="clearfix sidebar">
		<div class="clearfix single_sidebar category_items">
			<h2>Danh mục bất động sản</h2>
			<ul>
			<c:if test="${not empty categories}">
			<c:forEach items="${categories}" var="category">
				<c:choose>
					<c:when test="${category.id == id}">
						<li class="cat-item"><a href="${pageContext.request.contextPath}/danh-muc/${SlugUtil.makeSlug(category.name)}-${category.id}">${category.name}</a>(${category.total})</li>
					</c:when>
					<c:otherwise>
						<li class="" ><a href="${pageContext.request.contextPath}/danh-muc/${SlugUtil.makeSlug(category.name)}-${category.id}">${category.name}</a>(${category.total})</li>
					</c:otherwise>
				</c:choose>
				<!--  class="cat-item" -->
			</c:forEach>
			</c:if>
			</ul>
		</div>

		<div class="clearfix single_sidebar">
			<div class="popular_post">
				<div class="sidebar_title">
					<h2>Xem nhiều</h2>
				</div>
				<ul>
				<c:if test="${not empty landOfcount}">
				<c:forEach items="${landOfcount}" var="land">
						<li><a href="${pageContext.request.contextPath}/${SlugUtil.makeSlug(land.name)}-${land.id}.html">${land.name}</a></li>
					</c:forEach>
				</c:if>
				</ul>
			</div>
		</div>

		<div class="clearfix single_sidebar">
			<h2>Danh mục hot</h2>
			<ul>
				<li><a href="">Category Name <span>(12)</span></a></li>
				<li><a href="">Category Name <span>(12)</span></a></li>
				<li><a href="">Category Name <span>(12)</span></a></li>
				<li><a href="">Category Name <span>(12)</span></a></li>
			</ul>
		</div>
	</div>
</div>
