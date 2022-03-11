<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Wallet Entry Page</title>
   </head>
   <body>
      <div>
         <h1>Add Wallet Entry</h1>
         <form:form method="post" action="addWalletEntry">
            <table >
               <tr>
                  <td>Web Address : </td>
                  <td>
                     <form:input path="web_address" />
                  </td>
               </tr>
               <tr>
                  <td>Login :</td>
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
                  <td>Description :</td>
                  <td>
                     <form:input path="description" />
                  </td>
               </tr>
               <tr>
                  <td> </td>
                  <td> <input type="submit" value="Add Wallet Entry" /> </td>
               </tr>
            </table>
         </form:form>
      </div>
   </body>
</html>

