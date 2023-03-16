<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>
		  <div class="col-md-10">

	  			<div class="row">
	  				<div class="col-md-12 panel-info">
			  			<div class="content-box-header panel-heading">
		  					<div class="panel-title ">Thêm người dùng</div>
			  			</div>
			  			<div class="content-box-large box-with-header">
				  			<div>
								<div class="row mb-10"></div>
								<form action="${pageContext.request.contextPath}/admin/user/add" method="post">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label for="username">Username</label>
											<input id="username" name="username" type="text" class="form-control" placeholder="Nhập username">
											<form:errors cssStyle="color: red"  path="user.username"></form:errors>
										</div>
										<div class="form-group">
											<label for="fullname">Fullname</label>
											<input id="fullname" name="fullname" type="text" class="form-control" placeholder="Nhập fullname">
											<form:errors cssStyle="color: red"  path="user.fullname"></form:errors>
										</div>
										<div class="form-group">
											<label for="password">Password</label>
											<input id="password" name="password" type="password" class="form-control" placeholder="Nhập password">
											<form:errors cssStyle="color: red"  path="user.password"></form:errors>
										</div>
										<div class="form-group">
											<label>Role</label>
											<select name="roleId" class="form-control">
											<c:choose>
												<c:when test="${not empty roles}">
													<c:forEach items="${roles}" var="role">
														 <option value="${role.id}">${role.name}</option>
													</c:forEach>
												</c:when>
												<c:otherwise>
													 <option style="color: red">Không có quyền nào!</option>
												</c:otherwise>
											</c:choose>
											 
											</select>
										</div>
										
								</div>

								<hr>

								<div class="row">
									<div class="col-sm-12">
										<input type="submit" value="Thêm" class="btn btn-success" />
										<input type="reset" value="Nhập lại" class="btn btn-default" />
									</div>
								</div>

							</div>
							</form>
						</div>
			  		</div>
	  			</div><!-- /.row col-size -->
	  		
		  </div><!-- /.col-md-10 -->
