import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DantistDBCreate {
	static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	static String connect = "jdbc:derby:DantistDB;create=true";
	
	public static void main(String[] args) {

		// ������� ������� ��� Derby
		System.setProperty("derby.system.home", "C:\\Dantist" );
		
		try {
			// ������������ ������� JDBC
			Class.forName( driver );
			// ������������ � �� 
			Connection conn = DriverManager.getConnection(connect);
			// ��������� ������� 
			Statement st = conn.createStatement();
			st.executeUpdate("CREATE TABLE Service " +
				"(Name VARCHAR(32) PRIMARY KEY, Price REAL )");
			st.executeUpdate("CREATE TABLE Patients " + 
				"(FIO VARCHAR(32) PRIMARY KEY, Sex INT, " + 
				"BirthDate DATE )");
			st.executeUpdate("CREATE TABLE HealthCare " + 
				"(FIO VARCHAR(32) REFERENCES Patients (FIO), " +
				"Name VARCHAR(32) REFERENCES Service (Name), " +
				"Date DATE, isPaied BOOLEAN )");
			st.close();
			
		} catch (Exception e) {
			System.err.println("Run-time error: " + e );
		}

	}
}
