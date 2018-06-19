var sessionJsonTweet = null;
var sessionUser = null;

function openModalPublishTweet(tweetID) {
	var parametros = {
		tweetID : tweetID
	};
	executeAjax(parametros, "/Lab_3/tweetInformation", "POST", function(
			response) {
		successFillDivPublishTweet(response);
	}, function(e) {
		errorPublishTweet(e);
	});
}

function successFillDivPublishTweet(response) {

	if (response != null) {

		var tweet = response;

		try {
			tweet = JSON.parse(response);
		} catch (e) {
		}

		if (tweet.tweetID > -1) {
			fillDivPublishTweet(tweet);
			setValue("hiddenCommentTweetId", -1);
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
	modal.style.display = "none";
	setValue("hiddenTweetId", -1);
	setValue("hiddenCommentTweetId", -1);
	setValue("modalTweetMessage", "");
}

function publishTweet() {
	var tweetId = getValue("hiddenTweetId");
	var commentTweetId = getValue("hiddenCommentTweetId");
	var message = getValue("modalTweetMessage");

	if (message.length <= 255) {

		var tweet = {
			tweetID : tweetId,
			message : message,
			author : sessionUser.user,
			originalAuthor : sessionUser.user
		};

		jsonTweet = JSON.stringify(tweet);

		var parametros = {
			data : jsonTweet,
			commentTweetId : commentTweetId
		};
		executeAjax(parametros, "/Lab_3/publishTweet", "POST", function(
				response) {
			successPublishTweet(response);
		}, function(e) {
			errorPublishTweet(e);
		});

	} else {
		alert("This message is to long!");
	}
}

function retweet(tweet) {

	var user = sessionUser;
	try {
		user = JSON.parse(sessionUser);
	} catch (e) {
	}

	var tweet = {
		tweetID : -1,
		message : tweet.message,
		author : user.user,
		originalAuthor : tweet.originalAuthor,
		originalID : tweet.originalID
	};

	jsonTweet = JSON.stringify(tweet);

	var parametros = {
		data : jsonTweet,
		commentTweetId : -1
	};
	executeAjax(parametros, "/Lab_3/publishTweet", "POST", function(response) {
		successPublishTweet(response);
	}, function(e) {
		errorPublishTweet(e);
	});

}

function successPublishTweet(response) {


	var result = response;

	try {
		result = JSON.parse(response);
	} catch (e) {
	}

	if (result.errors.length > 0) {
		showErrorsInAlert(result.errors);
	} else {
		closePublishModal();
		window.location.href = window.location.href;
	}

}

function errorPublishTweet(e) {
	alert("Error Publishing Tweet .....");
}

function messageLength() {
	var message = getValue("modalTweetMessage");
	var messageLength = getElement("messageLength");
	var length = message.length;
	getElement("messageLength").innerHTML = length;

	if (length > 255) {
		messageLength.style.color = 'red';
	} else {
		messageLength.style.color = 'black';
	}
}