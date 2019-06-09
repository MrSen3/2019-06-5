package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polito.tdp.model.AnnoCount;
import it.polito.tdp.model.Distretto;
import it.polito.tdp.model.Event;
import it.polito.tdp.model.Evento;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Event> eventiInUnAnno(int anno){
		String sql = "SELECT * FROM EVENTS " + 
				"WHERE YEAR(reported_date)=? " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<AnnoCount> getAnni() {
		// TODO Auto-generated method stub
		String sql = "SELECT YEAR(reported_date) as anno, COUNT(*) AS cnt " + 
				"FROM EVENTS " + 
				"GROUP BY YEAR(reported_date) " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<AnnoCount> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					list.add(new AnnoCount(res.getInt("anno"), res.getInt("cnt")));
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

	public List<Distretto> getDistrettiAnno(int annoScelto) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT district_id AS distretto, COUNT(*) AS cnt, AVG(geo_lon) AS longitude, AVG(geo_lat) AS latitude " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date)=? " + 
				"GROUP BY district_id " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, annoScelto);
			List<Distretto> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					list.add(new Distretto(res.getInt("distretto"), res.getInt("cnt"), annoScelto, res.getDouble("latitude"), res.getDouble("longitude")));
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public boolean cercaData(int anno, int mese, int giorno) {
			// TODO Auto-generated method stub
		boolean trovata=false;
			String sql = "SELECT reported_date AS data, COUNT(*) AS cnt " + 
					"FROM EVENTS " + 
					"WHERE YEAR(reported_date)=? " + 
					"AND MONTH(reported_date)=? " + 
					"AND DAY(reported_date)= ? " + 
					"GROUP BY reported_date" ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				st.setInt(1, anno);
				st.setInt(2, mese);
				st.setInt(3, giorno);
				
				ResultSet res = st.executeQuery() ;
				
				if(res.next()) {
					if(res.getInt("cnt")>0)
						trovata=true;
				}
				
				conn.close();
				return trovata;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return trovata ;
			}
			
	}

	public List<Event> getEventiGiorno(int anno, int mese, int giorno) {
		// TODO Auto-generated method stub
		List<Event>result=new ArrayList<>();
		String sql = "SELECT * " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date)=? " + 
				"AND MONTH(reported_date)=? " + 
				"AND DAY(reported_date)= ? " + 
				"ORDER BY reported_date ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				try {
					result.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

}
