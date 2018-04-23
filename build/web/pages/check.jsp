<%@page contentType="text/html" import="p2.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>JSP Page</title>
 </head>
 <body>
 <%
 DBAccess con = new DBAccess();
 boolean res;
 res = con.accessTest();
 %>
 <h1><%=res%></h1>
 </body>
</html>