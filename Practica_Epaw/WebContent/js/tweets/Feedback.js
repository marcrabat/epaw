var sessionFeedbackTweet = null;

function openModalFeedback(jsonTweet) {
	
	var tweet = jsonTweet;
	try {
		tweet = JSON.parse(jsonTweet);
	} catch(e) {}
	
	var parametros = {
			data : tweet.tweetID,
			mode : "retrieveFeedbackForTweet"
	};
	
	sessionFeedbackTweet = tweet;

	executeAjax(parametros, "/Lab_3/checkFeedErrors", "POST", 
					function(response) { successFillDivFeedbackTweet(response); },
					function(e) { errorFeedbackTweet(e); });
}

function openModalFeedbackAnonymous(jsonTweet) {
	
	var tweet = jsonTweet;
	try {
		tweet = JSON.parse(jsonTweet);
	} catch(e) {}
	
	var parametros = {
			data : tweet.tweetID,
			mode : "retrieveFeedbackForAnonymousTweets"
	};
	
	sessionFeedbackTweet = tweet;

	executeAjax(parametros, "/Lab_3/checkFeedErrors", "POST", 
					function(response) { successFillDivFeedbackTweet(response); },
					function(e) { errorFeedbackTweet(e); });
}


function successFillDivFeedbackTweet(response) {

	if (response != null) {
		
		var feedbackTweets = response; 
		
		try {
			feedbackTweets = JSON.parse(response);
		} catch (e) {}
		
		if (feedbackTweets.length > 0) {
			fillDivHeaderTweet();
			fillDivFeedbackTweet(feedbackTweets);
			showFeedbackModal();
		} else {
			generateNoMessagesAvailable();
			showFeedbackModal();			
		}
		
	}

}



function fillDivHeaderTweet() {
	var tweet = sessionFeedbackTweet;
	var myButtons = genereteMyButtonsOfTweet(tweet, sessionUser);
	var html = generateTweetCard(tweet, myButtons); 
	setHtml("TweetContentModal", html);
}

function fillDivFeedbackTweet(feedbackTweets) {
	var html = "";
	var user = sessionUser;

	for (var i = 0; i < feedbackTweets.length; i++) {
		var tweet = feedbackTweets[i];
		var myButtons = genereteMyButtonsOfTweet(tweet, sessionUser);
		html += generateTweetCard(tweet, myButtons);
	}
	
	setHtml("feedbackContentModal", html);
}

function generateNoMessagesAvailable(){
	setHtml("feedbackContentModal", "No messages available.");
}

function showFeedbackModal() {
	var modal = getElement('modalFeedbackTweet');
	modal.style.display = "block";
}

function closeFeedbackModal() {
	var modal = getElement('modalFeedbackTweet');
	modal.style.display = "none";
	setHtml("feedbackContentModal", "");
	setHtml("TweetContentModal", "");
}


function errorFeedbackTweet(e) {
	alert("Error at showing the feedback!");
}