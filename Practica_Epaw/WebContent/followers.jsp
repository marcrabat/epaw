<html>

<head>
    <title>Followers</title>
    
    <%@ include file='includesOldBoot.jsp' %>
	<%@ include file='navbar.jsp' %>
    
    <style>
        .top-buffer { margin-top:15px; }
        .json-key { color: brown; }
        .json-value { color: navy; }
        .json-string { color: olive; }
    </style>

	<script src="js/tweets/Followers.js"></script>

    <script type="text/javascript">

        /////////////////////// FUNCTIONS ////////////////////////    
        
        $(document).ready( function() {
        	followersSessionId = "${sessionScope.Session_ID}";
        	user = '${sessionScope.userInfo}';
        	mode = '${sessionScope.followersViewMode}';
        	
        	if (followersSessionId == "") {
        		redirectToMain();
        	} else{
        		jsonRequest();
        	}
        });

        //////////////////////////////////////////////////////////        

    </script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>
    <div class="container" style="margin-top:1%;">
		<div class="row">
	        <div class="col">
				<ul class="list-group" id="followers">
					
				</ul>
	        </div>
	    </div>
	    <br />
	    <div id="followersButton">
	    	<button id="followersClose" class="btn btn-primary" onclick="goBack();">
	    		Close
	    	</button>
	    </div>	      
    </div>
	<%@ include file='footer.jsp' %>
</body>

</html>
