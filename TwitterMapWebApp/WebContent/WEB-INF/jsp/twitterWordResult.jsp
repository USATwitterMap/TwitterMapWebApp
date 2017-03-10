<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Spring MVC Form Handling</title>
   </head>

   <body>
      <h2>Query twitter database results</h2>
      <table>
         <tr>
            <td>Keyword</td>
            <td>${results.keyword}</td>
         </tr>
         <tr>
            <td>State</td>
            <td>${results.state}</td>
         </tr>
         <tr>
            <td>Occurances</td>
            <td>${results.occurances}</td>
         </tr>
      </table>  
   </body>
   
</html>