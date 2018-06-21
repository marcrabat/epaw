<html>

<head>
    <title>Feed</title>
    
    <%@ include file='includes.jsp' %>
	<%@ include file='navbar.jsp' %>
    
    <script src="js/user/Profile.js"></script>
    <script src="js/tweets/Feed.js"></script>
    <script src="js/tweets/Feedback.js"></script>
    <script src="js/tweets/PublishTweet.js"></script>
    <script src="js/anonymous/Anonymous.js"></script>
    
    <script type="text/javascript">
		
		$(document).ready(function() {
			
			sessionId = '${sessionScope.Session_ID}';
			userToLook = '${sessionScope.userToLook}';
			sessionTweetFeedback = '${sessionScope.tweetFeedback}';
			var mode = "${sessionScope.modeOfFeed}";
			
			if (sessionId == "") {
				redirectToMainPage();
			} else {
				
				setupProfile();
				
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

				if(sessionId == "anonymous") {
	        		anonymousTweetsRequest(userToLook);
	        	} else {
					tweetsRequest(userToLook, mode);
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
