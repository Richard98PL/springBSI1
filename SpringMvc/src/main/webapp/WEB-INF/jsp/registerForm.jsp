<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Register Page</title>
   </head>
   <body>
      <div>
         <h1>Register</h1>
         <form:form method="post" action="save">
            <table >
               <tr>
                  <td>Login : </td>
                  <td>
                     <form:input path="login" />
                  </td>
               </tr>
               <tr>
                  <td>Password :</td>
                  <td>
                     <form:input path="password" />
                  </td>
               </tr>
               <tr>
                   <tr>
                  <td>HASH or HMAC :</td>
                  <td>
                     <form:checkbox path="isPasswordKeptAsHash" />
                  </td>
               </tr>
               <tr>
                  <td> </td>
                  <td> <input type="submit" value="Register" /> </td>
               </tr>
            </table>
         </form:form>
      </div>
   </body>
</html>

