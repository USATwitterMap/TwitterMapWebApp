<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Spring MVC Form Handling</title>  
      <style>
		   .box {
		    position:fixed;
		    top:10%;
		    left:10%;
		    width:80%;
		    height:80%;
		}
		.inner_element {
		    text-align: center;
		}
		.slider-width100
		{
		    width: 100%;
		}
	   </style> 
	   <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	   <script>
		   function updateTextInput(val, startTime, endTime) {
			   currentDateValue = (endTime - startTime) * val / 1000 + startTime;
			   		var d = new Date(currentDateValue);
			   		document.getElementById('textInput').innerHTML=d; 
		        }
		   
		   function testAjax() {
			   document.getElementById('textInput').innerHTML="starting"; 
			   var obj = { "id":"hello" };
			   
			   $.ajax({
		             	 type: "POST",
			             contentType: "application/json",
			             url: "twitterAjax",
			             data: JSON.stringify(obj),
			             dataType: 'json',
			             headers: { 
			                 'Accept': 'application/json',
			                 'Content-Type': 'application/json' 
			             },
			             timeout: 1000,
			             done: function () {
			            	 document.getElementById('textInput').innerHTML="success";
			             },
			             fail: function () {
			            	 document.getElementById('textInput').innerHTML="error";
			             },
			             always : function() { 
			            	 document.getElementById('textInput').innerHTML="always";
			             }
			             
				});
		   }

		   var pastDateTime;
		   var windowDateTime;
		   var tickWeight;
		   var currentDateValue;
		   var rangeAnimation;
		   
		   function animateTimeSlider(startTime, endTime) {
			   if(document.getElementById('PlayPauseButton').value == "Play") {
				   var val = document.getElementById('timeSlider').value;
				   this.pastDateTime = startTime;
				   this.windowDateTime = (endTime - startTime);
				   this.currentDateValue = windowDateTime * val / 1000 + pastDateTime;
				   this.tickWeight = this.windowDateTime / 1000;
				   this.rangeAnimation = setInterval(incrementTime, 1000);
				   document.getElementById('PlayPauseButton').value="Pause";
			   }
			   else 
			   {
				   clearInterval(this.rangeAnimation);
				   document.getElementById('PlayPauseButton').value="Play";
			   }
			}

			function incrementTime() {
				if(currentDateValue < windowDateTime + pastDateTime) {
					this.currentDateValue += 1000;
					var tickTimeVal = document.getElementById('timeSlider').value * this.tickWeight;
					if(this.currentDateValue - this.pastDateTime > tickTimeVal + this.tickWeight) {
						document.getElementById('timeSlider').stepUp((this.currentDateValue - this.pastDateTime - tickTimeVal + this.tickWeight) / this.tickWeight);
					}
					document.getElementById('textInput').innerHTML = new Date(this.currentDateValue);
				}
			}
	   </script>  
   </head>
   <body>
   test 14
      <form:form id="wordSearchForm" method = "POST" action = "searchWords">
         <table class="box">
           	<tr class="inner_element">
               	<td colspan="3"><form:label path = "keyword">${results.occurances} Keyword: </form:label><form:input path = "keyword" /></td>
            </tr>
            <tr class="inner_element">
            	<td colspan="3"> <img src="USA.svg" height="75%" width="75%"/></td>
			</tr>
            <tr class="inner_element">
            	<td><form:label path = "earliestDate">${command.earliestDate}</form:label></td>
               	<td><form:input id="timeSlider" class="slider-width100" type="range" path="date" min="0" max="1000" oninput="updateTextInput(this.value, ${command.earliestDate.time}, ${command.latestDate.time});" /></td>
               	<td><form:label path = "earliestDate">${command.latestDate}</form:label></td>
            </tr>
            <tr class="inner_element">
            	<td colspan="3"><label id="textInput"></label></td>
            </tr>
            <tr class="inner_element">
               	<td colspan="3"><input type = "submit" value = "Search"/></td>
            </tr>
         </table>          
      </form:form>
      <input id="PlayPauseButton" type="button" value="Play" onclick="animateTimeSlider(${command.earliestDate.time}, ${command.latestDate.time})" /> 
      <input id="TEstAjaxButton" type="button" value="testAjax" onclick="testAjax()" />
   </body>
   
</html>