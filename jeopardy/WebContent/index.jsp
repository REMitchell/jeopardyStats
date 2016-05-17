<html>
<head>
    <title><%= request.getAttribute("title") %></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
</head>
<body>
    Stats JSON:
    <%= request.getAttribute("stats") %>

<div id="testGrid"></div>
<script>
var testObj = $.parseJSON("<%= request.getAttribute("stats") %>");
$.each(testObj, function( index, value ) {
	
	$("#testGrid").append(",");
	$("#testGrid").append(value);

	  });
</script>

</body>
</html>
