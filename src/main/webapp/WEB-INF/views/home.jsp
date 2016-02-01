<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<security:csrfInput />
<security:csrfMetaTags />
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!--Import Google Icon Font-->
<link href="http://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!--Import materialize.css-->
<!-- Compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">

<!--Import jQuery before materialize.js-->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<!--Let browser know website is optimized for mobile-->
<!-- Compiled and minified JavaScript -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.js"></script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.8/css/dataTables.bootstrap.min.css">
<script
	src="https://cdn.datatables.net/1.10.8/js/jquery.dataTables.min.js"></script>
<!-- <script -->
<!-- 	src="https://cdn.datatables.net/1.10.8/js/dataTables.bootstrap.min.js"></script> -->

<script>
	$(document).ready(function() {
		// the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
		$('.modal-trigger').leanModal();
	});
</script>
<style type="text/css">
body {
	background-color: #f4f4f4;
}

.modal {
	max-height: 75%
}

h4 {
	border-bottom: 5px ridge;
	color: #424242;
}

.col-sm-6 {
	width: 50% !important;
	float: left;
}

.col-sm-5 {
	width: 40% !important;
	float: left;
}

.col-sm-7 {
	width: 60% !important;
	float: left;
}

th {
	width: auto !important;
}

.alert {
	display: block;
}

.alrt {
	top: 30px;
	position: absolute;
	z-index: 9999;
	width: 40% !important;
	margin-left: 30%;
	padding: 0 30px;
	margin-top: 0px;
	font-weight: lighter;
	font-size: 24px;
	border-radius: 0px !important;
	height: 60px;
	line-height: 20px;
}

.dataTables_length select {
	height: 30px;
}
</style>

</head>
<title>Papoye | User</title>
<body>
	<div class="alert center-align alrt fade in">
		<div class="col-xs-12">
			<div class="col-xs-1">
				<span id="sign" style="font-size: 32px; line-height: 1.2"></span>
			</div>
			<div class="col-xs-11">
				<p id="al_body" style="color: white;"></p>
			</div>
		</div>
	</div>
	<!-- LOGIN PART -->
	<nav id="menubar" style="display: block">
		<div class="nav-wrapper">
			<a href="home" class="brand-logo">&nbsp;Papoye User Management
				Admin </a>
			<ul class="right hide-on-med-and-down" id="header2">
				<li><a class="btn waves-effect waves-light modal-trigger"
					data-target="modalAdd"><i class="material-icons left"
						style="margin-top: -7%">group_add</i> Create New User </a></li>
				<li><a href="#">Welcome ${sessionScope.user.firstName}</a></li>
				<li><a href="<c:url value="slogout" />">Logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<c:if test="${sessionScope.user eq null}">
			<script type="text/javascript">
				$("#header2").remove();
			</script>
			<div class="col s12 main-container" style="margin-top: 10%">
				<div class="row">
					<div class="col s3">&nbsp;</div>
					<div class="col s6 grey lighten-5 z-depth-1">
						<div class="row">
							<div class="input-field col s12 center "
								style="border-bottom: 1px ridge #eeeeee; font-size: 24px; padding-bottom: 2%;">


								LOGIN</div>
							<form:form modelAttribute="user" action="j_spring_security_check"
								method="post" autocomplete="off">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<div class="input-field col s12">

									<form:input id="icon_prefix" type="text" cssClass="validate"
										path="email" />
									<label for="icon_prefix" data-error="wrong"
										data-success="right">Email /Mobile</label>
								</div>
								<div class="input-field col s12">

									<form:password id="icon_prefix" cssClass="validate"
										path="password" />
									<label for="icon_prefix" data-error="wrong"
										data-success="right">password</label>
								</div>
								<div class="col s12" style="margin-top: 5%">

									<div class="col s6">
										<form:button type="submit"
											class="modal-action waves-effect waves-light btn right blue lighten-1"
											style="margin-right: 1%; width: 100%">
											Login<i class="material-icons left">done</i>
										</form:button>
									</div>
									<div class="col s6">
										<form:button type="reset" style="width: 100%"
											class=" modal-action waves-effect waves-light btn right red lighten-1">
											Clear<i class="material-icons left">highlight_off</i>
										</form:button>
									</div>




								</div>
							</form:form>
						</div>

					</div>
					<div class="col s3">&nbsp;</div>
				</div>
			</div>
		</c:if>
	</div>
	<!-- Inner part aftet Login -->
	<c:if test="${sessionScope.user ne null}">
		<div class="container">
			<div class="col s12" id="inner_main"
				style="display: block; margin-bottom: 5%">
				<table class="highlight table responsive-table" id="table1">
					<thead>
						<tr class="grey lighten-2">
							<th data-field="id">Id</th>
							<th data-field="name">Name of User</th>
							<th data-field="email">Email Address</th>
							<th data-field="mobile">Mobile No.</th>
							<th data-field="Status">Status</th>
							<th data-field="Edit">Edit</th>
							<th data-field="delete">Delete</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${allUsers}" var="user" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<td>${user.firstName}&nbsp;${user.lastName}</td>
								<td>${user.email}</td>
								<td>${user.mobileNumber}</td>
								<td style="color: red;"><c:if test="${user.enable}">Enable</c:if>
									<c:if test="${!user.enable}">Disable</c:if></td>
								<td><button
										class="btn waves-effect waves-light modal-trigger blue"
										data-target="modal1" id="${user.userId}"
										onclick="editDataLoad(this);">
										Edit<i class="material-icons left">border_color</i>
									</button></td>
								<td><button class="btn waves-effect waves-light red"
										name="action" onclick="DeleteUser('${user.userId}');">
										Delete<i class="material-icons left">delete</i>
									</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
		<div id="modalAdd" class="modal">
			<form:form action="adduser" modelAttribute="user">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="modal-content">
					<h4 class="center">Add New User</h4>

					<div class="col s12">
						<div class="row">
							<div class="input-field col m4 s12">

								<form:input id="firstName" type="text" class="validate"
									path="firstName" pattern="[a-zA-Z]{0,255}"
									title="Only alphabets are allowed" />
								<label for="lastName" data-error="wrong" data-success="right">First
									Name</label>
							</div>
							<div class="input-field col m4 s12">

								<form:input id="lastName" type="text" class="validate"
									path="lastName" pattern="[a-zA-Z]{0,255}"
									title="Only alphabets are allowed" />
								<label for="lastName" data-error="wrong" data-success="right">Last
									Name</label>
							</div>
							<div class="input-field col m4 s12">

								<form:input id="icon_telephone1" type="text" class="validate"
									path="mobileNumber" pattern="[7-9]{1}[0-9]{9}"
									title="Only Mobile number is acceptable"
									onblur="if(this.value)ExistUser(this);" />
								<label for="icon_telephone1" data-error="wrong"
									data-success="right">Mobile No.</label>
							</div>
						</div>

						<div class="row">
							<div class="input-field col m8 s12">

								<form:input id="email1" type="email" class="validate"
									path="email" onblur="if(this.value)ExistUser(this);" />
								<label for="email1" data-error="wrong" data-success="right">Email
									Address</label>
							</div>
							<div class="input-field col m4 s12">
								<form:password id="password" class="validate" path="password" />
								<label for="password" data-error="wrong" data-success="right">Password</label>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<a href="#!"
						class=" modal-action modal-close waves-effect waves-light btn red"
						style="margin-left: 1%">Cancel</a>
					<button type="submit"
						class=" modal-action waves-effect waves-light  btn green">OK</button>
				</div>
			</form:form>
		</div>

		<div id="modal1" class="modal">
			<form:form modelAttribute="user" action="edituser">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<form:hidden path="userId" id="edituserId" />
				<form:hidden path="password" id="editpassword" />
				<div class="modal-content">
					<h4 class="center">Edit User Information</h4>
					<div class="col s12">
						<div class="row">
							<div class="input-field col s12 m6">
								<form:input id="editfirstName" type="text" class="validate"
									path="firstName" pattern="[a-zA-Z]{0,255}"
									title="Only alphabets are allowed" />
								<label for="editfirstName" data-error="wrong"
									data-success="right">First Name</label>
							</div>
							<div class="input-field col s12 m6">

								<form:input id="editlastName" type="text" class="validate"
									path="lastName" pattern="[a-zA-Z]{0,255}"
									title="Only alphabets are allowed" />
								<label for="editlastName" data-error="wrong"
									data-success="right">Last Name</label>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s12">

								<form:input id="editemail" type="email" class="validate"
									path="email" onpaste="if(this.value)EditExistUser(this);"
									onchange="if(this.value)EditExistUser(this);" />
								<label for="editemail" data-error="wrong" data-success="right">Email
									Address</label>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s6">

								<form:input id="editMobile" type="text" class="validate"
									path="mobileNumber" pattern="[7-9]{1}[0-9]{9}"
									title="Only Mobile number is acceptable"
									onpaste="if(this.value)EditExistUser(this);"
									onkeydown="if(this.value)EditExistUser(this);" />
								<label for="editMobile" data-error="wrong" data-success="right">Mobile
									No.</label>
							</div>

							<div class="input-field col s6">
								<!-- Switch -->
								<p>
									<form:radiobutton path="enable" value="true" class="with-gap" />
									<label for="enable1">Enable</label>
									<form:radiobutton path="enable" value="false" class="with-gap" />
									<label for="enable2">Disable</label>
								</p>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<a href="#!"
						class="modal-action modal-close waves-effect waves-light btn red"
						style="margin-left: 1%">Cancel</a>
					<button type="submit"
						class=" modal-action waves-effect waves-light  btn green">OK</button>
				</div>
			</form:form>
		</div>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#table1').DataTable();
			});
		</script>
		<script type="text/javascript">
			var email, mobile;
			function editDataLoad(param) {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				$.ajax({
					type : "GET",
					url : 'existUser',
					data : "id=" + param.id,
					beforeSend : function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success : function(obj) {
						if (obj != null) {
							$("#edituserId").val(obj.userId);
							$("#editpassword").val(obj.password);
							$("#editfirstName").val(obj.firstName);
							$("label[for='editfirstName']").addClass('active');
							$("#editlastName").val(obj.lastName);
							$("label[for='editlastName']").addClass('active');
							$("#editemail").val(obj.email);
							email = obj.email;
							mobile = obj.mobileNumber;
							$("label[for='editemail']").addClass('active');
							$("#editMobile").val(obj.mobileNumber);
							$("label[for='editMobile']").addClass('active');
							if (obj.enable) {
								$("#enable1").attr('checked', true);
							} else {
								$("#enable2").attr('checked', true);
							}
						}
					},
					error : function(res) {
						console.log(res.responseText);
					}
				});
			}

			function DeleteUser(param) {
				if (confirm("Are you want to delete this user!")) {
					var token = $("meta[name='_csrf']").attr("content");
					var header = $("meta[name='_csrf_header']").attr("content");
					$
							.ajax({
								type : "POST",
								url : 'deleteUser',
								data : "id=" + param,
								beforeSend : function(xhr) {
									xhr.setRequestHeader(header, token);
								},
								success : function(obj) {
									if (obj) {
										window.location.href = 'deleteredirect?status=true';
									} else {
										window.location.href = 'deleteredirect?status=false';
									}
								},
								error : function(res) {
									console.log(res.responseText);
									window.location.href = 'deleteredirect?status=false';
								}
							});
				}
			}
			function EditExistUser(param) {
				if (param.value.indexOf("@") > -1 && param.value != email) {
					ExistUser(param);
				} else if (param.value != mobile) {
					ExistUser(param);
				}
			}
			function ExistUser(param) {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				$.ajax({
					type : "GET",
					url : 'checkuser',
					data : 'username=' + param.value,
					beforeSend : function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success : function(obj) {
						if (obj) {
							if (param.value.indexOf("@") > -1) {
								alert("This email is already exist");
							} else {
								alert("This mobile number is already exist");
							}
							param.value = '';
							param.focus();
						}
					},
					error : function(res) {
						console.log(res.responseText);
					}
				});
			}
		</script>
	</c:if>
	<c:if test="${error ne null}">
		<script type="text/javascript">
			$("#al_body").html('${error}');
			$('.alert').show();
			$(".alert").addClass("red darken-2");
			$('.alrt').delay(5000).fadeOut(220, function() {
				$(this).remove();
			});
		</script>
	</c:if>
	<c:if test="${info ne null}">
		<script type="text/javascript">
			$("#al_body").html('${info}');
			$('.alert').show();
			$(".alert").addClass("blue darken-2");
			$('.alrt').delay(5000).fadeOut(220, function() {
				$(this).remove();
			});
		</script>
	</c:if>
	<c:if test="${success ne null}">
		<script type="text/javascript">
			$("#al_body").html('${success}');
			$('.alert').show();
			$(".alert").addClass("green darken-2");
			$('.alrt').delay(5000).fadeOut(220, function() {
				$(this).remove();
			});
		</script>
	</c:if>
</body>
</html>