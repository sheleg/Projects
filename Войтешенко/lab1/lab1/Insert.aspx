<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Insert.aspx.cs" Inherits="lab1.Insert" %>

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
    <h1 class="text-center">Choose options for INSERT command:</h1>
   
    <form class="form-horizontal" id="insertform" runat="server">
        <div class="form-group">
            <label class="control-label col-sm-2">Player ID: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="playerid" runat="server" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Jersey: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="jersey" runat="server" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Fullname: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="fname" runat="server" />
            </div>
        </div>
        
       <div class="form-group">
            <label class="control-label col-sm-2">Surname: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="sname" runat="server" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Position: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="position" runat="server" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2">Birthday: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="birthday" runat="server" placeholder="YYYY-MM-DD hh:mm:ss" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2"> Weight: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="weight" runat="server" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2"> Height: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="height" runat="server" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2"> Birth city: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="birthcity" runat="server" />
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2"> Birth state: </label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="birthstate" runat="server" />
            </div>
        </div>

        <button type="submit" class="btn btn-link btn-md btn-block">Insert</button>
    </form>
        </div>
</body>
</html>

