<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Wallet Overwiev</title>
   </head>
   <body>
      <div>
         <h3>Password Wallet</h3>
         <p>
             <a href="addWalletEntryForm">Add Wallet Entry</a><br />
         </p>
      </div>
       
       <div>
            <h1>Wallet list</h1>
            <table border>
               <tr>
                  <th>Id</th>
                  <th>Web Address</th>
                  <th>Description</th>
               </tr>
               <c:forEach var="walletEntry" items="${list}">
                  <tr>
                     <td> ${walletEntry.id} </td>
                     <td> ${walletEntry.web_address} </td>
                     <td> ${walletEntry.description} </td>
                     <td><a href="showWalletCredentials/${walletEntry.id}"> Pokaż </a></td>
                  </tr>
               </c:forEach>
            </table>
            <br/>
       </div>
   </body>
</html>

