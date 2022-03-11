<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Password Wallet</title>
   </head>
   <body>
       <a href="../walletOverwiev">Wallet overwiev</a><br />
      <div>
            <h1>Wallet entry</h1>
            <table border>
               <tr>
                  <th>Id</th>
                  <th>Web Address</th>
                  <th>Description</th>
                  <th>Login</th>
                  <th>Password</th>
               </tr>
               <c:forEach var="walletEntry" items="${list}">
                  <tr>
                     <td> ${walletEntry.id} </td>
                     <td> ${walletEntry.web_address} </td>
                     <td> ${walletEntry.description} </td>
                     <td> ${walletEntry.login} </td>
                     <td> ${walletEntry.password} </td>
                  </tr>
               </c:forEach>
            </table>
            <br/>
       </div>
   </body>
</html>

