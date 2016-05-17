<html>
<head>
    <title>Jeopardy Scores T Test</title>
    <style>
    	body {
    		font-family: Helvetica, Arial, Sans-Serif;
    	}
    	
    	#label {
    		font-weight:bold;
    		border:none;
    	}
    	
    	#testGrid table tbody tr td {
    		margin:5px; 
    		border:1px solid black;
    	}
    	
    	#testGrid table tbody tr #accept {
    		background-color:#00ff00;
    	}
    	
    	
    	
    	#testGrid table tbody tr #reject {
    		background-color:#ff0000;
    	}
    	
    	span.note {
    		font-size:.8em;
    		color:#555;
    	}
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
</head>
<body>
<h2>Jeopardy Winners Average Score T Test</h2>
<form method="GET">
Alpha:
<input type="number" id="alphaInput" name="alpha" min=".001" max=".499" step=".001" value=".05">
<p>
Game score to test:
<select name="testScore" id="testScoreInput">
	<option value="breakscore">After first commercial break</option>
	<option value="round1">After Round 1</option>
	<option value="round2">After Round 2</option>
	<option value="final">Final score</option>
	<option value="coryat">Coryat* score</option>
</select>
<p>
<input type="submit" value="Submit">
</form>


<div id="testGrid"></div>

<span class="note">
*The Coryat score, from J-Archive: a player's score if all wagering is disregarded. In the Coryat score, there is no penalty for forced incorrect responses on Daily Doubles, but correct responses on Daily Doubles earn only the natural values of the clues, and any gain or loss from the Final Jeopardy! Round is ignored.

</span>

<script>

var alpha = <%= request.getAttribute("alpha") %>;
$("#alphaInput").val(alpha);
var testScore = "<%= request.getAttribute("testScore") %>";
$("#testScoreInput").val(testScore);

var nVals = $.parseJSON("<%= request.getAttribute("nVals") %>");

var testObj = $.parseJSON("<%= request.getAttribute("stats") %>");
	$("#testGrid").html("<table><tbody><tr id=\"head\"></tr></tbody></table>");
	//Empty corner cell
	$("#testGrid table tbody #head").append("<th></th>");

	$.each(testObj, function(rowIdx, row ) {
		$("#testGrid table tbody").append("<tr id=\""+rowIdx+"\">");
		var rowNum = rowIdx + 1;
		$("#testGrid table tbody #head").append("<th id=\"label\">"+rowNum+"<br><span class=\"note\">n="+nVals[rowIdx]+"</span></th>");
		$("#testGrid table tbody #"+rowIdx).append("<td id=\"label\">"+rowNum+"</td>");
		$.each(row, function(colIdx, cell){
			if(cell){
				$("#testGrid table tbody #"+rowIdx).append("<td id=\"reject\">Reject</td>");
			} else {
				$("#testGrid table tbody #"+rowIdx).append("<td id=\"accept\">Accept</td>");
			}
		});
		$("#testGrid table tbody").append("</tr>");
	  });
</script>

</body>
</html>
