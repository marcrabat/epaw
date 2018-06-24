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

                //if(tweet.author!=tweet.originalAuthor) 
                //    HTML += "<div class='card-title' onClick='seeUserFeed(" + tweet.orignalAuthor + ");'>" + "Original Author: " + tweet.originalAuthor + "</div>";
            HTML += "    </div>";
            

            HTML += "<div class='row lefted'>";
                HTML += "<p class='p2'>"+ tweet.message +"</p>";
                var mess = "You have to log in to like a tweet";
                HTML += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' Onclick='alert("+mess+ ");'><span class='glyphicon glyphicon-heart' aria-hidden='true'></span>&nbsp;" + tweet.likes + "</button>"

                HTML += "&nbsp;&nbsp;<button type='button' class='btn btn-default btn-xs' Onclick='openModalFeedbackAnonymous("+ JSON.stringify(tweet) + ");'><span class='glyphicon glyphicon-list' aria-hidden='true'></span>&nbsp;view feedback </button>"

            HTML += "    </div>";     
        HTML += "    </div>";  

    HTML += "</div>";
    HTML += "        </div>";
    HTML += "    </div>";    
    HTML += "</div>";

    return HTML;
}