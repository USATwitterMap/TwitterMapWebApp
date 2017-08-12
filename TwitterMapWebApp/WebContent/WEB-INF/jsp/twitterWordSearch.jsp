<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Spring MVC Form Handling</title>  
       <style>
body{margin:40px;}

.btn-circleadd.btn-lg {
  width: 50px;
  height: 50px;
  padding: 0px 10px;
  font-size: 30px;
  line-height: 0;
  border-radius: 25px;
}
.btn-circleremove.btn-lg {
  width: 50px;
  height: 50px;
  padding: 0px 0px;
  font-size: 30px;
  line-height: 0;
  border-radius: 25px;
}
.btn-circlepaint.btn-lg {
  width: 50px;
  height: 50px;
  padding: 0px 10px;
  font-size: 30px;
  line-height: 0;
  border-radius: 25px;
}
.first {
    z-index: 2;
}

.second {
    z-index: 1;
}
.colorTable {
  border-collapse:collapse;
  border-spacing:0;
  margin:0;
  padding:0;
  border:0;
  width:auto;
}
.input-lg {
  font-size: 32px;
}
</style> 
	   <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	   <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyCd9uxfx1yJarUMlvGVOTNEhiDZHCKbEvU&sensor=false"></script>
       <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		
	   <script>
	 var curRow = 1;	      
      $(document).on("click", "#add_word", function(){
    	  nextRow=curRow + 1;
	      var word = document.getElementById('word'+curRow).value;
	      var color = document.getElementById('color'+curRow).style.backgroundColor;
	      $('#word_table').append('<tr id="wordRow'+(nextRow)+'"></tr>');
	      var y = document.getElementById("wordRow"+curRow);
	      y.innerHTML = "<td><label class='input-lg'>"+word+"</label></td><td><label><button type='button' id='color'"+curRow+" style='background-color: "+color+";' data-toggle='modal' data-target='#colorPicker' class='btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint'></button></label></td><td><button type='button' id='remove_word' class='btn btn-danger btn-circleremove btn-lg glyphicon glyphicon-remove'></button></td>";
	      var y2 = document.getElementById("wordRow"+nextRow);
	      y2.innerHTML = "<td><input type='text' class='form-control input-lg' id='word"+nextRow+"'></td><td><button type='button' id='color"+nextRow+"' data-toggle='modal' data-target='#colorPicker' class='btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint'></button></td><td><button type='button' id='add_word' class='btn btn-success btn-circleadd btn-lg glyphicon glyphicon-plus'></button></td>";
	      curRow++; 
	  });
      
      $(document).on("click", "#remove_word", function(){
    	  var test1 = $(this).parent();
    	  test2 = test1.parent();
    	  test3 = test2.attr('id');
    	  test4 = test3.replace("wordRow", "");
    	  $(test2).html('');
	  });
      
      var maxColorColumnsAndRows = 10;
      $( document ).ready(function() {
    	  var rowcontent = ""
   		  for (rowNum = 1; rowNum <= maxColorColumnsAndRows; rowNum++) { 
   			$('#color_table').append('<tr id="colorRow'+rowNum+'"></tr>');
   			var rowcontent = "";
	    	  for (colNum = 1; colNum <= maxColorColumnsAndRows; colNum++) {
	    		  var step = colNum + (rowNum - 1) * maxColorColumnsAndRows;
	    		  var color = rainbow(maxColorColumnsAndRows * maxColorColumnsAndRows, step);
	    		  rowcontent += "<td style='border:0'><button type='button' onclick=\"setColor('"+color+"')\"' class='btn btn-default btn-circleadd btn-lg' style='background-color: "+color+";'></button></td>";
	    	  }
	    	  var y2 = document.getElementById("colorRow"+rowNum);
    	      y2.innerHTML = rowcontent;
	      }
    	  
    	  $('#colorPicker').on('shown.bs.modal', function(e) { 
        	  colorInvoker = e.relatedTarget;
        	});
    	  
    	});
      
      var colorInvoker;
      
      
      
      function setColor(toColor) {
    	  colorInvoker.setAttribute("style", "background-color: "+toColor+";");
    	  $('#colorPicker').modal('toggle');
      }
      
	   var map;
		function initialize() {
		  var mapOptions = {
		    zoom: 5,
		    minZoom: 5,
		    maxZoom: 10,
			clickableIcons: false,
			mapTypeControl: false,
			streetViewControl: false,
		    center: new google.maps.LatLng(39.8282, -98.5795)
		  };
		  map = new google.maps.Map(document.getElementById('map-canvas'),
		      mapOptions);
		  google.maps.event.addListener(map, 'center_changed', function() {
			    checkBounds(map);
			});
			// If the map position is out of range, move it back
			function checkBounds(map) {

			var latNorth = map.getBounds().getNorthEast().lat();
			var latSouth = map.getBounds().getSouthWest().lat();
			var newLat;

			if(latNorth<85 && latSouth>-85)     /* in both side -> it's ok */
			    return;
			else {
			    if(latNorth>85 && latSouth<-85)   /* out both side -> it's ok */
			        return;
			    else {
			        if(latNorth>85)   
			            newLat =  map.getCenter().lat() - (latNorth-85);   /* too north, centering */
			        if(latSouth<-85) 
			            newLat =  map.getCenter().lat() - (latSouth+85);   /* too south, centering */
			    }   
			}
			if(newLat) {
			    var newCenter= new google.maps.LatLng( newLat ,map.getCenter().lng() );
			    map.setCenter(newCenter);
			    }   
			}
		}
		
		function rainbow(numOfSteps, step) {
		    // This function generates vibrant, "evenly spaced" colours (i.e. no clustering). This is ideal for creating easily distinguishable vibrant markers in Google Maps and other apps.
		    // Adam Cole, 2011-Sept-14
		    // HSV to RBG adapted from: http://mjijackson.com/2008/02/rgb-to-hsl-and-rgb-to-hsv-color-model-conversion-algorithms-in-javascript
		    var r, g, b;
		    var h = step / numOfSteps;
		    var i = ~~(h * 6);
		    var f = h * 6 - i;
		    var q = 1 - f;
		    switch(i % 6){
		        case 0: r = 1; g = f; b = 0; break;
		        case 1: r = q; g = 1; b = 0; break;
		        case 2: r = 0; g = 1; b = f; break;
		        case 3: r = 0; g = q; b = 1; break;
		        case 4: r = f; g = 0; b = 1; break;
		        case 5: r = 1; g = 0; b = q; break;
		    }
		    var c = "#" + ("00" + (~ ~(r * 255)).toString(16)).slice(-2) + ("00" + (~ ~(g * 255)).toString(16)).slice(-2) + ("00" + (~ ~(b * 255)).toString(16)).slice(-2);
		    return (c);
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
			   var obj = { "keyword": document.getElementById('keywordInput').value, "date": this.currentDateValue };
			   
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
			            	 var twitterData = data;
			            	 var arrayLength = twitterData.markers.length;
			            	 for (var i = 0; i < arrayLength; i++) {
			            		 (function(i) {
			            			 var delay = twitterData.markers[i].delay;
			            			 var counter = twitterData.markers[i].counter;
			            			 this.markerAnimations.push(setInterval(function() {
			            				 if(counter > 0)
			            		            addDynamicMarker(new google.maps.LatLng(twitterData.markers[i].longitude, twitterData.markers[i].latitude));
			            					counter--;
			            		        }, delay))
			            		    })(i);
			            	 }
			            	 refreshMarkers = setTimeout(function() {
		            	 		for (var i = 0; i < this.markerAnimations.length; i++) {
		     					   clearInterval(this.markerAnimations[i]);
		     	            	}
		            	 		while(currentDateValue < twitterData.nextTime) {
		            	 			incrementTime()
	            	 			}
		            		 	testAjax();
            				}, twitterData.nextTime - currentDateValue);
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
		   var refreshMarkers;
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
				   clearInterval(this.refreshMarkers);
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
						var tempUp = (this.currentDateValue - this.pastDateTime - tickTimeVal + this.tickWeight) / this.tickWeight;
						if(tempUp >= 1) 
						{
							document.getElementById('timeSlider').stepUp((this.currentDateValue - this.pastDateTime - tickTimeVal + this.tickWeight) / this.tickWeight);
						}
					}
					document.getElementById('textInput').innerHTML = new Date(this.currentDateValue);
				}
			}
	   </script>  
   </head>
   <body>
   <!-- Modal -->
	<div id="colorPicker" class="modal fade" role="dialog" style="text-align: center;">
	  <div class="modal-dialog" style="width: auto !important;display: inline-block;">
	
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Select a color</h4>
	      </div>
	      <div class="modal-body">
	        <table class="table" id="color_table" style="border-collapse:collapse;border-spacing:0;margin:0;padding:0;border:0;width:auto;">
	            <thead>
	              <tr>
	              </tr>
	            </thead>
	            <tbody>
	            </tbody>
	          </table>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	
	  </div>
	</div>
   
   <div class="row first">
        <div class="col-sm-4 first">
          <div class="panel panel-success first">
            <div class="panel-heading first">
              <h3 class="panel-title first">Words</h3>
            </div>
            <div class="panel-body">
              <table class="table" id="word_table">
	            <thead>
	              <tr>
	                <th>Word</th>
	                <th>Color</th>
	                <th></th>
	              </tr>
	            </thead>
	            <tbody>
	              <tr id="wordRow1">
	                <td><input type="text" class="form-control input-lg" id="word1"></td>
	                <td><button type="button" id="color1" data-toggle='modal' data-target='#colorPicker' class="btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint"></button></td>
	                <td><button type="button" id="add_word" class="btn btn-success btn-circleadd btn-lg glyphicon glyphicon-plus"></button></td>
	              </tr>
	            </tbody>
	          </table>
            </div>
          </div>
          <div class="panel panel-success">
          	<div class="panel-heading">
              <h3 class="panel-title">Dates</h3>
            </div>
            <div class="panel-body">
              Dates
            </div>
          </div>
        </div><!-- /.col-sm-4 -->
      </div>
   	<div id="map-canvas" style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;z-index:1"></div>
   </body>
   
</html>