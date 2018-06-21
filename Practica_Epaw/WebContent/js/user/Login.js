var sessionId = null;

function redirectToPersonalPage() {
	if (sessionId != null) {
		seeUserFeed("");
	} else {
		window.location.href = "gamitter/main";
	}
}

function clear() {
	$('#userDanger').html("");
	$('#passwordDanger').html("");
	$('#serverSideDanger').html("");
};

function hasErrors() {

	clear();
	var hasError = false;

	if ($('#user').val() == "") {
		hasError = true;
		$('#userDanger').html("The field is wrong!");
	}

	if ($('#password').val() == "") {
		hasError = true;
		$('#passwordDanger').html("The field is wrong!");
	}

	return hasError;
}

function fillJson() {
	var mailIntroduced = "";
	var userIntroduced = "";

	if ($('#user').val().includes("@")) {
		mailIntroduced = $('#user').val();
	} else {
		userIntroduced = $('#user').val();
	}

	var json = {
		mail : mailIntroduced,
		user : userIntroduced,
		password : $('#password').val()
	};

	return json;
}

function manageErrors(data) {

	if (typeof data.errors == "undefined")
		return;

	for (var i = 0; i < data.errors.length; i++) {
		var error = data.errors[i];
		if ($('#' + error.name + 'Danger').length) {
			$('#' + error.name + 'Danger').html(error.error);
		} else {
			$('#serverSideDanger').append(error.error + "\n");
		}
	}
}

function jsonRequest(e) {

	var jsonObject = JSON.stringify(fillJson());

	e.preventDefault();

	if (hasErrors() == false) {
		$.ajax({
			url : '/gamitter/checkLoginErrors',
			type : 'post',
			dataType : 'text',
			data : {
				data : jsonObject
			},
			success : function(data) {

				var result = JSON.parse(data);

				if (result.errors.length > 0) {
					manageErrors(result);
					$('#validateDanger').html("Check the form errors!");
				} else {
					redirectToPersonalPage();
				}

			},
			error : function(xhr, status, error) {
				alert("Error: " + error);
			}
		});
	} else {
		$('#validateDanger').html("Check the form errors!");
	}

}
//////////////////////////////////////////////////////////

$(window).load(function() {

	///////////////////////// EVENTS /////////////////////////

	//Sends data to the server
	$('#loginButton').click(jsonRequest);
	//////////////////////////////////////////////////////////

});

function goToRegister() {
	window.location.href = "/gamitter/register";
}