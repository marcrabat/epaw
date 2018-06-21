function redirectToMainPage() {
	window.location.href = "/gamitter/main";
}

function tweetsRequest(username, mode) {
	var parametros = {
		data : username,
		mode : mode
	};

	$.ajax({
		type : 'post',
		dataType : 'text',
		url : "/gamitter/checkFeedErrors",
		data : parametros,
		success : function(data) {
			var tweets = parseJSON(data);
			
			tweetsIntoDivs(tweets);
		},
		error : function(xhr, status, error) {
			alert("Error: " + error);
		}
	});
}

function tweetsIntoDivs(tweets) {
	var i;
	var HTML = "";
	var JSONData = sessionUser;
	var user = parseJSON(JSONData);
	for (i = 0; i < tweets.length; i++) {
		HTML += generateHTML(tweets[i], user, false);
		document.getElementById("feedContent").innerHTML = HTML;
	}
}

function generateHTML(currentTweet, user, isFeedback) {
	var HTML = "";
	var myButtons = genereteMyButtonsOfTweet(currentTweet, user);

	if (isFeedback == true) {
		var HTML = "";
		HTML += "<div id='" + tweetID + "'>";
		HTML += generateTweetCard(tweet, userButtons);
		HTML += "<div id='feedback_" + currentTweet.tweetID + "_" + "'> </div>";
		HTML += "</div>";
		return HTML;
	}

	HTML = generateTweetDiv(currentTweet, currentTweet.tweetID, myButtons);
	return HTML;
}

function generateTweetDiv(tweet, tweetID, userButtons) {
	var HTML = "";
	HTML += "<div id='" + tweetID + "'>";
	HTML += generateTweetCard(tweet, userButtons);
	HTML += "<div id='feedback_" + tweetID + "'> </div>";
	HTML += "</div>";
	return HTML;
}

function genereteMyButtonsOfTweet(tweet, user) {
	var myButtons = "";

	if ((tweet.author == user.user) || user.isAdmin == true) {
		if (tweet.originalAuthor == user.user || user.isAdmin == true) {
			myButtons = "<button class='mybtn'><i class='fa fa-hand-paper-o'";
			myButtons += "onClick='openModalPublishTweet(" + tweet.tweetID
					+ ");'> edit </i></button>";
		}

		myButtons += "<button class='mybtn'><i class='fa fa-close'";
		myButtons += "onClick='deleteTweet(" + tweet.tweetID
				+ ");'> delete </i></button>";
	}
	return myButtons;
}

function generateTweetCard(tweet, userButtons) {

	if (tweet.author == tweet.originalAuthor)
		tweet.originalID = tweet.tweetID;

	var HTML = "";
	HTML += "<div class='card' style='width:30rem;'>";
	HTML += "<div class='card-body'>";
	HTML += "<h5 class='card-title' onClick='seeUserFeed(\"" + tweet.author
			+ "\");'>" + tweet.author + "</h5>";
	if (tweet.author != tweet.originalAuthor)
		HTML += "<h6 class='card-title' onClick='seeUserFeed("
				+ tweet.orignalAuthor + ");'>" + "Original Author: "
				+ tweet.originalAuthor + "</h6>";
	HTML += "<h6 class='card-subtitle mb-2 text-muted'>" + "at: "
			+ tweet.publishDate + "</h6>";
	HTML += "<p class='card-text'>" + tweet.message + "</p>";
	HTML += "<button class='mybtn' Onclick='insertLikeTweet(" + tweet.tweetID
			+ ");'><i class='fa fa-heart-o'>" + " " + tweet.likes
			+ "</i></button>";
	HTML += userButtons;

	HTML += "<button class='mybtn' Onclick='openModalFeedback("
			+ JSON.stringify(tweet) + ");'><i class='fa fa-eye'>"
			+ " view feedback </i></button>"
	HTML += "</div></div>";
	return HTML;
}

function commentTweet(tweetID) {

	setValue("hiddenCommentTweetId", tweetID);
	openModalPublishTweet(-1);
}

function insertLikeTweet(tweetID) {

	var JSONData = '${sessionScope.userInfo}';
	var user = parseJSON(JSONData);

	var parametros = {
		tweetID : tweetID,
		username : user.user,
		mode : "insertLikeForTweet"
	};
	$.ajax({
		type : 'post',
		dataType : 'text',
		url : "/gamitter/checkFeedErrors",
		data : parametros,
		success : function(data) {
		},
		error : function(xhr, status, error) {
			alert("Error: " + error);
		}
	});
}

function deleteTweet(tweetID) {

	var parametros = {
		data : tweetID,
		mode : "deleteTweet"
	};

	executeAjax(parametros, "/gamitter/checkFeedErrors", "POST", function(
			response) {
		successDeleteTweet(response);
	}, function(e) {
		errorDeleteTweet(e);
	});
}

function successDeleteTweet(response) {

	var result = parseJSON(response);

	if (result.errors.length > 0) {
		showErrorsInAlert(result.errors);
	} else {
		alert("The tweet was deleted");
		window.location.href = window.location.href;
	}
}

function errorDeleteTweet(e) {
	alert("Error Deleting Tweet .....");
}