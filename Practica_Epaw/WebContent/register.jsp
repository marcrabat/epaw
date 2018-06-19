<html>

<head>
    <title>Register</title>
    
    <%@ include file='includes.jsp' %>
    <script src="js/user/Register.js"></script>
    
    <style>
        .top-buffer { margin-top:15px; }
        .json-key { color: brown; }
        .json-value { color: navy; }
        .json-string { color: olive; }
    </style>

    
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
