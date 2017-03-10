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
   </head>
   <body>
   
      <h2>Twitter database results</h2>
      <form:form method = "POST" action = "searchWords">
      
         <table class="box">
           	<tr class="inner_element">
               	<td><form:label path = "keyword">Keyword:</form:label><form:input path = "keyword" /></td>
            </tr>
            <tr class="inner_element">
               	<td><form:input class="slider-width100" type="range" path="date" min="0" max="100" /></td>
            </tr>
            <tr class="inner_element">
               	<td><input type = "submit" value = "Search"/></td>
            </tr>
         </table>          
      </form:form>
   </body>
   
</html>