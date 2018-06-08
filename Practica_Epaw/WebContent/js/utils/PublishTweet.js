var sessionJsonTweet = null;

function generateDivPublishTweet(tweetID) {
	var parametros = {tweetID: tweetID};
	executeAjax(parametros, "/Lab_3/tweetInformation", "POST", 
					function(response) { successFillDivPublishTweet(response); },
					function(e) { errorPublishTweet(e); });
}

function successFillDivPublishTweet(response) {
	
	// TODO Pasar las variables de session a la jsp, y rellenar una variable global.
	
	var jsonTweet = "${sessionScope.Session_ID}";
	if (jsonTweet != "") {
		var tweet = JSON.parse(tweet);
		createDivPublishTweet(tweet);
		fillDivPublishTweet(tweet);
	} else {
		var tweet = "";
		createDivPublishTweet(tweet);
	}
	
	/*
	console.log(response);
	
	var result = JSON.parse(response);
	
	if (result.errors.length > 0) { 
		showErrors(result.errors);
	} else {
		var jsonTweet = "${sessionScope.tweetInfo}";
		if (jsonTweet != "") {
			var tweet = JSON.parse(tweet);
			createDivPublishTweet(tweet);
			fillDivPublishTweet(tweet);
		}
	}
	*/

}

function showErrors(errors) {
	var message = "";

	for (var i = 0; i < errors.length; i++) {
		message += errors[i] + "\n";
	}

	if (message != "") {
		alert(message);
	}
}

function createDivPublishTweet(tweet) {
	var div = createElement("div", "divPublishTweet");
	var divMessage = createElement("div", "divMessagePublishTweet");
	var inputMessage = createElement("textarea", "tweetMessage");
	
	divMessage.appendChild(inputMessage);
	
	var divButtons = createElement("div", "divPublishTweet");
	var buttonPublish = createElement("button", "buttonPublishTweet");
	buttonPublish.onclick = function(tweetID) { publishTweet(tweet) }
	var buttonCancel = createElement("button", "buttonCancelPublish");
	buttonCancel.onclick = function() { document.removeChild("divPublishTweet"); }
	
	divButtons.appendChild(buttonPublish);
	divButtons.appendChild(buttonCancel);
	
	div.appendChild(divMessage);
	div.appendChild(divButtons);
}

function fillDivPublishTweet(tweet) {
	if (tweet.tweetID > -1) {
		setValue("tweetMessage", tweet.message);
	} else {
		setValue("tweetMessage", "");
	}
}

function publishTweet(tweet) {
	var message = getValue("tweetMessage");
	var parametros = {data: tweet};
	executeAjax(parametros, "/Lab_3/PublishTweetController", "POST", 
					function(response) { successPublishTweet(response); },
					function(e) { errorPublishTweet(e); });
}

function successPublishTweet(response) {
	
	console.log(response);
	
	var result = JSON.parse(response);
	
	if (result.errors.length > 0) { 
		showErrors(result.errors);
	} else {
		alert("You publish a tweet!");
		document.removeChild("divPublishTweet");
	}

}

function errorPublishTweet(e) {
	alert("Error Publishing Tweet .....");
}