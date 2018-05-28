using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Lab4
{
    /// <summary>
    /// Summary description for TestService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class TestService : System.Web.Services.WebService
    {

        [WebMethod]
        public DataTable SelectRosters()
        {
            String requestToDB = "rostersList";

            string connectString = "Provider=Microsoft.Jet.OLEDB.4.0;"
                   + "Data Source=Y:\\Downloads\\CRITTERS.MDB";

            using (OleDbConnection cn = new OleDbConnection(connectString))
            {
                //requestToDB = "CREATE PROCEDURE rostersList AS SELECT * FROM Roster;";
                //requestToDB = "SELECT * FROM Roster;";
                
                OleDbCommand cmd = new OleDbCommand(requestToDB, cn)
                {
                    CommandType = System.Data.CommandType.StoredProcedure
                };
                cn.Open();
                cmd.ExecuteNonQuery();

                using (OleDbDataAdapter adapt = new OleDbDataAdapter())
                {
                    adapt.SelectCommand = cmd;
                    using (DataTable dt = new DataTable())
                    {
                        dt.TableName = "Rosters";
                        adapt.Fill(dt);
                        return dt;
                    }
                }
            }
        }

        [WebMethod]
        public string InsertRoster(string playerid1, int jersey1, string fname1, string sname1, 
                string position1, string birthday1, int weight1, int height1, string birthcity1, string birthstate1)
        {
            String requestToDB = "insertRosters";
            //requestToDB = "CREATE PROCEDURE insertRosters ("
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

            requestToDB = "INSERT INTO Roster (playerid,jersey,fname,sname,position,birthday,weight,height,birthcity,birthstate) VALUES (@playerid, @jersey, @fname, @sname, @position, @birthday, @weight, @height, @birthcity, @birthstate);";

            string connectString = "Provider=Microsoft.Jet.OLEDB.4.0;"
                + "Data Source=Y:\\Downloads\\CRITTERS.MDB";

            using (OleDbConnection cn = new OleDbConnection(connectString))
            {
                OleDbCommand cmd = new OleDbCommand(requestToDB, cn)
                {
                    //CommandType = System.Data.CommandType.StoredProcedure
                };
                //cn.Open();
                //cmd.ExecuteNonQuery();
                cmd.Parameters.AddWithValue("@playerid", playerid1);
                cmd.Parameters.AddWithValue("@jersey", jersey1);
                cmd.Parameters.AddWithValue("@fname", fname1);
                cmd.Parameters.AddWithValue("@sname", sname1);
                cmd.Parameters.AddWithValue("@position", position1);
                cmd.Parameters.AddWithValue("@birthday", birthday1);
                cmd.Parameters.AddWithValue("@weight", weight1);
                cmd.Parameters.AddWithValue("@height", height1);
                cmd.Parameters.AddWithValue("@birthcity", birthcity1);
                cmd.Parameters.AddWithValue("@birthstate", birthstate1);

                OleDbDataAdapter adapt = new OleDbDataAdapter();
                adapt.InsertCommand = cmd;
                adapt.InsertCommand.Connection.Open();
                adapt.InsertCommand.ExecuteNonQuery();

            }
            return null;
        }
    }
}
