<script src="js/tweets/PublishTweet.js"></script>

<script>

	$(document).ready(function() {
		
		sessionUser = '${sessionScope.Session_ID}';
		sessionJsonTweet = '${sessionScope.tweetInfo}';
		
		if(sessionUser == "anonymous"){
			var publishButton = document.getElementById('buttonPublishTweet');
			publishButton.style.display='none';
			var profileButton = document.getElementById('profileButton');
			profileButton.style.display='none';
			var usersButton = document.getElementById('usersButton');
			usersButton.style.display='none';
			var logoutButton = document.getElementById('logoutButton');
			$('#logoutButton').text('Main Page');
		}
		
		/*
		$("#buttonPublishTweet").click(function(){
	        $("#dialogPublishTweet").modal();
	    });
		*/
	});

</script>

    <nav class="navbar navbar-expand-sm navbar-light" style="background-color: #77517f;">

        <div class="navbar-header pull-left">
            <a class="navbar-brand navbar-header pull-left" style="color: #ffffff;" onClick="seeUserFeed('');">Gamitter</a>
        </div>
 
        <ul class="navbar-nav">
            <li class="nav-item">
                <a id="buttonPublishTweet" class="nav-link" style="color: #ffffff;" onClick="openModalPublishTweet(-1)">
                	Publish
                </a>
            </li>
            <li class="nav-item">
                <a id="profileButton" class="nav-link" style="color: #ffffff;" onClick="seeUserFeed('${sessionScope.Session_ID}');">
                	Profile
               	</a>
            </li>
            <li class="nav-item">
                <a id="usersButton" class="nav-link" style="color: #ffffff;" onClick="seeUsers('allUsersNotFollowedList');">
                	Explore Users
               	</a>
            </li>
            <li class="nav-item">
                <a id="logoutButton" class="nav-link" href="logout" style="color: #ffffff;">Logout</a>
            </li>
        </ul>
        
    </nav>

   	<div id="modalPublishTweet" class="modal" role="dialog">
	    <div class="modal-dialog">
	    
	      <!-- Modal content-->
	      <div class="modal-content">
		        <div class="modal-header">
		          <h4 class="modal-title">Write your Tweet</h4>
		        </div>
		        <div class="modal-body">
		          <textarea id="modalTweetMessage" onKeyUp="messageLength();"></textarea>
		          <span id="messageLength"></span>
		        </div>
		        <div class="modal-footer">
					<button type="button" class="btn btn-default" Onclick="publishTweet()">Publish</button>
					<button type="button" class="btn btn-default" Onclick="closePublishModal()">Close</button>
	    		</div>
	    		
	    		<input type="hidden" id="hiddenTweetId" value="-1">
	    		<input type="hidden" id="hiddenCommentTweetId" value="-1">
	    		
	    	</div>
		      
	    </div>
	</div>