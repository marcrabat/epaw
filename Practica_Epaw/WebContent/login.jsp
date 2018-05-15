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
        
        function fillJson() {
			var userConsoles = new Array();
            $("input:checkbox[name=consoles]:checked").each(function(){
                if ($(this).val() != "") { userConsoles.push($(this).val()); }
            });
			
			var gameGenres = new Array();
			$("input:checkbox[name=genres]:checked").each(function(){
				if ($(this).val() != "") { gameGenres.push($(this).val()); }
            });

            var json =  {
                            name: $('#name').val(),
                            surname: $('#surname').val(),
                            mail: $('#mail').val(),
                            user: $('#user').val(),
                            password: $('#password').val(),
                            birthDate: $('#birthDate').val(),
                            description: $('#description').val(),
                            gender: $("input:radio[name=gender]:checked").val(),
                            userConsoles: userConsoles,
                            gameGenres: gameGenres,                   
                            youtubeChannelID: $('#youtubeChannelID').val(),
                            twitchChannelID: $('#twitchChannelID').val()                           
                        };
            
            return  json;
        }
 
        function manageErrors(data) {

            if(typeof data.errors == "undefined") return;        
            
            for (var i = 0; i < data.errors.length; i++) {
            	var error = data.errors[i];
            	$('#'+ error.name + 'Danger').html(error.error);
            }
        }    

        function jsonRequest(e) {

        	var jsonObject = JSON.stringify(fillJson());
        	/*
        	if($('#name').val()=="") $('#nameDanger').html("The field is wrong!");
        	if($('#surname').val()=="") $('#surnameDanger').html("The field is wrong!");
        	if($('#mail').val()=="") $('#mailDanger').html("The email need to have the format xx@xx.xx");
        	if($('#user').val()=="") $('#userDanger').html("The field is wrong!");
        	if($('#password').val()=="") $('#passwordDanger').html("The field is wrong!");
        	if($('#birthDate').val()=="") $('#birthDateDanger').html("The field is wrong!");        	
        	*/
        	
            e.preventDefault();
			
            $.ajax({
                url: '/Lab_2/checkLoginErrors',
                type: 'post',
                dataType: 'text',
                data: {data: jsonObject },
                success: function (data) {
                	console.log(data);
                	
                	var result = JSON.parse(data);
                	
                	if (result.errors.length > 0) { manageErrors(result);}
                	else { alert("Straigth forward to your principal page"); }
                	
                },
                error: function(xhr, status, error) { alert("Error: " + error); }
            });
			
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
        	window.location.href = "/Lab_2/register.jsp";
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
