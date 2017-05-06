import java.sql.*;

class ServiceItem {
	String name;
	Float  price;
	
	ServiceItem( String name, float price ) {
		this.name = name;
		this.price = price;
	}
}

class PatientsItem {
	String fio;
	Integer sex;
	Date birthDate;
	
	PatientsItem( String fio, int sex, Date birthDate ) {
		this.fio = fio;
		this.sex = sex;
		this.birthDate = birthDate;		
	}
}

class HealthCareItem {
	String fio;
	String name;
	Date date;
	Boolean isPaied;
	HealthCareItem ( String fio, String name, Date date, boolean isPaied ) {
		this.fio = fio;
		this.name = name;
		this.date = date;
		this.isPaied = isPaied;		
	}
}

public class DantistDBFill {

	static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	static String connect = "jdbc:derby:DantistDB;create=true";
	
	static String serviceIns = "INSERT INTO Service(Name, Price) VALUES(?,?)";
	static String patientsIns = "INSERT INTO Patients(FIO, Sex, BirthDate) VALUES(?,?,?)";
	static String healthCareIns = "INSERT INTO HealthCare(FIO, Name, Date, isPaied) VALUES(?,?,?,?)";
	
	static ServiceItem [] arrService = {
            new ServiceItem( "Обследование", 9.99f ),
            new ServiceItem( "Удаление зуба", 99.99f ),
            new ServiceItem( "Лечение кариеса", 49.99f ),
            new ServiceItem( "Лечение кариеса, осложнённое", 89.99f ),
            new ServiceItem( "Лечение канала зуба", 89.99f ),
            new ServiceItem( "Отбеливание зубов", 199.99f ),
            new ServiceItem( "Рентгендиагностика", 3.99f )	
	};

        static PatientsItem [] arrPatients = {
            new PatientsItem( "Прудник Пётр Иванович", 1, new Date( 80, 1, 1 )),
            new PatientsItem( "Свирид Дмитрий Алексеевич", 1, new Date( 77, 4, 6 )),
            new PatientsItem( "Русакова Екатерина Сергеевна", 0, new Date( 91, 2, 7 )),
            new PatientsItem( "Касаржевская Алла Марковна", 0, new Date( 83, 9, 11 )), 
            new PatientsItem( "Гореликов Тимофей Дмитриевич", 1, new Date( 90, 0, 10 )), 
            new PatientsItem( "Серединский Михаил Алексеевич", 1, new Date( 79, 5, 21 )),
            new PatientsItem( "Бруцкий Владислав Витальевич", 1, new Date( 88, 7, 31 )),
            new PatientsItem( "Максимов Сергей Леонидович", 1, new Date( 89, 1, 27 ))
        };

        static HealthCareItem [] arrHealthCare = {	
            new HealthCareItem( "Касаржевская Алла Марковна", "Обследование", new Date( 16, 10, 1 ), true ),
            new HealthCareItem( "Бруцкий Владислав Витальевич", "Удаление зуба", new Date( 16, 10, 1 ), true ),
            new HealthCareItem( "Свирид Дмитрий Алексеевич", "Обследование", new Date( 16, 10, 2 ), true ),
            new HealthCareItem( "Бруцкий Владислав Витальевич", "Лечение кариеса", new Date( 16, 10, 3 ), true ),
            new HealthCareItem( "Прудник Пётр Иванович", "Удаление зуба", new Date( 16, 10, 3 ), true ),
            new HealthCareItem( "Касаржевская Алла Марковна", "Лечение кариеса", new Date( 16, 10, 11 ), true ),
            new HealthCareItem( "Бруцкий Владислав Витальевич", "Рентгендиагностика", new Date( 16, 10, 12 ), true ),
            new HealthCareItem( "Бруцкий Владислав Витальевич", "Лечение кариеса, осложнённое", new Date( 16, 10, 13 ), true ),
            new HealthCareItem( "Касаржевская Алла Марковна", "Лечение кариеса", new Date( 16, 10, 13 ), true ),
            new HealthCareItem( "Гореликов Тимофей Дмитриевич", "Обследование", new Date( 16, 10, 21 ), true ),
            new HealthCareItem( "Русакова Екатерина Сергеевна", "Лечение кариеса", new Date( 16, 10, 22 ), false ), 
            new HealthCareItem( "Гореликов Тимофей Дмитриевич", "Отбеливание зубов", new Date( 16, 10, 23 ), true ),
            new HealthCareItem( "Максимов Сергей Леонидович", "Лечение кариеса", new Date( 16, 10, 23 ), false ),
            new HealthCareItem( "Касаржевская Алла Марковна", "Лечение канала зуба", new Date( 16, 10, 24 ), false ),
            new HealthCareItem( "Прудник Пётр Иванович", "Лечение кариеса, осложнённое", new Date( 16, 10, 29 ), true )
        };

	public static void main(String[] args) {

		// Текущий каталог для Derby
		System.setProperty("derby.system.home", "C:\\Dantist" );
		
		try {
			// Регистрируем драйвер JDBC
			Class.forName( driver );
			// Подключаемся к БД 
			Connection conn = DriverManager.getConnection(connect);
			// Выполняем запросы 
			
                        PreparedStatement st = conn.prepareStatement(serviceIns);
			for ( ServiceItem si : arrService ) {
                            try {
                                st.setString( 1, si.name );
                                st.setFloat( 2, si.price );
                                st.executeUpdate();
                            } catch (SQLException e) {
			        System.err.println("Insert error: " + e );
		            }
                        }			
			st.close();

                        st = conn.prepareStatement(patientsIns);
			for ( PatientsItem pi : arrPatients ) {
                            try {
                                st.setString( 1, pi.fio );
                                st.setInt( 2, pi.sex );
                                st.setDate( 3, pi.birthDate );
                                st.executeUpdate();
                            } catch (SQLException e) {
			        System.err.println("Insert error: " + e );
		            }
                        }			
			st.close();

                        st = conn.prepareStatement(healthCareIns);
			for ( HealthCareItem hc : arrHealthCare ) {
                            try {
                                st.setString( 1, hc.fio );
                                st.setString( 2, hc.name );
                                st.setDate( 3, hc.date );
                                st.setBoolean( 4, hc.isPaied );
                                st.executeUpdate();
                            } catch (SQLException e) {
			        System.err.println("Insert error: " + e );
		            }
                        }			
			st.close();

			conn.close();

		} catch (Exception e) {
			System.err.println("Run-time error: " + e );
		}
	}
}
