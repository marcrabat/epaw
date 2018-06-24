<html>

<head>
    <title>Feed</title>
    
    <%@ include file='includesOldBoot.jsp' %>
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
					var userToLookInfo = parseJSON(userToLookJSON);
					sessionUser = JSON.parse(userJSON);
					if (userToLookInfo.user != undefined) {
						fillProfileForm(userToLookInfo);
					}
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
		        hr{
            padding: 0px;
            margin: 9px;    
        }
        .p2{
            margin: 2px 15px 10px 10px;
        }
        .well2{
            min-height: 20px;
            padding: 19px;
            margin-bottom: 20px;
        }
        .lefted{
            text-align:left !important;
        }
        .heightCentered{
            position: relative !important;
            top: 50% !important;
        }
	</style>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>


  <div class="container text-center wrapper">    
    <div class="row">


      <div class="col-sm-3">
          <div id="profilePage">
              <div class="well" style="background-color: #f8f0f5;">
              	<%@ include file='profile.jsp' %>
              </div>
          </div>  
      </div>

      
      
      
      <div class="col-sm-6" id="feedContent">
        <div class="row">
          <div class="col-sm-12">
            <div class="well" style="background-color: #f8f0f5;">
              <p>There are no messages to show.</p>
            </div>
          </div>
        </div>     
      </div>
      
      
      
      
      <div class="col-sm-3">
          <div id="profileMenu">
              <div class="well"  style="background-color: #f8f0f5;">
                <div class="well" id="buttonDeleteAccount" onClick="deleteSessionAccount();" style="background-color: #faf5f8;">
                  <p>Delete account</p>
                </div>
                <div class="well" id="buttonDeleteAllTweets" onClick="deleteAllTweets();" style="background-color: #faf5f8;">
                  <p>Delete all tweets</p>
                </div>
                <div class="well" id="buttonViewFollowers" onClick="seeUsers('followers');" style="background-color: #faf5f8;">
                  <p>View followers</p>
                </div>
                <div class="well" id="buttonViewFollowings" onClick="seeUsers('following');" style="background-color: #faf5f8;">
                  <p>View followings</p>
                </div>
              </div>  
          </div>
      </div>


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


  <%@ include file='footer.jsp' %>

	<!-- Tornar a afegir footer! tret per debuggar millor amb consola del navegador -->

</body>
</html>
