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
	   <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyCd9uxfx1yJarUMlvGVOTNEhiDZHCKbEvU&sensor=false"></script>
	   
	   <script>
	   
	   var map;
		function initialize() {
		  var mapOptions = {
		    zoom: 3,
			clickableIcons: false,
			mapTypeControl: false,
			streetViewControl: false,
		    center: new google.maps.LatLng(39.8282, -98.5795)
		  };
		  map = new google.maps.Map(document.getElementById('map-canvas'),
		      mapOptions);
		}
		
		google.maps.event.addDomListener(window, 'load', initialize);
		
		   function updateTextInput(val, startTime, endTime) {
			   currentDateValue = (endTime - startTime) * val / 1000 + startTime;
			   		var d = new Date(currentDateValue);
			   		document.getElementById('textInput').innerHTML=d; 
		        }
		   var image = {
				    url: 'dot.gif',
				    // This marker is 128 pixels wide by 128 pixels high.
				    size: new google.maps.Size(128, 128),
				    // The origin for this image is (0, 0).
				    origin: new google.maps.Point(0, 0),
				    // The anchor for this image is the center of the image at 64-64
				    anchor: new google.maps.Point(64, 64)
				  };
		   function addDynamicMarker(location) {
			    var marker = new google.maps.Marker({
			        position: location,
			        map: map,
			        draggable: false,
			        optimized:false, // <-- required for animated gif
			        icon: image
			    });
			    setTimeout(function () {
			        marker.setMap(null);
			        delete marker;
			    }, 300);
			    return marker;
			}
		   
		   function testAjax() {
			   var obj = { "keyword": document.getElementById('keywordTxt').innerHTML, "date": this.currentDateValue };
			   
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
			             success: function (data) {
			            	 var dataArray = data;
			            	 var arrayLength = dataArray.length;
			            	 for (var i = 0; i < arrayLength; i++) {
			            		 (function(i) {
			            			 var delay = dataArray[i].delay;
			            			 this.markerAnimations.push(setInterval(function() {
			            		            addDynamicMarker(new google.maps.LatLng(dataArray[i].longitude, dataArray[i].latitude));
			            		        }, delay))
			            		    })(i);
			            	 }
			             },
			             fail: function () {
			             },
			             always : function() { 
			             }
			             
				});
		   }

		   var pastDateTime;
		   var windowDateTime;
		   var tickWeight;
		   var currentDateValue;
		   var rangeAnimation;
		   var markerAnimations = [];
		   
		   function animateTimeSlider(startTime, endTime) {
			   if(document.getElementById('PlayPauseButton').value == "Play") {
				   this.markerAnimations = [];
				   var tickerVal = document.getElementById('timeSlider').value;
				   updateTextInput(tickerVal, startTime, endTime)
				   testAjax();
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
				   for (var i = 0; i < this.markerAnimations.length; i++) {
					   clearInterval(this.markerAnimations[i]);
	            	}
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
        <table class="box">
          	<tr class="inner_element">
              	<td colspan="3"><label id = "keywordTxt">Keyword: </label><input id="keywordInput"/></td>
           </tr>
           <tr class="inner_element">
           	<td colspan="3"><div id="map-canvas" style="height:500px; width:100%"></div></td>
		</tr>
           <tr class="inner_element">
           	<td><label>${command.earliestDate}</label></td>
              	<td><input id="timeSlider" class="slider-width100" type="range"  min="0" max="1000" oninput="updateTextInput(this.value, ${command.earliestDate.time}, ${command.latestDate.time});" /></td>
              	<td><label>${command.latestDate}</label></td>
           </tr>
           <tr class="inner_element">
           	<td colspan="3"><label id="textInput"></label></td>
           </tr>
           <tr class="inner_element">
              	<td colspan="3"><input id="PlayPauseButton" type="button" value="Play" onclick="animateTimeSlider(${command.earliestDate.time}, ${command.latestDate.time})" /> </td>
           </tr>
        </table> 
         <input id="TEstAjaxButton" type="button" value="testAjax" onclick="testAjax()" />
   </body>
   
</html>