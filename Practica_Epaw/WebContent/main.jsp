<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Page Properties -->
    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file='includes.jsp' %>
    
    <title>Gamitter</title>
    <style>
        .top-buffer { margin-top:100px; }
        .json-key { color: brown; }
        .json-value { color: navy; }
        .json-string { color: olive; }
    </style> 
   
    <script type="text/javascript">
        function goToRegister() {
            window.location.href = "/Lab_3/register";
        }

        function goToLogin() {
            window.location.href = "/Lab_3/login";
        }
        
        function proceedAsAnonymous(){
        	window.location.href = "/Lab_3/anonymous";
        }

    </script>
	
	
	
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
                    <button id="anonymous" onClick = "proceedAsAnonymous();"type="button"class="btn btn-outline-primary">Proceed as Anonymous</button> 
                </div>
                    
            </div> 
    </div>
    
</body>
</html>