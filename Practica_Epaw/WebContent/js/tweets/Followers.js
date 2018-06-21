var followersSessionId = null;
var user;
var mode;

function redirectToMain() {
	window.location.href = "/gamitter/main"
}

function jsonRequest() {

	var userJSON = user;
	if (userJSON != "") {

		sessionUser = parseJSON(userJSON);

		if (mode == "")
			mode = "followers";
		var parametros = {
			data : sessionUser.user,
			mode : mode
		};

		$.ajax({
				url : '/gamitter/checkFollowers',
				type : 'post',
				dataType : 'text',
				data : parametros,
				success : function(data) {

					var result = parseJSON(data);

					if (!result.length)
						return;

					for (var i = 0; i < result.length; i++) {
						var result_parts = result[i].split(',');
						if (result_parts[1] == 0) {
							$('#followers').append(
											'<li class="list-group-item">'
													+ result_parts[0]
													+ '<button type="button" id='
													+ "'"
													+ result_parts[0]
													+ "'"
													+ ' class="btn btn-light" style="margin-left: 70%;" onclick="changeRelation('
													+ "'"
													+ result_parts[0]
													+ "'"
													+ ","
													+ "'"
													+ "insert"
													+ "'"
													+ ')">Follow</button></li>');
						} else if (result_parts[1] == 1) {
							$('#followers').append(
											'<li class="list-group-item">'
													+ result_parts[0]
													+ '<button type="button" class="btn btn-secondary" style="margin-left: 70%;" onclick="changeRelation('
													+ "'"
													+ result_parts[0]
													+ "'"
													+ ","
													+ "'"
													+ "delete"
													+ "'"
													+ ')">Following</button></li>');
						}
					}
				},
				error : function(xhr, status, error) {
					alert("Error: " + error);
				}
			});
	}

}

function changeRelation(userB_, mode_) {

	var userJSON = user;
	if (userJSON != "") {

		sessionUser = parseJSON(userJSON);
		var parametros = {
			userA : sessionUser.user,
			userB : userB_,
			mode : mode_
		};

		$.ajax({
			url : '/gamitter/changeRelation',
			type : 'post',
			dataType : 'text',
			data : parametros,
			success : function(data) {
				location.reload();
			},
			error : function(xhr, status, error) {
				alert("Error: " + error);
			}
		});
	}
}