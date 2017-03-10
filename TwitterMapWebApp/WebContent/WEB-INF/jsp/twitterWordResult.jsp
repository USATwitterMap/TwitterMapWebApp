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
            <td>${keyword}</td>
         </tr>
         <tr>
            <td>Date</td>
            <td>${date}</td>
         </tr>
      </table>  
   </body>
   
</html>