<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
      <title>Spring MVC Form Handling</title>
   </head>

   <body>
      <h2>Twitter database results</h2>
      <form:form method = "POST" action = "searchWords">
         <table>
            <tr>
               <td><form:label path = "keyword">Keyword</form:label></td>
               <td><form:input path = "keyword" /></td>
            </tr>
            <tr>
               <td><form:label path = "date">Date</form:label></td>
               <td><form:input path = "date" /></td>
            </tr>
            <tr>
               <td colspan = "2">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
   
</html>