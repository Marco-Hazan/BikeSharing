package ClassiPrincipali;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaDaoImpl implements CartaDao {
	
	public void registraCarta(CreditCard c) {
		Connection conn = UtilFunc.connectdb();
		PreparedStatement st = null;
		if(get(c.getNumero()) != null) {
			return;
		}
		String sql = "INSERT INTO carta (numero,cvv,mese,anno) VALUES(?,?,?,?)";
		try {
			st = conn.prepareStatement(sql);
			st.setString(1,c.getNumero());
			st.setString(2, c.getCvv());
			st.setInt(3, c.getMeseScadenza());
			st.setInt(4, c.getAnnoScadenza());
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
	
	public void update(CreditCard c,double importo) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			String sql = "UPDATE carta SET soldi_spesi = soldi_spesi + ? WHERE numero = ?";
			conn = UtilFunc.connectdb();
			st = conn.prepareStatement(sql);
			st.setDouble(1, importo);
			st.setString(2, c.getNumero());
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
	
	public double getDisponibilita(CreditCard carta) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			String sql = "SELECT tetto - soldi_spesi as disponibilita from carta WHERE numero = ?";
			conn = UtilFunc.connectdb();
			st = conn.prepareStatement(sql);
			st.setString(1, carta.getNumero());
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return rs.getDouble("disponibilita");
			}
			return -1;
		}catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}finally {
			try {
				st.close();
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public CreditCard get(String numero) {
		Connection conn = null;
		PreparedStatement st = null;
		if(numero.length() != 16){
			throw new IllegalArgumentException("numero non ha 16 cifre");
		}
		try {
			String sql = "SELECT * from carta WHERE numero = ?";
			conn = UtilFunc.connectdb();
			st = conn.prepareStatement(sql);
			st.setString(1, numero);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				return new CreditCard(rs.getString("numero"),rs.getString("cvv"),rs.getInt("mese"),rs.getInt("anno"));
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
	
	public void delete(CreditCard carta) {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			String sql = "DELETE FROM carta where numero = ?";
			conn = UtilFunc.connectdb();
			st = conn.prepareStatement(sql);
			st.setString(1, carta.getNumero());
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
}
