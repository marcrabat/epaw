function seeUserSearched() {
	var userSearched = getValue("searchUser");
	var userCleaned = escapeHtml(userSearched);
	seeUserFeed(userSearched);
}

function seeUserFeed(user) {
	var parametros = {userToLook: user, mode: "retrieveListOfTweetsForUser"};
	
	executeAjax(parametros, "feed", "POST",
			function(response) { window.location.href = "feed" }, 
			function(e) { alert("errror"); });
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