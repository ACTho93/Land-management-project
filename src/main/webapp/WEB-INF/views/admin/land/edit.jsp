<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/templates/taglib.jsp" %>
		  <div class="col-md-10">

	  			<div class="row">
	  				<div class="col-md-12 panel-info">
			  			<div class="content-box-header panel-heading">
		  					<div class="panel-title ">Sửa tin tức</div>
			  			</div>
			  			<div class="content-box-large box-with-header">
				  			<div>
								<div class="row mb-10"></div>
								<form action="${pageContext.request.contextPath}/admin/land/edit/${land.id}" method="post" enctype="multipart/form-data">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label for="name">Tên tin tức</label>
												<input value="${land.name}"  name="name" type="text" class="form-control" placeholder="Nhập tên tin tức">
												<form:errors cssStyle="color:red" path="news.name"></form:errors>
											</div>
											
											<div class="form-group">
												<label>Danh mục</label>
												<select name="categoryid" class="form-control">
													<c:choose>
														<c:when test="${not empty category}">
															<c:forEach items="${category}" var="cate">
															
																<c:choose>
																	<c:when test="${cate.id == land.category.id}">
																		<option selected="selected" value="${cate.id}">${cate.name}</option>
																	</c:when>
																	<c:otherwise>
																		 <option value="${cate.id}">${cate.name}</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</c:when>
														<c:otherwise>
															 <option style="color: red">Không có quyền nào!</option>
														</c:otherwise>
													</c:choose>
												</select>
												
											</div>
	
											<div class="form-group">
												<label>Sửa hình ảnh</label>
												<input name="hinhanh" type="file" class="btn btn-default" id="exampleInputFile1"><br />
												<img width="100px" src="${pageContext.request.contextPath}/files/${land.picture}" />
											</div>
											
											<div class="form-group">
											   <label>Diện tích</label>
											   <textarea name="area" class="form-control" rows="3" placeholder="Nhập diện tích">${land.area}</textarea>
											   <form:errors cssStyle="color:red" path="news.area"></form:errors>
											</div>
											
											<div class="form-group">
												<label for="address">Địa chỉ</label>
												<input value="${land.address}"  name="address"  type="text" class="form-control" placeholder="Nhập địa chỉ">
												<form:errors cssStyle="color:red" path="news.address"></form:errors>
											</div>
											
											<div class="form-group">
											   <label>Mô tả</label>
											   <textarea name="description" class="form-control" rows="3" placeholder="Nhập mô tả">${land.description}</textarea>
											   <form:errors cssStyle="color:red" path="news.description"></form:errors>
											</div>
											
											<div class="form-group">
											   <label>Chi tiết</label>
											   <textarea name="detail" class="form-control" rows="3" placeholder="Nhập mô tả">${land.detail}</textarea>
											   <form:errors cssStyle="color:red" path="news.detail"></form:errors>
											</div>
											
											<script type="text/javascript">
												CKEDITOR.replace('detail');
											
											</script>
										</div>
									</div>
								
								<hr>

								<div class="row">
									<div class="col-sm-12">
										<input type="submit" value="Update" class="btn btn-success" />
										<input type="reset" value="Nhập lại" class="btn btn-default" />
									</div>
								</div>
							</form>
							</div>
						</div>
			  		</div>
	  			</div><!-- /.row col-size -->
	  		
		  </div><!-- /.col-md-10 -->
