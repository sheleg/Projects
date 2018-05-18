using System;
using System.Collections.Generic;
using System.Data.OleDb;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace lab1
{
    public partial class Insert : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (IsPostBack)
            {
                System.Collections.Specialized.NameValueCollection postedValues = Request.Form;
                String requestToDB = "rostersInsert";

                string connectString = "Provider=Microsoft.Jet.OLEDB.4.0;"
                    + "Data Source=Y:\\Downloads\\CRITTERS.MDB";
                OleDbConnection cn = new OleDbConnection(connectString);
                cn.Open();

                //requestToDB = "CREATE PROCEDURE rostersInsert ("
                //    + "@playerid text, "
                //    + "@jersey int, "
                //    + "@fname text, "
                //    + "@sname text, "
                //    + "@position text, "
                //    + "@birthday text, "
                //    + "@weight int, "
                //    + "@height int, "
                //    + "@birthcity text, "
                //    + "@birthstate text) "
                //    + "AS INSERT INTO Roster VALUES (@playerid, @jersey, @fname, @sname, @position, @birthday, @weight, @height, @birthcity, @birthstate);";

                OleDbCommand cmd = new OleDbCommand(requestToDB, cn)
                {
                    CommandType = System.Data.CommandType.StoredProcedure
                };
                cmd.Parameters.AddWithValue("@playerid", postedValues["playerid"]);
                cmd.Parameters.AddWithValue("@jersey", postedValues["jersey"]);
                cmd.Parameters.AddWithValue("@fname", postedValues["fname"]);
                cmd.Parameters.AddWithValue("@sname", postedValues["sname"]);
                cmd.Parameters.AddWithValue("@position", postedValues["position"]);
                cmd.Parameters.AddWithValue("@birthday", postedValues["birthday"]);
                cmd.Parameters.AddWithValue("@weight", postedValues["weight"]);
                cmd.Parameters.AddWithValue("@height", postedValues["height"]);
                cmd.Parameters.AddWithValue("@birthcity", postedValues["birthcity"]);
                cmd.Parameters.AddWithValue("@birthstate", postedValues["birthstate"]);


                OleDbDataReader reader = cmd.ExecuteReader();
                reader.Close();
                cn.Close();
            }
        }
    }
}