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
import db.Diagnosis;
import db.Doctor;
import db.ICDChapter;
import db.ICDSubchapter;
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

	public static Diagnosis getDiagnosis(String code) {
		Diagnosis diagnosis = new Diagnosis();
		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();
			// Crear una sentencia SQL
			Statement statement = connection.createStatement();
			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM ICD10_DIAGNOSIS WHERE CODE LIKE '" + code + "'";
			ResultSet resultSet = statement.executeQuery(sql);
			// Procesar los resultados
			while (resultSet.next()) {
				String theCode = resultSet.getString("code");
				String description = resultSet.getString("name");
				String longDesc = resultSet.getString("description");
				// Procesa otros campos según la estructura de tu tabla
				diagnosis.code = theCode;
				diagnosis.description = description;
				diagnosis.longDescription = longDesc;
			}
			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diagnosis;
	}

	public static List<Diagnosis> getDiagnosis(String from, String to) {
		List<Diagnosis> diagnosis = new ArrayList<Diagnosis>();
		int numDigits = from.length();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();
			// Crear una sentencia SQL
			Statement statement = connection.createStatement();
			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM ICD10_DIAGNOSIS WHERE CODE between '" + from.toUpperCase() + "' AND '"
					+ to.toUpperCase() + "' AND LENGTH(CODE)=" + numDigits + " ORDER BY CODE ASC";
			ResultSet resultSet = statement.executeQuery(sql);
			// Procesar los resultados
			while (resultSet.next()) {
				String code = resultSet.getString("code");
				String description = resultSet.getString("name");
				String longDesc = resultSet.getString("description");
				// Procesa otros campos según la estructura de tu tabla
				diagnosis.add(new Diagnosis(code, description, longDesc));
			}
			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diagnosis;
	}

	public static List<Diagnosis> getDiagnosis(String from, int numDigits) {
		List<Diagnosis> diagnosis = new ArrayList<Diagnosis>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();
			// Crear una sentencia SQL
			Statement statement = connection.createStatement();
			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM ICD10_DIAGNOSIS WHERE CODE LIKE '" + from.toUpperCase() + "%' AND LENGTH(CODE)="
					+ numDigits + " ORDER BY CODE ASC";
			ResultSet resultSet = statement.executeQuery(sql);
			// Procesar los resultados
			while (resultSet.next()) {
				String code = resultSet.getString("code");
				String description = resultSet.getString("name");
				String longDesc = resultSet.getString("description");
				// Procesa otros campos según la estructura de tu tabla
				diagnosis.add(new Diagnosis(code, description, longDesc));
			}
			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diagnosis;
	}

	public static List<ICDChapter> getChapters() {
		List<ICDChapter> chapters = new ArrayList<ICDChapter>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();
			// Crear una sentencia SQL
			Statement statement = connection.createStatement();
			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM ICD_CHAPTERS ORDER BY CHAPTER ASC";
			ResultSet resultSet = statement.executeQuery(sql);
			// Procesar los resultados
			while (resultSet.next()) {
				String chapter = resultSet.getString("chapter");
				String description = resultSet.getString("description");
				String from = resultSet.getString("desde");
				String to = resultSet.getString("hasta");
				// Procesa otros campos según la estructura de tu tabla
				chapters.add(new ICDChapter(chapter, description, from, to));
			}
			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chapters;
	}

	public static List<ICDSubchapter> getSubchapters(String from, String to) {
		List<ICDSubchapter> subchapters = new ArrayList<ICDSubchapter>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();
			// Crear una sentencia SQL
			Statement statement = connection.createStatement();
			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM ICD_SUBCHAPTERS WHERE DESDE BETWEEN '" + from + "' AND '" + to + "'";
			ResultSet resultSet = statement.executeQuery(sql);
			// Procesar los resultados
			while (resultSet.next()) {
				String sections = resultSet.getString("subchapter");
				String description = resultSet.getString("description");
				String from1 = resultSet.getString("desde");
				String to1 = resultSet.getString("hasta");
				// Procesa otros campos según la estructura de tu tabla
				subchapters.add(new ICDSubchapter(sections, description, from1, to1));
			}
			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subchapters;
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
				BigInteger id = resultSet_patients.getBigDecimal("id").toBigInteger();
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
	
	public static List<Patient> getListOfPatients() throws Exception {
		List<Patient> patients = new ArrayList<>();

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
				BigInteger id = resultSet_patients.getBigDecimal("id").toBigInteger();
				String contactinfo = resultSet_patients.getString("contactinfo");
				String name = resultSet_patients.getString("firstname");
				String surname = resultSet_patients.getString("surname");
				String dni = resultSet_patients.getString("dni");
				int ssnumber = resultSet_patients.getInt("ssnumber");

				patients.add(new Patient(id, name, surname, dni, contactinfo, ssnumber));

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
			ps.setInt(1, appointment.attended);
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
				apmnt.id = rs.getInt("id");
				apmnt.patientid = rs.getInt("patientid");
				apmnt.doctorid = rs.getInt("doctorid");
				apmnt.startDate = rs.getString("startdate");
				apmnt.endDate = rs.getString("enddate");

				apmnt.urgency = rs.getInt("urgency") == 0 ? false : true;
				apmnt.attended = rs.getInt("attended");
				apmnt.checkIn = rs.getString("checkedin");
				apmnt.checkOut = rs.getString("checkedout");
				apmnt.officeid = rs.getInt("officeid");
				apmnt.information = rs.getString("information");
				apmnt.patientName = rs.getString("firstname");
				apmnt.patientSurname = rs.getString("surname");
				apmnt.officeCode = rs.getString("officecode");
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
	
	public static List<AppointmentBLDto> getAppointmentsList() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM (APPOINTMENT JOIN PATIENT on appointment.patientid = patient.id)");

			rs = ps.executeQuery();
			List<AppointmentBLDto> appointments = new ArrayList<AppointmentBLDto>();
			AppointmentBLDto apmnt = null;

			while (rs.next()) {
				apmnt = new AppointmentBLDto();
				apmnt.id = rs.getInt("id");
				apmnt.patientid = rs.getInt("patientid");
				apmnt.doctorid = rs.getInt("doctorid");
				apmnt.startDate = rs.getString("startdate");
				apmnt.endDate = rs.getString("enddate");

				apmnt.urgency = rs.getInt("urgency") == 0 ? false : true;
				apmnt.attended = rs.getInt("attended");
				apmnt.checkIn = rs.getString("checkedin");
				apmnt.checkOut = rs.getString("checkedout");
				apmnt.officeid = rs.getInt("officeid");
				apmnt.information = rs.getString("information");
				apmnt.patientName = rs.getString("firstname");
				apmnt.patientSurname = rs.getString("surname");

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
//						System.out.println(starthour);
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
				System.out.println("no trabaja");
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
				BigInteger id = resultSet.getBigDecimal("id").toBigInteger();
				BigInteger patientId = resultSet.getBigDecimal("patientid").toBigInteger();
				BigInteger doctorId = resultSet.getBigDecimal("doctorid").toBigInteger();
				String startdate = resultSet.getString("startdate");
				String endate = resultSet.getString("enddate");
				int urgency = resultSet.getInt("urgency");
				int attended = resultSet.getInt("attended");
				String checkedin = resultSet.getString("checkedin");
				String checkedout = resultSet.getString("checkedout");
				BigInteger officeid = resultSet.getBigDecimal("officeid").toBigInteger();
				String information = resultSet.getString("information");
				String status = resultSet.getString("status");

				apps.addElement(new Appointment(id, patientId, doctorId, startdate, endate, urgency, attended,
						checkedin, checkedout, officeid, information, status));

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

	public static void createAppointment(BigInteger patientID, BigInteger doctorID, String startDate, String endDate,
			int urgency, int officeId, String information) throws Exception {
		// Datos de conexión a la base de datos (ajusta estos valores según tu
		// configuración)

		// Datos para la inserción

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL con parámetros
			String insertQuery = "INSERT INTO Appointment (PatientID, DoctorID, StartDate, EndDate, Urgency, Attended, CheckedIn, CheckedOut, OfficeId, Information, Status) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// Crear un PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

			BigDecimal doc = new BigDecimal(doctorID);

			// Establecer valores para los parámetros
			preparedStatement.setBigDecimal(1, new BigDecimal(patientID));
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
			preparedStatement.setString(11, "Booked");

			// Ejecutar la inserción
			int filasAfectadas = preparedStatement.executeUpdate();

//			if (filasAfectadas > 0) {
//				System.out.println("Inserción exitosa.");
//			} else {
//				System.out.println("La inserción no se pudo realizar.");
//			}

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

	public static DefaultListModel<Office> getOfficesDefaultList() {
		DefaultListModel<Office> offices = new DefaultListModel<>();

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

				offices.addElement(new Office(id, officeCode));
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

//			// Establecer valores para los parámetros
			BigDecimal aux2 = new BigDecimal(id_doctor);

			preparedStatement.setDate(1, startDate);
			preparedStatement.setDate(2, endDate);
			preparedStatement.setBigDecimal(3, aux2);

			// Ejecutar la inserción
			int filasAfectadas = preparedStatement.executeUpdate();

//			if (filasAfectadas > 0) {
//				System.out.println("Inserción exitosa.");
//			} else {
//				System.out.println("La inserción no se pudo realizar.");
//			}

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

			BigDecimal aux2 = new BigDecimal(workperiod_id);

			preparedStatement.setString(1, weekday);
			preparedStatement.setString(2, startHour);
			preparedStatement.setString(3, endHour);
			preparedStatement.setBigDecimal(4, wpid);

			// Ejecutar la inserción
			int filasAfectadas = preparedStatement.executeUpdate();

//			if (filasAfectadas > 0) {
//				System.out.println("Inserción exitosa.");
//			} else {
//				System.out.println("La inserción no se pudo realizar.");
//			}

			// Cerrar la conexión y el PreparedStatement
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static String getFreeHours(List<Doctor> doctors, Date day) throws Exception {
		String res = "";
		String res2 = "";
		Connection connection = ConnectionFactory.getOracleConnection();
		if (doctors.size() == 1) {
			String freeHours = "\nThe doctor is free from ";

			// hay que comprobar que la fecha que se pasa como parámetro esté dentro de ese
			// workperiod
			String query = "SELECT id from workperiod where fk_doctorid = ? and ? >= startday and ? <= finalday ";
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			BigDecimal b = new BigDecimal(doctors.get(0).getId());
			preparedStatement.setBigDecimal(1, b);
			preparedStatement.setDate(2, day);
			preparedStatement.setDate(3, day);

			ResultSet resultSet = preparedStatement.executeQuery();
			BigDecimal wpid = null;

			while (resultSet.next()) {
				wpid = resultSet.getBigDecimal("id");
			}

			if (wpid == null) {
//				System.out.println("No hay free hours porque ese dia no esta dentro de su workperiod");
			}
			// si si que está dentro del workperiod
			else {
//				System.out.println("El dia si esta dentro de su workperiod");
				String query2 = "SELECT * from workday where workperiodid = ? and weekday = ?";

				PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(day);
				int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

				preparedStatement2.setBigDecimal(1, wpid);
				preparedStatement2.setString(2, obtenerNombreDia(diaSemana));

				ResultSet resultSet2 = preparedStatement2.executeQuery();

				// me tiene que devolver un workday del dia de la semana adecuado
				// TODO: NO SE ESTÁ CONTEMPLANDO QUE HAYA SIDO MODIFICADO EL DÍA
				WorkDay workday = null;
				BigDecimal id = null;
				String weekday = null;
				String startHour = null;
				String endHour = null;
				while (resultSet2.next()) {
					id = resultSet2.getBigDecimal("id");
					weekday = resultSet2.getString("weekday");
					startHour = resultSet2.getString("starthour");
					endHour = resultSet2.getString("endhour");

				}
				workday = new WorkDay(id.toBigInteger(), weekday, startHour, endHour, wpid.toBigInteger());

				if (workday == null) {
//					System.out.println("workday null");
				} else {
					// ahora falta filtrar en el caso de que tenga citas

					String s = "The doctor works from " + startHour + " to " + endHour;
					res = s;
					freeHours += startHour + " to ";
					// APPOINTMENT?
					String query3 = "SELECT * FROM APPOINTMENT WHERE DOCTORID = ?";

					PreparedStatement preparedStatement3 = connection.prepareStatement(query3);

					preparedStatement3.setBigDecimal(1, new BigDecimal(doctors.get(0).getId()));

					ResultSet resultSet3 = preparedStatement3.executeQuery();

					List<Appointment> apps = new ArrayList<>();
					while (resultSet3.next()) {
						apps.add(new Appointment(resultSet3.getBigDecimal("id").toBigInteger(),
								resultSet3.getBigDecimal("patientid").toBigInteger(),
								resultSet3.getBigDecimal("doctorid").toBigInteger(), resultSet3.getString("startdate"),
								resultSet3.getString("enddate"), resultSet3.getInt("urgency"),
								resultSet3.getInt("attended"), resultSet3.getString("checkedin"),
								resultSet3.getString("checkedout"), resultSet3.getBigDecimal("officeid").toBigInteger(),
								resultSet3.getString("information"), resultSet3.getString("status")));

					}

					// filtrarlos por las que sean en el día, hay que pasar el string a date
					List<Appointment> appsThatDay = new ArrayList<>();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					for (Appointment a : apps) {
						if (dateFormat.parse(a.getStartdate()).after(dateFormat.parse(day + " 00:00:00"))
								&& dateFormat.parse(a.getEnddate()).before(dateFormat.parse(day + " 24:00:00"))
								&& !a.getStatus().equals("Cancelled")) {

							appsThatDay.add(a);
						}
					}

					List<String> listOfHours = new ArrayList<>();

					// Define un comparador para ordenar por fecha
					Comparator<Appointment> comparadorFecha = Comparator.comparing(Appointment::getStartdate);

					// Ordena la lista usando el comparador
					Collections.sort(appsThatDay, comparadorFecha);
					// si hay citas ese dia
					if (!appsThatDay.isEmpty()) {
						for (int i = 0; i < appsThatDay.size(); i++) {
							res += "\n The doctor has appointment from:\n\t "
									+ dateFormat.parse(appsThatDay.get(i).getStartdate()).getHours() + ":"
									+ dateFormat.parse(appsThatDay.get(i).getStartdate()).getMinutes() + " to\n\t "
									+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getHours() + ":"
									+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getMinutes() + "\n";

							freeHours += dateFormat.parse(appsThatDay.get(i).getStartdate()).getHours() + ":"
									+ dateFormat.parse(appsThatDay.get(i).getStartdate()).getMinutes();
							if (appsThatDay.size() > 1 && i < appsThatDay.size() - 1) {
								freeHours += " \n\tand from "
										+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getHours() + ":"
										+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getMinutes() + " to "
//										+ dateFormat.parse(appsThatDay.get(i + 1).getStartdate()).getHours() + ":"
//										+ dateFormat.parse(appsThatDay.get(i + 1).getStartdate()).getMinutes() + " "
								;
							} else {
								freeHours += " \n\tand from "
										+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getHours() + ":"
										+ dateFormat.parse(appsThatDay.get(i).getEnddate()).getMinutes() + " to "
										+ endHour;
//								res += freeHours;
								String aux = res;
								res2 = freeHours + "\n" + aux;
							}
						}
					} else {
						freeHours += endHour;
						String aux = res;
						res2 = freeHours + "\n" + aux;
//						res += freeHours;
//						System.out.println("no tiene más citas");
					}

				}

			}
		}
		if (doctors.size() > 1) {
			String freeHours = "\nBoth doctors are free from ";

			// hay que comprobar que la fecha que se pasa como parámetro esté dentro de ese
			// workperiod
			String query = "SELECT id from workperiod where fk_doctorid = ? and ? >= startday and ? <= finalday ";
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			// ids the workperiods
			List<BigDecimal> ids = new ArrayList<>();
			for (int i = 0; i < doctors.size(); i++) {
				BigDecimal b = new BigDecimal(doctors.get(i).getId());
				preparedStatement.setBigDecimal(1, b);
				preparedStatement.setDate(2, day);
				preparedStatement.setDate(3, day);

				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					ids.add(resultSet.getBigDecimal("id"));
				}

				if (ids.size() < doctors.size()) {
//					System.out.println("No hay free hours porque ese dia uno de los médicos no trabaja (workperiod)");
				}
				// si si que está dentro del workperiod
				else {
//					System.out.println("El dia si esta dentro del workperiod de todos los doctores");
					String query2 = "SELECT * from workday where workperiodid = ? and weekday = ?";

					PreparedStatement preparedStatement2 = connection.prepareStatement(query2);

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(day);
					int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

					List<WorkDay> workdays = new ArrayList<>();

					for (int j = 0; j < ids.size(); j++) {
						preparedStatement2.setBigDecimal(1, ids.get(j));
						preparedStatement2.setString(2, obtenerNombreDia(diaSemana));

						ResultSet resultSet2 = preparedStatement2.executeQuery();

						// me tiene que devolver un workday del dia de la semana adecuado
						// TODO: NO SE ESTÁ CONTEMPLANDO QUE HAYA SIDO MODIFICADO EL DÍA
						while (resultSet2.next()) {
							workdays.add(new WorkDay(resultSet2.getBigDecimal("id").toBigInteger(),
									resultSet2.getString("weekday"), resultSet2.getString("starthour"),
									resultSet2.getString("endhour"), ids.get(j).toBigInteger()));

						}

					}
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

					if (workdays.size() != doctors.size()) {
//						System.out.println("la lista de workdays no coincide con la de doctores");
					} else {
						// ahora falta filtrar en el caso de que tenga citas

						String horaMasTardeDeEntrada = workdays.get(0).getStartHour();
						String horaMasProntoDeSalida = workdays.get(0).getEndHour();

						for (int j = 0; j < workdays.size(); j++) {
							if (dateFormat.parse(workdays.get(j).getStartHour())
									.after(dateFormat.parse(horaMasTardeDeEntrada))) {
								horaMasTardeDeEntrada = workdays.get(j).getStartHour();
							}
							if (dateFormat.parse(workdays.get(j).getEndHour())
									.before(dateFormat.parse(horaMasProntoDeSalida))) {
								horaMasProntoDeSalida = workdays.get(j).getEndHour();
							}

						}
						String s = "The doctors work from " + horaMasTardeDeEntrada + " to " + horaMasProntoDeSalida;
						res = s;
						freeHours += horaMasTardeDeEntrada + " to ";

						// APPOINTMENT?
						String query3 = "SELECT * FROM APPOINTMENT WHERE DOCTORID = ?";

						PreparedStatement preparedStatement3 = connection.prepareStatement(query3);
						List<Appointment> apps = new ArrayList<>();

						for (int j = 0; j < doctors.size(); j++) {
							preparedStatement3.setBigDecimal(1, new BigDecimal(doctors.get(j).getId()));
							ResultSet resultSet3 = preparedStatement3.executeQuery();
							while (resultSet3.next()) {
								apps.add(new Appointment(resultSet3.getBigDecimal("id").toBigInteger(),
										resultSet3.getBigDecimal("patientid").toBigInteger(), ids.get(j).toBigInteger(),
										resultSet3.getString("startdate"), resultSet3.getString("enddate"),
										resultSet3.getInt("urgency"), resultSet3.getInt("attended"),
										resultSet3.getString("checkedin"), resultSet3.getString("checkedout"),
										resultSet3.getBigDecimal("officeid").toBigInteger(),
										resultSet3.getString("information"), resultSet3.getString("status")));
							}
						}

						// filtrarlos por las que sean en el día, hay que pasar el string a date
						List<Appointment> appsThatDay = new ArrayList<>();
						SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						for (Appointment a : apps) {
//							System.out.println("a.getStartdate" + a.getStartdate());
//							System.out.println("day" + dateFormat2.parse(day + " 00:00:00"));
//							System.out.println("cita " + a);
//							System.out.println("a.getend" + a.getEnddate());
							if (dateFormat2.parse(a.getStartdate()).after(dateFormat2.parse(day + " 00:00:00"))
									&& dateFormat2.parse(a.getEnddate()).before(dateFormat2.parse(day + " 24:00:00"))
									&& !a.getStatus().toLowerCase().equals("cancelled")) {
//								System.out.println(a);
								appsThatDay.add(a);
							}
						}

						// Define un comparador para ordenar por fecha
						Comparator<Appointment> comparadorFecha = Comparator.comparing(Appointment::getStartdate);

						// Ordena la lista usando el comparador
						Collections.sort(appsThatDay, comparadorFecha);
						// si hay citas ese dia
						if (!appsThatDay.isEmpty()) {
							for (int j = 0; j < appsThatDay.size(); j++) {
								res += "\n A doctor has appointment from:\n\t "
										+ dateFormat2.parse(appsThatDay.get(j).getStartdate()).getHours() + ":"
										+ dateFormat2.parse(appsThatDay.get(j).getStartdate()).getMinutes() + " to\n\t "
										+ dateFormat2.parse(appsThatDay.get(j).getEnddate()).getHours() + ":"
										+ dateFormat2.parse(appsThatDay.get(j).getEnddate()).getMinutes() + "\n";

								freeHours += dateFormat2.parse(appsThatDay.get(j).getStartdate()).getHours() + ":"
										+ dateFormat2.parse(appsThatDay.get(j).getStartdate()).getMinutes();
								if (appsThatDay.size() > 1 && j < appsThatDay.size() - 1) {
									freeHours += " \n\tand from "
											+ dateFormat2.parse(appsThatDay.get(j).getEnddate()).getHours() + ":"
											+ dateFormat2.parse(appsThatDay.get(j).getEnddate()).getMinutes() + " to "
//											+ dateFormat.parse(appsThatDay.get(i + 1).getStartdate()).getHours() + ":"
//											+ dateFormat.parse(appsThatDay.get(i + 1).getStartdate()).getMinutes() + " "
									;
								} else {
									freeHours += " \n\tand from "
											+ dateFormat2.parse(appsThatDay.get(j).getEnddate()).getHours() + ":"
											+ dateFormat2.parse(appsThatDay.get(j).getEnddate()).getMinutes() + " to "
											+ horaMasProntoDeSalida;
//									res += freeHours;
									String aux = res;
									res2 = freeHours + "\n" + aux;
								}
							}
						} else {
							freeHours += horaMasProntoDeSalida;
							String aux = res;
							res2 = freeHours + "\n" + aux;
//							res += freeHours;
//							System.out.println("no tiene más citas");
						}

					}

				}

			}

		}

		// todo: por si escogen mas de un medico
		return res2;

	}

	public static DefaultListModel<Appointment> getAppointments() throws Exception {
		DefaultListModel<Appointment> appointments = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL
			Statement statement = connection.createStatement();

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM APPOINTMENT";
			ResultSet resultSet = statement.executeQuery(sql);

			// Procesar los resultados
			while (resultSet.next()) {
				BigDecimal id = resultSet.getBigDecimal("id");
				BigDecimal patientid = resultSet.getBigDecimal("patientid");
				BigDecimal doctorid = resultSet.getBigDecimal("doctorid");

				String startDate = resultSet.getString("startdate");
				String enddate = resultSet.getString("enddate");

				int urgency = resultSet.getInt("urgency");
				int attended = resultSet.getInt("attended");

				String checkedin = resultSet.getString("checkedin");
				String checkedout = resultSet.getString("checkedout");
				BigDecimal officeid = resultSet.getBigDecimal("officeid");
				String information = resultSet.getString("information");
				String status = resultSet.getString("status");

				// Procesa otros campos según la estructura de tu tabla
				appointments.addElement(new Appointment(id.toBigInteger(), patientid.toBigInteger(),
						doctorid.toBigInteger(), startDate, enddate, urgency, attended, checkedin, checkedout,
						officeid.toBigInteger(), information, status));
			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appointments;

	}

	public static DefaultListModel<Appointment> getAppointmentsByOffice(int pid) {
		DefaultListModel<Appointment> appointments = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM APPOINTMENT WHERE officeid = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setBigDecimal(1, new BigDecimal(pid));
			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {
				BigDecimal id = resultSet.getBigDecimal("id");
				BigDecimal patientid = resultSet.getBigDecimal("patientid");
				BigDecimal doctorid = resultSet.getBigDecimal("doctorid");

				String startDate = resultSet.getString("startdate");
				String enddate = resultSet.getString("enddate");

				int urgency = resultSet.getInt("urgency");
				int attended = resultSet.getInt("attended");

				String checkedin = resultSet.getString("checkedin");
				String checkedout = resultSet.getString("checkedout");
				BigDecimal officeid = resultSet.getBigDecimal("officeid");
				String information = resultSet.getString("information");
				String status = resultSet.getString("status");

				// Procesa otros campos según la estructura de tu tabla
				appointments.addElement(new Appointment(id.toBigInteger(), patientid.toBigInteger(),
						doctorid.toBigInteger(), startDate, enddate, urgency, attended, checkedin, checkedout,
						officeid.toBigInteger(), information, status));
			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appointments;
	}

	public static DefaultListModel<Appointment> getAppointmentsByPatientID(BigInteger pid) throws Exception {
		DefaultListModel<Appointment> appointments = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM APPOINTMENT WHERE patientid = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setBigDecimal(1, new BigDecimal(pid));
			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {
				BigDecimal id = resultSet.getBigDecimal("id");
				BigDecimal patientid = resultSet.getBigDecimal("patientid");
				BigDecimal doctorid = resultSet.getBigDecimal("doctorid");

				String startDate = resultSet.getString("startdate");
				String enddate = resultSet.getString("enddate");

				int urgency = resultSet.getInt("urgency");
				int attended = resultSet.getInt("attended");

				String checkedin = resultSet.getString("checkedin");
				String checkedout = resultSet.getString("checkedout");
				BigDecimal officeid = resultSet.getBigDecimal("officeid");
				String information = resultSet.getString("information");
				String status = resultSet.getString("status");

				// Procesa otros campos según la estructura de tu tabla
				appointments.addElement(new Appointment(id.toBigInteger(), patientid.toBigInteger(),
						doctorid.toBigInteger(), startDate, enddate, urgency, attended, checkedin, checkedout,
						officeid.toBigInteger(), information, status));
			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appointments;

	}

	public static DefaultListModel<Appointment> getAppointmentsByDoctorsId(List<BigInteger> ids) {
		DefaultListModel<Appointment> appointments = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM APPOINTMENT WHERE doctorid = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			for(int i = 0; i< ids.size();i++) {
				statement.setBigDecimal(1, new BigDecimal(ids.get(i)));
				ResultSet resultSet = statement.executeQuery();

				// Procesar los resultados
				while (resultSet.next()) {
					BigDecimal id = resultSet.getBigDecimal("id");
					BigDecimal patientid = resultSet.getBigDecimal("patientid");
					BigDecimal doctorid = resultSet.getBigDecimal("doctorid");

					String startDate = resultSet.getString("startdate");
					String enddate = resultSet.getString("enddate");

					int urgency = resultSet.getInt("urgency");
					int attended = resultSet.getInt("attended");

					String checkedin = resultSet.getString("checkedin");
					String checkedout = resultSet.getString("checkedout");
					BigDecimal officeid = resultSet.getBigDecimal("officeid");
					String information = resultSet.getString("information");
					String status = resultSet.getString("status");

					// Procesa otros campos según la estructura de tu tabla
					appointments.addElement(new Appointment(id.toBigInteger(), patientid.toBigInteger(),
							doctorid.toBigInteger(), startDate, enddate, urgency, attended, checkedin, checkedout,
							officeid.toBigInteger(), information, status));
				}
				resultSet.close();

			}
			

			// Cerrar la conexión
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appointments;
	}
	
	public static DefaultListModel<Appointment> getAppointmentsByDoctorId(BigInteger did) {
		DefaultListModel<Appointment> appointments = new DefaultListModel<>();

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Crear una sentencia SQL

			// Ejecutar una consulta SQL
			String sql = "SELECT * FROM APPOINTMENT WHERE doctorid = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setBigDecimal(1, new BigDecimal(did));
			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {
				BigDecimal id = resultSet.getBigDecimal("id");
				BigDecimal patientid = resultSet.getBigDecimal("patientid");
				BigDecimal doctorid = resultSet.getBigDecimal("doctorid");

				String startDate = resultSet.getString("startdate");
				String enddate = resultSet.getString("enddate");

				int urgency = resultSet.getInt("urgency");
				int attended = resultSet.getInt("attended");

				String checkedin = resultSet.getString("checkedin");
				String checkedout = resultSet.getString("checkedout");
				BigDecimal officeid = resultSet.getBigDecimal("officeid");
				String information = resultSet.getString("information");
				String status = resultSet.getString("status");

				// Procesa otros campos según la estructura de tu tabla
				appointments.addElement(new Appointment(id.toBigInteger(), patientid.toBigInteger(),
						doctorid.toBigInteger(), startDate, enddate, urgency, attended, checkedin, checkedout,
						officeid.toBigInteger(), information, status));
			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appointments;
	}

	public static String getPatient(BigInteger id) throws Exception {

		String name = "";

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Ejecutar una consulta SQL
			String sql = "SELECT firstname FROM patient where id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setBigDecimal(1, new BigDecimal(id));
			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {

				name = resultSet.getString("firstname");

			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name;

	}

	public static BigInteger getPatientId(String name) throws Exception {

		BigInteger id = null;

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Ejecutar una consulta SQL
			String sql = "SELECT id FROM patient where firstname = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {

				id = resultSet.getBigDecimal("id").toBigInteger();

			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;

	}

	public static String getDoctor(BigInteger id) throws Exception {

		String name = "";

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Ejecutar una consulta SQL
			String sql = "SELECT name FROM doctor where id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setBigDecimal(1, new BigDecimal(id));
			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {

				name = resultSet.getString("name");

			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name;

	}

	public static void RemoveAppointment(Appointment appointment) {

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Ejecutar una consulta SQL
			String sql = "UPDATE appointment SET status = 'cancelled' WHERE id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setBigDecimal(1, new BigDecimal(appointment.getId()));
			statement.executeQuery();

			// Cerrar la conexión
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getOffice(BigInteger id) {
		String officecode = "";

		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Ejecutar una consulta SQL
			String sql = "SELECT officecode FROM office where id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setBigDecimal(1, new BigDecimal(id));
			ResultSet resultSet = statement.executeQuery();

			// Procesar los resultados
			while (resultSet.next()) {

				officecode = resultSet.getString("officecode");

			}

			// Cerrar la conexión
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return officecode;
	}

	public static void updateAppointment(BigInteger id, BigInteger doctorID, BigInteger patientID, String startDate,
			String endDate, int urgency, int officeIdFrom, String newContactInfo) throws Exception {
		try {
			// Establecer la conexión
			Connection connection = ConnectionFactory.getOracleConnection();

			// Consulta SQL con parámetros
			String insertQuery = "UPDATE Appointment SET patientid = ?, doctorid = ?, startdate = ?, enddate=?, urgency=?, information=? WHERE id = ?";

			// Crear un PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

			BigDecimal doc = new BigDecimal(doctorID);

			// Establecer valores para los parámetros
			preparedStatement.setBigDecimal(1, new BigDecimal(patientID));
			preparedStatement.setBigDecimal(2, doc);
			preparedStatement.setString(3, startDate);
			preparedStatement.setString(4, endDate);
			preparedStatement.setInt(5, urgency);
			preparedStatement.setString(6, newContactInfo);
			preparedStatement.setBigDecimal(7, new BigDecimal(id));

			// Ejecutar la inserción
			int filasAfectadas = preparedStatement.executeUpdate();

			if (filasAfectadas > 0) {
//				System.out.println("Inserción exitosa.");
			} else {
//				System.out.println("La inserción no se pudo realizar.");
			}

			// Cerrar la conexión y el PreparedStatement
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	

}
