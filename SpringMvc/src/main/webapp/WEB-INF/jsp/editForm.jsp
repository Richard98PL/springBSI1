<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Edit Page</title>
   </head>
   <body>
      <div>
         <h1>Edit</h1>
         <form:form method="post" action="update">
            <table >
               <tr>
                  <td>Login : </td>
                  <td style = "background-color:gray;">
                     <form:input path="login" readOnly = "true"/>
                  </td>
               </tr>
               <tr>
                  <td>Password :</td>
                  <td>
                     <form:input path="password"/>
                  </td>
               </tr>
               <tr>
                   <tr>
                  <td>Use HASH (check) or HMAC (empty)?:</td>
                  <td>
                     <form:checkbox path="isPasswordKeptAsHash" />
                  </td>
               </tr>
               <tr>
                  <td> </td>
                  <td> <input type="submit" value="Save" /> </td>
               </tr>
            </table>
         </form:form>
      </div>
   </body>
</html>

