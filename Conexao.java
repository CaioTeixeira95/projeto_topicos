import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class Conexao {
			
	Connection conn = null;
	PreparedStatement st = null;
	Statement stmt = null;
	ResultSet rs = null;
	static final String URL = "jdbc:mysql://localhost/fundo_imobiliario";

	public void getConnection() {
		try {
			this.conn = DriverManager.getConnection(this.URL, "caio", "Caio1995");
		} catch(SQLException ex) {
			System.out.println("Erro na conexão: " + ex.getMessage());
			ex.printStackTrace();
		}		
	}

	public boolean selectVerificar(String sql, String columLabel) {

		int num_rows = 0;

		try {

			this.getConnection();
			this.stmt = this.conn.createStatement();
			this.rs = this.stmt.executeQuery(sql);

			if(this.rs.next()) {
				num_rows = this.rs.getInt(columLabel);
			}

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			ex.printStackTrace();
			System.out.println("Não foi possível realizar a consulta.");
		} finally {
			try {
				this.rs.close();
				this.stmt.close();
				this.conn.close();
			} catch(Exception exception) {
				exception.printStackTrace();
			}

			return (num_rows > 0) ? true : false;
		}
	}

	/*try {

		// Fazendo a conexão
		conn = DriverManager.getConnection(URL, "caio", "Caio1995");
		stmt = conn.createStatement();

		// Executando a query
		String sql = "SELECT * FROM contato";
		rs = stmt.executeQuery(sql);

		ResultSetMetaData meta = rs.getMetaData();
		int numColunas = meta.getColumnCount();

		while(rs.next()) {
			for(int i = 1; i <= numColunas; i++) {
				System.out.printf("%-8s\t", rs.getObject(i));
			}
			System.out.println();
		}
	} catch(SQLException ex) {
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
		ex.printStackTrace();
	} finally {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}*/
}
