<html>

<head>
    <title>Feed</title>
    
    <%@ include file='includes.jsp' %>
	<%@ include file='navbar.jsp' %>
    
    <script src="js/user/Profile.js"></script>
    <script src="js/tweets/Feedback.js"></script>
    <script src="js/tweets/PublishTweet.js"></script>
    <script src="js/anonymous/Anonymous.js"></script>
    
    <script type="text/javascript">
		
		$(document).ready(function() {
			
			sessionId = '${sessionScope.Session_ID}';
			userToLook = '${sessionScope.userToLook}';
			sessionTweetFeedback = '${sessionScope.tweetFeedback}';
			if (sessionId == "") {
				window.location.href = "/gamitter/main";
			} else {
				clear();
				
				changeVisibility("gamerInfo");
				changeVisibility("socialNetwork");
				changeVisibility("profilePassword");
		
				disableInputs();
				var userToLookJSON = '${sessionScope.userToLookInfo}';
				var userJSON = '${sessionScope.userInfo}';
				if (userJSON != "") {
					var userToLookInfo = JSON.parse(userToLookJSON);
					sessionUser = JSON.parse(userJSON);
					fillProfileForm(userToLookInfo);
					createAdminButtons(sessionUser);
				}
				
				if (userToLook == "") {
					changeVisibility("profilePage");
				}
				
				if (userToLook != sessionId) {
					if (sessionUser.isAdmin == false) {
						changeVisibility("editOrView");
					}
					changeVisibility("newPassword");
					changeVisibility("profileMenu");
				}
	
			}
			
		});
	        
	</script>
    
    <style>
		.mybtn {
		    background-color: DodgerBlue; 
		    border: none; 
		    color: white; 
		    padding: 12px 16px; 
		    font-size: 16px; 
		    cursor: pointer; 
			}
	
		.mybtn:hover {
		    background-color: RoyalBlue;
		}
	</style>
	<script>
	
        /////////////////////// FUNCTIONS ////////////////////////    
        $(document).ready( function() {
        	var sessionId = "${sessionScope.Session_ID}";
			var userToLook = "${sessionScope.userToLook}";
			var mode = "${sessionScope.modeOfFeed}";
			
        	if (sessionId == "") {
        		redirectToMainPage();
        	} else if(sessionId == "anonymous") {
        		anonymousTweetsRequest(userToLook);
        	} else {
				tweetsRequest(userToLook, mode);
        	}
        });
        
        function redirectToMainPage(){
        	window.location.href = "/gamitter/main";
        }
                
        function tweetsRequest(username, mode){
        	var parametros = {
        			data : username,
        			mode : mode
        		};
            $.ajax({
                type: 'post', 
                dataType: 'text', 
                url: "/gamitter/checkFeedErrors",
                data: parametros,
                success: function (data) {
                    var tweets = JSON.parse(data);
                    tweetsIntoDivs(tweets);
                },
                error: function(xhr,status,error) { alert("Error: " + error);} });
        }

        function tweetsIntoDivs(tweets){
        	var i;
        	var HTML = "";
        	var JSONData = '${sessionScope.userInfo}';
        	var user = JSON.parse(JSONData);
        	for (i = 0; i < tweets.length; i++) {
        		HTML += generateHTML(tweets[i], user, false);
                document.getElementById("feedContent").innerHTML = HTML;
        	}	
        }

        function generateHTML(currentTweet, user, isFeedback){
        	var HTML = "";
        	var myButtons = genereteMyButtonsOfTweet(currentTweet, user);
			
        	
        	
        	if(isFeedback == true){
        		var HTML = "";
            	HTML += "<div id='"+tweetID+"'>";
            	HTML += generateTweetCard(tweet, userButtons);
            	HTML += "<div id='feedback_" + currentTweet.tweetID +"_" + "'> </div>";
            	HTML += "</div>";
        		return HTML;
        	}
        	
        	HTML = generateTweetDiv(currentTweet, currentTweet.tweetID, myButtons);
			return HTML;
        }
        
        function generateTweetDiv(tweet, tweetID, userButtons){
        	var HTML = "";
        	HTML += "<div id='"+tweetID+"'>";
        	HTML += generateTweetCard(tweet, userButtons);
        	HTML += "<div id='feedback_" + tweetID + "'> </div>";
        	HTML += "</div>";
            return HTML;
        }
        
        function genereteMyButtonsOfTweet(tweet, user) {
        	var myButtons = "";
        	if(user == "anonymous"){
				myButtons ="";
			}
        	if((tweet.author == user.user) || user.isAdmin == true){
				if(tweet.originalAuthor == user.user || user.isAdmin == true){
        			myButtons = "<button class='mybtn'><i class='fa fa-hand-paper-o'";
        			myButtons += "onClick='openModalPublishTweet(" + tweet.tweetID + ");'> edit </i></button>";
				}	
                
        		myButtons += "<button class='mybtn'><i class='fa fa-close'";
        		myButtons += "onClick='deleteTweet(" + tweet.tweetID + ");'> delete </i></button>";
        	}
        	return myButtons;
        }
        
        function generateTweetCard(tweet, userButtons){
        	
        	if(tweet.author==tweet.originalAuthor) 
        		tweet.originalID = tweet.tweetID;
        	
        	var HTML = "";
        	HTML += "<div class='card' style='width:30rem;'>";
        	HTML += "<div class='card-body'>";
        	HTML += "<h5 class='card-title' onClick='seeUserFeed(\"" + tweet.author + "\");'>" + tweet.author + "</h5>";
        	if(tweet.author!=tweet.originalAuthor) 
        		HTML += "<h6 class='card-title' onClick='seeUserFeed(" + tweet.orignalAuthor + ");'>" + "Original Author: " + tweet.originalAuthor + "</h6>";
        	HTML += "<h6 class='card-subtitle mb-2 text-muted'>" + "at: " + tweet.publishDate  + "</h6>";
        	HTML += "<p class='card-text'>" + tweet.message +"</p>";
        	HTML += "<button class='mybtn' Onclick='insertLikeTweet("+tweet.tweetID+ ");'><i class='fa fa-heart-o'>"+ " " + tweet.likes +"</i></button>";
        	HTML += userButtons;
        	if(sessionId != "anonymous"){
        		HTML += "<button class='mybtn' onClick='commentTweet("+tweet.tweetID+")'><i class='fa fa-comment-o'>" + " comment </i></button>"	
            	HTML += "<button class='mybtn' Onclick='retweet("+JSON.stringify(tweet)+");'><i class='fa fa-mail-reply-all'>" + " retweet </i></button>";	
        	}
            HTML += "<button class='mybtn' Onclick='openModalFeedback("+ JSON.stringify(tweet) + ");'><i class='fa fa-eye'>" + " view feedback </i></button>"
            HTML += "</div></div>";
            return HTML;
        }
		
        function commentTweet(tweetID){
        	
        	setValue("hiddenCommentTweetId", tweetID);
        	openModalPublishTweet(-1);
        }
        
        
       	
        
        function insertLikeTweet(tweetID){
        	
        	var JSONData = '${sessionScope.userInfo}';
        	var user = JSON.parse(JSONData);
        	
        	var parametros = {
        			tweetID : tweetID,
        			username : user.user,
        			mode : "insertLikeForTweet"
        		};
            $.ajax({
                type: 'post', 
                dataType: 'text', 
                url: "/gamitter/checkFeedErrors",
                data: parametros,
                success: function (data) {
                },
                error: function(xhr,status,error) { alert("Error: " + error);} });
        }
        
		function deleteTweet(tweetID){

        	var parametros = {
        			data : tweetID,
        			mode : "deleteTweet"
        		};
        		
        	executeAjax(parametros, "/gamitter/checkFeedErrors", "POST", 
					function(response) { successDeleteTweet(response); },
					function(e) { errorDeleteTweet(e); });
        }
		
		function successDeleteTweet(response) {
		
			
			var result = response;
			
			try {
				result = JSON.parse(response);
			} catch (e) {}
			
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
        
    </script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>
	<h1 style='center'> Feed</h1>
	<h2> Your feed</h2>
	<div id="content">
		
		
		<div id="profilePage" style="width:25%; float:left; position:relative; margin-left:2%;">
			<%@ include file='profile.jsp' %>
		</div>

		<div id="feedContent" style="width:50%; float:left"></div>

		<div id="profileMenu" style="position:relative; width:20%; float: right; margin-right:2%">
			<button id="buttonDeleteAccount" class="btn btn-primary" onClick="deleteSessionAccount();">Delete account</button>
			<button id="buttonDeleteAllTweets" class="btn btn-primary" onClick="deleteAllTweets();">Delete all tweets</button>
			<button id="buttonViewFollowers" class="btn btn-primary" onClick="seeUsers('followers');">View followers</button>
			<button id="buttonViewFollowings" class="btn btn-primary" onClick="seeUsers('following');">View followings</button>
		</div>
	</div>

	<div id="modalFeedbackTweet" class="modal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<div id="TweetContentModal"></div>
				</div>
				<div class="modal-body">
					<div id="feedbackContentModal"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default"
						Onclick="closeFeedbackModal()">Close</button>
				</div>
			</div>
	
		</div>
	</div>


	<!-- Tornar a afegir footer! tret per debuggar millor amb consola del navegador -->

</body>
</html>
