<%@page import="vn.edu.vinaenter.util.SlugUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>      
					<div class="clearfix slider">
						<ul class="pgwSlider">
							<li><img src="${pageContext.request.contextPath}/publicUrl/images/thumbs/megamind_07.jpg" alt="Paris, France" data-description="Eiffel Tower and Champ de Mars" data-large-src="${pageContext.request.contextPath}/publicUrl/images/slides/megamind_07.jpg"/></li>
							<li><img src="${pageContext.request.contextPath}/publicUrl/images/thumbs/wall-e.jpg" alt="Montréal, QC, Canada" data-large-src="${pageContext.request.contextPath}/publicUrl/images/slides/wall-e.jpg" data-description="Eiffel Tower and Champ de Mars"/></li>
							<li>
								<img src="${pageContext.request.contextPath}/publicUrl/images/thumbs/up-official-trailer-fake.jpg" alt="Shanghai, China" data-large-src="${pageContext.request.contextPath}/publicUrl/images/slides/up-official-trailer-fake.jpg" data-description="Shanghai ,chaina">
							</li>


						</ul>
					</div>
					
					<div class="clearfix content">
						<div class="content_title"><h2>Bài viết mới</h2></div>
						<c:if test="${not empty lands}">
						<c:forEach items="${lands}" var="land">
							
						<div class="clearfix single_content">
							<div class="clearfix post_date floatleft">
								<div class="date">
									<h3>
										<fmt:formatDate value="${land.date_create}" pattern="dd"/>
									</h3>
									<p>
										Tháng <fmt:formatDate value="${land.date_create}" pattern="M"/>
									</p>
								</div>
							</div>
							<div class="clearfix post_detail">
								<h2><a href="${pageContext.request.contextPath}/${SlugUtil.makeSlug(land.name)}-${land.id}.html">${land.name} </a></h2>
								<div class="clearfix post-meta">
									<p><span><i class="fa fa-clock-o"></i> Địa chỉ: ${land.address}</span> <span><i class="fa fa-folder"></i> Diện tích: ${land.area}</span> <span><i class="fa fa-folder"></i> Lượt xem: ${land.count_views}</span></p>
								</div>
								<div class="clearfix post_excerpt">
									<img width="20px" src="${pageContext.request.contextPath}/files/${land.picture}" alt=""/>
									<p>${land.description}</p>
								</div>
								<a href="${pageContext.request.contextPath}/${SlugUtil.makeSlug(land.name)}-${land.id}.html">Đọc thêm</a>
							</div>
						</div>
						
						</c:forEach>
						</c:if>
			
					</div>
					
					<div class="pagination">
						<nav>
							<ul>
								<c:choose>
										<c:when test="${page == 1}">
											<li class="active"><a href=""> << </a></li>
										</c:when>
										<c:otherwise>
											<li class=""><a href="${pageContext.request.contextPath}/?page=${page - 1}"> << </a></li>
										</c:otherwise>
									</c:choose>
							
								
								<c:forEach begin="1" end="${numberOfPages}" step="1" varStatus="loop">
									<c:choose>
										<c:when test="${loop.count == page}">
											<li class ="active" ><a href="${pageContext.request.contextPath}/?page=${loop.count}">${loop.count}</a></li>
										</c:when>
										<c:otherwise>
											<li class ="" ><a href="${pageContext.request.contextPath}/?page=${loop.count}">${loop.count}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
									
									
									<c:choose>
										<c:when test="${page == numberOfPages}">
											<li class="active" ><a href=""> >> </a></li>
										</c:when>
										<c:otherwise>
											<li class="" ><a href="${pageContext.request.contextPath}/?page=${page + 1}"> >> </a></li>
										</c:otherwise>
									</c:choose>
								
							</ul>
						</nav>
					</div>
	<script type="text/javascript">

	 document.getElementById("color").classList.add('cat-item');
	
</script>	