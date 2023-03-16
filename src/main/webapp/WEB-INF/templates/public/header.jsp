<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>  
<section id="header_area">
	<div class="wrapper header">
		<div class="clearfix header_top">
			<div class="clearfix logo floatleft">
				<a href="${pageContext.request.contextPath}/index"><h1>
						<span>C</span>Land
					</h1></a>
			</div>
			<div class="clearfix search floatright">
				<form action="${pageContext.request.contextPath}/search" method="get">
					<input name="search" type="text" placeholder="Search" /> <input type="submit" />
				</form>
			</div>
		</div>
		<div class="header_bottom">
			<nav>
				<ul id="nav">
					<li><a href="${pageContext.request.contextPath}/">Trang
							chủ</a></li>
					<li id="dropdown"><a
						href="${pageContext.request.contextPath}/cat">Bất động
							sản</a>
						<ul>
						
						<c:choose>
									<c:when test="${not empty categoriess}">
										<c:forEach items="${categoriess}" var="category">
											<li><a href="${pageContext.request.contextPath}/danh-muc/${SlugUtil.makeSlug(category.name)}-${category.id}">${category.name}</a></li>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<div>Không có danh mục!</div>
									</c:otherwise>
								</c:choose>
							
						</ul>
					</li>
					<li><a href="${pageContext.request.contextPath}/single1">Giới
							thiệu</a></li>
					<li><a
						href="${pageContext.request.contextPath}/lien-he">Liên
							hệ</a></li>
				</ul>
			</nav>
		</div>
	</div>
</section>
