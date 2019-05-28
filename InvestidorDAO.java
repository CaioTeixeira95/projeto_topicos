import java.sql.SQLException;

class InvestidorDAO extends Investidor {

	 public void mostrarDadosInvestidor() {
        System.out.println("****** Dados do Investidor *******");
        System.out.println("Nome:\t" + this.getNome());
        System.out.println("CPF:\t" + this.getCpf());
        System.out.println("Banco:\t" + this.contaBancaria.getBanco());
        System.out.println("Nº Conta:\t" + this.contaBancaria.getNumConta());
        System.out.println("Agência:\t" + this.contaBancaria.getAgencia());
        System.out.println("Saldo Atual:\t" + this.contaBancaria.getSaldo());
    }

    public void salvarInvestidor() {
        String sql = "INSERT INTO investidor (nome, cpf, email, senha) VALUES (?, ?, ?, ?)";
        this.insert(sql, this);
    }

    public boolean verificaSeExiste(String campo, String valor) {
        Conexao conexao = new Conexao();
        String sql = "SELECT COUNT(*) AS " + campo + " FROM investidor WHERE " + campo + " = '" + valor + "'";
        return conexao.selectVerificar(sql, campo);
    }

    /*public void login(String email, String senha) {
        Conexao conexao = new Conexao();
        String sql = "SELECT * FROM investidor WHERE email = '" + email + "' AND senha = '" + senha + "'";
    }*/

    public void insert(String sql, Investidor investidor) {

    	Conexao connection = new Conexao();

		try {

			connection.getConnection();
			connection.st = connection.conn.prepareStatement(sql);
			connection.st.setString(1, investidor.getNome());
			connection.st.setString(2, investidor.getCpf());
			connection.st.setString(3, investidor.getEmail());
			connection.st.setString(4, investidor.getSenha());

			connection.st.executeUpdate();
			connection.st.close();

			System.out.println("Investidor cadastrado com sucesso!");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			System.out.println("Falha ao cadastrar o investidor!");
			ex.printStackTrace();
		} finally {
			try {
				connection.conn.close();
				connection.st.close();
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

			System.out.println("Investidor excluído com sucesso.");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			System.out.println("Não foi possível deletar o Investidor");
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

	public void update(String sql, Investidor investidor) {

		Conexao connection = new Conexao();

		try {
			
			connection.getConnection();
			connection.st = connection.conn.prepareStatement(sql);
			connection.st.setString(1, investidor.getNome());
			connection.st.setString(2, investidor.getCpf());
			connection.st.setString(3, investidor.getEmail());
			connection.st.setString(4, investidor.getSenha());

			connection.st.executeUpdate();
			connection.st.close();

			System.out.println("Investidor alterado com sucesso!");

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			System.out.println("Não foi possível alterar o Investidor");
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