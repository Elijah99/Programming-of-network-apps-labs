<%--
  Created by IntelliJ IDEA.
  User: xrono
  Date: 20.12.2019
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <script language="JavaScript">
    function fun1(){
      document.all.form1.Find.value=document.all.form1.find.value;
      document.all.form1.submit();
    }

  </script>
  <title>Finder</title>
</head>

<style>

  h1
  {
    font-family: 'Calibri';
  }

</style>


<form action="SimpleServlet" method="post" id="form1">

  <hr width = 65%>
  <table align = "center">
    <tr>
      <td>
        <b>Input temperature:  </b>
        <input id="find" type="text" name="Find">
      </td>
    </tr>
  </table>
  <hr width = 65%>
  <center><input type="button" value="View Result" onclick = "fun1();"></center>
</form>
</html>


