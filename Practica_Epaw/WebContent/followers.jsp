<html>

<head>
    <title>Login</title>
    <style>
        .top-buffer { margin-top:15px; }
        .json-key { color: brown; }
        .json-value { color: navy; }
        .json-string { color: olive; }
    </style>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <!-- <script src="./js/user/login.js"></script> -->
    <script type="text/javascript">

        /////////////////////// FUNCTIONS ////////////////////////    
        
        $(document).ready( function() {
        	var sessionId = "${sessionScope.Session_ID}";
        	if (sessionId == NULL) {
        		redirectToMain();
        	} else{
        		jsonRequest();
        	}
        });

        function redirectToMain() {
        	window.location.href = "/Lab_3/main"
        }        
        
        function jsonRequest(e) {

            e.preventDefault();

            var userJSON = '${sessionScope.userInfo}';
            if (userJSON != "") {
            	
			 	sessionUser = JSON.parse(userJSON);
			 	var parametros = { data: sessionUser.user+"", mode: "followers" };
			 	
	           	$.ajax({
	                   url: '/Lab_3/checkFollowers',
	                   type: 'post',
	                   dataType: 'text',
	                   data: parametros,
	                   success: function (data) {
							console.log(data);
							
							var result = JSON.parse(data);
							
				            if(typeof data.errors == "undefined") return;        
				            
				            for (var i = 0; i < data.errors.length; i++) {
				            	var error = data.errors[i];
				            	if(error.name!="followers") continue;			            	
				            	$('#followers').append(error.error + "\n");
				            }
	                   	
	                   },
	                   error: function(xhr,status,error) { alert("Error: " + error);} });
	         	
            }
                        
        }
        //////////////////////////////////////////////////////////        

    </script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>
    <div class="container">
		<div class="row top-buffer">
	              <div class="col">
	                  <h2>Login</h2>
	                  <hr>
	                  <span id="followers"></span>
	              </div>
	    </div>
    </div>
</body>
</html>
