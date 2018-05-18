<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Delete.aspx.cs" Inherits="lab1.Delete" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
  <title>Insert</title>
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
    <h1 class="text-center">Input data for DELETE command:</h1>
   
    <form class="form-horizontal" id="deleteform" runat="server">
        <div class="form-group">
            <label class="control-label col-sm-2">Player ID: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="playerid" runat="server" />
            </div>
        </div>

        <button type="submit" class="btn btn-link btn-md btn-block">Delete</button>

        <asp:Table ID="DisplayTable" CssClass="table-striped" runat="server" Height="16px" Width="338px">
        </asp:Table>
    </form>
        </div>
</body>
</html>
