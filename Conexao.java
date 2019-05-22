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

	public void insert(String sql, Investidor investidor) {

		try {

			this.getConnection();
			this.st = this.conn.prepareStatement(sql);
			this.st.setString(1, investidor.getNome());
			this.st.setString(2, investidor.getCpf());
			this.st.setString(3, investidor.getEmail());
			this.st.setString(4, investidor.getSenha());

			this.st.executeUpdate();
			this.st.close();

			System.out.println("Investidor cadastrado com sucesso!");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			System.out.println("Falha ao cadastrar o investidor!");
			ex.printStackTrace();
		} finally {
			try {
				this.conn.close();
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}

	}

	public void insert(String sql, Conta conta) {

		try {

			this.getConnection();
			this.st = this.conn.prepareStatement(sql);
			this.st.setString(1, conta.getBanco());
			this.st.setString(2, conta.getNumConta());
			this.st.setString(3, conta.getAgencia());
			this.st.setString(4, conta.getCpfTitular());
			this.st.setDouble(5, conta.getSaldo());

			this.st.executeUpdate();
			this.st.close();

			System.out.println("Conta cadastrada com sucesso!");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			ex.printStackTrace();
			System.out.println("Falha ao cadastrar a conta!");
		} finally {
			try {
				this.st.close();
				this.conn.close();
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}

	public boolean selectVerificar(String sql) {

		try {

			this.getConnection();
			this.stmt = this.conn.createStatement();
			this.rs = this.stmt.executeQuery(sql);

			ResultSetMetaData meta = this.rs.getMetaData();
			int numColunas = meta.getColumnCount();

			return (numColunas > 0) ? true : false;

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
			return true;
		}
	}

	public Object select(String sql, Object obj) {

		try {

			this.getConnection();
			this.stmt = this.conn.createStatement();
			this.rs = this.stmt.executeQuery(sql);

			return (numColunas > 0) ? true : false;

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
			return true;
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
