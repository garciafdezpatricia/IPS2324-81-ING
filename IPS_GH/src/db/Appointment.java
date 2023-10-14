package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.AppointmentBLDto;
import util.ConnectionFactory;

public class Appointment {

	public static List<AppointmentBLDto> getAppointmentsByDoctorId(int doctorId) {
		Connection con = null;
		PreparedStatement ps= null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getOracleConnection();
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
				apmnt.checkIn = rs.getInt(8)==0?false:true;
				apmnt.checkOut = rs.getInt(9)==0?false:true;
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
}
