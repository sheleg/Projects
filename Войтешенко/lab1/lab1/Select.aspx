<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Select.aspx.cs" Inherits="lab1.Select" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
  <title>Select</title>
  <meta charset="utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
     <nav class="navbar navbar-default">
  <div class="container-fluid">
    <ul class="nav navbar-nav">
      <li class="active"><a>Choose action:</a></li>
      <li><a href="Select">Select</a></li>
      <li><a href="Insert">Insert</a></li>      
      <li><a href="Update">Update</a></li>
      <li><a href="Delete">Delete</a></li>
    </ul>
  </div>
</nav>
    <div class="container">
    <h1 class="text-center">SELECT command:</h1>
    <form class="form-horizontal" id="selectform" runat="server">
       

        <button type="submit" class="btn btn-link btn-md btn-block">Select</button>

        <asp:Table ID="DisplayTable" CssClass="table-striped" runat="server" Height="16px" Width="338px">
        </asp:Table>
    </form>
        </div>
</body>
</html>
