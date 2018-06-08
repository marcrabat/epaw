<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

		<title>Profile</title>
		
		<%@ include file='includes.jsp' %>
		<%@ include file='navbar.jsp' %>
		<link rel="stylesheet" href="css/profile.css">
		
		<script src="js/user/Profile.js"></script>
		
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
		        
		</script>
	</head>
	
	<body>

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
		 		<div id="profileForm" class="rosa">
		
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
		            
		            <span onClick="changeVisibility('gamerInfo');"> Gamer Info </span>
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
					
					<span onClick="changeVisibility('socialNetwork')"> Youtube / TWitch Channel ID </span>
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
			        
			        <span onClick="changeVisibility('profilePassword')"> Do you Want to change the Password? </span>
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
		
	</body>
</html>