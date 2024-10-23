<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<!-- Main content -->
<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <!-- general form elements -->
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Chỉnh sửa thông tin sách</h3>
                        </div>
                        <div class="row justify-content-center" style="margin-top: 15px; margin-bottom: -15px;">
                            <div style="color: red;">${errorString}</div>
                        </div>
                        <!-- form start -->
                        <form role="form" method="post" action="${pageContext.request.contextPath}/EditBook" enctype="multipart/form-data">
                            <div class="card-body">
                                <input type="hidden" name="id" value="${book.id}" />
                                <div class="form-group">
                                    <label>Tên sách</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${book.getName()}">
                                </div>
                                <div class="form-group">
                                    <label>Thể loại</label>
                                    <select name="category" id="category" class="form-control" required>
                                        <c:forEach items="${categoryList}" var="category">
                                            <option value="${category.getId()}" ${category.getId() == book.getCategory().getId() ? 'selected' : ''}>${category.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Số lượng</label>
                                    <input type="number" class="form-control" id="count" name="count" min="1" value="${book.getAmount()}">
                                </div>
                                <div class="form-group">
                                    <label>Ảnh bìa hiện tại</label>
                                    <br>
                                    <img src="${pageContext.request.contextPath}/Resources/img/products/${book.getImage()}" alt="Ảnh bìa" style="max-width: 200px;">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputFile">Thay ảnh bìa (Nếu cần)</label>
                                    <div class="input-group">
                                        <div class="custom-file">
                                            <input type="file" accept="image/png, image/jpeg" class="custom-file-input" id="customFile" name="fileImage">
                                            <label class="custom-file-label" for="customFile">Chọn file</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Tác giả</label>
                                    <input type="text" class="form-control" id="tacgia" name="tacgia" value="${book.getTacgia()}">
                                </div>
                                <div class="form-group">
    								<label>Nhà xuất bản</label>
    								<input type="text" class="form-control" id="nhaxuatban" name="nhaxuatban" value="${book.getNhaxuatban()}">
								</div>

                            </div>
                            <div class="card-footer">
                                <button type="submit" class="btn btn-primary">Lưu</button>
                                <input type="button" value="Trở lại" class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/ManageBook'">
                            </div>
                        </form>
                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>

<script src="./Resources/plugins/jquery/jquery.min.js"></script>
<script src="./Resources/plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<script src="./Resources/js/adminlte.min.js"></script>
<script src="./Resources/js/demo.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        bsCustomFileInput.init();
    });
</script>
