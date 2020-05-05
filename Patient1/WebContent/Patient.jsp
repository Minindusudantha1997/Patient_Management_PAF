<%@page import="com.Patient"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Patient.js"></script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6" id="colPatient">
				<h1>Patient Details</h1>
				<form  id="formPatient" name="formPatient">
					Patient Name:<input id="Name" name="Name" type="text"
						class="form-control form-control-sm"> <br> Age of
					patient: <input id="age" name="age" type="text"
						class="form-control form-control-sm"> <br> NIC number
					of patient: <input id="nic" name="nic" type="text"
						class="form-control form-control-sm"> <br> Phone
					number of guardian: <input id="phoneNo" name="phoneNo" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hiduserIDSave" name="hiduserIDSave" value="">&nbsp;&nbsp;<input
						id="btnReset" name="btnReset" type="reset" value="Reset"
						class="btn btn-primary">

				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>


				<div id="divPatientGrid">

					<%
						Patient p1 = new Patient();
						out.print(p1.readPatient());
					%>
				</div>
			</div>
		</div>
	</div>


</body>
</html>