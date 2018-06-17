function seeUserFeed(user) {
	var parametros = {userToLook: user};
	
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