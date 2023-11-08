package util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
				BigDecimal id = resultSet.getBigDecimal("id");
				BigInteger aux = id.toBigInteger();

				String numcolegiado = resultSet.getString("numcolegiado");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				String personal_id = resultSet.getString("personal_id");
				String specialization = resultSet.getString("specialization");
				// Procesa otros campos según la estructura de tu tabla
				doctors.addElement(new Doctor(aux, numcolegiado, name, surname, email, personal_id, specialization));
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// TODO: falta por añadir las causas
			con = getOracleConnection();
			ps = con.prepareStatement(
					"UPDATE APPOINTMENT SET " + "attended = ?, checkedin = ?, checkedout = ? " + "WHERE id = ?");
			ps.setInt(1, appointment.attended ? 1 : 0);
			ps.setString(2, appointment.checkIn);
			ps.setString(3, appointment.checkOut);
			ps.setInt(4, appointment.id);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
	}

	public static List<AppointmentBLDto> getAppointmentsByDoctorId(int doctorId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM (APPOINTMENT JOIN PATIENT on appointment.patientid = patient.id) "
					+ "JOIN OFFICE on officeid = office.id  WHERE doctorid = ?");
			ps.setInt(1, doctorId);
			rs = ps.executeQuery();
			List<AppointmentBLDto> appointments = new ArrayList<AppointmentBLDto>();
			AppointmentBLDto apmnt = null;

			while (rs.next()) {
				apmnt = new AppointmentBLDto();
				apmnt.id = rs.getInt(1);
				apmnt.patientid = rs.getInt(2);
				apmnt.doctorid = rs.getInt(3);
				apmnt.startDate = rs.getString(4);
				apmnt.endDate = rs.getString(5);

				apmnt.urgency = rs.getInt(6) == 0 ? false : true;
				apmnt.attended = rs.getInt(7) == 0 ? false : true;
				apmnt.checkIn = rs.getString(8);
				apmnt.checkOut = rs.getString(9);
				apmnt.officeid = rs.getInt(10);
				apmnt.information = rs.getString(11);
				apmnt.patientName = rs.getString(15);
				apmnt.patientSurname = rs.getString(16);
				apmnt.officeCode = rs.getString(19);

				appointments.add(apmnt);

			}
			return appointments;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
	}

	public static boolean isWorking(Date utilDate, String hourFrom, String hourTo, BigInteger idDoctor)
			throws Exception {
		DefaultListModel<WorkPeriod> workperiod = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();
			String sql_workperiod = "SELECT * FROM WORKPERIOD WHERE FK_DOCTORID = ?";

			// Crear una sentencia SQL
			PreparedStatement statement_workperiod = connection.prepareStatement(sql_workperiod);

			BigDecimal doc = new BigDecimal(idDoctor);
			statement_workperiod.setBigDecimal(1, doc);

			ResultSet resultSet_workperiod = statement_workperiod.executeQuery();

			// Procesar los resultados
			while (resultSet_workperiod.next()) {
				BigDecimal id = resultSet_workperiod.getBigDecimal("id");
				BigInteger aux = id.toBigInteger();

				Date startDate = resultSet_workperiod.getDate("startday");
				Date finalDate = resultSet_workperiod.getDate("finalday");
				BigInteger id_doctor = idDoctor;

				workperiod.addElement(new WorkPeriod(aux, startDate, finalDate, id_doctor));

			}

			resultSet_workperiod.close();
			statement_workperiod.close();

			DefaultListModel<WorkDay> workday = new DefaultListModel<>();

//			if (utilDate == null || workperiod.get(0).getStartDate()==null || workperiod.get(0).getEndDate()==null) {
//				System.out.println();
//			}
			try {
				if (utilDate.after(workperiod.get(0).getStartDate())
						&& utilDate.before(workperiod.get(0).getEndDate())) {
					System.out.println("esta en el workperiod");
					String sql_workday = "SELECT * FROM WORKDAY WHERE WORKPERIODID = ?";

					// Crear una sentencia SQL
					PreparedStatement statement_workday = connection.prepareStatement(sql_workday);

					BigDecimal a = new BigDecimal(workperiod.get(0).getId());
					statement_workday.setBigDecimal(1, a);

					ResultSet resultSet_workday = statement_workday.executeQuery();

					// Procesar los resultados
					while (resultSet_workday.next()) {
						BigDecimal id = resultSet_workday.getBigDecimal("id");
						BigInteger aux = id.toBigInteger();

						String weekday = resultSet_workday.getString("weekday");
						String starthour = resultSet_workday.getString("starthour");
						System.out.println(starthour);
						String endhour = resultSet_workday.getString("endhour");
						BigInteger workperiodid = workperiod.get(0).getId();

						workday.addElement(new WorkDay(aux, weekday, starthour, endhour, workperiodid));

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
										.after(sdf3.parse(utilDate + " " + workday.get(i).getStartHour() + ":00"))
								&& sdf3.parse(utilDate + " " + hourTo + ":00")
										.before(sdf3.parse(utilDate + " " + workday.get(i).getEndHour() + ":00"))) {

							return true;
						}
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

			BigDecimal doc = new BigDecimal(doctor.getId());

			statement.setBigDecimal(1, doc);

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

	public static void createAppointment(int patientID, BigInteger doctorID, String startDate, String endDate,
			int urgency, int officeId, String information) throws Exception {
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

			BigDecimal doc = new BigDecimal(doctorID);

			// Establecer valores para los parámetros
			preparedStatement.setInt(1, patientID);
			preparedStatement.setBigDecimal(2, doc);
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

	public static void createWorkPeriod(BigInteger id, Date startDate, Date endDate, BigInteger id_doctor)
			throws Exception {
		// Datos de conexión a la base de datos (ajusta estos valores según tu
		// configuración)

		// Datos para la inserción

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL con parámetros
			String insertQuery = "INSERT INTO WorkPeriod (startday, finalday, fk_doctorid) " + "VALUES (?, ?, ?)";
			// Crear un PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

			// Establecer valores para los parámetros
			BigDecimal aux2 = new BigDecimal(id_doctor);

			preparedStatement.setDate(1, startDate);
			preparedStatement.setDate(2, endDate);
			preparedStatement.setBigDecimal(3, aux2);

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

	public static void createWorkDay(BigInteger id, String weekday, String startHour, String endHour,
			BigInteger workperiod_id, BigInteger doctorid) {
		// Datos de conexión a la base de datos (ajusta estos valores según tu
		// configuración)

		// Datos para la inserción

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL con parámetros
			String insertQuery = "INSERT INTO WorkDay (weekday, startHour, endHour, workperiodid) "
					+ "VALUES (?, ?, ?, ?)";
			String query2 = "SELECT id FROM workperiod WHERE fk_doctorid = ?";

			PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
			BigDecimal b = new BigDecimal(doctorid);
			preparedStatement2.setBigDecimal(1, b);

			ResultSet resultSet = preparedStatement2.executeQuery();
			BigDecimal wpid = null;

			while (resultSet.next()) {
				wpid = resultSet.getBigDecimal("id");

			}

			// Crear un PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

			// Establecer valores para los parámetros

			preparedStatement.setString(1, weekday);
			preparedStatement.setString(2, startHour);
			preparedStatement.setString(3, endHour);
			preparedStatement.setBigDecimal(4, wpid);

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DefaultListModel<Doctor> doctorFromName(String name) throws Exception {
		DefaultListModel<Doctor> docs = new DefaultListModel<Doctor>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL para buscar el doctor
			String sql = "SELECT * FROM Doctor WHERE UPPER(name) = UPPER(?)";

			// Crear una sentencia preparada
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);

			// Ejecutar la consulta
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Extraer datos del resultado
				BigInteger id = resultSet.getBigDecimal("id").toBigInteger();
				String numColegiado = resultSet.getString("numColegiado");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				String personal_id = resultSet.getString("personal_id");
				String specialization = resultSet.getString("specialization");

				// Crear un objeto Doctor
				Doctor doctor = new Doctor(id, numColegiado, name, surname, email, personal_id, specialization);

				docs.addElement(doctor);

				// Ahora puedes trabajar con el objeto Doctor
				System.out.println("Doctor found: " + doctor.getName());
			} else {
//				System.out.println("Doctor not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return docs;
	}

	public static DefaultListModel<Doctor> doctorFromSurname(String surname) throws Exception {
		DefaultListModel<Doctor> docs = new DefaultListModel<Doctor>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL para buscar el doctor
			String sql = "SELECT * FROM Doctor WHERE UPPER(surname) = UPPER(?)";

			// Crear una sentencia preparada
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, surname);

			// Ejecutar la consulta
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Extraer datos del resultado
				BigInteger id = resultSet.getBigDecimal("id").toBigInteger();
				String numColegiado = resultSet.getString("numColegiado");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String personal_id = resultSet.getString("personal_id");
				String specialization = resultSet.getString("specialization");

				// Crear un objeto Doctor
				Doctor doctor = new Doctor(id, numColegiado, name, surname, email, personal_id, specialization);

				docs.addElement(doctor);

				// Ahora puedes trabajar con el objeto Doctor
				System.out.println("Doctor found: " + doctor.getName());
			} else {
//				System.out.println("Doctor not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return docs;
	}

	public static Doctor doctorFromNameAndSurname(String ns) throws Exception {
		for (int i = 0; i < getDoctors().size(); i++) {
			String a = getDoctors().get(i).getName().toLowerCase() + " "
					+ getDoctors().get(i).getSurname().toLowerCase();
			if (a.toLowerCase().equals(ns.toLowerCase())) {
				System.out.println(a);
				return getDoctors().get(i);
			}
		}
		return null;
	}

	public static DefaultListModel<Doctor> doctorFromPersonalID(String personalID) throws Exception {
		DefaultListModel<Doctor> docs = new DefaultListModel<Doctor>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL para buscar el doctor
			String sql = "SELECT * FROM Doctor WHERE UPPER(personal_id) = UPPER(?)";

			// Crear una sentencia preparada
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, personalID);

			// Ejecutar la consulta
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Extraer datos del resultado
				BigInteger id = resultSet.getBigDecimal("id").toBigInteger();
				String numColegiado = resultSet.getString("numColegiado");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				String specialization = resultSet.getString("specialization");

				// Crear un objeto Doctor
				Doctor doctor = new Doctor(id, numColegiado, name, surname, email, personalID, specialization);

				docs.addElement(doctor);

				// Ahora puedes trabajar con el objeto Doctor
				System.out.println("Doctor found: " + doctor.getName());
			} else {
//				System.out.println("Doctor not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return docs;
	}

	public static DefaultListModel<Doctor> doctorFromMedicalLicenseID(String medLicID) throws Exception {
		DefaultListModel<Doctor> docs = new DefaultListModel<Doctor>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL para buscar el doctor
			String sql = "SELECT * FROM Doctor WHERE UPPER(personal_id) = UPPER(?)";

			// Crear una sentencia preparada
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, medLicID);

			// Ejecutar la consulta
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Extraer datos del resultado
				BigInteger id = resultSet.getBigDecimal("id").toBigInteger();
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				String personal_id = resultSet.getString("personal_id");
				String specialization = resultSet.getString("specialization");

				// Crear un objeto Doctor
				Doctor doctor = new Doctor(id, medLicID, name, surname, email, personal_id, specialization);

				docs.addElement(doctor);

				// Ahora puedes trabajar con el objeto Doctor
				System.out.println("Doctor found: " + doctor.getName());
			} else {
//				System.out.println("Doctor not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return docs;
	}

	public static boolean doctorHasWorkPeriod(BigInteger doctorId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT 1 FROM WORKPERIOD WHERE fk_doctorid = ?");

			BigDecimal a = new BigDecimal(doctorId);
			ps.setBigDecimal(1, a);

			rs = ps.executeQuery();

			System.out.println("the doctor has workperiod? " + rs.next());
			return rs.next(); // Retorna true si hay al menos un registro, de lo contrario, retorna false
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
	}

	public static List<WorkPeriod> getWorkPeriodByDoctorId(BigInteger doctorId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<WorkPeriod> workperiods = new ArrayList<WorkPeriod>();

		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM WORKPERIOD WHERE fk_doctorid = ?");

			BigDecimal a = new BigDecimal(doctorId);

			System.out.println(a);
			ps.setBigDecimal(1, a);

			rs = ps.executeQuery();

			WorkPeriod wp = null;

			while (rs.next()) {
				// BigInteger id, Date startDate, Date endDate, BigInteger id_doctor
				BigDecimal aux = rs.getBigDecimal(1);

				BigInteger id = aux.toBigInteger();
				Date startDate = rs.getDate(2);
				Date endDate = rs.getDate(3);

				System.out.println("id=" + id + " startdate=" + startDate + " enddate=" + endDate);

				wp = new WorkPeriod(id, startDate, endDate, doctorId);

				workperiods.add(wp);

			}
			return workperiods;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
	}

	public static List<WorkDay> getWorkDayByWPId(BigInteger wpID) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<WorkDay> workdays = new ArrayList<WorkDay>();

		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM WORKDAY WHERE workperiodid = ?");
			BigDecimal a = new BigDecimal(wpID);
			ps.setBigDecimal(1, a);
			rs = ps.executeQuery();
			WorkDay wd = null;

			while (rs.next()) {
				// BigInteger id, String weekday, String startHour, String endHour, BigInteger
				// workperiodid
				BigDecimal aux = rs.getBigDecimal(1);

				BigInteger id = aux.toBigInteger();
				String weekday = rs.getString(2);
				String startHour = rs.getString(3);
				String endHour = rs.getString(4);

				wd = new WorkDay(id, weekday, startHour, endHour, wpID);

				workdays.add(wd);

			}
			return workdays;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
	}

	public static void updateWorkPeriod(BigInteger wpID, Date startDate, Date endDate, BigInteger id_doctor) {
		// Datos de conexión a la base de datos (ajusta estos valores según tu
		// configuración)

		Connection con = null;
		PreparedStatement ps = null;

		/*
		 * 
		 * 
		 * 
		 * 
		 * ps = con.prepareStatement( "UPDATE APPOINTMENT SET " +
		 * "attended = ?, checkedin = ?, checkedout = ? " + "WHERE id = ?");
		 * ps.setInt(1, appointment.attended ? 1 : 0); ps.setString(2,
		 * appointment.checkIn); ps.setString(3, appointment.checkOut); ps.setInt(4,
		 * appointment.id); ps.executeUpdate();
		 */
		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL con parámetros
			String insertQuery = "UPDATE workperiod SET startday = ?, finalday = ?, fk_doctorid = ? WHERE id = ?;";
			// Crear un PreparedStatement
			ps = connection.prepareStatement(insertQuery);
			ps.setDate(1, startDate);
			ps.setDate(2, endDate);
			BigDecimal a = new BigDecimal(id_doctor);
			ps.setBigDecimal(3, a);
			BigDecimal b = new BigDecimal(wpID);
			ps.setBigDecimal(4, b);

			ps.executeUpdate();

			// Ejecutar la inserción
			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Inserción exitosa.");
			} else {
				System.out.println("La inserción no se pudo realizar.");
			}

			// Cerrar la conexión y el PreparedStatement
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public static List<Appointment> getAppointmentsFromOffice(int officeId) {
		List<Appointment> apps = new ArrayList<Appointment>();
		// Datos de conexión a la base de datos (ajusta estos valores según tu
		// configuración)

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM APPOINTMENT WHERE officeId = ?");

			ps.setInt(1, officeId);

			ResultSet resultSet = ps.executeQuery();

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
				String information = resultSet.getString("information");

				apps.add(new Appointment(id, patientId, doctorId, startdate, endate, urgency, attended, checkedin,
						checkedout, officeId, information));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}

		return apps;
	}

	public static int getOfficeIDFromCode(String code) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int id = -100;

		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM OFFICE WHERE officecode = ?");

			System.out.println(code);
			ps.setString(1, code);

			rs = ps.executeQuery();

			WorkPeriod wp = null;

			while (rs.next()) {
				id = rs.getInt("id");

				System.out.println("id=" + id);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				throw new RuntimeException();
			}
		}
		return id;
	}

	public static String getFreeHours(int officeId, String start, String end) throws Exception {
		String res = "";
		String res2 = "";
		List<Appointment> apps = new ArrayList<Appointment>();
		Connection connection = ConnectionFactory.getOracleConnection();
		// hay que comprobar que la fecha que se pasa como parámetro esté dentro de ese
		// workperiod
		String query = "SELECT * from appointment where officeid = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setInt(1, officeId);

		ResultSet resultSet = preparedStatement.executeQuery();

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
			String information = resultSet.getString("information");

			apps.add(new Appointment(id, patientId, doctorId, startdate, endate, urgency, attended, checkedin,
					checkedout, officeId, information));
		}

		if (apps.size() == 0) {
			return "The office has no appointments for today.";
		}
		// si si que tiene alguna cita reservada para ese día
		else {

			// filtrarlos por las que sean en el día, hay que pasar el string a date
			List<Appointment> appsThatDay = new ArrayList<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Appointment a : apps) {
				java.util.Date st = dateFormat.parse(a.getStartdate());
				java.util.Date e = dateFormat.parse(a.getEnddate());

				java.util.Date stAux = dateFormat.parse(start + " 00:00:00");
				java.util.Date eAux = dateFormat.parse(end + " 23:59:00");

				if (st.after(stAux) && e.before(eAux)) {
					appsThatDay.add(a);
				}
			}

			System.out.println("appointments" + appsThatDay.toString());
			// Define un comparador para ordenar por fecha
			Comparator<Appointment> comparadorFecha = Comparator.comparing(Appointment::getStartdate);

			// Ordena la lista usando el comparador
			Collections.sort(appsThatDay, comparadorFecha);
			// si hay citas ese dia
			if (!appsThatDay.isEmpty()) {
				res = "\n The office is booked from:\n ";
				Date today = new Date(System.currentTimeMillis());
				for (int i = 0; i < appsThatDay.size(); i++) {
					java.util.Date st = dateFormat.parse(appsThatDay.get(i).getStartdate());

					Calendar cal1 = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					cal1.setTime(today);
					cal2.setTime(st);

					if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
							&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
							&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {

						
								res += "\t" + dateFormat.parse(appsThatDay.get(i).getStartdate()).getHours() + ":"
								+ dateFormat.parse(appsThatDay.get(i).getStartdate()).getMinutes() + " to "
								+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getHours() + ":"
								+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getMinutes() + "\n";

						res2 += dateFormat.parse(appsThatDay.get(i).getStartdate()).getHours() + ":"
								+ dateFormat.parse(appsThatDay.get(i).getStartdate()).getMinutes();
						String aux = res;
						res2 = "\n" + aux;
					}
				}
			} else {
				res2 += "14:00";
				res2 = "\n" + res;
			}

		}
		System.out.println(res2);
		return res2;

	}
}
