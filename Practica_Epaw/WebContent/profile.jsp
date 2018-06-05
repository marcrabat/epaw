<%@ include file='header_provisional.jsp' %>
<style>
	#profileMenu {
		width: 15%;
	    position: relative;
	    float: left;
	    border-width: 4px;
	    border-color: black;
	    border-style: outset;
	    margin-left: 2%;
	    padding: 10px;
    }
    
    #profileMenu button {
		align: center;
	    margin-top: 2%;
    }
    
    #profileContent {
    	position: relative;
	    float: right;
    	width: 70%;
    	margin-right: 2%;
    	
    	border-width: 4px;
	    border-color: black;
	    border-style: outset;
    }
    
    .displayNone {
    	display: none;
    }
    
</style>
<script type="text/javascript">

	var sessionUser = null;
	
	$(document).ready(function() {
		
		var sessionId = "${sessionScope.Session_ID}";
		if (sessionId == "") {
			window.location.href = "/Lab_3/main";
		} else {
		
			clear();
			
			changeVisibility("gamerInfo");
			changeVisibility("socialNetwork");
			changeVisibility("profilePassword");
	
			disableInputs();
			//var userJSON = '{"userConsoles": []}';
			var userJSON = '${sessionScope.userInfo}';
			if (userJSON != "") {
				sessionUser = JSON.parse(userJSON);
				console.log(sessionUser);
				fillProfileForm(sessionUser);
				createAdminButtons(sessionUser);
			}

		}
		
	});
	
	function changeVisibility(id) {
		var element = $("#" + id);
		if (element.hasClass("displayNone") == true) {
			element.removeClass("displayNone");
		} else {
			element.addClass("displayNone");
		}
	}
	
	function fillProfileForm(user) {
		
		if (user != null) {
			
			var youtubeChannel = "https://www.youtube.com/channel/";
			var twitchChannel = "https://www.twitch.tv/";
			
			var youtubeChannelID = user.youtubeChannelID.substring(youtubeChannel.length, user.youtubeChannelID.length);
			var twitchChannelID = user.twitchChannelID.substring(twitchChannel.length, user.twitchChannelID.length);
			
			getElement("description").value = user.description;
			getElement("youtubeChannelID").value = youtubeChannelID;
			getElement("twitchChannelID").value = twitchChannelID;
			
			var consolesCheckbox = $("input:checkbox[name=consoles]");
			
			for (var i = 0; i < user.userConsoles.length; i++) {
			
				var userValue = user.userConsoles[i].trim(); 
				
				for (var j = 0; j < consolesCheckbox.size(); j++) {
					
					var checkboxValue = consolesCheckbox[j].value.trim();

					if (userValue == checkboxValue) {
						consolesCheckbox[j].checked = true;
						break;
					}
					
				}
				
			}
			
			var gameGenresCheckbox = $("input:checkbox[name=genres]");
			
			for (var i = 0; i < user.gameGenres.length; i++) {
			
				var userValue = user.gameGenres[i].trim(); 
				
				for (var j = 0; j < gameGenresCheckbox.size(); j++) {
					
					var checkboxValue = gameGenresCheckbox[j].value.trim();
					
					if (userValue == checkboxValue) {
						gameGenresCheckbox[j].checked = true;
						break;
					}
					
				}
				
			}
			
		}
	
	}
	 
	function createAdminButtons(user) {
		if (user.isAdmin == true) {
			var profileMenu = getElement("profileMenu");
			var buttonDeleteUserAccount = createElement("button", "buttonDeleteUserAccount");
			buttonDeleteUserAccount.innerText = "Delete user account";
			buttonDeleteUserAccount.className = "btn btn-primary";
			profileMenu.appendChild(buttonDeleteUserAccount);
			
			/*
			var buttonDeleteUserAccount = createElement("button", "buttonDeleteUserAccount");
			buttonDeleteUserAccount.innerText = "Delete user account";
			profileMenu.appendChild(buttonDeleteUserAccount);
			*/
		}
	};
	
	function enableInputs() {
		$("#description").prop("disabled", false);
		
        $("input:checkbox[name=consoles]").each(function(){
            $(this).prop("disabled", false);
        });
        
        $("input:checkbox[name=genres]").each(function(){
        	$(this).prop("disabled", false);
        });
        
        $("input:[type=text]").each(function(){
        	$(this).prop("disabled", false);
        });
        
        $("#password").prop("disabled", false);
        
        $("#newPassword").prop("disabled", false);
	}
	
	function disableInputs() {
		
		$("#description").prop("disabled", true);
		
        $("input:checkbox[name=consoles]").each(function(){
            $(this).prop("disabled", true);
        });
        
        $("input:checkbox[name=genres]").each(function(){
        	$(this).prop("disabled", true);
        });
        
        $("input:[type=text]").each(function(){
        	$(this).prop("disabled", true);
        });
        
        $("#password").prop("disabled", true);
        
        $("#newPassword").prop("disabled", true);
	}
	
	function changeEditOrView() {
		if (getElement("editOrView").innerHTML == "View") {
			disableInputs();
			getElement("profileButtons").style = "display: none;";
			getElement("editOrView").innerHTML = "Edit";
		} else {
			enableInputs();
			getElement("profileButtons").style = "display: block;";
			getElement("editOrView").innerHTML = "View";
			
		}
	}
	
	function fillJson() {
		var userConsoles = new Array();
		         $("input:checkbox[name=consoles]:checked").each(function(){
		             if ($(this).val() != "") { userConsoles.push($(this).val()); }
		         });
		
		var gameGenres = new Array();
		$("input:checkbox[name=genres]:checked").each(function(){
			if ($(this).val() != "") { gameGenres.push($(this).val()); }
		         });
		
		var newPassword = "";
		if ($("#newPassword").checked == true) {
			newPassword = getValue("password");
		}
		
		var youtubeChannelID = "";
        if ($("#youtubeChannelID").val() != "") {
        	youtubeChannelID = "https://www.youtube.com/channel/" + $('#youtubeChannelID').val();
        }
        
        var twitchChannelID = "";
        if ($("#twitchChannelID").val() != "") {
        	twitchChannelID = "https://www.twitch.tv/" + $('#twitchChannelID').val();
        }

        var json =  {
                        password: newPassword,
                        description: $('#description').val().trim(),
                        userConsoles: userConsoles,
                        gameGenres: gameGenres,                   
                        youtubeChannelID: youtubeChannelID,
                        twitchChannelID: twitchChannelID                          
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
		$('#descriptionDanger').html("");
		$('#passwordDanger').html("");
		$('#serverSideDanger').html("");
		$('#editDanger').html("");
	};
	
	function editProfile() {
		
		if (confirm("Are you sure?")) {
		
		 	var jsonObject = JSON.stringify(fillJson());
		 	var parametros = { data: jsonObject, mode: "editProfile" };
		 	
		 	executeAjax(parametros, "/Lab_3/checkProfileErrors", "POST", 
		 					function(response){ succesEditProfile(response); }, 
		 					function(e){ errorEditProfile(e); });
	 	
		}
		
	}
	
	function succesEditProfile(response) {
		
		console.log(response);
		
		var result = response;
		
		if (result.errors.length > 0) { 
			manageErrors(result);
			$('#editDanger').html("Check the form errors!");
		} else {
			alert("the edit profile is good!");
	    	changeEditOrView();
		}
		
		
	}
	
	function errorEditProfile(e) {
		alert("Error.....");
	}
	
	function deleteSessionAccount() {
		
		if (sessionUser != null) {
		
			if (confirm("Are you sure?")) {
	
			 	var jsonObject = JSON.stringify(sessionUser);
			 	var parametros = { data: jsonObject, mode: "deleteAccount" };
			 	
			 	executeAjax(parametros, "/Lab_3/checkProfileErrors", "POST", 
			 					function(response){ succesDeleteSessionAccount(response); }, 
			 					function(e){ errorDeleteSessionAccount(e); });
		 	
			}
			
		}
		
	}
	
	function succesDeleteSessionAccount(response) {
		
		console.log(response);
		
		var result = response;
		
		if (result.errors.length > 0) { 
			manageErrors(result);
		} else {
			alert("Your account deleted succesfully !!");
	    	window.location.href = "/Lab_3/main";
		}

	}
	
	function errorDeleteSessionAccount(e) {
		alert("Error trying to delete the account");
	}
	
	function deleteAllTweets() {
		
		if (sessionUser != null) {
		
			if (confirm("Are you sure?")) {
	
			 	var jsonObject = JSON.stringify(sessionUser);
			 	var parametros = { data: jsonObject, mode: "deleteAllTweets" };
			 	
			 	executeAjax(parametros, "/Lab_3/checkProfileErrors", "POST", 
			 					function(response){ succesDeleteAllTweets(response); }, 
			 					function(e){ errorDeleteAllTweets(e); });
		 	
			}
			
		}
		
	}
	
	function succesDeleteAllTweets(response) {
		
		console.log(response);
		
		var result = response;
		
		if (result.errors.length > 0) { 
			manageErrors(result);
		} else {
			alert("All your tweets are deleted !!");
		}

	}
	
	function errorDeleteAllTweets(e) {
		alert("Error trying to delete the tweets");
	}
	
	function viewFollowers() {
		alert("not implemented yet");
	}
	
	function viewFollowings() {
		alert("not implemented yet");
	}
        
</script>
<div id="profilePage" style="witdh: 80%; margin-left:2%; margin-right:2%;">
 
 	<!-- Title -->
    <div class="row top-buffer">
        <div class="col">
            <h2>My Profile</h2>
            <hr>
            <span class="text-danger" id="serverSideDanger"></span>
        </div>
    </div>
 
 	<div id="profileMenu" style="width:="30%; float: left;">
	 	<button id="buttonDeleteAccount" class="btn btn-primary" onClick="deleteSessionAccount();">Delete account</button>
	 	<button id="buttonDeleteAllTweets" class="btn btn-primary" onClick="deleteAllTweets();">Delete all tweets</button>
	 	<button id="buttonViewFollowers" class="btn btn-primary" onClick="viewFollowers();">View followers</button>
	 	<button id="buttonViewFollowings" class="btn btn-primary" onClick="viewFollowings();">View followings</button>
 	</div>
 	
 	<div id="profileContent" class="content" style="width:="70%; float: right;">
 		<div id="profileForm">

			<div style="position:relative; float:rigth;">
				<span id="editOrView" onClick="changeEditOrView();"> Edit </span>
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
            
            <span href="#" onClick="changeVisibility('gamerInfo');"> Gamer Info </span>
            <div id="gamerInfo">
	            <div class="row">    
	                <div class="col">
	                    <div class="form-group">
	                        <label for="consoles">Consoles you have</label>
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
            </div>
            
            <br/>
			
			<span href="#" onClick="changeVisibility('socialNetwork')"> Youtube / TWitch Channel ID </span>
			<div id="socialNetwork">
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
	        </div>
	        
	        <br/>
	        
	        <span href="#" onClick="changeVisibility('profilePassword')"> Do you Want to change the Password? </span>
	        <div id="profilePassword">
		        <div class="row">    
	                <div class="col">
	                    <div class="form-group">
	                        <label for="password">New Password: </label>
	                        <input type="password" class="form-control" id="password" value="">
	                        <span class="text-danger" id="passwordDanger"></span>
	                    </div>
	                </div>
	                
	                <div class="checkbox">     
                        <label><input type="checkbox" id="newPassword" name="newPassword"> Yes</label>
                    </div>
	                
	            </div>
	       </div>
	       
	       <br/>

            <!-- Sumbit Button -->
            <div id="profileButtons" style="display: none">
	            <div class="row">
	                <div class="col">
	                    <div class="form-group">
	                        <button id="edit" class="btn btn-primary" onClick="editProfile();">Edit Profile</button>
	                        <span class="text-danger" id="editDanger"></span>
	                    </div>
	                </div>                              
	            </div>
	       </div>
 			
 		</div>
 	</div>
 	
</div>

<div style="margin-top:65%; width:100;"></div>

<%@ include file='footer_provisional.jsp' %>