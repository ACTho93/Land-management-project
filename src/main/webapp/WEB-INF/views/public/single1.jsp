<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>  					
					<div class="clearfix content">
						<c:if test="${not empty landd}">
						<c:forEach items="${landd}" var="land">
						<h1>${land.name} </h1>
						<div class="clearfix post-meta">
							<p><span><i class="fa fa-clock-o"></i> Địa chỉ: ${land.address}</span> <span><i class="fa fa-folder"></i> Diện tích: ${land.area}m2</span> <span><i class="fa fa-folder"></i> Lượt xem: ${land.count_views}</span></p>
						</div>
						
						<div class="vnecontent">
							<p>${land.description}</p>
						</div>
						
						<a class="btn" href="${pageContext.request.contextPath}/single/${land.id-1}">Bài trước</a>
						<a class="btn" href="${pageContext.request.contextPath}/single/${land.id+1}">Bài kế</a>
					</c:forEach>
						</c:if>
					</div>
					
						<div class="more_themes">
							<h2>Bất động sản liên quan <i class="fa fa-thumbs-o-up"></i></h2>
							<div class="more_themes_container">
							<c:if test="${not empty landRelated}">
							<c:forEach items="${landRelated}" var="landrelated">
							
								<div class="single_more_themes floatleft">
									<img src="${pageContext.request.contextPath}/publicUrl/images/thumb.png" alt=""/>
									<a href=""><h2>${landrelated.name}</h2></a>
								</div>
								
							</c:forEach>
							</c:if>
							

							</div>
						</div>
	<script type="text/javascript">

	 document.getElementById("color").classList.add('cat-item');
	
</script>	