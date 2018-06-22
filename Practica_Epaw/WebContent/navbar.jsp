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

	<nav class="navbar navbar-inverse" style="background-color: #77517f;">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>                        
	      </button>
	      <a class="navbar-brand" onClick="seeFollowingUsersTweets('${sessionScope.Session_ID}');">Gamitter</a>
	    </div>
	    <div class="collapse navbar-collapse" id="myNavbar">
	      <ul class="nav navbar-nav">
            <li><a id="buttonPublishTweet" class="nav-link" style="color: #ffffff;" onClick="openModalPublishTweet(-1)">Publish</a></li>
            <li><a id="profileButton" class="nav-link" style="color: #ffffff;" onClick="seeUserFeed('');">Discover Tweets</a></li>
            <li><a id="usersButton" class="nav-link" style="color: #ffffff;" onClick="seeUsers('allUsersNotFollowedList');">Explore Users</a></li>
            <li><a id="logoutButton" class="nav-link" href="logout" style="color: #ffffff;">Logout</a></li>
	      </ul>
	      <form class="navbar-form navbar-right" role="search">
	        <div class="form-group input-group">
	          <input type="text" class="form-control" id="searchUser" placeholder="Search..">
	          <span class="input-group-btn">
	            <button class="btn btn-default" type="button">
	              <span class="glyphicon glyphicon-search"></span>
	            </button>
	          </span>        
	        </div>
	      </form>
	      <ul class="nav navbar-nav navbar-right">
	        <li><a onClick="seeUserFeed('${sessionScope.Session_ID}');"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
	      </ul>
	    </div>
	  </div>
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