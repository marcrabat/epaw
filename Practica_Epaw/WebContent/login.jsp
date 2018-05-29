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
        	if (sessionId != "") {
        		redirectToPersonalPage();
        	}
        });

        function redirectToPersonalPage() {
        	window.location.href = "/Lab_3/feed"
        }
        
        function clear(){
        	$('#userDanger').html("");
        	$('#passwordDanger').html("");
        	$('#serverSideDanger').html("");
        };
        
        function hasErrors(){
        	
        	clear();
        	var hasError = false;
        	
        	if($('#user').val()=="") {
        		hasError = true;
        		$('#userDanger').html("The field is wrong!");
        	}
        	
        	if($('#password').val()=="") {
        		hasError = true;
        		$('#passwordDanger').html("The field is wrong!");
        	}
        	
        	return hasError;
        }
        
        
        function fillJson() {
			var mailIntroduced = "";
			var userIntroduced = "";
			
			if($('#user').val().includes("@")){
				mailIntroduced = $('#user').val();
			}else{
				userIntroduced = $('#user').val();
			}
			
			
            var json =  {
                            mail: mailIntroduced,
                            user: userIntroduced,
                            password: $('#password').val()                       
                        };
            
            return  json;
        }
 
        function manageErrors(data) {

            if(typeof data.errors == "undefined") return;        
            
            for (var i = 0; i < data.errors.length; i++) {
            	var error = data.errors[i];
            	if($('#'+ error.name + 'Danger').length){
            		$('#'+ error.name + 'Danger').html(error.error);
            	} else{
            		$('#serverSideDanger').append(error.error + "\n");
            	}
            }
        }    

  
        
        
        
        
        function jsonRequest(e) {

        	var jsonObject = JSON.stringify(fillJson());
  	
            e.preventDefault();
			
            if(hasErrors() == false){
            	$.ajax({
                    url: '/Lab_3/checkLoginErrors',
                    type: 'post',
                    dataType: 'text',
                    data: {data: jsonObject },
                    success: function (data) {
                    	console.log(data);
                    	
                    	var result = JSON.parse(data);
                    	
                    	if (result.errors.length > 0) { 
                    		manageErrors(result);
                    		$('#validateDanger').html("Check the form errors!");
                    	} else {
                    		redirectToPersonalPage();
                    	}
                    	
                    },
                    error: function(xhr,status,error) { alert("Error: " + error);} });
            } else{
            	$('#validateDanger').html("Check the form errors!");
            }
            
            console.log(jsonObject);
        }
        //////////////////////////////////////////////////////////


        $(window).load(function() {        

            ///////////////////////// EVENTS /////////////////////////

            //Sends data to the server
            $('#loginButton').click(jsonRequest);
            //////////////////////////////////////////////////////////

        });
        
        function goToRegister() {
        	window.location.href = "/Lab_3/register";
        }

    </script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>
    <div class="container">
        <!-- <form onSubmit="return false;"> -->

            <!-- Title -->
            <div class="row top-buffer">
                <div class="col">
                    <h2>Login</h2>
                    <hr>
                    <span class="text-danger" id="serverSideDanger"></span>
                </div>
            </div>

            <!-- Main Parameters -->

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="mail">Username / E-mail Address</label>
                        <input type="text" class="form-control" id="user" placeholder="example@domain.com">
                        <span class="text-danger" id="userDanger"></span>
                    </div>
                </div>
            </div>

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password">
                        <span class="text-danger" id="passwordDanger"></span>
                    </div>
                </div>
            </div>        

            <!-- Sumbit Button -->
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <button id="loginButton" class="btn btn-primary">Login</button>
                        <span class="text-danger" id="validateDanger"></span>
                    </div>
                </div>                              
            </div>

			<div class="row">
                <div class="col">
                    <div class="form-group">
                        <button id="goToRegisterButton" class="btn btn-primary" onClick="goToRegister();">
                        	Go to Register
                        </button>
                    </div>
                </div>                              
            </div>

       <!-- </form> -->
    </div>

</body>
</html>
