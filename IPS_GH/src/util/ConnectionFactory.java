package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultListModel;

import db.Doctor;
import db.Patient;
import db.WorkDay;
import db.WorkPeriod;

public class ConnectionFactory {

	public static Connection getOracleConnection() throws Exception {

		String directory = (System.getProperty("user.dir") + "\\wallet").replace('\\', '/');
		String DB_URL = "jdbc:oracle:thin:@ips5jl6rhzy4qxy1y_medium?TNS_ADMIN=" + directory;
		String DB_USER = "admin";
		String DB_PASSWORD = "LyQmZ7HwG4edJ2";// Encontrar manera de esconder contraseña
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	}

	public static DefaultListModel<Doctor> getDoctors() throws Exception {
		DefaultListModel<Doctor> doctors = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL
			Statement statement = connection.createStatement();

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM DOCTOR";
			ResultSet resultSet = statement.executeQuery(sql);

			// Procesar los resultados
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String numcolegiado = resultSet.getString("numcolegiado");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				// Procesa otros campos según la estructura de tu tabla
				doctors.addElement(new Doctor(id, numcolegiado, name, surname, email));
			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doctors;
	}

	public static DefaultListModel<Patient> getPatients() throws Exception {
		DefaultListModel<Patient> patients = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL
			Statement statement = connection.createStatement();

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM PATIENT";
			ResultSet resultSet_patients = statement.executeQuery(sql);

			// Procesar los resultados
			while (resultSet_patients.next()) {
				int id = resultSet_patients.getInt("id");
				String contactinfo = resultSet_patients.getString("contactinfo");
				String name = resultSet_patients.getString("firstname");
				String surname = resultSet_patients.getString("surname");
				String dni = resultSet_patients.getString("dni");
				int ssnumber = resultSet_patients.getInt("ssnumber");

				patients.addElement(new Patient(id, name, surname, dni, contactinfo, ssnumber));

			}

			// Cerrar la conexión

			// Cerrar la conexión
			resultSet_patients.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return patients;
	}

	public static boolean isWorking(Date utilDate, String hourFrom, String hourTo, int idDoctor) throws Exception {
		DefaultListModel<WorkPeriod> workperiod = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();
			String sql_workperiod = "SELECT * FROM WORKPERIOD WHERE FK_DOCTORID = ?";

			// Crear una sentencia SQL
			PreparedStatement statement_workperiod = connection.prepareStatement(sql_workperiod);

			statement_workperiod.setInt(1, idDoctor);

			ResultSet resultSet_workperiod = statement_workperiod.executeQuery();

			// Procesar los resultados
			while (resultSet_workperiod.next()) {
				int id = resultSet_workperiod.getInt("id");
				Date startDate = resultSet_workperiod.getDate("startday");
				Date finalDate = resultSet_workperiod.getDate("finalday");
				int id_doctor = idDoctor;

				workperiod.addElement(new WorkPeriod(id, startDate, finalDate, id_doctor));

			}

			resultSet_workperiod.close();
			statement_workperiod.close();

			DefaultListModel<WorkDay> workday = new DefaultListModel<>();

			if (utilDate.after(workperiod.get(0).getStartDate()) && utilDate.before(workperiod.get(0).getEndDate())) {
				System.out.println("esta en el workperiod");
				String sql_workday = "SELECT * FROM WORKDAY WHERE WORKPERIODID = ?";

				// Crear una sentencia SQL
				PreparedStatement statement_workday = connection.prepareStatement(sql_workday);

				statement_workday.setInt(1, workperiod.get(0).getId());

				ResultSet resultSet_workday = statement_workday.executeQuery();

				// Procesar los resultados
				while (resultSet_workday.next()) {
					int id = resultSet_workday.getInt("id");
					String weekday = resultSet_workday.getString("weekday");
					String starthour = resultSet_workday.getString("starthour");
					System.out.println(starthour);
					String endhour = resultSet_workday.getString("endhour");
					int workperiodid = workperiod.get(0).getId();

					workday.addElement(new WorkDay(id, weekday, starthour, endhour, workperiodid));

				}
				// Cerrar la conexión
				resultSet_workday.close();
				statement_workday.close();
				
				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(utilDate);
				int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

				// Convertir el valor numérico a nombre del día
				String nombreDia = obtenerNombreDia(diaSemana);
				System.out.println(workday);
				for(int i = 0; i < workday.size(); i++) {
					System.out.println("dd  "+nombreDia.toLowerCase());
					System.out.println(workday.get(i).getWeekday().toLowerCase());
					if (workday.get(i).getWeekday().toLowerCase().equals(nombreDia.toLowerCase())
							&& sdf3.parse(utilDate + " " + hourFrom + ":00")
									.after(sdf3.parse(utilDate + " " + workday.get(i).getStartHour()))
							&& sdf3.parse(utilDate + " " + hourTo + ":00")
									.before(sdf3.parse(utilDate + " " + workday.get(i).getEndHour()))) {
						
						return true;
					}
				}
				
			}

			connection.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private static String obtenerNombreDia(int diaSemana) {
		switch (diaSemana) {
		case Calendar.SUNDAY:
			return "Sunday";
		case Calendar.MONDAY:
			return "Monday";
		case Calendar.TUESDAY:
			return "Tuesday";
		case Calendar.WEDNESDAY:
			return "Wednesday";
		case Calendar.THURSDAY:
			return "Thursday";
		case Calendar.FRIDAY:
			return "Friday";
		case Calendar.SATURDAY:
			return "Saturday";
		default:
			return "Día no válido";
		}
	}

}
