<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Spring MVC Form Handling</title>
      <style>
         body{margin:40px;}
         .btn-circleadd.btn-lg {
         width: 25px;
         height: 25px;
         padding: 0px 5px;
         font-size: 15px;
         line-height: 0;
         border-radius: 12px;
         }
         .btn-circleremove.btn-lg {
         width: 25px;
         height: 25px;
         padding: 0px 0px;
         font-size: 15px;
         line-height: 0;
         border-radius: 12px;
         }
         .btn-circlepaint.btn-lg {
         width: 25px;
         height: 25px;
         padding: 0px 5px;
         font-size: 15px;
         line-height: 0;
         border-radius: 12px;
         }
         .top {
         z-index: 4;
         }
         .first {
         z-index: 3;
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
         font-size: 12px;
         }
         .systemHealth{
         display: inline-block;
         width:auto;
         }
         .container-fluid {
         overflow-x:scroll;white-space: nowrap;
         }
         #loading-indicator {
         position: absolute;
         left:50%; 
         top:50%;
         }
      </style>
      <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
      <script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
      <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyCd9uxfx1yJarUMlvGVOTNEhiDZHCKbEvU"></script>
      <!-- Include Date Range Picker -->
      <script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
      <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />
      <script>
         google.charts.load('current', {
         'packages':['corechart', 'calendar'],
         // Note: you will need to get a mapsApiKey for your project.
         // See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
         'mapsApiKey': 'AIzaSyCd9uxfx1yJarUMlvGVOTNEhiDZHCKbEvU'
         });
         google.charts.setOnLoadCallback(drawRegionsMap);
         function drawRegionsMap() {
         var data = google.visualization.arrayToDataTable([
          ['State', 'Word'],
         ]);
         
         var options = {
          region: 'US', 
          colorAxis: {colors: ['black', '#e31b23']},
          backgroundColor: '#81d4fa',
          datalessRegionColor: '#black',
          defaultColor: '#black',
         };
         var mapOptions = {
          zoom: 5,
         clickableIcons: false,
         mapTypeControl: false,
         streetViewControl: false,
          center: new google.maps.LatLng(39.8283, -98.5795),
         			draggable: false,
         			maxZoom: 8,
         			minZoom: 4,
         			mapTypeId: google.maps.MapTypeId.ROADMAP,
         			styles: [
         		      {
         			    featureType: "administrative",
         			    elementType: "labels",
         			    stylers: [
         			      { visibility: "off" }
         			    ]
         			  },{
         			    featureType: "poi",
         			    stylers: [
         			      { visibility: "off" }
         			    ]
         			  },{
         			    featureType: "water",
         			    elementType: "labels",
         			    stylers: [
         			      { visibility: "off" }
         			    ]
         			  },{
         			    featureType: "road",
         			    stylers: [
         			      { visibility: "off" }
         			    ]
         			  },{
         		    featureType: "landscape",
          			    stylers: [
          			      { visibility: "off" }
          			    ]
          			  } 
         			]
         };
         
          geochart = new google.visualization.GeoChart(document.getElementById('geochart-colors'));
          geochart.draw(data, {region: "US", resolution: "provinces"});
          earthchart = new google.maps.Map(document.getElementById('earthchart-colors'), mapOptions);
          calendarChart = new google.visualization.Calendar(document.getElementById('calendar_basic'));
         };
      </script>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script>
         var geochart;
         var calendarChart;
         var earthchart;
         var projection;
         var stateMarkers = [];
         var curRow = 1;	      
           $(document).on("click", "#add_word", function(){
         	  nextRow=curRow + 1;
            var word = document.getElementById('word'+curRow).value;
            var color = rgbToHex(document.getElementById('color'+curRow).style.backgroundColor);
            $('#word_table').append('<tr id="wordRow'+(nextRow)+'"></tr>');
            var y = document.getElementById("wordRow"+curRow);
            y.innerHTML = "<td><label>"+word+"</label></td><td><label><button type='button' id='color"+curRow+"' style='background-color: "+color+";' data-toggle='modal' data-target='#colorPicker' class='btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint'></button></label></td><td><button type='button' id='remove_word' class='btn btn-danger btn-circleremove btn-lg glyphicon glyphicon-remove'></button></td>";
            var y2 = document.getElementById("wordRow"+nextRow);
            var newColor = rainbow(maxColorColumnsAndRows * maxColorColumnsAndRows, getRandomInt(0, maxColorColumnsAndRows * maxColorColumnsAndRows));
            y2.innerHTML = "<td><input type='text' class='form-control' id='word"+nextRow+"'></td><td><button type='button' style='background-color: "+newColor+"' id='color"+nextRow+"' data-toggle='modal' data-target='#colorPicker' class='btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint'></button></td><td><button type='button' id='add_word' class='btn btn-success btn-circleadd btn-lg glyphicon glyphicon-plus'></button></td>";
            curRow++; 
         });
           
           $(document).on("click", "#remove_word", function(){
         	  document.getElementById($(this).parent().parent().attr('id')).remove();
         });
           var maxColorColumnsAndRows = 10;
           $( document ).ready(function() {
            
            retrieveSystemHealth();
           
         
         	  ChartMarker.prototype = new google.maps.OverlayView;
         
         	  ChartMarker.prototype.onAdd = function() {
         	      $( this.getPanes().overlayMouseTarget ).append( this.$div );
         	  };
         
         	  ChartMarker.prototype.onRemove = function() {
         	      this.$div.remove();
         	  };
         
         	  ChartMarker.prototype.draw = function() {
         	      var marker = this;
         	      var projection = this.getProjection();
         	      var position = projection.fromLatLngToDivPixel( this.get('position') );
         
         	      this.$div.css({
         	          left: position.x,
         	          top: position.y - 60,
         	          display: 'block'
         	      })
         
         	      this.$inner
         	          .html( '<img src="' + this.get('image') + '"/>' )
         	          .click( function( event ) {
         	              var events = marker.get('events');
         	              events && events.click( event );
         	          });
         	          
         	      this.chart = new google.visualization.PieChart( this.$inner[0] );
         	      this.chart.draw( this.get('chartData'), this.get('chartOptions') );
         	  };
         	      
         	 $(document).ajaxSend(function(event, request, settings) {
         	    $('#loading-indicator').show();
         	});
         
         	$(document).ajaxComplete(function(event, request, settings) {
         	    $('#loading-indicator').hide();
         	});
         	  
         	  $(function() {
         		    $('input[name="daterange"]').daterangepicker({
         		        timePicker: true,
         		        timePickerIncrement: 30,
         		        locale: {
         		            format: 'YYYY-MM-DD HH:mm'
         		        }
         		    });
         		});
         	  
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
         	  
         	  $('#search').on('click', function (e) {
         		  var searchData = [];
         		  var table = $('#word_table')[0];
         		  var rows = table.rows;
         		  var time = $('#startEndTime span')[0].innerText;
         		  for (var i = 1; i < rows.length-1; i++) {
         			  var wordDetails = [];
         			  wordDetails.push(rows[i].children[0].textContent);
         			  var temp = rows[i].children[1].innerHTML;
         			  temp =  temp.substring(temp.indexOf("background-color: ") + "background-color: ".length);
         			  wordDetails.push(temp.substring(0, temp.indexOf(";")));
         			  wordDetails.push(time);
         			  searchData.push(wordDetails);
         		  }
         		 var query = { "searchData": searchData, "populationControl": $("#PopControl").is(':checked') };
         		  if(searchData.length == 1) 
         		  {
         		  	searchSingle(query);
         		  }
         		  else if(searchData.length >1) 
         		  {
           		  	searchMultiple(query);
           		  }
         		 $('.collapse').collapse('hide');
         		})
         	});
           
           var colorInvoker;
           $(function() {
         
             var start = moment().subtract(7, 'days');
             var end = moment();
         
             function cb(start, end) {
             	start.set({minute:0,second:0,millisecond:0});
             	end.set({minute:0,second:0,millisecond:0});
                 $('#startEndTime span').html(start.format('YYYY-MM-DD HH') + ' - ' + end.format('YYYY-MM-DD HH'));
             }
             
             $('#startEndTime').daterangepicker({
                 startDate: start,
                 endDate: end,
                 timePicker: true,
                 timePickerIncrement: 60,
                 timePicker24Hour: true,
                 maxDate: moment(),
                 opens: 'right',
                 ranges: {
                    'Last Week': [start, end],
                    'Week Prior': [moment().subtract(14, 'days'), moment().subtract(7, 'days')],
                    'Last 30 Days': [moment().subtract(30, 'days'), moment()],
                    'This Year': [moment().subtract(1, 'year'), moment()]
                 },
         	    locale: {
                     format: 'YYYY-MM-DD HH'
                 }
             }, cb);
         
             cb(start, end);
             
         });
           
           function getRandomInt(min, max) {
             return Math.floor(Math.random() * (max - min + 1)) + min;
         }
           
           function componentFromStr(numStr, percent) {
         	    var num = Math.max(0, parseInt(numStr, 10));
         	    return percent ?
         	        Math.floor(255 * Math.min(100, num) / 100) : Math.min(255, num);
         	}
         
         	function rgbToHex(rgb) {
         	    var rgbRegex = /^rgb\(\s*(-?\d+)(%?)\s*,\s*(-?\d+)(%?)\s*,\s*(-?\d+)(%?)\s*\)$/;
         	    var result, r, g, b, hex = "";
         	    if ( (result = rgbRegex.exec(rgb)) ) {
         	        r = componentFromStr(result[1], result[2]);
         	        g = componentFromStr(result[3], result[4]);
         	        b = componentFromStr(result[5], result[6]);
         
         	        hex = "#" + (0x1000000 + (r << 16) + (g << 8) + b).toString(16).slice(1);
         	    }
         	    return hex;
         	}
           
           function setColor(toColor) {
         	  colorInvoker.setAttribute("style", "background-color: "+toColor+";");
         	  $('#colorPicker').modal('toggle');
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
         function searchMultiple(searchData) {
           var obj = searchData;
           
           for (var i = 0; i < stateMarkers.length; i++) 
           {
            stateMarkers[i].setMap(null);
              }
           stateMarkers = [];
           $.ajax({
                    	 type: "POST",
                     contentType: "application/json",
                     url: "searchMultiple",
                     data: JSON.stringify(obj),
                     dataType: 'json',
                     headers: { 
                         'Accept': 'application/json',
                         'Content-Type': 'application/json' 
                     },
                     timeout: 60000,
                     success: function (rawStateData) {
                    	 document.getElementById("earthchart-colors").style.zIndex = "2";
                    	 document.getElementById("geochart-colors").style.zIndex = "1";
                    	 for(var i = 0; i < rawStateData.locations.length; i++)
                    	 {
                    	  var colorData = [];
                   		  var latLng = new google.maps.LatLng( rawStateData.locations[i].latitude, rawStateData.locations[i].longitude );
                   		  var stateDataArray = [];
                   		  stateDataArray.push([]);
                 				  stateDataArray[0].push('Word');
                 				  stateDataArray[0].push('Occurances');
                   		  for(var j = 0; j < rawStateData.locations[i].wordData.length; j++) 
                   		  {
                   			stateDataArray.push([]);
                   			stateDataArray[j + 1].push(rawStateData.locations[i].wordData[j][0]);
                   			colorData.push(rawStateData.locations[i].wordData[j][1]);
                   			stateDataArray[j + 1].push(rawStateData.locations[i].wordData[j][2]);
                   		  }
                  		 	  var data = google.visualization.arrayToDataTable(stateDataArray);
         	 	      var options = {
         	 	          fontSize: 8,
         	 	          backgroundColor: 'transparent',
         	 	          legend: 'none',
         	 	          colors: colorData,
         	 	          //chartArea:{left:0,top:0,width:60,height:60}
         	 	      };
                   		  var marker = new ChartMarker({
               	 	          map: earthchart,
               	 	          position: latLng,
               	 	          width: '120px',
               	 	          height: '120px',
               	 	          chartData: data,
               	 	          chartOptions: options,
               	 	      });
                   		  stateMarkers.push(marker);
                   	  }
                     }, 
                     fail: function () 
                     {
                    	var test = 1; 
                     },
                     always : function() 
                     { 
                  	var test2 = 1; 
                     }
                     
         });
          }
         
          function searchSingle(searchData) {
           var obj = searchData;
           $.ajax({
                    	 type: "POST",
                     contentType: "application/json",
                     url: "searchSingle",
                     data: JSON.stringify(obj),
                     dataType: 'json',
                     headers: { 
                         'Accept': 'application/json',
                         'Content-Type': 'application/json' 
                     },
                     timeout: 60000,
                     success: function (rawStateData) {
                    	 document.getElementById("geochart-colors").style.zIndex = "2";
                    	 document.getElementById("earthchart-colors").style.zIndex = "1";
                    	 var data = google.visualization.arrayToDataTable(rawStateData.areaChart);
          	        var options = {
          	          region: 'US', 
          	          colorAxis: {colors: ['black', data.color]},
          	          backgroundColor: '#81d4fa',
          	          datalessRegionColor: '#f8bbd0',
          	          defaultColor: '#f5f5f5',
          	        };
         			geochart.draw(data, {region: "US", resolution: "provinces", colors: [rawStateData.color]});
                     },
                     fail: function () 
                     {
                    	var test = 1; 
                     },
                     always : function() 
                     { 
                  	var test2 = 1; 
                     }
                     
         });
          }
          
          function retrieveSystemHealth() {
              $.ajax({
                       	 type: "POST",
                        contentType: "application/json",
                        url: "getSystemHealth",
                        dataType: 'json',
                        headers: { 
                            'Accept': 'application/json',
                            'Content-Type': 'application/json' 
                        },
                        timeout: 10000,
                        success: function (systemHealthData) {                       	 
                       	var dataTable = new google.visualization.DataTable();
                       	
                        dataTable.addColumn({ type: 'date', id: 'Date' });
                        dataTable.addColumn({ type: 'number', id: 'Won/Loss' });
                        var arrayLength = systemHealthData.systemHealth.length;
                        var lowestMonth = 12;
                        for (var i = 0; i < arrayLength; i++) {
                        	var resolvedDate = new Date(systemHealthData.systemHealth[i][0]);
                        	dataTable.addRow([resolvedDate, systemHealthData.systemHealth[i][1]]);
                        	if(lowestMonth > resolvedDate.getMonth()) 
                        	{
                        		lowestMonth = resolvedDate.getMonth();
                        	}
                        }
                        
                        var calOptions = {
                       		title: "System Downtime - Missing Data",
                       		colorAxis: {minValue: 0, maxValue: 23, colors: ['#00FF00', '#FF0000']},
                        	width: '1000px'
                        };
                        document.getElementById('calendar_basic').setAttribute("style","width:1000px");
                        calendarChart.draw(dataTable, calOptions);
                        $(".container-fluid").animate({scrollLeft: (lowestMonth / 12) * 1000}, 800);
                        },
                        fail: function () 
                        {
                       	var test = 1; 
                        },
                        always : function() 
                        { 
                     	var test2 = 1; 
                        }
                        
            });
             }
          
          function ChartMarker( options ) {
            this.setValues( options );
            
            this.$inner = $('<div>').css({
                position: 'relative',
                left: '-50%', top: '-50%',
                width: options.width,
                height: options.height,
                fontSize: '1px',
                lineHeight: '1px',
                backgroundColor: 'transparent',
                cursor: 'default'
            });
         
            this.$div = $('<div>')
                .append( this.$inner )
                .css({
                    position: 'absolute',
                    display: 'none'
                });
         };
          
      </script>  
   </head>
   <body>
      <!-- Modal -->
      <img src="${pageContext.request.contextPath}/images/loadingAjax.gif" id="loading-indicator" style="display:none" class="top"/>
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
      <div class="container first col-sm-4" style="width: 500px">
         <!-- Root accordian -->
         <div class="panel-group first" id="accordionControls">
            <div class="panel panel-default first">
               <div class="panel-heading first">
                  <h4 class="panel-title first" style="text-align: center">
                     <a class="panel-toggle first" data-toggle="collapse" data-parent="#accordionControls" href=".collapseTarget">
                     Search Controls
                     </a>
                  </h4>
               </div>
               <div class="collapseTarget collapse in">
                  <!-- Here we insert another nested accordion -->
                  <div id="collapseWords" class="panel-body first">
                     <div class="panel-inner first">
                        <div class="panel-group first" id="accordionWords">
                           <div class="panel panel-default first">
                              <div class="panel-heading first">
                                 <h4 class="panel-title first" style="text-align: center">
                                    Word
                                 </h4>
                              </div>
                              <div class="panel-body first">
                                 <table class="table first" id="word_table">
                                    <thead>
                                       <tr>
                                          <th>Word</th>
                                          <th>Color</th>
                                          <th></th>
                                       </tr>
                                    </thead>
                                    <tbody>
                                       <tr id="wordRow1">
                                          <td><input type="text" class="form-control" id="word1"></td>
                                          <td><button type="button" id="color1" style="background-color: #ff0f00" data-toggle='modal' data-target='#colorPicker' class="btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint"></button></td>
                                          <td><button type="button" id="add_word" class="btn btn-success btn-circleadd btn-lg glyphicon glyphicon-plus"></button></td>
                                       </tr>
                                    </tbody>
                                 </table>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
                  <!-- Here we insert another nested accordion -->
                  <div id="collapseDates" class="panel-body first">
                     <div class="panel-inner first">
                        <div class="panel-group first" id="accordionDates">
                           <div class="panel panel-default first">
                              <div class="panel-heading first">
                                 <h4 class="panel-title first" style="text-align: center">
                                    Dates
                                 </h4>
                              </div>
                              <div class="panel-body first">
                                 <div id="startEndTime" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;
                                    <span></span> <b class="caret"></b>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
                  <!-- Here we insert another nested accordion -->
                  <div id="collapseHealth" class="panel-body first">
                     <div class="panel-inner first">
                        <div class="panel-group first" id="accordionDates">
                           <div class="panel panel-default first">
                              <div class="panel-heading first">
                                 <h4 class="panel-title first" style="text-align: center">
                                    System Health
                                 </h4>
                              </div>
                              <div class="panel-body first">
                                 <div  id="calendar_scroll" class="panel-body first container-fluid ">
                                    <div id="calendar_basic" class="systemHealth"></div>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
                  <div class="panel-body first">
                     <button id="search" type="button" class="btn btn-primary">Search</button>
                     <label><input id="PopControl" type="checkbox" value="">Control for population</label>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <div id="geochart-colors" style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;z-index:1;"></div>
      <div id="earthchart-colors" style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;z-index:2;"></div>
   </body>
</html>