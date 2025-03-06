package ClassiPrincipali;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import Eccezioni.LoginException;
import Eccezioni.UserNotFoundException;

public class PersonaleDaoImpl implements PersonaleDao{

	@Override
	public Personale get(String codice) throws UserNotFoundException {
		Connection conn = UtilFunc.connectdb();
		PreparedStatement st = null;
		String sql = "SELECT * FROM personale WHERE codice = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, codice);
			ResultSet rs = st.executeQuery();
			if(!rs.next()) {
				throw new UserNotFoundException();
			}
			return new Personale(codice,rs.getString("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void insert(Personale p) {
		Connection conn = UtilFunc.connectdb();
		PreparedStatement st = null;
		try {
			String sql = "INSERI INTO personale (codice,password) VALUES(?,?)";
			st = conn.prepareStatement(sql);
			st.setString(1, p.getCodice());
			st.setString(2, p.getCodice());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Personale p) {
		Connection conn = UtilFunc.connectdb();
		PreparedStatement st = null;
		try {
			String sql = "UPDATE personale set password = ? WHERE codice = ?";
			st = conn.prepareStatement(sql);
			st.setString(1, p.getPwd());
			st.setString(2, p.getCodice());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void delete(Personale p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Personale> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Personale loginPersonale(String codice,String password) throws UserNotFoundException{
		try {
			Personale personale = get(codice);
			if(personale.getPwd().equals(password)) {
				return personale;
			}else {
				throw new LoginException();
			}
		}catch(UserNotFoundException e) {
			throw new UserNotFoundException("codice non trovato");
		}
	}

	
	
}
