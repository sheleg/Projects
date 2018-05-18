using System;
using System.Collections.Generic;
using System.Data.OleDb;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace lab1
{
    public partial class Delete : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (IsPostBack)
            {
                System.Collections.Specialized.NameValueCollection postedValues = Request.Form;
                //string requestToDB = "rostersDeleteByPlayerID";
                string requestToDB = "DELETE FROM Roster WHERE playerid=\"" + postedValues["playerid"] + "\";";

                string connectString = "Provider=Microsoft.Jet.OLEDB.4.0;"
                    + "Data Source=Y:\\Downloads\\CRITTERS.MDB";
                OleDbConnection cn = new OleDbConnection(connectString);
                cn.Open();

                //requestToDB = "CREATE PROCEDURE rostersDeleteByPlayerID ("
                //    + "@playerid text) "
                //    + "AS DELETE FROM Roster WHERE playerid=@playerid;";


                OleDbCommand cmd = new OleDbCommand(requestToDB, cn)
                {
                    //CommandType = System.Data.CommandType.StoredProcedure
                };
                //cmd.Parameters.AddWithValue("@playerid", postedValues["playerid"]);

                OleDbDataReader reader = cmd.ExecuteReader();

                reader.Close();
                cn.Close();
            }
        }
    }
}