<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>
		  <div class="col-md-10">

	  			<div class="row">
	  				<div class="col-md-12 panel-info">
			  			<div class="content-box-header panel-heading">
		  					<div class="panel-title ">Thêm liên hệ</div>
			  			</div>
			  			<div class="content-box-large box-with-header">
				  			<div>
								<div class="row mb-10"></div>
								<form action="${pageContext.request.contextPath}/admin/contact/edit/${contact.id}" method="post">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<label for="fullname">Fullname</label>
											<input readonly="readonly" value="${contact.fullname}" id="fullname" name="fullname" type="text" class="form-control" placeholder="Nhập fullname">
											<form:errors cssStyle="color: red"  path="user.fullname"></form:errors>
										</div>
										<div class="form-group">
											<label for="email">EmailTo</label>
											<input value="${contact.email}" id="email" name="email" type="text" class="form-control" placeholder="Nhập password">
											<form:errors cssStyle="color: red"  path="user.password"></form:errors>
										</div>
										<div class="form-group">
											<label for="subject">Subject</label>
											<input value="${contact.subject}" id="subject" name="subject" type="text" class="form-control" placeholder="Nhập subject">
											<form:errors cssStyle="color: red"  path="user.fullname"></form:errors>
										</div>
										
										<div class="col-sm-12">
										
									</div>
										<div class="form-group">
										   <label>Content</label>
										   <textarea  name="content" class="form-control" rows="7">${contact.content}</textarea>
										</div>
								</div>

								<hr>

								<div class="row">
									<div class="col-sm-12">
										<input type="submit" value="Update" class="btn btn-success" />
										<input type="reset" value="Nhập lại" class="btn btn-default" />
									</div>
								</div>

							</div>
							</form>
						</div>
			  		</div>
	  			</div><!-- /.row col-size -->
	  		
		  </div><!-- /.col-md-10 -->
