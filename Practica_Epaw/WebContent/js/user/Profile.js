function changeVisibility(id) {
	var element = $("#" + id);
	if (element.hasClass("displayNone") == true) {
		element.removeClass("displayNone");
	} else {
		element.addClass("displayNone");
	}
}

function fillProfileForm(user) {

	if (user != null) {

		var youtubeChannel = "https://www.youtube.com/channel/";
		var twitchChannel = "https://www.twitch.tv/";

		var youtubeChannelID = user.youtubeChannelID.substring(
				youtubeChannel.length, user.youtubeChannelID.length);
		var twitchChannelID = user.twitchChannelID.substring(
				twitchChannel.length, user.twitchChannelID.length);

		getElement("description").value = user.description;
		getElement("youtubeChannelID").value = youtubeChannelID;
		getElement("twitchChannelID").value = twitchChannelID;

		var consolesCheckbox = $("input:checkbox[name=consoles]");

		for (var i = 0; i < user.userConsoles.length; i++) {

			var userValue = user.userConsoles[i].trim();

			for (var j = 0; j < consolesCheckbox.size(); j++) {

				var checkboxValue = consolesCheckbox[j].value.trim();

				if (userValue == checkboxValue) {
					consolesCheckbox[j].checked = true;
					break;
				}

			}

		}

		var gameGenresCheckbox = $("input:checkbox[name=genres]");

		for (var i = 0; i < user.gameGenres.length; i++) {

			var userValue = user.gameGenres[i].trim();

			for (var j = 0; j < gameGenresCheckbox.size(); j++) {

				var checkboxValue = gameGenresCheckbox[j].value.trim();

				if (userValue == checkboxValue) {
					gameGenresCheckbox[j].checked = true;
					break;
				}

			}

		}

	}

}

function createAdminButtons(user) {
	if (user.isAdmin == true) {
		var profileMenu = getElement("profileMenu");
		var buttonDeleteUserAccount = createElement("button",
				"buttonDeleteUserAccount");
		buttonDeleteUserAccount.innerText = "Delete user account";
		buttonDeleteUserAccount.className = "btn btn-primary";
		profileMenu.appendChild(buttonDeleteUserAccount);

		/*
		var buttonDeleteUserAccount = createElement("button", "buttonDeleteUserAccount");
		buttonDeleteUserAccount.innerText = "Delete user account";
		profileMenu.appendChild(buttonDeleteUserAccount);
		 */
	}
};

function enableInputs() {
	$("#description").prop("disabled", false);

	$("input:checkbox[name=consoles]").each(function() {
		$(this).prop("disabled", false);
	});

	$("input:checkbox[name=genres]").each(function() {
		$(this).prop("disabled", false);
	});

	$("input:[type=text]").each(function() {
		$(this).prop("disabled", false);
	});

	$("#password").prop("disabled", false);

	$("#newPassword").prop("disabled", false);
}

function disableInputs() {

	$("#description").prop("disabled", true);

	$("input:checkbox[name=consoles]").each(function() {
		$(this).prop("disabled", true);
	});

	$("input:checkbox[name=genres]").each(function() {
		$(this).prop("disabled", true);
	});

	$("input:[type=text]").each(function() {
		$(this).prop("disabled", true);
	});

	$("#password").prop("disabled", true);

	$("#newPassword").prop("disabled", true);
}

function changeEditOrView() {
	if (getElement("editOrView").innerHTML == "View") {
		disableInputs();
		getElement("profileButtons").style = "display: none;";
		getElement("editOrView").innerHTML = "Edit";
	} else {
		enableInputs();
		getElement("profileButtons").style = "display: block;";
		getElement("editOrView").innerHTML = "View";

	}
}

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

	var newPassword = "";
	if ($("#newPassword").checked == true) {
		newPassword = getValue("password");
	}

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
		password : newPassword,
		description : $('#description').val().trim(),
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
	$('#descriptionDanger').html("");
	$('#passwordDanger').html("");
	$('#serverSideDanger').html("");
	$('#editDanger').html("");
};

function editProfile() {

	if (confirm("Are you sure?")) {

		var jsonObject = JSON.stringify(fillJson());
		var parametros = {
			data : jsonObject,
			mode : "editProfile"
		};

		executeAjax(parametros, "/Lab_3/checkProfileErrors", "POST", function(
				response) {
			succesEditProfile(response);
		}, function(e) {
			errorEditProfile(e);
		});

	}

}

function succesEditProfile(response) {

	console.log(response);

	var result = response;

	if (result.errors.length > 0) {
		manageErrors(result);
		$('#editDanger').html("Check the form errors!");
	} else {
		alert("the edit profile is good!");
		changeEditOrView();
	}

}

function errorEditProfile(e) {
	alert("Error.....");
}

function deleteSessionAccount() {

	if (sessionUser != null) {

		if (confirm("Are you sure?")) {

			var jsonObject = JSON.stringify(sessionUser);
			var parametros = {
				data : jsonObject,
				mode : "deleteAccount"
			};

			executeAjax(parametros, "/Lab_3/checkProfileErrors", "POST",
					function(response) {
						succesDeleteSessionAccount(response);
					}, function(e) {
						errorDeleteSessionAccount(e);
					});

		}

	}

}

function succesDeleteSessionAccount(response) {

	console.log(response);

	var result = response;

	if (result.errors.length > 0) {
		manageErrors(result);
	} else {
		alert("Your account deleted succesfully !!");
		window.location.href = "/Lab_3/main";
	}

}

function errorDeleteSessionAccount(e) {
	alert("Error trying to delete the account");
}

function deleteAllTweets() {

	if (sessionUser != null) {

		if (confirm("Are you sure?")) {

			var jsonObject = JSON.stringify(sessionUser);
			var parametros = {
				data : jsonObject,
				mode : "deleteAllTweets"
			};

			executeAjax(parametros, "/Lab_3/checkProfileErrors", "POST", succesDeleteAllTweets(response),
					function(e) { errorDeleteAllTweets(e);});

		}

	}

}

var succesDeleteAllTweets = function (response) {

	console.log(response);

	var result = response;

	if (result.errors.length > 0) {
		manageErrors(result);
	} else {
		alert("All your tweets are deleted !!");
	}

}

function errorDeleteAllTweets(e) {
	alert("Error trying to delete the tweets");
}

function viewFollowers() {
	alert("not implemented yet");
}

function viewFollowings() {
	alert("not implemented yet");
}