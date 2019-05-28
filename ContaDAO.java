import java.sql.SQLException;

class ContaDAO extends Conta {

	public void depositar(double valor) {
        this.saldo += valor;
    }

    public void salvarConta() {
        String sql = "INSERT INTO conta(banco, num_conta, agencia, cpf_titular, saldo) VALUES (?, ?, ?, ?, ?)";
        this.insert(sql, this);
    }

    public Conta buscaConta(String cpf) {

    	Conexao connection = new Conexao();
    	Conta conta = new Conta();

    	try {
        	
        	String sql = "SELECT * FROM conta WHERE cpf_titular = '" + cpf + "'";
        	connection.stmt = connection.conn.createStatement();
        	connection.rs = connection.stmt.executeQuery(sql);

        	if(connection.rs.next()) {
        		conta.setSaldo(connection.rs.getDouble("saldo"));
        		conta.setNumConta(connection.rs.getString("num_conta"));
        		conta.setAgencia(connection.rs.getString("agencia"));
        		conta.setBanco(connection.rs.getString("banco"));
        		conta.setCpfTitular(cpf);
        	}

        } catch(SQLException ex) {
        	System.out.println("Erro na instrução SQL: " + ex.getMessage());
        	System.out.println("Não foi possível fazer o login.");
        	ex.printStackTrace();
        } finally {
        	try {
        		connection.conn.close();
        		connection.stmt.close();
        	} catch(Exception exception) {
        		exception.printStackTrace();
        	}

        	return conta;
        }

    }

    public void insert(String sql, Conta conta) {

    	Conexao connection = new Conexao();

		try {

			connection.getConnection();
			connection.st = connection.conn.prepareStatement(sql);
			connection.st.setString(1, conta.getBanco());
			connection.st.setString(2, conta.getNumConta());
			connection.st.setString(3, conta.getAgencia());
			connection.st.setString(4, conta.getCpfTitular());
			connection.st.setDouble(5, conta.getSaldo());

			connection.st.executeUpdate();
			connection.st.close();

			System.out.println("Conta cadastrada com sucesso!");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			ex.printStackTrace();
			System.out.println("Falha ao cadastrar a conta!");
		} finally {
			try {
				connection.st.close();
				connection.conn.close();
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}
		
	}

	public void delete(String sql) {

		Conexao connection = new Conexao();

		try {
			
			connection.getConnection();
			connection.stmt = connection.conn.createStatement();
			connection.stmt.executeQuery(sql);

			System.out.println("Conta excluída com sucesso.");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			System.out.println("Não foi possível deletar a Conta");
			ex.printStackTrace();
		} finally {
			try {
				connection.conn.close();
				connection.stmt.close();
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}

	}

	public void update(String sql, Conta conta) {

		Conexao connection = new Conexao();

		try {
			
			connection.getConnection();
			connection.st = connection.conn.prepareStatement(sql);
			connection.st.setString(1, conta.getBanco());
			connection.st.setString(2, conta.getNumConta());
			connection.st.setString(3, conta.getAgencia());
			connection.st.setString(4, conta.getCpfTitular());
			connection.st.setDouble(5, conta.getSaldo());

			connection.st.executeUpdate();
			connection.st.close();

			System.out.println("Conta alterada com sucesso!");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			System.out.println("Não foi possível alterar a Conta");
			ex.printStackTrace();
		} finally {
			try {
				connection.conn.close();
				connection.stmt.close();
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}

	}
}