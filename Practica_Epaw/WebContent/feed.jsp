<html>

<head>
    <title>Feed</title>
    
    <%@ include file='includes.jsp' %>
	<%@ include file='navbar.jsp' %>
    
    <script src="js/user/Profile.js"></script>
    
    <script type="text/javascript">
		
		var sessionUser = null;
		
		$(document).ready(function() {
			
			sessionId = '${sessionScope.Session_ID}';
			userToLook = '${sessionScope.userToLook}';
			
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
        
        function feedbackIntoDivs(tweets, tweetToInsertFeedback){
        	var i;
        	var HTML = "";
        	var JSONData = '${sessionScope.userInfo}';
        	var user = JSON.parse(JSONData);
        	for (i = 0; i < tweets.length; i++) {
        		HTML += generateHTML(tweets[i], user, true);
        		var existFeedback = document.getElementById("feedback_" + tweetToInsertFeedback); 
                if(existFeedback != null && existFeedback != 'undefined'){
                	existFeedback.innerHTML = HTML;	
                } 
        	}
        	
        }
        
        function generateHTML(currentTweet, user, isFeedback){
        	/*If the tweet is mine or i'm an admin, total edition, o.w. without edit/delete*/
        	var myButtons = "";
			if(currentTweet.author == user.user || user.isAdmin == true){
				myButtons = "<button class='mybtn'><i class='fa fa-hand-paper-o'";
        		myButtons += "onClick='openModalPublishTweet(" + currentTweet.tweetID+ ");'> edit </i></button>";
                
        		myButtons += "<button class='mybtn'><i class='fa fa-close'> delete </i></button>";
        	}
			
			var width = 0;
			if(isFeedback == false){ //
            	width = 50;
            } else{
            	width = 30;
            }
			
        	var HTML = ""; 
        	HTML += "<div class='card' " + "id='tweet_" + currentTweet.tweetID + "' style='width:" + width + "rem;'>";
        	HTML += "<div class='card-body'>";
        	HTML += "<h5 class='card-title'>" + currentTweet.author + "</h5>";
        	HTML += "<h6 class='card-subtitle mb-2 text-muted'>" + "at: " + currentTweet.publishDate  + "</h6>";
        	HTML += "<p class='card-text'>" + currentTweet.message +"</p>";
        	HTML += "<button class='mybtn'><i class='fa fa-heart-o'>"+ " " + currentTweet.likes +"</i></button>";
        	HTML += myButtons;
        	HTML += "<button class='mybtn'><i class='fa fa-comment-o'>" + " comment </i></button>"
            HTML += "<button class='mybtn'><i class='fa fa-mail-reply-all'>" + " retweet </i></button>"
            HTML += "<button class='mybtn' Onclick='viewFeedbackForTweet("+currentTweet.tweetID+ ");'><i class='fa fa-eye'>" + " view feedback </i></button>"
            HTML += "</div></div>";
            
            if(isFeedback == false){ //
            	HTML +="<div id='feedback_" + currentTweet.tweetID + "'>";
    			HTML += "</div>"	
            }
            
			return HTML;
        }
		
       
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
        
        function successRetrieveFeedbackForTweet(response) {

        	console.log(response);

        	var result = response;

        	if (result.errors.length > 0) {
        		alert("There are some errors")
        	} else {
        		console.log("Need to implement insertion of feedback.");
        	}

        }

        function errorRetrieveFeedbackForTweet(e) {
        	alert("Error when loading the feed for this tweet.");
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


<!-- Tornar a afegir footer! tret per debuggar millor amb consola del navegador -->

</body>
</html>
