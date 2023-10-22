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
import java.util.List;

import javax.swing.DefaultListModel;

import db.Appointment;
import db.Doctor;
import db.Office;
import db.Patient;
import db.WorkDay;
import db.WorkPeriod;
import gui.doctor.DoctorAppointmentView;

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
				// Procesa otros campos según la estructura de tu tabla
				doctors.addElement(new Doctor(aux, numcolegiado, name, surname, email));
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

//			// Establecer valores para los parámetros
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

			BigDecimal aux2 = new BigDecimal(workperiod_id);

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

	@SuppressWarnings("unused")
	public static String getFreeHours(List<Doctor> doctors, Date day) throws Exception {
		String res = "";
		Connection connection = ConnectionFactory.getOracleConnection();
		if (doctors.size() == 1) {
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
				System.out.println("No hay free hours porque ese dia no esta dentro de su workperiod");
			}
			// si si que está dentro del workperiod
			else {
				System.out.println("El dia si esta dentro de su workperiod");
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
					System.out.println("workday null");
				} else {
					// ahora falta filtrar en el caso de que tenga citas

					String s = "The doctors works from " + startHour + " to " + endHour;
					res = s;

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
								resultSet3.getString("information")));

					}

					// filtrarlos por las que sean en el día, hay que pasar el string a date
					List<Appointment> appsThatDay = new ArrayList<>();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					for (Appointment a : apps) {
						System.out.println("a.getStartdate" + a.getStartdate());
						System.out.println("day" + dateFormat.parse(day + " 00:00:00"));

						System.out.println("a.getend" + a.getEnddate());

						if (dateFormat.parse(a.getStartdate()).after(dateFormat.parse(day + " 00:00:00"))
								&& dateFormat.parse(a.getEnddate()).before(dateFormat.parse(day + " 24:00:00"))) {

							appsThatDay.add(a);
						}
					}

					// si hay citas ese dia
					if (!appsThatDay.isEmpty()) {
						for (Appointment a : appsThatDay) {
							res += "\n The doctor has appointment from:\n\t "
									+ dateFormat.parse(a.getStartdate()).getHours() + ":"
									+ dateFormat.parse(a.getStartdate()).getMinutes() + " to\n\t "
									+ dateFormat.parse(a.getEnddate()).getHours() + ":"
									+ dateFormat.parse(a.getEnddate()).getMinutes() + "\n";
						}
					} else {
						System.out.println("no tiene más citas");
					}
				}

			}
		}
		if (doctors.size() > 1) {
			// hay que comprobar que la fecha que se pasa como parámetro esté dentro de ese
			// workperiod
			String query = "SELECT id from workperiod where fk_doctorid = ? and ? >= startday and ? <= finalday ";
			PreparedStatement preparedStatement = connection.prepareStatement(query);

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
					System.out.println("No hay free hours porque ese dia uno de los médicos no trabaja (workperiod)");
				}
				// si si que está dentro del workperiod
				else {
					System.out.println("El dia si esta dentro del workperiod de todos los doctores");
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

					if (workdays.size() != ids.size()) {
						System.out.println("la lista de workdays no coincide con la de doctores");
					} else {
						// ahora falta filtrar en el caso de que tenga citas

						String horaMasTardeDeEntrada = workdays.get(0).getStartHour();
						String horaMasProntoDeSalida = workdays.get(0).getEndHour();
						
						for(int j = 0; j< workdays.size();j++) {
							if(dateFormat.parse(workdays.get(j).getStartHour()).after(dateFormat.parse(horaMasTardeDeEntrada))) {
								horaMasTardeDeEntrada = workdays.get(j).getStartHour();
							}
							if(dateFormat.parse(workdays.get(j).getEndHour()).before(dateFormat.parse(horaMasProntoDeSalida))) {
								horaMasProntoDeSalida = workdays.get(j).getEndHour();
							}
								
						}
						String s = "The doctors work from " + horaMasTardeDeEntrada + " to " + horaMasProntoDeSalida;
						res = s;

						// APPOINTMENT?
						String query3 = "SELECT * FROM APPOINTMENT WHERE DOCTORID = ?";

						PreparedStatement preparedStatement3 = connection.prepareStatement(query3);

						preparedStatement3.setBigDecimal(1, ids.get(i));

						ResultSet resultSet3 = preparedStatement3.executeQuery();

						List<Appointment> apps = new ArrayList<>();
						while (resultSet3.next()) {
							apps.add(new Appointment(resultSet3.getBigDecimal("id").toBigInteger(),
									resultSet3.getBigDecimal("patientid").toBigInteger(),
									resultSet3.getBigDecimal("doctorid").toBigInteger(),
									resultSet3.getString("startdate"), resultSet3.getString("enddate"),
									resultSet3.getInt("urgency"), resultSet3.getInt("attended"),
									resultSet3.getString("checkedin"), resultSet3.getString("checkedout"),
									resultSet3.getBigDecimal("officeid").toBigInteger(),
									resultSet3.getString("information")));

						}

						// filtrarlos por las que sean en el día, hay que pasar el string a date
						List<Appointment> appsThatDay = new ArrayList<>();
						SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						for (Appointment a : apps) {
							System.out.println("a.getStartdate" + a.getStartdate());
							System.out.println("day" + dateFormat2.parse(day + " 00:00:00"));

							System.out.println("a.getend" + a.getEnddate());

							if (dateFormat2.parse(a.getStartdate()).after(dateFormat2.parse(day + " 00:00:00"))
									&& dateFormat2.parse(a.getEnddate()).before(dateFormat2.parse(day + " 24:00:00"))) {

								appsThatDay.add(a);
							}
						}

						// si hay citas ese dia
						if (!appsThatDay.isEmpty()) {
							for (Appointment a : appsThatDay) {
								res += "\n A doctor has appointment from:\n\t "
										+ dateFormat2.parse(a.getStartdate()).getHours() + ":"
										+ dateFormat2.parse(a.getStartdate()).getMinutes() + " to\n\t "
										+ dateFormat2.parse(a.getEnddate()).getHours() + ":"
										+ dateFormat2.parse(a.getEnddate()).getMinutes() + "\n";
							}
						} else {
							System.out.println("no tiene más citas");
						}
					}

				}

			}

		}

		// todo: por si escogen mas de un medico
		return res;

	}
}
