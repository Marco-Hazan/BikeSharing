package principalclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import exceptions.BikeNotFoundException;

public class BiciclettaDaoImpl implements BiciclettaDao {

    public Bicicletta get(String codice) throws BikeNotFoundException{
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
        try {
            String sql = "SELECT * FROM bicicletta WHERE codice = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, codice);
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                throw new BikeNotFoundException("Nessuna bici trovata con codice " + codice);
            }
            String tipo = rs.getString("tipo");
            Bicicletta b =  BiciclettaFactory.generaBici(codice, tipo);
            if(!rs.getString("stato").equals("OK")) {
            	b.setStato(rs.getString("stato"));
            }
            return b;
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

    public void insert(Bicicletta b,String nome_rastrelliera,int posizione){
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
    	String sql = "";
        sql = "INSERT INTO bicicletta (codice,rastrelliera,posizione,tipo) VALUES(?,?,?,?)";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, b.getCodice());
            st.setString(2, nome_rastrelliera);
            st.setInt(3, posizione);
            st.setString(4, BiciclettaFactory.getType(b));
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
    
    public void insert(Bicicletta b){
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
        String sql = "";
        sql = "INSERT INTO bicicletta (codice,tipo) VALUES(?,?)";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, b.getCodice());
            st.setString(2, BiciclettaFactory.getType(b));
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
    
    
    public Set<Bicicletta> getBiciDanneggiate(){
    	Set<Bicicletta> biciclette = new HashSet<>();
    	Connection conn = UtilFunc.connectdb();
    	Statement st = null;
    	String sql ="SELECT * FROM bicicletta where stato <> 'OK' AND rastrelliera IS NOT NULL ";
    	try {
    		st = conn.createStatement();
    		ResultSet rs = st.executeQuery(sql);
    		while(rs.next()) {
    			biciclette.add(get(rs.getString("codice")));
    		}
        	return biciclette;
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return biciclette;
    	}finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }
    
    public void update(Bicicletta b) {
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
    	try {
    		String sql ="UPDATE bicicletta set stato = ? where codice = ?";
    		st = conn.prepareStatement(sql);
    		st.setString(1, b.getStato());
    		st.setString(2, b.getCodice());
    		st.executeUpdate();
    	}catch(SQLException e) {
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
    
    public boolean delete(Bicicletta b) {
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
    	try {
    		String sql ="DELETE FROM bicicletta where codice = ?";
    		st = conn.prepareStatement(sql);
    		st.setString(1, b.getCodice());
    		int rowcount = st.executeUpdate();
    		return rowcount != 0;
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return false;
    	}finally {
        	try {
            	st.close();
            	conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }
    
    public String getRastrelliera(Bicicletta bicicletta) {
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
    	try {
    		String sql ="SELECT rastrelliera FROM bicicletta where codice = ? AND rastrelliera IS NOT NULL";
    		st = conn.prepareStatement(sql);
    		st.setString(1, bicicletta.getCodice());
    		ResultSet rs = st.executeQuery();
    		if(rs.next()) {
    			return rs.getString("rastrelliera");
    		}
    		return null;
    	}catch(SQLException e) {
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

}
