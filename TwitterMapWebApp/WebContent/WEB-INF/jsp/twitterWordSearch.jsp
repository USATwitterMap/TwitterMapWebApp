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
         .middle {
         z-index: 3;
         }
         .second {
         z-index: 1;
         }
         .last {
         z-index: 0;
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
         .bottomControls{
         position: absolute;
         bottom: 0px;
         margin-right: auto;
         margin-left: auto;
         left: 0px;
         right: 0px;
         }
         .container-vert-fluid {
         overflow-y:scroll;white-space: nowrap;
         }
         .container-fluid {
         overflow-x:scroll;white-space: nowrap;
         }
         #loading-indicator {
         position: absolute;
         left:50%; 
         top:50%;
         }
         #loading-indicator-calendar {
         left:50%; 
         top:50%;
         }
         .btn-space {
         margin-top: 10px;
         }
         .nav-tabs.centered > li, .nav-pills.centered > li {
         float:none;
         display:inline-block;
         *display:inline; /* ie7 fix */
         zoom:1; /* hasLayout ie7 trigger */
         }
         .nav-tabs.centered, .nav-pills.centered {
         text-align:center;
         }
         .tab-content > .tab-pane:not(.active),
         .pill-content > .pill-pane:not(.active) {
         display: block;
         position: absolute;
         zindex: 0;
         height: 100%;
         width: 100%;
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
         			fullscreenControl: false,
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
          lineGraphChart = new google.visualization.LineChart(document.getElementById('lineGraph'));
          geochart = new google.visualization.GeoChart(document.getElementById('geochart-colors'));
          geochart.draw(data, {region: "US", resolution: "provinces"});
          earthchart = new google.maps.Map(document.getElementById('earthchart-colors'), mapOptions);
          calendarChart = new google.visualization.Calendar(document.getElementById('calendar_basic'));
          
          retrieveSystemHealth();
         };
      </script>
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script>
         var geochart;
         var lineGraphChart;
         var calendarChart;
         var earthchart;
         var projection;
         var lineGraphData;
         var stateMarkers = [];
         var geoData = [];
         var curRow = 1;	      
           $(document).on("click", "#add_word", function(){
         	  nextRow=curRow + 1;
            var word = document.getElementById('word'+curRow).value;
            var color = rgbToHex(document.getElementById('color'+curRow).style.backgroundColor);
            $('#word_table > tbody:last-child').append('<tr id="wordRow'+(nextRow)+'"></tr>');
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
         	  
         	 $('#selState1').on('change', function(){
         		updateLineGraph();
         	  });
         	$('#selState2').on('change', function(){
         		updateLineGraph();
         	  });
         	$('#selWord1').on('change', function(){
         		updateLineGraph();
         	  });
         	$('#selWord2').on('change', function(){
         		updateLineGraph();
         	  });
         	$('#selGeoWord').on('change', function(){
         		updateGeoGraph($('#selGeoWord').find('option:selected').attr('value'));
          	});
         	  $('#colorPicker').on('shown.bs.modal', function(e) { 
             	  colorInvoker = e.relatedTarget;
             	});
         	  
          	 $('#findPopular').click(function(){
          		var time = $('#startEndTime span')[0].innerText;
          		var query = {"searchTime": time};
          		$("#popular_terms_table > tbody").html("");
          		searchPopularTerms(query);
          		$("#popularTermsDisplay").modal('show');
          	 });
         	  
         	  $('#search').on('click', function (e) {
         		  var searchData = [];
         		  var table = $('#word_table')[0];
         		  var rows = table.rows;
         		  var time = $('#startEndTime span')[0].innerText;
         		  $('#selWord1').empty();
         		  $('#selWord2').empty();
         		 $('#selGeoWord').empty();
         		  var firstWord = -1;
         		  var secondWord = -1;
         		  for (var i = 1; i < rows.length-1; i++) {
         			  var wordDetails = [];
         			  wordDetails.push(rows[i].children[0].textContent);
         			  var temp = rows[i].children[1].innerHTML;
         			  temp =  temp.substring(temp.indexOf("background-color: ") + "background-color: ".length);
         			  temp = temp.substring(0, temp.indexOf(";"));
                      $('#selWord1').append('<option value=' + temp + '>'+ rows[i].children[0].textContent +'</option>');
                      $('#selGeoWord').append('<option value=' + (i - 1) + '>'+ rows[i].children[0].textContent +'</option>');
                      if(firstWord == -1) 
                      {
                      	firstWord = i - 1;
                      }
                      $('#selWord2').append('<option value=' + temp + '>'+ rows[i].children[0].textContent +'</option>');
                      secondWord =  i - 1;
         			  wordDetails.push(temp);
         			  wordDetails.push(time);
         			  searchData.push(wordDetails);
         		  }
         		 if(firstWord != -1)  
         		 {
         			$('#selWord1')[0].selectedIndex = firstWord
         			$('#selGeoWord')[0].selectedIndex = firstWord
         		 }
         		 if(secondWord != -1)  
         		 {
         		 	$('#selWord2')[0].selectedIndex = secondWord
         		 }
         		 var query = { "searchData": searchData, "populationControl": $("#PopControl").is(':checked') };
         		search(query);
         		  if(searchData.length < 0) 
         		  {
         			 search(query);
         		  }
         		  else
         		  {
           		  	//warning
           		  }
         		 $('.collapseTarget').collapse('hide');
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
         
           function addWord(word) 
           {
            nextRow=curRow + 1;
               var color = rainbow(maxColorColumnsAndRows * maxColorColumnsAndRows, getRandomInt(0, maxColorColumnsAndRows * maxColorColumnsAndRows));
               $('#word_table > tbody:last-child').append('<tr id="wordRow'+(nextRow)+'"></tr>');
               var y = document.getElementById("wordRow"+curRow);
               y.innerHTML = "<td><label>"+word+"</label></td><td><label><button type='button' id='color"+curRow+"' style='background-color: "+color+";' data-toggle='modal' data-target='#colorPicker' class='btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint'></button></label></td><td><button type='button' id='remove_word' class='btn btn-danger btn-circleremove btn-lg glyphicon glyphicon-remove'></button></td>";
               var y2 = document.getElementById("wordRow"+nextRow);
               var newColor = rainbow(maxColorColumnsAndRows * maxColorColumnsAndRows, getRandomInt(0, maxColorColumnsAndRows * maxColorColumnsAndRows));
               y2.innerHTML = "<td><input type='text' class='form-control' id='word"+nextRow+"'></td><td><button type='button' style='background-color: "+newColor+"' id='color"+nextRow+"' data-toggle='modal' data-target='#colorPicker' class='btn btn-danger btn-circlepaint btn-lg glyphicon glyphicon-tint'></button></td><td><button type='button' id='add_word' class='btn btn-success btn-circleadd btn-lg glyphicon glyphicon-plus'></button></td>";
               curRow++; 
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
         function search(searchData) {
           var obj = searchData;
           
           for (var i = 0; i < stateMarkers.length; i++) 
           {
            stateMarkers[i].setMap(null);
              }
           stateMarkers = [];
           $.ajax({
                    	 type: "POST",
                     contentType: "application/json",
                     url: "search",
                     data: JSON.stringify(obj),
                     dataType: 'json',
                     headers: { 
                         'Accept': 'application/json',
                         'Content-Type': 'application/json' 
                     },
                     timeout: 60000,
                     success: function (searchResultView) {
                    	 if(searchResultView.multiWordView != null) 
                    	 {
                     	 for(var i = 0; i < searchResultView.multiWordView.locations.length; i++)
                     	 {
                      	  var colorData = [];
                     		  var latLng = new google.maps.LatLng( searchResultView.multiWordView.locations[i].latitude, searchResultView.multiWordView.locations[i].longitude );
                     		  var stateDataArray = [];
                     		  stateDataArray.push([]);
                   				  stateDataArray[0].push('Word');
                   				  stateDataArray[0].push('Occurances');
                     		  for(var j = 0; j < searchResultView.multiWordView.locations[i].wordData.length; j++) 
                     		  {
                     			stateDataArray.push([]);
                     			stateDataArray[j + 1].push(searchResultView.multiWordView.locations[i].wordData[j][0]);
                     			colorData.push(searchResultView.multiWordView.locations[i].wordData[j][1]);
                     			stateDataArray[j + 1].push(searchResultView.multiWordView.locations[i].wordData[j][2]);
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
                    	 }
                    	 if(searchResultView.singleWordView != null) 
                    	 {
                    		 geoData = searchResultView.singleWordView;
                    		 updateGeoGraph(0);
                    	 }
                    	 if(searchResultView.lineGraphView != null) 
                   		 {
                    		lineGraphData = google.visualization.arrayToDataTable(searchResultView.lineGraphView.lineData);
               	 	  		var options = {
               	           		title: 'Company Performance',
               	           		curveType: 'function',
               	           		legend: { position: 'bottom' }
               	        	};
               	 	  		updateLineGraph();
                   		 }
                     }, 
                     fail: function () 
                     {
                     },
                     always : function() 
                     { 
                     }
                     
         });
          }
         
         function updateGeoGraph(index) 
         {
          var data = google.visualization.arrayToDataTable(geoData[index].areaChart);
             var options = {
               region: 'US', 
               colorAxis: {colors: ['black', data.color]},
               backgroundColor: '#81d4fa',
               datalessRegionColor: '#f8bbd0',
               defaultColor: '#f5f5f5',
             };
         geochart.draw(data, {region: "US", resolution: "provinces", colors: [geoData[index].color]});
         document.getElementById('geochart-colors').className = 'active';
         }
         
         function updateLineGraph() 
         {
          var lineGraphView = new google.visualization.DataView(lineGraphData);
          var numOfColumns = lineGraphView.getNumberOfColumns();
         var disabledColumns = [];
         var enabledColumn1 = $('#selState1').find('option:selected').attr('value') + ": " + $('#selWord1').find('option:selected').text();
         var column1Color =  "";
         var enabledColumn2 = $('#selState2').find('option:selected').attr('value') + ": " + $('#selWord2').find('option:selected').text();
         var column2Color =  "";
         var dashedLine;
         $("#LineSelector0").css("background", $('#selWord1').find('option:selected').attr('value'));
         $("#LineSelector1").css("background", $('#selWord2').find('option:selected').attr('value'));
         for(var j = 0; j < numOfColumns; j++) 
         {
         var columnLabel = lineGraphView.getColumnLabel(j);
         if(columnLabel != "Time" ) 
         {
          		if(columnLabel == enabledColumn1) 
          		{
          			if(column1Color == "") 
          			{
          				column1Color =  $('#selWord1').find('option:selected').attr('value');
          			}
          			else 
          			{
          				column2Color =  $('#selWord1').find('option:selected').attr('value');
          			}
          		}
          		else if(columnLabel == enabledColumn2) 
          		{
          			if(column1Color == "") 
          			{
          				dashedLine = 0;
          				column1Color = $('#selWord2').find('option:selected').attr('value');
          			}
          			else 
          			{
          				dashedLine = 1;
          				column2Color = $('#selWord2').find('option:selected').attr('value');
          			}
          		}
          		else
          		{
          			disabledColumns.push(j);
          		}
         }
         }
         
         var options;
         if(dashedLine == 0) {
         options = {
               		title: 'Keywords Over Time',
               		curveType: 'function',
               		legend: { position: 'top' },
               		lineWidth: 4,
         		colors: [column1Color,column2Color],
         	 	series: { 0 : { lineDashStyle: [10, 2]  }, }
         	};
         }
         else {
         options = {
               		title: 'Keywords Over Time',
               		curveType: 'function',
               		legend: { position: 'top' },
               		lineWidth: 4,
         		colors: [column1Color,column2Color],
         	 	series: { 1 : { lineDashStyle: [10, 2]  }, }
         	};
         }
         lineGraphView.hideColumns(disabledColumns); 
         lineGraphChart.draw(lineGraphView, options);
         }
         
         function searchPopularTerms(searchData) {
             var obj = searchData;
             $.ajax({
                      	 type: "POST",
                       contentType: "application/json",
                       url: "searchPopularTerms",
                       data: JSON.stringify(obj),
                       dataType: 'json',
                       headers: { 
                           'Accept': 'application/json',
                           'Content-Type': 'application/json' 
                       },
                       timeout: 60000,
                       success: function (popularData) {
                   	   	  for(var i = 0; i < popularData.results.length; i++) 
                   		  {
                   	      	$('#popular_terms_table > tbody:last-child').append('<tr id="popularRow'+i+'"></tr>');
                   	     	var y = document.getElementById("popularRow"+i);
                        	y.innerHTML = "<td>" + popularData.results[i] + "</td><td><button type='button' id='add_pop_word' onclick=\"addWord('"+popularData.results[i]+"')\"' class='btn btn-success btn-circleadd btn-lg glyphicon glyphicon-plus'></button></td>";
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
          
          function retrieveSystemHealth() {
        	  var jsonRaw = $('#systemHealthData')[0].value;
        	  var systemHealthData = JSON.parse(jsonRaw);
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
	             $('#loading-indicator-calendar').hide();
              
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
   	<input type="hidden" id="systemHealthData" value='${systemHealth}'/>
      <img src="${pageContext.request.contextPath}/images/loadingAjax.gif" id="loading-indicator" style="display:none" class="top"/>
      <!-- Modal color picker -->
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
      <!-- Modal popular terms -->
      <div id="popularTermsDisplay" class="modal fade" role="dialog" style="text-align: center;">
         <div class="modal-dialog" style="width: auto !important;display: inline-block;">
            <!-- Modal content-->
            <div class="modal-content">
               <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                  <h4 class="modal-title">Popular Terms For Selected Dates</h4>
               </div>
               <div class="modal-body">
                  <table class="table" id="popular_terms_table" style="border-collapse:collapse;border-spacing:0;margin:0;padding:0;border:0;width:auto;width:100%;">
                     <thead>
                        <tr>
                           <th>Word</th>
                           <th></th>
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
      <div class="container first col-sm-4" style="width: 500px; ">
         <!-- Root accordian -->
         <div class="panel-group first container-vert-fluid" id="accordionControls" style="max-height: 90%">
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
                                 <button id="findPopular" type="button" class="btn btn-default btn-space">View Popular Terms</button>
                                 <div id="startEndTime" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%;">
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
                                    <img src="${pageContext.request.contextPath}/images/loadingAjax.gif" id="loading-indicator-calendar" style="display:none" class="top"/>
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
      <div style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;">
         <ul class="nav nav-tabs navbar-right second">
            <li class="active second"><a data-toggle="tab" href="#home">Keyword Ratio Per State</a></li>
            <li class="second"><a data-toggle="tab" href="#menu1">Keyword Occurances By State</a></li>
            <li class="second"><a data-toggle="tab" href="#menu2">Keyword Occurances Over Time</a></li>
         </ul>
         <div class="tab-content"  style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;">
            <div id="home" class="tab-pane fade in active last">
               <div id="earthchart-colors" style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;"></div>
            </div>
            <div id="menu1" class="tab-pane fade last">
               <div id="geochart-colors" style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;"></div>
               <div class="container first bottomControls">
                  <!-- Root accordian -->
                  <div class="panel-group first" id="accordionGeoControls">
                     <div class="panel panel-default first">
                        <div class="panel-heading first">
                           <h4 class="panel-title first" style="text-align: center;">
                              <a class="panel-toggle first" data-toggle="collapse" data-parent="accordionGeoControls" href=".collapseGeoControls" style="opacity: 1.0">
                              Map Control
                              </a>
                           </h4>
                        </div>
                        <div class="collapseGeoControls panel-body collapse in first">
                           <div class="panel-group">
                              <div class="panel panel-default">
                                 <div class="panel-heading"><b>Word</b></div>
                                 <div class="panel-body">
                                    <label for="selGeoWord" class="col-sm-2">Select Word:</label>
                                    <select class="form-control col-sm-10" id="selGeoWord" style="width:80%">
                                    </select>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
            <div id="menu2" class="tab-pane fade last">
               <div id="lineGraph" style="height:100%; width:100%; position: absolute;top: 0px;left: 0px;"></div>
               <div class="container first bottomControls">
                  <!-- Root accordian -->
                  <div class="panel-group first" id="accordionLineControls">
                     <div class="panel panel-default first">
                        <div class="panel-heading first">
                           <h4 class="panel-title first" style="text-align: center;">
                              <a class="panel-toggle first" data-toggle="collapse" data-parent="accordionLineControls" href=".collapseLineControls" style="opacity: 1.0">
                              Line Graph Control
                              </a>
                           </h4>
                        </div>
                        <div class="collapseLineControls panel-body collapse in first">
                           <div class="panel-group">
                              <div class="panel panel-default">
                                 <div class="panel-heading"><b>First Line Series (Solid line)</b> <label id="LineSelector0"><b>COLOR</b></label></div>
                                 <div class="panel-body">
                                    <label for="selWord1" class="col-sm-2">Select Word:</label>
                                    <select class="form-control col-sm-4" id="selWord1" style="width:80%">
                                    </select>
                                    <label for="selState1" class="col-sm-2">Select State:</label>
                                    <select class="form-control col-sm-4" id="selState1" style="width:80%">
                                       <option value="OVERALL">OVERALL</option>
                                       <option value="AL">Alabama</option>
                                       <option value="AK">Alaska</option>
                                       <option value="AZ">Arizona</option>
                                       <option value="AR">Arkansas</option>
                                       <option value="CA">California</option>
                                       <option value="CO">Colorado</option>
                                       <option value="CT">Connecticut</option>
                                       <option value="DE">Delaware</option>
                                       <option value="FL">Florida</option>
                                       <option value="GA">Georgia</option>
                                       <option value="HI">Hawaii</option>
                                       <option value="ID">Idaho</option>
                                       <option value="IL">Illinois</option>
                                       <option value="IN">Indiana</option>
                                       <option value="IA">Iowa</option>
                                       <option value="KS">Kansas</option>
                                       <option value="KY">Kentucky</option>
                                       <option value="LA">Louisiana</option>
                                       <option value="ME">Maine</option>
                                       <option value="MD">Maryland</option>
                                       <option value="MA">Massachusetts</option>
                                       <option value="MI">Michigan</option>
                                       <option value="MN">Minnesota</option>
                                       <option value="MS">Mississippi</option>
                                       <option value="MO">Missouri</option>
                                       <option value="MT">Montana</option>
                                       <option value="NE">Nebraska</option>
                                       <option value="NV">Nevada</option>
                                       <option value="NH">NewHampshire</option>
                                       <option value="NJ">NewJersey</option>
                                       <option value="NM">NewMexico</option>
                                       <option value="NY">NewYork</option>
                                       <option value="NC">NorthCarolina</option>
                                       <option value="ND">NorthDakota</option>
                                       <option value="OH">Ohio</option>
                                       <option value="OK">Oklahoma</option>
                                       <option value="OR">Oregon</option>
                                       <option value="PA">Pennsylvania</option>
                                       <option value="RI">RhodeIsland</option>
                                       <option value="SC">SouthCarolina</option>
                                       <option value="SD">SouthDakota</option>
                                       <option value="TN">Tennessee</option>
                                       <option value="TX">Texas</option>
                                       <option value="UT">Utah</option>
                                       <option value="VT">Vermont</option>
                                       <option value="VA">Virginia</option>
                                       <option value="WA">Washington</option>
                                       <option value="WV">WestVirginia</option>
                                       <option value="WI">Wisconsin</option>
                                       <option value="WY">Wyoming</option>
                                    </select>
                                 </div>
                              </div>
                              <div class="panel panel-default">
                                 <div class="panel-heading"><b>Second Line Series (Dotted line)</b> <label id="LineSelector1"><b>COLOR</b></label></div>
                                 <div class="panel-body">
                                    <label for="selWord2"  class="col-sm-2">Select Word:</label>
                                    <select class="form-control col-sm-4" id="selWord2" style="width:80%">
                                    </select>
                                    <label for="selState2" class="col-sm-2">Select State:</label>
                                    <select class="form-control col-sm-4" id="selState2" style="width:80%">
                                       <option value="OVERALL">OVERALL</option>
                                       <option value="AL">Alabama</option>
                                       <option value="AK">Alaska</option>
                                       <option value="AZ">Arizona</option>
                                       <option value="AR">Arkansas</option>
                                       <option value="CA">California</option>
                                       <option value="CO">Colorado</option>
                                       <option value="CT">Connecticut</option>
                                       <option value="DE">Delaware</option>
                                       <option value="FL">Florida</option>
                                       <option value="GA">Georgia</option>
                                       <option value="HI">Hawaii</option>
                                       <option value="ID">Idaho</option>
                                       <option value="IL">Illinois</option>
                                       <option value="IN">Indiana</option>
                                       <option value="IA">Iowa</option>
                                       <option value="KS">Kansas</option>
                                       <option value="KY">Kentucky</option>
                                       <option value="LA">Louisiana</option>
                                       <option value="ME">Maine</option>
                                       <option value="MD">Maryland</option>
                                       <option value="MA">Massachusetts</option>
                                       <option value="MI">Michigan</option>
                                       <option value="MN">Minnesota</option>
                                       <option value="MS">Mississippi</option>
                                       <option value="MO">Missouri</option>
                                       <option value="MT">Montana</option>
                                       <option value="NE">Nebraska</option>
                                       <option value="NV">Nevada</option>
                                       <option value="NH">NewHampshire</option>
                                       <option value="NJ">NewJersey</option>
                                       <option value="NM">NewMexico</option>
                                       <option value="NY">NewYork</option>
                                       <option value="NC">NorthCarolina</option>
                                       <option value="ND">NorthDakota</option>
                                       <option value="OH">Ohio</option>
                                       <option value="OK">Oklahoma</option>
                                       <option value="OR">Oregon</option>
                                       <option value="PA">Pennsylvania</option>
                                       <option value="RI">RhodeIsland</option>
                                       <option value="SC">SouthCarolina</option>
                                       <option value="SD">SouthDakota</option>
                                       <option value="TN">Tennessee</option>
                                       <option value="TX">Texas</option>
                                       <option value="UT">Utah</option>
                                       <option value="VT">Vermont</option>
                                       <option value="VA">Virginia</option>
                                       <option value="WA">Washington</option>
                                       <option value="WV">WestVirginia</option>
                                       <option value="WI">Wisconsin</option>
                                       <option value="WY">Wyoming</option>
                                    </select>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </body>
</html>