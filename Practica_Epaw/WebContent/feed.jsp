<html>

<head>
    <title>Feed</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery-1.6.2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">

        /////////////////////// FUNCTIONS ////////////////////////    
        
        $(document).ready( function() {
        	var sessionId = "${sessionScope.Session_ID}";
        	if (sessionId != "") {
        		redirectToMainPage();
        	}else{
        			
        	}
        	
        });
        
        function redirectToMainPage(){
        	window.location.href = "/Lab_3/main";
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

        $(window).load(function() {        

        });
        

    </script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>
<h1 style='center'> Feed</h1>
<h2> Your feed</h2>

</body>
</html>
