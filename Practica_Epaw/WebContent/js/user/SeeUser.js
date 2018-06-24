function seeUserSearched() {
	var userSearched = getValue("searchUser");
	var userCleaned = escapeHtml(userSearched);
	seeUserFeed(userSearched);
}

function seeUserFeed(user) {
	var parametros = {userToLook: user, mode: "retrieveListOfTweetsForUser"};
	
	executeAjax(parametros, "feed", "POST",
			function(response) { successSeeUserFeed(response); }, 
			function(e) { alert("errror"); });
}

function successSeeUserFeed(response) {
	var result = parseJSON(response);
	
	if (result.errors != undefined && result.errors.length > 0) {
		showErrorsInAlert(result.errors);
	} else {
		window.location.href = "feed";
	}
}

function seeFollowingUsersTweets(user) {
	var parametros = {userToLook: user, mode: "viewFollowingTweets"};
	
	executeAjax(parametros, "feed", "POST",
			function(response) { window.location.href = "feed" }, 
			function(e) { alert("errror"); });
}

function seeUsers(mode) {
	var parametros = {mode: mode};
	
	executeAjax(parametros, "followers", "POST",
			function(response) { window.location.href = "followers" }, 
			function(e) { alert("errror"); });
}