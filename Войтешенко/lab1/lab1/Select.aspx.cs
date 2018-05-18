using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.OleDb;
using System.Web.ModelBinding;
using System.Data.SqlClient;

namespace lab1
{
    public partial class Select : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (IsPostBack)
            {
                System.Collections.Specialized.NameValueCollection postedValues = Request.Form;
                String requestToDB = "rostersList";

                string connectString = "Provider=Microsoft.Jet.OLEDB.4.0;" 
                    + "Data Source=Y:\\Downloads\\CRITTERS.MDB";
                OleDbConnection cn = new OleDbConnection(connectString);
                cn.Open();

                //requestToDB = "CREATE PROCEDURE rostersList AS SELECT * FROM Roster;";

                OleDbCommand cmd = new OleDbCommand(requestToDB, cn)
                {
                    CommandType = System.Data.CommandType.StoredProcedure
                };
                OleDbDataReader reader = cmd.ExecuteReader();


                DisplayTable.Width = Unit.Percentage(90.00);
                //Create a new row for adding a table heading.
                TableRow tableHeading = new TableRow();

                //Create and add the cells that contain the Customer ID column heading text.
                TableHeaderCell customerIDHeading = new TableHeaderCell
                {
                    Text = "Player ID",
                    HorizontalAlign = HorizontalAlign.Left
                };
                tableHeading.Cells.Add(customerIDHeading);

                //Create and add the cells that contain the Contact Name column heading text.
                TableHeaderCell contactNameHeading = new TableHeaderCell
                {
                    Text = "Full Name",
                    HorizontalAlign = HorizontalAlign.Left
                };
                tableHeading.Cells.Add(contactNameHeading);

                //Create and add the cells that contain the Phone column heading text.
                TableHeaderCell phoneHeading = new TableHeaderCell
                {
                    Text = "Surname",
                    HorizontalAlign = HorizontalAlign.Left
                };
                tableHeading.Cells.Add(phoneHeading);

                DisplayTable.Rows.Add(tableHeading);


                while (reader.Read())
                {
                    TableRow detailsRow = new TableRow();
                    TableCell customerIDCell = new TableCell
                    {
                        Text = reader["playerid"].ToString()
                    };
                    detailsRow.Cells.Add(customerIDCell);

                    TableCell contactNameCell = new TableCell
                    {
                        Text = reader["fname"].ToString()
                    };
                    detailsRow.Cells.Add(contactNameCell);

                    TableCell phoneCell = new TableCell
                    {
                        Text = reader["sname"].ToString()
                    };
                    detailsRow.Cells.Add(phoneCell);

                    DisplayTable.Rows.Add(detailsRow);
                }
                reader.Close();
                cn.Close();
            }
        }
    }
}