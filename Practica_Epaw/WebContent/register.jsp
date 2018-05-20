<html>

<head>
    <title>Register</title>
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
            	if($('#'+ error.name + 'Danger').length){
            		$('#'+ error.name + 'Danger').html(error.error);
            	} else{
            		$('#serverSideDanger').append(error.error + "\n");
            	}
            }
        }    

        function clear(){
        	$('#nameDanger').html("");
        	$('#surnameDanger').html("");
        	$('#mailDanger').html("");
        	$('#userDanger').html("");
        	$('#passwordDanger').html("");
        	$('#birthDateDanger').html("");
        	$('#serverSideDanger').html("");
        	$('#validateDanger').html("");
        };
        
        function hasErrors(){
        	
        	clear();
        	var hasError = false;
        	
        	if($('#name').val()=="") {
        		hasError = true;
        		$('#nameDanger').html("The field is wrong!");
        	}
        	
        	if($('#surname').val()==""){
        		hasError = true;
        		$('#surnameDanger').html("The field is wrong!");
        	} 
        	
        	if($('#mail').val()==""){ 
        		hasError = true;
        		$('#mailDanger').html("The email need to have the format xx@xx.xx");
        	}
        	
        	if($('#user').val()=="") {
        		hasError = true;
        		$('#userDanger').html("The field is wrong!");
        	}
        	
        	if($('#password').val()=="") {
        		hasError = true;
        		$('#passwordDanger').html("The field is wrong!");
        	}
        	
        	if($('#birthDate').val()=="") {
        		hasError = true;
        		$('#birthDateDanger').html("The field is wrong!");
        	}        	
        	if($('#password').val()!=$('#password_conf').val()) {
        		hasError = true;
        		$('#passwordDanger').html("Password is not equal");
        	}
        	
        	return hasError;
        }
        
        function jsonRequest(e) {
        	
        	
        	
        	var jsonObject = JSON.stringify(fillJson());
        
        	
            e.preventDefault();
			
            if(hasErrors() == false){
            	$.ajax({
                    url: '/Lab_2/checkErrors',
                    type: 'post',
                    dataType: 'text',
                    data: {data: jsonObject },
                    success: function (data) {
                    	console.log(data);
                    	
                    	var result = JSON.parse(data);
                    	
                    	if (result.errors.length > 0) { 
                    		manageErrors(result);
                    		$('#validateDanger').html("Check the form errors!");
                    	}else { 
                    		alert("Your registry has been completed successfully");
                    		window.location.href = "/Lab_2/main.jsp";
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
            $('#validate').click(jsonRequest);
            
            //////////////////////////////////////////////////////////

        });

    </script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>
    <div class="container">
        <!-- <form onSubmit="return false;"> -->

            <!-- Title -->
            <div class="row top-buffer">
                <div class="col">
                    <h2>Register</h2>
                    <hr>
                    <span class="text-danger" id="serverSideDanger"></span>
                </div>
            </div>
			
            <!-- Main Parameters -->
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <label for="name">Name *</label>
                        <input type="text" class="form-control" id="name">
                        <span class="text-danger" id="nameDanger"></span>
                    </div>
                </div>
            </div>


            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="surname">Surname *</label>
                        <input type="text" class="form-control" id="surname">
                        <span class="text-danger" id="surnameDanger"></span>
                    </div>
                </div>
            </div>    

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="mail">E-Mail Adress *</label>
                        <input type="text" class="form-control" id="mail" placeholder="example@domain.com">
                        <span class="text-danger" id="mailDanger"></span>
                    </div>
                </div>
            </div>

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="user">Username *</label>
                        <input type="text" class="form-control" id="user">
                        <span class="text-danger" id="userDanger"></span>
                    </div>
                </div>
            </div>

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="password">Password *</label>
                        <input type="password" class="form-control" id="password">
                        <span class="text-danger" id="passwordDanger"></span>
                    </div>
                </div>
            </div>        

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="password_conf">Password Confirmation *</label>
                        <input type="password" class="form-control" id="password_conf">
                    </div>
                </div>
            </div>                        

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="birthDate">Date of birth *</label>
                        <input type="text" class="form-control" id="birthDate" placeholder="10/03/1995">
                        <span class="text-danger" id="birthDateDanger"></span>
                    </div>
                </div>
            </div>

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description"></textarea>
                        <span class="text-danger" id="descriptionDanger"></span>
                    </div>
                </div>
            </div>  

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="gender">Gender</label>
                        <br><label class="radio-inline">
                            Male<input type="radio" class="form-control" name="gender" value="male" checked> 
                        </label>
                        <label class="radio-inline">
                            Female<input type="radio" class="form-control" name="gender" value="female">
                        </label>
                        <label class="radio-inline">
                            Other<input type="radio" class="form-control" name="gender" value="other">
                        </label>
                        <span class="text-danger" id="genderDanger"></span>
                    </div>
                </div>
            </div>  

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="gender">Consoles you have</label>
                        <div class="checkbox">
                            <label><input type="checkbox" name="consoles" value="PS1">   PS1</label>
                        </div>
                        <div class="checkbox">    
                            <label><input type="checkbox" name="consoles" value="PS2">   PS2</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="PS3">   PS3</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="PS4">   PS4</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="Nintendo (3)DS">   Nintendo (3)DS</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="Nintendo Wii">   Nintendo Wii</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="Nintendo Switch">   Nintendo Switch</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="Xbox">   Xbox</label>
                        </div>
                        <div class="checkbox"> 
                            <label><input type="checkbox" name="consoles" value="Xbox360">   Xbox360</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="Xbox One">   Xbox One</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="PC">   PC</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="Android / iOS">   Android / iOS</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="consoles" value="Others">   Others</label>
                        </div>
                        <span class="text-danger" id="consolesDanger"></span>
                    </div>
                </div>
            </div>  

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="gender">Game Genre</label>
                        <div class="checkbox">
                            <label><input type="checkbox" name="genres" value="Action">   Action</label>
                        </div>
                        <div class="checkbox">    
                            <label><input type="checkbox" name="genres" value="Action-Adventure">   Action-Adventure</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="genres" value="Adventure">   Adventure</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="genres" value="Role-playing">   Role-playing</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="genres" value="Simulation">   Simulation</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="genres" value="Strategy">   Strategy</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="genres" value="Sports">   Sports</label>
                        </div>
                        <div class="checkbox">     
                            <label><input type="checkbox" name="genres" value="Other">   Other</label>
                        </div>
                        <span class="text-danger" id="genresDanger"></span>
                    </div>
                </div>
            </div>    

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="youtubeChannelID">Youtube Channel ID</label>
                        <input type="text" class="form-control" id="youtubeChannelID" placeholder="youtube.com/user/ID">
                        <span class="text-danger" id="youtubeChannelIDDanger"></span>
                    </div>
                </div>
            </div>

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="twitchChannelID">Twitch Channel ID</label>
                        <input type="text" class="form-control" id="twitchChannelID" placeholder="twitch.tv/ID">
                        <span class="text-danger" id="twitchChannelIDDanger"></span>
                    </div>
                </div>
            </div>            

            <!-- Sumbit Button -->
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <button id="validate" class="btn btn-primary">Submit</button>
                        <span class="text-danger" id="validateDanger"></span>
                    </div>
                </div>                              
            </div>       

       <!-- </form> -->
    </div>

</body>
</html>
