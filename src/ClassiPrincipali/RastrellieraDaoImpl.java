package ClassiPrincipali;


import java.sql.Statement;
import Eccezioni.RackNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 */
public class RastrellieraDaoImpl implements RastrellieraDao {

    /**
     * Default constructor
     */


	
	public void insert(Rastrelliera rastrelliera) {
		Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
        String sql = "INSERT INTO RASTRELLIERA (nome,size,tot_morse_elettriche) VALUES (?,?,?)";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, rastrelliera.getNome());
            st.setInt(2, rastrelliera.getSize());
            st.setInt(3, rastrelliera.getTotMorseElettriche());
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
    /**
     * @param String nome 
     * @return
     */
    public Rastrelliera get(String nome) throws RackNotFoundException{
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
        String sql = "SELECT * FROM rastrelliera WHERE nome = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, nome);
            ResultSet rs = st.executeQuery();
            if(!rs.next()){
                return null;
            }
            int size = rs.getInt("size");
            int tot_morse_elettriche = rs.getInt("tot_morse_elettriche");
            Map<Integer,Bicicletta> bikes = new TreeMap<>();
            for(int i=1;i<=size;i++){
                bikes.put(i,null);
            }
            sql =  "SELECT * FROM bicicletta where rastrelliera = ?";
            st.close();
            st = conn.prepareStatement(sql);
            st.setString(1, nome);
            rs = st.executeQuery();
            while(rs.next()){
            	bikes.put(rs.getInt("posizione"),BiciclettaFactory.generaBici(rs.getString("codice"), rs.getString("tipo")));
            }
            return new Rastrelliera(nome, bikes, size,tot_morse_elettriche);
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

    
    public Set<Rastrelliera> getAllRastrelliere() {
    	Connection conn = UtilFunc.connectdb();
    	Statement st = null;
        Set<Rastrelliera> rastrelliere = new HashSet<>();

        try {
            String sql = "SELECT * FROM rastrelliera";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                rastrelliere.add(this.get(rs.getString("nome")));
            }
            return rastrelliere;
        } catch (SQLException e) {
            e.printStackTrace();
    		return rastrelliere;
        }finally {
        	try {
        		st.close();
        		conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
    }

    public void update(Rastrelliera r) {
    	Connection conn = UtilFunc.connectdb();
    	PreparedStatement st = null;
    	String sql = "";
    	try {
    		for(int i=1;i<=r.getSize();i++) {
    			if(r.get(i) instanceof Bicicletta ) {
    				sql = "UPDATE bicicletta SET rastrelliera=?,posizione=? WHERE codice = ?";
            		st = conn.prepareStatement(sql);
            		st.setString(1,r.getNome());
            		st.setInt(2, i);
            		st.setString(3, r.get(i).getCodice());
            		st.executeUpdate();
    			}else {
    				sql = "UPDATE bicicletta SET rastrelliera = NULL,posizione = NULL WHERE rastrelliera = ? AND posizione = ?";
    				st = conn.prepareStatement(sql);
    				st.setString(1, r.getNome());
    				st.setInt(2, i);
    				st.executeUpdate();
    			}
    			st.close();
    		}
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



	public boolean delete(String nome) {
		Connection conn = UtilFunc.connectdb();
		PreparedStatement st = null;
		String sql = "DELETE FROM rastrelliera WHERE nome = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, nome);
			st.executeUpdate();
			st.close();
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
		return true;
	}








}	










/**
 * @param Rastrelliera r 
 * @param int posizione 
 * @param Bicicletta b
 */
/* public void addBike(Rastrelliera r, int posizione,Bicicletta b) throws RackNotFoundException,BikeNotFoundException{
	Connection conn = UtilFunc.connectdb();
	PreparedStatement st = null;
    try {
        int tot = r.getTot() + 1;
        String sql = "SELECT * FROM bicicletta WHERE codice = ?";
        st = conn.prepareStatement(sql);
        st.setString(1, b.getCodice());
        ResultSet rs = st.executeQuery();
        if(!rs.next()){
            throw new BikeNotFoundException("Nessuna bicicletta è stata trovata con codice " + b.getCodice());
        }
        sql = "UPDATE bicicletta SET rastrelliera = ?,posizione = ? WHERE codice = ?";
        st.close();
        st = conn.prepareStatement(sql);
        st.setString(1, r.getNome());
        st.setInt(2, posizione);
        st.setString(3,b.getCodice());
        st.executeUpdate();
        st.close();
        sql = "UPDATE rastrelliera SET tot = "+ Integer.toString(tot) + "WHERE nome = '"+ r.getNome()+"'";
        conn.createStatement().executeUpdate(sql);
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
}*/

/**
 * @param Rastrelliera r 
 * @param int posizione
 */
/*public Bicicletta removeBike(Rastrelliera r,int posizione) throws RackNotFoundException,BikeNotFoundException{
	Connection conn = UtilFunc.connectdb();
	PreparedStatement st = null;
    int tot = r.getTot() - 1;
    Bicicletta b = r.get(posizione);
    try {
        String sql = "SELECT * FROM bicicletta WHERE rastrelliera = ? AND posizione = ?";
        st = conn.prepareStatement(sql);
        st.setString(1, r.getNome());
        st.setInt(2, posizione);
        ResultSet rs = st.executeQuery();
        if(!rs.next()){
            throw new BikeNotFoundException("Nessuna bicicletta è stata trovata in questa posizione, rimozione annullata");
        }
        sql = "UPDATE bicicletta SET rastrelliera = NULL, posizione = NULL WHERE rastrelliera = ? AND posizione = ?";
        st.close();
        st = conn.prepareStatement(sql);
        st.setString(1, r.getNome());
        st.setInt(2, posizione);
        st.executeUpdate();
        st.close();
        sql = "UPDATE rastrelliera SET tot = "+ Integer.toString(tot) + "WHERE nome = ?";
        st = conn.prepareStatement(sql);
        st.setString(1,r.getNome());
        st.executeUpdate();
        st.close();
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
    return b;
}*/

/**
 * @return
 */