function seeUserFeed(user) {
	var parametros = {userToLook: user};
	
	executeAjax(parametros, "feed", "POST",
			function(response) { }, 
			function(e) { alert("errror"); });
}