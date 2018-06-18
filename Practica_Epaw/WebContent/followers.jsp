<html>

<head>
    <title>Followers</title>
    
    <%@ include file='includes.jsp' %>
	<%@ include file='navbar.jsp' %>
    
    <style>
        .top-buffer { margin-top:15px; }
        .json-key { color: brown; }
        .json-value { color: navy; }
        .json-string { color: olive; }
    </style>

    <script type="text/javascript">

        /////////////////////// FUNCTIONS ////////////////////////    
        
        $(document).ready( function() {
        	var sessionId = "${sessionScope.Session_ID}";
        	if (sessionId == "") {
        		redirectToMain();
        	} else{
        		jsonRequest();
        	}
        });

        function redirectToMain() {
        	window.location.href = "/Lab_3/main"
        }        
        
        function jsonRequest() {

            var userJSON = '${sessionScope.userInfo}';
            if (userJSON != "") {
            	
			 	sessionUser = JSON.parse(userJSON);
			 	var mode = '${sessionScope.followersViewMode}';
			 	console.log(mode);
			 	if(mode=="") mode = "followers";
			 	console.log(mode);
			 	var parametros = { data: sessionUser.user, mode: mode };
			 	
	           	$.ajax({
	                   url: '/Lab_3/checkFollowers',
	                   type: 'post',
	                   dataType: 'text',
	                   data: parametros,
	                   success: function (data) {
							console.log(data);
							
							var result = JSON.parse(data);
							
				            if(!result.length) return;
				            
				            for (var i = 0; i < result.length; i++) {		            
				            	var result_parts = result[i].split(',');
				            	if(result_parts[1]==0){
					            	$('#followers').append('<li class="list-group-item">'+result_parts[0]+'<button type="button" id=' +"'"+result_parts[0]+"'"+ ' class="btn btn-light" style="margin-left: 70%;" onclick="changeRelation('+"'"+result_parts[0]+"'"+","+"'"+"insert"+"'"+')">Follow</button></li>');
				            	} else if (result_parts[1]==1) {
					            	$('#followers').append('<li class="list-group-item">'+result_parts[0]+'<button type="button" class="btn btn-secondary" style="margin-left: 70%;" onclick="changeRelation('+"'"+result_parts[0]+"'"+","+"'"+"delete"+"'"+')">Following</button></li>');
				            	}
				            }
	                   },
	                   error: function(xhr,status,error) { alert("Error: " + error);} });
            }
                        
        }
        
        function changeRelation(userB_, mode_){
        	
            var userJSON = '${sessionScope.userInfo}';
            if (userJSON != "") {
            	
			 	sessionUser = JSON.parse(userJSON);
			 	var parametros = { userA: sessionUser.user, userB: userB_, mode: mode_ };
        		console.log(parametros);
        		
	           	$.ajax({
	                url: '/Lab_3/changeRelation',
	                type: 'post',
	                dataType: 'text',
	                data: parametros,
	                success: function (data) {
							console.log(data);
							location.reload();
	                },
	                error: function(xhr,status,error) { alert("Error: " + error);} });     
        	}
        }
        
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
