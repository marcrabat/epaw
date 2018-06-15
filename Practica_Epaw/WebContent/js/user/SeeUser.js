function seeUserFeed(user) {
	var parametros = {userToLook: user};
	
	executeAjax(parametros, "feed", "POST",
			function(response) { window.location.href = "feed" }, 
			function(e) { alert("errror"); });
}