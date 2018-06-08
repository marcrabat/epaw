var sessionJsonTweet = null;
var sessionUser = null;

function openModalPublishTweet(tweetID) {
	var parametros = {tweetID: tweetID};
	executeAjax(parametros, "/Lab_3/tweetInformation", "POST", 
					function(response) { successFillDivPublishTweet(response); },
					function(e) { errorPublishTweet(e); });
}

function successFillDivPublishTweet(response) {

	if (response != null) {
		
		var tweet = response; 
		
		try {
			tweet = JSON.parse(response);
		} catch (e) {}
		
		if (tweet.tweetID > -1) {
			fillDivPublishTweet(tweet);
		} else {
			setValue("hiddenTweetId", -1);
		}
		showPublishModal();
	}

}

function fillDivPublishTweet(tweet) {
	if (tweet.tweetID > -1) {
		setValue("hiddenTweetId", tweet.tweetID);
		setValue("modalTweetMessage", tweet.message);
	} else {
		setValue("modalTweetMessage", "");
	}
}

function showPublishModal() {
	var modal = getElement('modalPublishTweet');
	modal.style.display = "block";
}

function closePublishModal() {
	var modal = getElement('modalPublishTweet');
	setValue("modalTweetMessage", "");
	modal.style.display = "none";
}

function publishTweet() {
	var tweetID = getValue("hiddenTweetId");
	var message = getValue("modalTweetMessage");
	
	var tweet = {
					tweetID: tweetID,
					message: message,
					author: sessionUser.user
				};
	
	alert(tweet);
	
	jsonTweet = JSON.stringify(tweet);
	
	var parametros = {data: jsonTweet};
	executeAjax(parametros, "/Lab_3/publishTweet", "POST", 
					function(response) { successPublishTweet(response); },
					function(e) { errorPublishTweet(e); });
}

function successPublishTweet(response) {
	
	console.log(response);
	
	var result = response;
	
	try {
		result = JSON.parse(response);
	} catch (e) {}
	
	if (result.errors.length > 0) { 
		showErrors(result.errors);
	} else {
		alert("You publish a tweet!");
		closePublishModal();
	}

}

function showErrors(errors) {
	var message = "";

	for (var i = 0; i < errors.length; i++) {
		message += errors[i].error + "\n";
	}

	if (message != "") {
		alert(message);
	}
}

function errorPublishTweet(e) {
	alert("Error Publishing Tweet .....");
}