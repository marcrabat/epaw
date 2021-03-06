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
	if((tweet.author == user.user) || user.isAdmin == true){
		if(tweet.originalAuthor == user.user || user.isAdmin == true){
            myButtons += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' onClick='openModalPublishTweet(" + tweet.tweetID + ");'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span>&nbsp;edit </button>";
		}	
        myButtons += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' onClick='deleteTweet(" + tweet.tweetID + ");'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span>&nbsp;delete </button>";
	}
	return myButtons;
}

function generateTweetCard(tweet, userButtons) {

	if (tweet.author == tweet.originalAuthor)
		tweet.originalID = tweet.tweetID;

	var HTML = "";

    HTML += "<div class='row'>";
    HTML += "    <div class='col-sm-12'>";
    HTML += "        <div class='well well-sm' style='background-color: #f8f0f5;'>";
    HTML += "<div class='row'>";
       
        HTML += "    <div class='col-sm-2 heightCentered'>";
            HTML += "<img src='images/member1.jpg' class='img-circle' height='55' width='55' alt='Avatar'>";
        HTML += "    </div>";


        HTML += "    <div class='col-sm-10'>";
            HTML += "<div class='row'>";
                HTML += "<div class='card-title lefted' onClick='seeUserFeed(\"" + tweet.author + "\");'>" + "&nbsp;&nbsp;<b>" + tweet.author + "</b> " + "at: " + tweet.publishDate  + "</div>";

                if(tweet.author!=tweet.originalAuthor) {
                    HTML += "<div class='card-title' onClick='seeUserFeed(\"" + tweet.originalAuthor + "\");'";
                	HTML += "style='float: left; margin-left: 2%; font-size: 80%; color: grey;'>";
                    HTML += "<b>Original Author:</b> " + tweet.originalAuthor + "</div>";
                }

            HTML += "    </div>";
            

            HTML += "<div class='row lefted'>";
                HTML += "<p class='p2'>"+ tweet.message +"</p>";

                HTML += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' Onclick='insertLikeTweet("+tweet.tweetID+ ");'><span class='glyphicon glyphicon-heart' aria-hidden='true'></span>&nbsp;" + tweet.likes + "</button>"

                HTML += userButtons;

                HTML += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' onClick='commentTweet("+tweet.tweetID+")';'><span class='glyphicon glyphicon-comment' aria-hidden='true'></span>&nbsp;comment </button>"
                HTML += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' Onclick='retweet("+JSON.stringify(tweet)+");'><span class='glyphicon glyphicon-retweet' aria-hidden='true'></span>&nbsp;retweet </button>"
                HTML += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' Onclick='openModalFeedback("+ JSON.stringify(tweet) + ");'><span class='glyphicon glyphicon-list' aria-hidden='true'></span>&nbsp;view feedback </button>"

            HTML += "    </div>";     
        HTML += "    </div>";  

    HTML += "</div>";
    HTML += "        </div>";
    HTML += "    </div>";    
    HTML += "</div>";
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
			window.location.href = "/gamitter/feed";
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