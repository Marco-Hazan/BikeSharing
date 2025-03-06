package principalclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Eccezioni.ExpiredCardException;
import Eccezioni.RunNotFoundException;
import Eccezioni.UserNotFoundException;

/**
 * 
 */
public class CorsaDaoImpl implements CorsaDao {

    /**
     * Default constructor
     *



    /**
     * 
     */

    /**
     * @param Bicicletta b 
     * @return
     * @throws ExpiredCardException 
     * @throws UserNotFoundException 
     */
    public Corsa getAttiva(Bicicletta b){
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
        try {
            String sql = "SELECT * FROM corsa WHERE bicicletta = ? AND attiva = true";
            st = conn.prepareStatement(sql);
            st.setString(1, b.getCodice());
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                throw new RunNotFoundException("Nessuna corsa trovata per bici con codice "+ b.getCodice());
            }
            Timestamp start = rs.getTimestamp("start");
            Cliente c =  new ClienteDaoImpl().get(rs.getString("abbonato"));
            String rastrelliera =rs.getString("rastrelliera_partenza");
            Corsa corsa =  new Corsa(c, b, rastrelliera,start);
            if(rs.getBoolean("multata")) {
            	corsa.setMulta();
            }
            return corsa;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExpiredCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return null;
    }

    /**
     * @param Utente u 
     * @return
     */
    public Corsa getAttiva(Cliente c) throws RunNotFoundException{
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
        try {
            String sql = "SELECT * FROM corsa WHERE abbonato = ? AND attiva = true";
            st = conn.prepareStatement(sql);
            st.setString(1, c.getCodice());
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                throw new RunNotFoundException("Nessuna corsa in corso per bici utente "+ c.getCodice());
            }
            Timestamp start = rs.getTimestamp("start");
            Bicicletta b = new BiciclettaDaoImpl().get(rs.getString("bicicletta"));
            String rastrelliera =  rs.getString("rastrelliera_partenza");
            Corsa corsa =  new Corsa(c, b, rastrelliera,start);
            if(rs.getBoolean("multata")) {
            	corsa.setMulta();
            }
            return corsa;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return null;
    }

    /**
     * @param Corsa c
     */
    public void insert(Corsa c) {
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
        try {
            String sql = "INSERT INTO corsa (start,abbonato,bicicletta,rastrelliera_partenza,attiva) VALUES(?,?,?,?,true)";
            st = conn.prepareStatement(sql);
            st.setTimestamp(1, c.getStart());
            st.setString(2,c.getClient().getCodice());
            st.setString(3,c.getBike().getCodice());
            st.setString(4, c.getRPartenza());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }
    
    public void update(Corsa c,boolean multata) {
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
        try {
            String sql = "UPDATE corsa SET multata = ? WHERE abbonato = ? AND start = ?";
            st = conn.prepareStatement(sql);
            st.setBoolean(1, multata);
            st.setString(2,c.getClient().getCodice());
            st.setTimestamp(3, c.getStart());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }

    /**
     * @param Corsa c 
     * @param int fine 
     * @param Rastrelliera r
     */
    public void update(Corsa c,Timestamp fine,String rastrelliera) {
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
        try {
            String sql = "UPDATE corsa SET rastrelliera_arrivo = ?,end_corsa = ?,attiva = false WHERE abbonato = ? AND start = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, rastrelliera);
            st.setTimestamp(2,fine);
            st.setString(3, c.getClient().getCodice());
            st.setTimestamp(4, c.getStart());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }

    public Set<Corsa> getAll(Cliente c){
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
        Set<Corsa> corse = new HashSet<>();
        try {
            String sql = "SELECT * FROM corsa JOIN bicicletta ON corsa.bicicletta = bicicletta.codice WHERE abbonato = ? order by start desc";
            st = conn.prepareStatement(sql);
            st.setString(1, c.getCodice());
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Timestamp start = rs.getTimestamp("start");
                Bicicletta b = BiciclettaFactory.generaBici(rs.getString("bicicletta"),rs.getString("tipo"));
                String rastrelliera = rs.getString("rastrelliera_partenza");
                if(rs.getString("attiva").equals("true")){
                    corse.add(new Corsa(c, b, rastrelliera, start));
                }else{
                    Timestamp end = rs.getTimestamp("end_corsa");
                    String rastrelliera2 = rs.getString("rastrelliera_arrivo");
                    Corsa corsa =  new Corsa(c, b, rastrelliera,start,rastrelliera2, end);
                    if(rs.getBoolean("multata")) {
                    	corsa.setMulta();
                    }
                    corse.add(corsa);
                }
            }
            return corse;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return corse;
    }

    public int totAttive(){
    	Statement st = null;
    	Connection conn = UtilFunc.connectdb();
        try {
            String sql = "SELECT COUNT(*) AS TOT FROM corsa WHERE attiva = true";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            return rs.getInt("tot");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return 0;
    }

    public Set<Corsa> getAllAttive(){
    	Statement st = null;
    	Connection conn = UtilFunc.connectdb();
        Set<Corsa> corse = new HashSet<>();
        try {
            String sql = "SELECT abbonato,bicicletta,rastrelliera_partenza,start,tipo,multata FROM corsa JOIN bicicletta ON bicicletta.codice = corsa.bicicletta"
            		+ " WHERE attiva = true";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Corsa c = new Corsa(
                     new ClienteDaoImpl().get(rs.getString("abbonato")),
                    BiciclettaFactory.generaBici(rs.getString("bicicletta"), rs.getString("tipo")),
                    rs.getString("rastrelliera_partenza"),
                    rs.getTimestamp("start")
                ); 
                if(rs.getBoolean("multata")) { c.setMulta(); }
                corse.add(c);
            }
        } catch (Exception e) {
           e.printStackTrace();
        }finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return corse;
    }
    
    public Corsa getLast(Cliente c) {
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
    	try {
    		String sql = "SELECT * FROM corsa where abbonato = ? order by end_corsa desc";
    		st = conn.prepareStatement(sql);
    		st.setString(1,c.getCodice());
    		ResultSet rs = st.executeQuery();
    		if(!rs.next()) {
    			return null;
    		}
    		Bicicletta b = new BiciclettaDaoImpl().get(rs.getString("bicicletta"));
    		Corsa corsa =  new Corsa(c,b,rs.getString("rastrelliera_partenza"),rs.getTimestamp("start"),rs.getString("rastrelliera_arrivo"),rs.getTimestamp("end_corsa"));
    		if(rs.getBoolean("multata")) {
    			corsa.setMulta();
    		}
    		return corsa;
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
    
    public List<Corsa> getAll(){
    	List<Corsa> corse = new ArrayList<>();
    	Statement st = null;
    	Connection conn = UtilFunc.connectdb();
    	try {
    		st = conn.createStatement();
    		String sql = "SELECT * FROM corsa";
    		ResultSet rs = st.executeQuery(sql);
    		while(rs.next()) {
    			Bicicletta b = new BiciclettaDaoImpl().get(rs.getString("bicicletta"));
    			Cliente c = new ClienteDaoImpl().get(rs.getString("abbonato"));
        		Corsa corsa =  new Corsa(c,b,rs.getString("rastrelliera_partenza"),rs.getTimestamp("start"),rs.getString("rastrelliera_arrivo"),rs.getTimestamp("end_corsa"));
        		if(rs.getBoolean("multata")) {
        			corsa.setMulta();
        		}
        		corse.add(corsa);
    		}
    		return corse;
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
    
    public long tot() {
    	Statement st = null;
		Connection conn = UtilFunc.connectdb();
		try {
			st = conn.createStatement();
			String sql = "select count(*) from corsa";
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			return (long)rs.getInt("count");
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