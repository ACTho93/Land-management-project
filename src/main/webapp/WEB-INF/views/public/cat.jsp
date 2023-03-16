<%@page import="vn.edu.vinaenter.util.SlugUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>     
<div class="clearfix content">
						<div class="content_title"><h2>${cate.name}</h2></div>
						
						<div class="clearfix single_work_container">
						
						<c:choose>
							<c:when test="${not empty lands}">
								<c:forEach items="${lands}" var="land">
									<div class="clearfix single_work">
										<img class="img_top" src="${pageContext.request.contextPath}/files/${land.picture}" alt=""/>
										<img class="img_bottom" src="${pageContext.request.contextPath}/files/${land.picture}" alt=""/>
										<a href="${pageContext.request.contextPath}/${SlugUtil.makeSlug(land.name)}-${land.id}.html"><h2 >${land.name}</h2></a>
										<a href="${pageContext.request.contextPath}/${SlugUtil.makeSlug(land.name)}-${land.id}.html"><p class="caption">${land.description}</p></a>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div>Không có tin tức!</div>
							
							</c:otherwise>
						
						</c:choose>
						
					</div>
					<div>		
							<div class="clearfix work_pagination">
								<nav>
									<a class="newer floatleft" href=""> < -- Trang trước</a>
									<a class="older floatright" href="">Trang kế -- ></a>
								</nav>
							</div>

						</div>
					</div>
	<script type="text/javascript">

	 document.getElementById("color").classList.add('cat-item');
	
</script>	