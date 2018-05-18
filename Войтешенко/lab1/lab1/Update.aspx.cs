using System;
using System.Collections.Generic;
using System.Data.OleDb;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace lab1
{
    public partial class Update : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

            if (IsPostBack)
            {
                System.Collections.Specialized.NameValueCollection postedValues = Request.Form;
                //string requestToDB = "rostersUpdatePlayerID";
                string requestToDB = "UPDATE Roster SET playerid=\"" + postedValues["newplayerid"]
                    + "\" WHERE playerid=\"" + postedValues["playerid"] + "\";";

                string connectString = "Provider=Microsoft.Jet.OLEDB.4.0;"
                    + "Data Source=Y:\\Downloads\\CRITTERS.MDB;" 
                    + "User Id=admin;Password=;";
                OleDbConnection cn = new OleDbConnection(connectString);
                cn.Open();

                //requestToDB = "CREATE PROCEDURE rostersUpdatePlayerID ("
                //    + "@playerid text, "
                //      + "@newplayerid text)"
                //    + "AS UPDATE Roster SET playerid=@newplayerid WHERE playerid=@playerid;";


                OleDbCommand cmd = new OleDbCommand(requestToDB, cn)
                {
                    //CommandType = System.Data.CommandType.StoredProcedure
                };
                //cmd.Parameters.AddWithValue("@playerid", postedValues["playerid"]);
                //cmd.Parameters.AddWithValue("@newplayerid", postedValues["newplayerid"]);


                //OleDbDataReader reader = cmd.ExecuteReader();
                cmd.ExecuteNonQuery();

                //reader.Close();
                cn.Close();
            }
        }
    }
}