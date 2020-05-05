$(document).ready(function()
{
	$("#alertSuccess").hide();
	
	$("#alertError").hide();
	  $("#btnSave").click(function(){
		    alert("Submitted");
		  });
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide(); 
	
	//Form validation-------------------
	var status = validatePatientForm();
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	

	// If valid-------------------------
	
	//$("#formPatient").submit();
	
	var type = ($("#hiduserIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
			{
				url : "PatientAPI",
				type : type,
				data : $("#formPatient").serialize(),
				dataType : "text",
				complete : function(response, status) {
					onPatientSaveComplete(response.responseText, status);
				}
			});
 });

function onPatientSaveComplete(response, status) {
	if (status == "success")
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success")
				{
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				
				$("#divPatientGrid").html(resultSet.data);
				}else if (resultSet.status.trim() == "error")
					{
					$("#alertError").text(resultSet.data);
					$("#alertError").show();
					
					}
		}else if (status == "error")
		{
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		}else
			{
			$("#alertError").text("UnKnown error while saving..");
			$("#alertError").show();
			}
	
		$("#hiduserIDSave").val("");
		$("#formPatient")[0].reset();
	
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hiduserIDSave").val($(this).closest("tr").find('#hiduserIDUpdate').val());
	$("#Name").val($(this).closest("tr").find('td:eq(0)').text());
	$("#age").val($(this).closest("tr").find('td:eq(1)').text());
	$("#nic").val($(this).closest("tr").find('td:eq(2)').text());
	$("#phoneNo").val($(this).closest("tr").find('td:eq(3)').text());
});

// REMOVE=========================================================
$(document).on("click", ".btnRemove", function(event)
		{
			$.ajax(
					{
						url : "PatientAPI",
						type : "DELETE",
						data : "userID=" + $(this).data("userid"),
						dataType : "text",
						complete : function(response, status)
						{
							onPatientDeleteComplete(response.responseText, ststus);
						}
					});
		});

function onPatientDeleteComplete(response, status) {
	if (status == "success")
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success")
				{
				$("#alertSuccess").text("Successfully Delete.");
				$("#alertSuccess").show();
				
				$("#divPatientGrid").html(resultSet.data);
				}else if (resultSet.status.trim() == "error")
					{
					$("#alertError").text(resultSet.data);
					$("#alertError").show();
					
					}
		}else if (status == "error")
		{
			$("#alertError").text("Error while deleting.");
			$("#alertError").show();
		}else
			{
			$("#alertError").text("UnKnown error while deleting..");
			$("#alertError").show();
			}
	
		$("#hiduserIDSave").val("");
		$("#formPatient")[0].reset();
	
}

//CLIENT-MODEL================================================================
function validatePatientForm()
{

	// NAME
	if ($("#Name").val().trim() == "")
	{
		return "Insert Patient Name.";
	}

	// age-------------------------------
	if ($("#age").val().trim() == "")
	{
		return "Insert Patients' age.";
	}



	// nic------------------------
	if ($("#nic").val().trim() == "")
	{
		return "Insert NIC number.";
	}
	//nic length
	var tmpnic = $("#nic").val().trim();
	 if(tmpnic<=9 ||tmpnic>=11) {
		    return("invalid nic (Ex: 123456789v)");
		  }
	 
		//Phone no------------------------
		if ($("#phoneNo").val().trim() == "")
		{
			return "Insert Phone number.";
		}
		
	 //is pNO numerical value
	var tmpphoneNo = $("#phoneNo").val().trim();
	if (!$.isNumeric(tmpphoneNo))
	{
		return "Insert only numbers for Phone Number.";
	}

	return true;
	}
