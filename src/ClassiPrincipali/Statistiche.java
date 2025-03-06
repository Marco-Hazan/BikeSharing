package ClassiPrincipali;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Statistiche {
	public static String getRPartenzaPiuUsata() {
		Statement st = null;
		Connection conn = UtilFunc.connectdb();
		try {
			st = conn.createStatement();
			String sql = "SELECT rastrelliera_partenza,count(*) from corsa group by rastrelliera_partenza order by count desc";
			ResultSet rs = st.executeQuery(sql);
			if(!rs.next()) {
				return null;
			};
			return rs.getString("rastrelliera_partenza");
	    } catch (SQLException e) {
	       e.printStackTrace();
	       return null;
	    }finally {
	    	try {
	        	st.close();
	        	conn.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public static String getRArrivoPiuUsata() {
		Statement st = null;
		Connection conn = UtilFunc.connectdb();
		try {
			st = conn.createStatement();
			String sql = "SELECT rastrelliera_arrivo,count(*) from corsa group by rastrelliera_arrivo order by count desc";
			ResultSet rs = st.executeQuery(sql);
			if(!rs.next()) {
				return null;
			};
			return rs.getString("rastrelliera_arrivo");
	    } catch (SQLException e) {
	       e.printStackTrace();
	       return null;
	    }finally {
	    	try {
	        	st.close();
	        	conn.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public static String getTipoPiuUsato() {
		Statement st = null;
		Connection conn = UtilFunc.connectdb();
		try {
			st = conn.createStatement();
			String sql = "select tipo,count(tipo) from corsa join bicicletta on corsa.bicicletta = bicicletta.codice group by tipo";
			ResultSet rs = st.executeQuery(sql);
			int max = 0;
			String maxtipo = "";
			boolean next;
			next = rs.next();
			if(!next) {
				return null;
			}
			while(next) {
				if(rs.getInt("count") > max) {
					max = rs.getInt("count");
					maxtipo = rs.getString("tipo");
				}
				next = rs.next();
			}
			if(maxtipo.equals("C")) {
				return "Classica";
			}else if(maxtipo.equals("E")) {
				return "Elettrica";
			}else {
				return "Elettrica con seggiolino";
			}
	    } catch (SQLException e) {
	       e.printStackTrace();
	       return null;
	    }finally {
	    	try {
	        	st.close();
	        	conn.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public static int abbonati(){
		PreparedStatement st = null;
		Connection conn = UtilFunc.connectdb();
		try {
			String sql = "SELECT COUNT(*) FROM abbonato where scadenza > ? or scadenza is null";
			st = conn.prepareStatement(sql);
			st.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ResultSet rs = st.executeQuery();
			if(!rs.next()) {
				return 0;
			};
			return rs.getInt("count");
	    } catch (SQLException e) {
	       e.printStackTrace();
	       return 0;
	    }finally {
	    	try {
	        	st.close();
	        	conn.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	
	
	public static double mediaCorse() {
		Statement st = null;
		Connection conn = UtilFunc.connectdb();
		try {
			st = conn.createStatement();
			String sql = "select MIN(start),MAX(start) from corsa";
			ResultSet rs = st.executeQuery(sql);
			if(!rs.next()){
				return 0;
			};
			Timestamp first_run = rs.getTimestamp("min");
			Timestamp last_run = rs.getTimestamp("max");
			if(first_run == null || last_run == null) {
				return 0;
			}
			long days =TimeUnit.MILLISECONDS.toDays(last_run.getTime() - first_run.getTime());
			System.out.println(days);
			return (double) new CorsaDaoImpl().tot() / (double)days;
	    } catch (SQLException e) {
	       e.printStackTrace();
	       return 0;
	    }finally {
	    	try {
	        	st.close();
	        	conn.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
	public static double percentualeStudenti() {
		PreparedStatement st = null;
		Connection conn = UtilFunc.connectdb();
		try {
			String sql = "SELECT COUNT(*) FROM abbonato where (scadenza > ? or scadenza is null) AND studente = TRUE";
			st = conn.prepareStatement(sql);
			st.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ResultSet rs = st.executeQuery();
			if(!rs.next()) {
				return 0;
			};
			return (double)rs.getInt("count") / (double)abbonati();
	    } catch (SQLException e) {
	       e.printStackTrace();
	       return 0;
	    }finally {
	    	try {
	        	st.close();
	        	conn.close();
	    	}catch(SQLException e) {
	    		e.printStackTrace();
	    	}
	    }
	}
	
}
