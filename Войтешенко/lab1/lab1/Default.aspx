<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="lab1._Default" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
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
</asp:Content>
