package principalclass;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import Eccezioni.ExpiredCardException;
import Eccezioni.LoginException;
import Eccezioni.TypeNotFoundException;
import Eccezioni.UserNotFoundException;

/**
 * 
 */
public class ClienteDaoImpl implements ClienteDao {

    /**
     * Default constructor
     */

    /**
     * @param String codice 
     * @return
     * @throws UserNotFoundException 
     * @throws ExpiredCardException 
     */
	
	public boolean isUnique(String username) {
		PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
    	try {
            String sql = "SELECT * FROM abbonato WHERE username = ?";
            st = conn.prepareStatement(sql);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        	try {
        		st.close();
        		conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return false;
	}
	
	public Cliente classicLogin(String username,String password) throws UserNotFoundException {
		PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
    	try {
            String sql = "SELECT * FROM abbonato WHERE username = ?";
            st = conn.prepareStatement(sql);
            st.setString(1,username);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
            	if(rs.getString("password").equals(password)) {
            		return get(rs.getString("codice"));
            	}else {
            		throw new LoginException();
            	}
            }else {
            	throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
        	try {
        		st.close();
        		conn.close();
        	}catch(SQLException e) {
        		e.printStackTrace();
        	}
        }
        return null;
	}
	
	
	
    public Cliente get(String codice) throws UserNotFoundException{
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
        try {
            String sql = "SELECT * FROM abbonato JOIN carta ON carta = carta.numero WHERE codice = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, codice);
            ResultSet rs = st.executeQuery();
            if(!rs.next()) {
            	return null;
            }
            String username = rs.getString("username");
            String pwd = rs.getString("password");
            CreditCard creditCard = new CreditCard
        			(rs.getString("numero"),rs.getString("cvv"),rs.getInt("mese"),
            			rs.getInt("anno")
					);
            boolean studente = rs.getBoolean("studente");
            double debito = rs.getDouble("debito");
            Abbonamento abbonamento = null;
            if(rs.getTimestamp("scadenza") != null) {
               abbonamento = AbbonamentoFactory.createAbbonamento(rs.getString("tipologia"),rs.getTimestamp("scadenza"));
            }else {
               abbonamento = AbbonamentoFactory.createAbbonamento(rs.getString("tipologia"));
            }
            Cliente client = new Cliente(codice,username,pwd,studente,debito,creditCard,abbonamento);
            client.setPen(rs.getInt("penalita"));
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
        }catch(ExpiredCardException e) {
        	throw new ExpiredCardException();
        }catch(TypeNotFoundException e) {
        	throw new TypeNotFoundException(e.getMessage());
        }
        finally {
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
     * @return
     * @throws UserNotFoundException 
     */
    public Set<Cliente> getAllClients() {
    	Statement st = null;
    	Connection conn = UtilFunc.connectdb();
        Set<Cliente> users = new HashSet<>();
        try {
            String sql = "SELECT * FROM abbonato";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            	try {
                    try {
						users.add(get(rs.getString("codice")));
					} catch (ExpiredCardException e) {
					}
            	}catch(UserNotFoundException e) {
            		e.printStackTrace();
            	}
            }
            return users;
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
        return users;
    }

    /**
     * @param Utente u
     */
    public void update(Cliente c) {
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
        try {
            String sql = "UPDATE abbonato SET password=?,penalita = ?,debito=?,scadenza = ?,carta = ? WHERE codice = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, c.getPwd());
            st.setInt(2, c.getPen());
            st.setDouble(3, c.getDebito());
            st.setTimestamp(4,c.getAbbonamento().getEnd());
            st.setString(5, c.getCarta().getNumero());
            st.setString(6, c.getCodice());
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
     * @param Persona p 
     * @param String pwd 
     * @return
     * @throws SQLException 
     */
    public boolean insert(Cliente client) {
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
        try {
            String sql = "INSERT INTO abbonato (codice,username,password,scadenza,tipologia,carta,studente) "
            		+ "VALUES (?,?,?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setString(1, client.getCodice());
            st.setString(2,client.getUsername());
            st.setString(3, client.getPwd());
            st.setTimestamp(4, client.getAbbonamento().getEnd());
            st.setString(5, client.getTypeAbbonamento());
            st.setString(6, client.getCarta().getNumero());
            st.setBoolean(7,client.isStudent());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
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
    
    public Cliente loginClient(String codice,String password) throws UserNotFoundException {
		Cliente cliente = get(codice);
		if(cliente == null) {
			throw new UserNotFoundException();
		}
		if(cliente.getPwd().equals(password)) {
			return cliente;
		}else {
			throw new LoginException();
		}
    }
    
    public void delete(Cliente client) {
    	PreparedStatement st = null;
    	Connection conn = UtilFunc.connectdb();
        try {
            String sql = "DELETE FROM abbonato where codice = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, client.getCodice());
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
}