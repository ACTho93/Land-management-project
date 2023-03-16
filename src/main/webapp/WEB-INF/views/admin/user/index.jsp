<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>  
		  <div class="col-md-10">

  			<div class="content-box-large">
  				<div class="row">
	  				<div class="panel-heading">
	  					<div class="panel-title ">Quản lý người dùng</div>
		  			</div>
				</div>
				<hr>	
				<div class="row">
					<div class="col-md-8">
						<a href="${pageContext.request.contextPath}/admin/user/add" class="btn btn-success"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;Thêm</a>

					</div>
                	<div class="col-md-4">
                	<form action="${pageContext.request.contextPath}/admin/user/search" method="get">
                 	 <div class="input-group form">
                       <input name="name" type="text" class="form-control" placeholder="Search...">
                       <span class="input-group-btn">
                         <button class="btn btn-primary" type="button">Search</button>
                       </span>
                  	 </div>
                  	 </form>
                  	</div>
				</div>

				<br />
				<c:if test="${not empty msg}">
					<div class="row">
						<div class="col-md-12">
							<div class="" style="color: green">
								${msg}
							</div>
						</div>
					</div>
				</c:if>
				
				<div class="row">
  				<div class="panel-body">
  					<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="example">
						<thead>
							<tr>
								<th>ID</th>
								<th>Username</th>
								<th>Fullname</th>
								<th>Role</th>
								<th>Chức năng</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
							<c:when test="${not empty users}">
								<c:forEach items="${users}" var="user">
								<c:set var="urlDel" value="${pageContext.request.contextPath}/admin/user/del/${user.id}" />
								<c:set var="urlEdit" value="${pageContext.request.contextPath}/admin/user/edit/${user.id}" />
							<tr class="even gradeA">
								<td>${user.id}</td>
								<td>${user.username}</td>
								<td>${user.fullname}</td>
								<td>${user.role.name}</td>
								
								<td class="center text-center">
									<a onclick="return confirm('Bạn có muốn sửa ${user.username} hay không?')" href="${urlEdit}" title="" class="btn btn-primary"><span class="glyphicon glyphicon-pencil "></span> Sửa</a>
                                   
                                   <c:if test="${user.username ne pageContext.request.userPrincipal.name }">
                                  	 <a onclick="return confirm('Bạn có muốn xóa ${user.username} hay không?')" href="${urlDel}" title="" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span> Xóa</a>
                                   </c:if>
                                   
                                   
                                    
								</td>
							</tr>
									
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="3" style="text-align: center">Không có người dùng nào cả</td>
								</tr>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>

					<!-- Pagination -->
					<nav class="text-center" aria-label="...">
					   <ul class="pagination">
					   
					   		<c:choose>
								<c:when test="${page == 1}">
									<li class="active"><a href="" aria-label="Previous"><span aria-hidden="true">«</span>
								</c:when>
								<c:otherwise>
									<li class=""><a href="${pageContext.request.contextPath}/admin/user/index/?page=${page - 1}"> << </a></li>
								</c:otherwise>
							</c:choose>
					   
					      
					      <c:forEach begin="1" end="${numberOfPages}" step="1" varStatus="loop">
							<c:choose>
								<c:when test="${loop.count == page}">
									<li class="active"><a href="${pageContext.request.contextPath}/admin/user/index/?page=${loop.count}">${loop.count}<span class="sr-only">(current)</span></a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/admin/user/index/?page=${loop.count}">${loop.count}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					      
					       <c:choose>
								<c:when test="${page == numberOfPages}">
									<li class="active"><a href="" aria-label="Next"><span aria-hidden="true">»</span></a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/admin/user/index/?page=${page + 1}" aria-label="Next"><span aria-hidden="true">»</span></a></li>
								</c:otherwise>
							</c:choose>
					      
					      
					   </ul>
					</nav>
					<!-- /.pagination -->
					
  				</div>
  				</div><!-- /.row -->
  			</div><!-- /.content-box-large -->



		  </div>