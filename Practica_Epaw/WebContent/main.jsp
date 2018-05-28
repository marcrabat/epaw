<html>
<head>

<!-- Page Properties -->
    
    <title>Gamitter</title>
    <style>
        .top-buffer { margin-top:100px; }
        .json-key { color: brown; }
        .json-value { color: navy; }
        .json-string { color: olive; }
    </style>
    
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script> 
    <link rel="icon" href="images/ghost-512.png">
    <body background="images/gaming_pattern_blue-.jpg">
   
    <script type="text/javascript">
        function goToRegister() {
            window.location.href = "/Lab_3/register";
        }

        function goToLogin() {
            window.location.href = "/Lab_3/login";
        }

    </script>
	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
</head>

<body>
    <div class="container">
            <!-- Title -->
            <div class="row top-buffer">
                <div class="col" >
                    <h1 style="color:black align:center;"> Welcome to Gamitter!</h1>
                    <br>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-4" align="center">
                    <h2>I'm a newcomer</h2>
                    <img src="images/newcomer.png" style="margin:auto; width:200px;display:block" />
                    <button id="register" onClick = "goToRegister();" type="button" class="btn btn-outline-success">Sign Up</button>
                </div>
                    
                <div class="col-sm-4" align="center">
                    <h2>I'm in love with this</h2>
                    <img src="images/inlove.png" style="margin:auto; width:200px;display:block" />
                    <button id="login" onClick = "goToLogin();" type="button" class="btn btn-outline-primary">Login</button>
                </div>
                    
                <div class="col-sm-4" align="center">
                    <h2>I'm a world traveller</h2>
                    <img src="images/anonymous.png" style="margin:auto; width:200px;display:block" />
                    <button id="anonymous" type="button" class="btn btn-outline-primary">Proceed as Anonymous</button> 
                </div>
                    
            </div> 
    </div>
    
</body>
</html>