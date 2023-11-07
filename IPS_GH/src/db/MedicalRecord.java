package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionFactory;
import util.MedicalRecordBLDto;

public class MedicalRecord {

	public static List<MedicalRecordBLDto> getVaccines(int id) {
		var list = new ArrayList<MedicalRecordBLDto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM vaccines  WHERE patientid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			MedicalRecordBLDto medre;

			while (rs.next()) {
				medre = new MedicalRecordBLDto();
				medre.id = rs.getInt(1);
				medre.patientId = rs.getInt("patientid");
				medre.doctorId = rs.getInt("doctorid");
				medre.date = rs.getString("initdate");
				medre.description = rs.getString("vaccineinfo");
				list.add(medre);
			}
			return list;

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

	public static List<MedicalRecordBLDto> getDiagnosis(int id) {
		var list = new ArrayList<MedicalRecordBLDto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM diagnosis  WHERE patientid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			MedicalRecordBLDto medre;

			while (rs.next()) {
				medre = new MedicalRecordBLDto();
				medre.id = rs.getInt(1);
				medre.patientId = rs.getInt("patientid");
				medre.doctorId = rs.getInt("doctorid");
				medre.date = rs.getString("initdate");
				medre.description = rs.getString("diagnosis");
				list.add(medre);
			}
			return list;

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

	public static List<MedicalRecordBLDto> getPrescription(int id) {
		var list = new ArrayList<MedicalRecordBLDto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM prescription  WHERE patientid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			MedicalRecordBLDto medre;

			while (rs.next()) {
				medre = new MedicalRecordBLDto();
				medre.id = rs.getInt(1);
				medre.patientId = rs.getInt("patientid");
				medre.doctorId = rs.getInt("doctorid");
				medre.date = rs.getString("initdate");
				medre.description = rs.getString("prescriptioninfo");
				list.add(medre);
			}
			return list;

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

	public static List<MedicalRecordBLDto> getCauses(int id) {
		var list = new ArrayList<MedicalRecordBLDto>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getOracleConnection();
			ps = con.prepareStatement("SELECT * FROM causes  WHERE patientid = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			MedicalRecordBLDto medre;

			while (rs.next()) {
				medre = new MedicalRecordBLDto();
				medre.id = rs.getInt(1);
				medre.patientId = rs.getInt("patientid");
				medre.doctorId = rs.getInt("doctorid");
				medre.date = rs.getString("initdate");
				medre.description = rs.getString("causeinfo");
				list.add(medre);
			}
			return list;

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
}
