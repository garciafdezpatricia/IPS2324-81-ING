package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListModel;

import db.Appointment;
import db.Doctor;
import db.Office;
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

	public static void updateAppointment(AppointmentBLDto appointment) {
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		try {
			// TODO: falta por añadir las causas
			con = getOracleConnection();
			ps = con.prepareStatement("UPDATE APPOINTMENT SET "
					+ " appointment.attended = ? appointment.checkedin = ? appointment.checkedout = ? "
					+ "WHERE appointment.id = ?");
			ps.setInt(1, appointment.attended ? 1 : 0);
			ps.setString(2, appointment.checkIn);
			ps.setString(3, appointment.checkOut); 
			ps.setInt(4, appointment.id);
			rs = ps.executeQuery();
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			try {
				if(con!=null) con.close();
				if(ps!=null) ps.close();
				if(rs!=null) rs.close();
					} catch (SQLException e) {
						throw new RuntimeException();
					}
			}
	}

	public static List<AppointmentBLDto> getAppointmentsByDoctorId(int doctorId) {
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM APPOINTMENT JOIN OFFICE on officeid = office.id  WHERE doctorid = ?");
			ps.setInt(1, doctorId);
			rs = ps.executeQuery();
			List<AppointmentBLDto> appointments = new ArrayList<AppointmentBLDto>();
			AppointmentBLDto apmnt = null;
		
			while(rs.next()) {
				apmnt = new AppointmentBLDto();
				apmnt.id = rs.getInt(1);
				apmnt.patientid = rs.getInt(2);
				apmnt.doctorid = rs.getInt(3);
				apmnt.startDate = rs.getString(4);
				apmnt.endDate = rs.getString(5);
	
				apmnt.urgency = rs.getInt(6)==0?false:true;
				apmnt.attended = rs.getInt(7)==0?false:true;
				apmnt.checkIn = rs.getString(8);
				apmnt.checkOut = rs.getString(9);
				apmnt.officeid = rs.getInt(10);
				apmnt.information = rs.getString(11);
				apmnt.officeCode = rs.getString(13);
				
				appointments.add(apmnt);
				
				
			}
			return appointments;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			try {
				if(con!=null) con.close();
				if(ps!=null) ps.close();
				if(rs!=null) rs.close();
					} catch (SQLException e) {
						throw new RuntimeException();
					}
			}
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
				for (int i = 0; i < workday.size(); i++) {
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

	public static boolean hasAnAppointment(Doctor doctor, String start, String end) throws Exception {
		DefaultListModel<Appointment> apps = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			String sql = "SELECT * FROM APPOINTMENT WHERE DOCTORID = ?";
			// Crear una sentencia SQL
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, doctor.getId());

			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int patientId = resultSet.getInt("patientid");
				int doctorId = resultSet.getInt("doctorid");
				String startdate = resultSet.getString("startdate");
				String endate = resultSet.getString("enddate");
				int urgency = resultSet.getInt("urgency");
				int attended = resultSet.getInt("attended");
				String checkedin = resultSet.getString("checkedin");
				String checkedout = resultSet.getString("checkedout");
				int officeid = resultSet.getInt("officeid");
				String information = resultSet.getString("information");

				apps.addElement(new Appointment(id, patientId, doctorId, startdate, endate, urgency, attended,
						checkedin, checkedout, officeid, information));

			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

			for (int i = 0; i < apps.size(); i++) {
				// si la hora de empezar de la appointment ya hecha es despues de la de empezar
				// nueva y la hora de empezar de l anueva es antes de que acabe la hecha
				if ((sdf3.parse(start).after(sdf3.parse(apps.get(i).getStartdate()))
						&& sdf3.parse(start).before(sdf3.parse(apps.get(i).getEnddate())) || (
				// la hora de entrada de la nueva es antes del final de la reservada
				// y despues del comienzo de la reservada
				(sdf3.parse(start).compareTo(sdf3.parse(apps.get(i).getStartdate())) == 0)))) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void createAppointment(int patientID, int doctorID, String startDate, String endDate, int urgency,
			int officeId, String information) throws Exception {
		// Datos de conexión a la base de datos (ajusta estos valores según tu
		// configuración)

		// Datos para la inserción

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL con parámetros
			String insertQuery = "INSERT INTO Appointment (PatientID, DoctorID, StartDate, EndDate, Urgency, Attended, CheckedIn, CheckedOut, OfficeId, Information) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// Crear un PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

			// Establecer valores para los parámetros
			preparedStatement.setInt(1, patientID);
			preparedStatement.setInt(2, doctorID);
			preparedStatement.setString(3, startDate);
			preparedStatement.setString(4, endDate);
			preparedStatement.setInt(5, urgency);
			preparedStatement.setInt(6, 0);
			preparedStatement.setString(7, "");
			preparedStatement.setString(8, "");
			preparedStatement.setInt(8, 0);
			preparedStatement.setInt(9, officeId);
			preparedStatement.setString(10, information);

			// Ejecutar la inserción
			int filasAfectadas = preparedStatement.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Inserción exitosa.");
			} else {
				System.out.println("La inserción no se pudo realizar.");
			}

			// Cerrar la conexión y el PreparedStatement
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public static List<Office> getOffices() throws Exception {
		List<Office> offices = new ArrayList<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL
			Statement statement = connection.createStatement();

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM OFFICE";
			ResultSet resultSet_offices = statement.executeQuery(sql);

			// Procesar los resultados
			while (resultSet_offices.next()) {
				int id = resultSet_offices.getInt("id");
				String officeCode = resultSet_offices.getString("officeCode");

				offices.add(new Office(id, officeCode));
			}

			// Cerrar la conexión
			resultSet_offices.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return offices;
	}

	public static String[] getOfficesCodes() throws Exception {
		String[] aux = new String[getOffices().size()];
		for (int i = 0; i < getOffices().size(); i++) {
			aux[i] = String.valueOf(getOffices().get(i).getOfficeCode());
		}

		return aux;
	}
	
	public static int officeIdFrom(String code) throws Exception {
		for (int i = 0; i < getOffices().size(); i++) {
			if (getOffices().get(i).getOfficeCode().equals(code))
				return getOffices().get(i).getId();
		}
		return -1;
	}

}
