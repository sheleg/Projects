import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DantistDB {
	
	static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	static String connect = "jdbc:derby:DantistDB";
	
	public static void main(String[] args) {

		// Текущий каталог для Derby
		System.setProperty("derby.system.home", "C:\\Dantist");
		
		try {
			// Регистрируем драйвер JDBC
			Class.forName( driver );
			// Подключаемся к БД 
			Connection conn = DriverManager.getConnection(connect);
			// Выполняем запрос 
			Statement st = conn.createStatement(); 
			ResultSet rec = st.executeQuery("SELECT * FROM Patients ORDER BY FIO");
			// Просматриваем и печатаем записи результирующей таблицы			
			while (rec.next()) {
				String fio = rec.getString("FIO");
				int sex = rec.getInt("Sex");
				Date birthDate = rec.getDate("BirthDate");
				System.out.println( " FIO: " + fio + " Sex: " + sex + " BirthDate: " + birthDate );
			}
			rec.close();
			st.close();			
		} catch (Exception e) {
			System.err.println("Run-time error: " + e );
		}

	}

}
