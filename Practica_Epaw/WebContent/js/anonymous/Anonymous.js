function anonymousTweetsRequest(username){
        	var parametros = {
        			data : username,
        			mode : "retrieveListOfTweetsForAnonymous"
        		};
        	$.ajax({
                type: 'post', 
                dataType: 'text', 
                url: "/gamitter/checkFeedErrors",
                data: parametros,
                success: function (data) {
                    var tweets = JSON.parse(data);
                    anonymousTweetsIntoDivs(tweets);
                },
                error: function(xhr,status,error) { alert("Error: " + error);} });
        }

function anonymousTweetsIntoDivs(tweets){
	var HTML = "";
	var i;
	for (i = 0; i < tweets.length; i++) {
		HTML += generateAnonymousHTML(tweets[i]);
        document.getElementById("feedContent").innerHTML = HTML;
	}	
}

function generateAnonymousHTML(tweet){
	var HTML = "";
	HTML += "<div id='"+tweet.tweetID+"'>";
	HTML += generateAnonymousTweetCard(tweet);
	HTML += "<div id='feedback_" + tweet.tweetID + "'> </div>";
	HTML += "</div>";
    return HTML;
}

function generateAnonymousTweetCard(tweet){
	if(tweet.author==tweet.originalAuthor) 
		tweet.originalID = tweet.tweetID;
	var HTML = "";
	HTML += "<div class='card' style='width:30rem;'>";
	HTML += "<div class='card-body'>";
	HTML += "<h5 class='card-title' onClick='seeUserFeed(\"" + tweet.author + "\");'>" + tweet.author + "</h5>";
	if(tweet.author!=tweet.originalAuthor) 
		HTML += "<h6 class='card-title' onClick='seeUserFeed(\"" + tweet.orignalAuthor + "\");'>" + "Original Author: " + tweet.originalAuthor + "</h6>";
	HTML += "<h6 class='card-subtitle mb-2 text-muted'>" + "at: " + tweet.publishDate  + "</h6>";
	HTML += "<p class='card-text'>" + tweet.message +"</p>";
	HTML += "<button class='mybtn'><i class='fa fa-heart-o'>"+ " " + tweet.likes +"</i></button>";
    HTML += "<button class='mybtn' Onclick='openModalFeedbackAnonymous("+ JSON.stringify(tweet) + ");'><i class='fa fa-eye'>" + " view feedback </i></button>"
    HTML += "</div></div>";
    return HTML;
}