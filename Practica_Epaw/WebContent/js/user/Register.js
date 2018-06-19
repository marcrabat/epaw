/////////////////////// FUNCTIONS ////////////////////////
function fillJson() {
	var userConsoles = new Array();
	$("input:checkbox[name=consoles]:checked").each(function() {
		if ($(this).val() != "") {
			userConsoles.push($(this).val());
		}
	});

	var gameGenres = new Array();
	$("input:checkbox[name=genres]:checked").each(function() {
		if ($(this).val() != "") {
			gameGenres.push($(this).val());
		}
	});

	var description = $('#description').val();

	var youtubeChannelID = "";
	if ($("#youtubeChannelID").val() != "") {
		youtubeChannelID = "https://www.youtube.com/channel/"
				+ $('#youtubeChannelID').val();
	}

	var twitchChannelID = "";
	if ($("#twitchChannelID").val() != "") {
		twitchChannelID = "https://www.twitch.tv/"
				+ $('#twitchChannelID').val();
	}

	var json = {
		name : $('#name').val(),
		surname : $('#surname').val(),
		mail : $('#mail').val(),
		user : $('#user').val(),
		password : $('#password').val(),
		birthDate : $('#birthDate').val(),
		description : escapeHtml(description),
		gender : $("input:radio[name=gender]:checked").val(),
		userConsoles : userConsoles,
		gameGenres : gameGenres,
		youtubeChannelID : youtubeChannelID,
		twitchChannelID : twitchChannelID
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

function clear() {
	$('#nameDanger').html("");
	$('#surnameDanger').html("");
	$('#mailDanger').html("");
	$('#userDanger').html("");
	$('#passwordDanger').html("");
	$('#birthDateDanger').html("");
	$('#serverSideDanger').html("");
	$('#validateDanger').html("");
};

function hasErrors() {

	clear();
	var hasError = false;

	if ($('#name').val() == "") {
		hasError = true;
		$('#nameDanger').html("The field is wrong!");
	}

	if ($('#surname').val() == "") {
		hasError = true;
		$('#surnameDanger').html("The field is wrong!");
	}

	if ($('#mail').val() == "") {
		hasError = true;
		$('#mailDanger').html("The email need to have the format xx@xx.xx");
	}

	if ($('#user').val() == "") {
		hasError = true;
		$('#userDanger').html("The field is wrong!");
	} else if($('#user').val() == "anonymous") {
		hasError = true;
		$('#userDanger').html("This username already exist!");
	}

	if ($('#password').val() == "") {
		hasError = true;
		$('#passwordDanger').html("The field is wrong!");
	}

	if ($('#birthDate').val() == "") {
		hasError = true;
		$('#birthDateDanger').html("The field is wrong!");
	}
	if ($('#password').val() != $('#password_conf').val()) {
		hasError = true;
		$('#passwordDanger').html("Password is not equal");
	}

	return hasError;
}

function jsonRequest(e) {

	var jsonObject = JSON.stringify(fillJson());

	e.preventDefault();

	if (hasErrors() == false) {
		$.ajax({
			url : '/Lab_3/checkErrors',
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
					alert("Your registry has been completed successfully");
					window.location.href = "/Lab_3/main.jsp";
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
	$('#validate').click(jsonRequest);

	//////////////////////////////////////////////////////////

});