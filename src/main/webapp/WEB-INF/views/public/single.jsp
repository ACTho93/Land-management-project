<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>  					
					<div class="clearfix content">
						
						<h1>${landById.name} </h1>
						<div class="clearfix post-meta">
							<p><span><i class="fa fa-clock-o"></i> Địa chỉ: ${landById.address}</span> <span><i class="fa fa-folder"></i> Diện tích: ${landById.area}m2</span> <span><i class="fa fa-folder"></i> Lượt xem: ${landById.count_views}</span></p>
						</div>
						
						<div class="vnecontent">
							<p>${landById.detail}</p>
						</div>
						
						<a class="btn" href="${pageContext.request.contextPath}/single/${land.id-1}">Bài trước</a>
						<a class="btn" href="${pageContext.request.contextPath}/single/${land.id+1}">Bài kế</a>
					
					</div>
					<div>
						<div class="more_themes">
							<h2>Bất động sản liên quan <i class="fa fa-thumbs-o-up"></i></h2>
							<div class="more_themes_container">
								<c:choose>
									<c:when test="${not empty listlandRelated}">
										<c:forEach items="${listlandRelated}" var="listRelated">
											<div class="single_more_themes floatleft">
											<img src="${pageContext.request.contextPath}/files/${listRelated.picture}" alt=""/>
											<a href=""><h2>${listRelated.name}</h2></a>
										</div>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<div>Không có bài viết liên quan!</div>
									</c:otherwise>
								</c:choose>
							</div>
							<br />
						</div>
						</div>
<!-- 	<script type="text/javascript">

	 document.getElementById("color").classList.add('cat-item');
	
</script>	 -->