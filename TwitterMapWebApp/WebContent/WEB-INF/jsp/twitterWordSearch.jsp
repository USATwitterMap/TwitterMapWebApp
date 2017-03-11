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
	   <script>
		   function updateTextInput(val, startTime, endTime) {
			   		var d = new Date((endTime - startTime) * val / 1000 + startTime);
			   		document.getElementById('textInput').innerHTML=d; 
		        }
	   </script>  
   </head>
   <body>
   
      <form:form method = "POST" action = "searchWords">
      
         <table class="box">
           	<tr class="inner_element">
               	<td colspan="3"><form:label path = "keyword">${results.occurances} Keyword: </form:label><form:input path = "keyword" /></td>
            </tr>
            <tr class="inner_element">
            	<td colspan="3"> <img src="USA.svg" height="75%" width="75%"/></td>
			</tr>
            <tr class="inner_element">
            	<td><form:label path = "earliestDate">${command.earliestDate}</form:label></td>
               	<td><form:input class="slider-width100" type="range" path="date" min="0" max="1000" oninput="updateTextInput(this.value, ${command.earliestDate.time}, ${command.latestDate.time});" /></td>
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
   </body>
   
</html>