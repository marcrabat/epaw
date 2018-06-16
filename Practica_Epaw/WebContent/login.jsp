<html>

<head>
    <title>Login</title>
    <style>
        .top-buffer { margin-top:15px; }
        .json-key { color: brown; }
        .json-value { color: navy; }
        .json-string { color: olive; }
    </style>

    <%@ include file='includes.jsp' %>
    
    <script src="./js/user/Login.js"></script>
    <script type="text/javascript">

        /////////////////////// FUNCTIONS ////////////////////////    

		$(document).ready(function() {
			sessionId = "${sessionScope.Session_ID}";
			if (sessionId != "") {
				
				seeUserFeed(sessionId);
			}
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
                    <h2>Login</h2>
                    <hr>
                    <span class="text-danger" id="serverSideDanger"></span>
                </div>
            </div>

            <!-- Main Parameters -->

            <div class="row">    
                <div class="col">
                    <div class="form-group">
                        <label for="user">Username / E-mail Address</label>
                        <input type="text" class="form-control" id="user" placeholder="example@domain.com" value="test">
                        <span class="text-danger" id="userDanger"></span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" value="testtest">
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
