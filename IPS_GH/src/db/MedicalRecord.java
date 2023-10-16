package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionFactory;
import util.MedicalRecordBLDto;

public class MedicalRecord {

	public static MedicalRecordBLDto getRecord(int id) {
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		try {
		con = ConnectionFactory.getOracleConnection();
		ps = con.prepareStatement("SELECT * FROM MEDICALRECORD  WHERE patientid = ?");
		ps.setInt(1, id);
		rs = ps.executeQuery();
		
		MedicalRecordBLDto medre = new MedicalRecordBLDto();
	
		while(rs.next()) {
			medre.id = rs.getInt(1);
			medre.patientId = rs.getInt(2);
			var diseases = rs.getString(3);
			var vaccines = rs.getString(4);
			
			if(diseases != null)
				medre.disease = diseases.split(";");
			
			if(vaccines != null)
				medre.vaccines = vaccines.split(";");
			
		}
		return medre;
	
	}catch(Exception e) {
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
	
}
