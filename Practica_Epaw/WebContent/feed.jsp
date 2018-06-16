<html>

<head>
    <title>Feed</title>
    
    <%@ include file='includes.jsp' %>
	<%@ include file='navbar.jsp' %>
    
    <script src="js/user/Profile.js"></script>
    <script src="js/tweets/Feedback.js"></script>
    
    <script type="text/javascript">
		
		var sessionUser = null;
		
		$(document).ready(function() {
			
			sessionId = '${sessionScope.Session_ID}';
			userToLook = '${sessionScope.userToLook}';
			sessionTweetFeedback = '${sessionScope.tweetFeedback}';
			
			if (sessionId == "") {
				window.location.href = "/Lab_3/main";
			} else {
			
				clear();
				
				changeVisibility("gamerInfo");
				changeVisibility("socialNetwork");
				changeVisibility("profilePassword");
		
				disableInputs();
				//var userJSON = '{"userConsoles": []}';
				var userJSON = '${sessionScope.userInfo}';
				if (userJSON != "") {
					sessionUser = JSON.parse(userJSON);
					console.log(sessionUser);
					fillProfileForm(sessionUser);
					createAdminButtons(sessionUser);
				}
				
				if (userToLook == "") {
					changeVisibility("profileMenu");
					changeVisibility("profilePage");
				}
				
				if (userToLook != sessionId) {
					changeVisibility("editOrView");
					changeVisibility("newPassword");
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

        	if (sessionId == "") {
        		redirectToMainPage();
        	} else{
				TweetsRequest("${sessionScope.userToLook}");
        	}
        });
        
        function redirectToMainPage(){
        	window.location.href = "/Lab_3/main";
        }
        
        function TweetsRequest(username){
        	var parametros = {
        			data : username,
        			mode : "retrieveListOfTweetsForUser"
        		};
            $.ajax({
                type: 'post', //rest Type
                dataType: 'text', //mispelled
                url: "/Lab_3/checkFeedErrors",
                data: parametros,
                success: function (data) {
                    console.log(data);
                    var tweets = JSON.parse(data);
                    console.log(tweets);
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
        	if(tweet.author == user.user || user.isAdmin == true){
				myButtons = "<button class='mybtn'><i class='fa fa-hand-paper-o'";
        		myButtons += "onClick='openModalPublishTweet(" + tweet.tweetID + ");'> edit </i></button>";
                
        		myButtons += "<button class='mybtn'><i class='fa fa-close'";
        		myButtons += "onClick='deleteTweet(" + tweet.tweetID + ");'> delete </i></button>";
        	}
        	return myButtons;
        }
        
        function generateTweetCard(tweet, userButtons){
        	var HTML = "";
        	HTML += "<div class='card' style='width:30rem;'>";
        	HTML += "<div class='card-body'>";
        	HTML += "<h5 class='card-title' onClick='seeUserFeed(" + tweet.author + ");'>" + tweet.author + "</h5>";
        	HTML += "<h6 class='card-subtitle mb-2 text-muted'>" + "at: " + tweet.publishDate  + "</h6>";
        	HTML += "<p class='card-text'>" + tweet.message +"</p>";
        	HTML += "<button class='mybtn' Onclick='insertLikeTweet("+tweet.tweetID+ ");'><i class='fa fa-heart-o'>"+ " " + tweet.likes +"</i></button>";
        	HTML += userButtons;
        	HTML += "<button class='mybtn' onClick='commentTweet("+tweet.tweetID+")'><i class='fa fa-comment-o'>" + " comment </i></button>"
            HTML += "<button class='mybtn'><i class='fa fa-mail-reply-all'>" + " retweet </i></button>"
            HTML += "<button class='mybtn' Onclick='openModalFeedback("+ JSON.stringify(tweet) + ");'><i class='fa fa-eye'>" + " view feedback </i></button>"
            HTML += "</div></div>";
            return HTML;
        }
		
        function commentTweet(tweetID){
        	
        	setValue("hiddenCommentTweetId", tweetID);
        	openModalPublishTweet(-1);
        }
       	
        /*
        function viewFeedbackForTweet(tweetID){
        	
    		var parametros = {
    			data : tweetID,
    			mode : "retrieveFeedbackForTweet"
    		};
    		
    		console.log(parametros);
    		alert(parametros.mode);
    		
        	$.ajax({
                type: 'post', //rest Type
                dataType: 'text', //mispelled
                url: "/Lab_3/checkFeedErrors",
                data: parametros,
                success: function (data) {
                    console.log(data);
                    var feedbackTweets = JSON.parse(data);
                    console.log(feedbackTweets);
                    var tweet = '${sessionScope.tweetFeedback}';
                    feedbackIntoDivs(feedbackTweets, tweet);
                    
                },
                error: function(xhr,status,error) { alert("Error: " + error);} });
    		
        }
        
        function feedbackIntoDivs(tweets, tweetToInsertFeedback){
        	var i;
        	var HTML = "";
        	var JSONData = '${sessionScope.userInfo}';
        	var user = JSON.parse(JSONData);
        	
        	var divFeedbackId = "feedback_" + tweetToInsertFeedback;
        	
        	for (i = 0; i < tweets.length; i++) {
        		//HTML += generateHTML(tweets[i], user, true);
        		var newId = tweetToInsertFeedback + "_" + tweets[i].tweetID;
        		alert(newId);
        		HTML += generateTweetDiv(tweets[i],newId, "");
        	}
        	var feedbackId = divFeedbackId; // + "_" + tweets[i].twwetID;
     		var insertFeedback = document.getElementById(feedbackId);
     		console.log(insertFeedback);
     		alert(insertFeedback);
     		insertFeedback.innerHTML = HTML;
        }
		*/
        
        function insertLikeTweet(tweetID){
        	
        	var JSONData = '${sessionScope.userInfo}';
        	var user = JSON.parse(JSONData);
        	
        	var parametros = {
        			tweetID : tweetID,
        			username : user.user,
        			mode : "insertLikeForTweet"
        		};
            $.ajax({
                type: 'post', //rest Type
                dataType: 'text', //mispelled
                url: "/Lab_3/checkFeedErrors",
                data: parametros,
                success: function (data) {
                    console.log(data);
                },
                error: function(xhr,status,error) { alert("Error: " + error);} });
        }
        
		function deleteTweet(tweetID){

        	var parametros = {
        			data : tweetID,
        			mode : "deleteTweet"
        		};
        		
        	executeAjax(parametros, "/Lab_3/checkFeedErrors", "POST", 
					function(response) { successDeleteTweet(response); },
					function(e) { errorDeleteTweet(e); });
        }
		
		function successDeleteTweet(response) {
		
			console.log(response);
			
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
			<button id="buttonViewFollowers" class="btn btn-primary" onClick="viewFollowers();">View followers</button>
			<button id="buttonViewFollowings" class="btn btn-primary" onClick="viewFollowings();">View followings</button>
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
					<button type="button" class="btn btn-default" Onclick="closeFeedbackModal()">Close</button>
	    		</div>
	    		
	    	</div>
		      
	    </div>
	</div>


<!-- Tornar a afegir footer! tret per debuggar millor amb consola del navegador -->

</body>
</html>
