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
        	if (sessionId == "") {
        		redirectToMainPage();
        	} else{
				TweetsRequest("${sessionScope.userToLookFeed}");
        	}
        });
        
        function redirectToMainPage(){
        	window.location.href = "/Lab_3/main";
        }
        
        function TweetsRequest(username){
            $.ajax({
                type: 'post', //rest Type
                dataType: 'text', //mispelled
                url: "/Lab_3/checkFeedErrors",
                data: username,
                success: function (data) {
                    console.log(data);
                    var tweets = JSON.parse(data);
                    console.log(tweets);
                    var HTML = "<div id='tweet_' "+ tweets[0].tweetID + "><span>"+ tweets[0].message + "</span></div>";
                    document.getElementById("content").innerHTML = HTML;
                },
                error: function(xhr,status,error) { alert("Error: " + error);} });
        }
        
        

    </script>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>

<body>
<h1 style='center'> Feed</h1>
<h2> Your feed</h2>
<div id="content">

</div>

</body>
</html>
