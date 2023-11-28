package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionFactory;
import util.DiagnosisBLDto;
import util.DoctorGraphBLDto;

public class GraphGenerator {
	public static List<DiagnosisBLDto> getDiagnostics() {
		List<DiagnosisBLDto> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getOracleConnection();
			ps = con.prepareStatement(
					"SELECT diagnosis,initdate,COUNT(*) as amount FROM diagnosis GROUP BY(diagnosis,initdate)");

			rs = ps.executeQuery();

			DiagnosisBLDto diagn;

			while (rs.next()) {
				diagn = new DiagnosisBLDto();
				diagn.amount = rs.getInt("amount");
				diagn.diagnosis = rs.getString("diagnosis");
				diagn.initDate = rs.getString("initdate");

				list.add(diagn);
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

	public static List<DoctorGraphBLDto> getDoctorsGraph() {
		List<DoctorGraphBLDto> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getOracleConnection();
			ps = con.prepareStatement(
					"SELECT SPECIALIZATION, COUNT(*) as amount FROM DOCTOR GROUP BY (SPECIALIZATION)");

			rs = ps.executeQuery();

			DoctorGraphBLDto doc;

			while (rs.next()) {
				doc = new DoctorGraphBLDto();
				doc.amount = rs.getInt("amount");
				doc.specialization = rs.getString("SPECIALIZATION");

				list.add(doc);
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
