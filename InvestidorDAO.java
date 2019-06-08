import java.util.Scanner;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

	public void alterarInvestidor() {

		Scanner s = new Scanner(System.in);

		// Variáveis
		String novoNome = "";
		String novoCpf = "";
		String novoEmail = "";
		String novaSenha = "";
		String novaSenha2 = "";
		boolean valido = false;

		System.out.println("Alterar dados do investidor. As informações que deseja manter basta deixar em branco.\n");
		System.out.println("****** Dados do Atuais do Investidor *******");
		System.out.println("Nome: " + this.getNome());
		System.out.println("CPF: " + this.getCpf());
		System.out.println("E-mail: " + this.getEmail() + "\n\n");

		System.out.print("Novo nome: ");
		novoNome = s.nextLine();

		if(!novoNome.isEmpty()) {
			this.setNome(novoNome);
		}

		do {
			
			System.out.print("Novo CPF: ");
			novoCpf = s.nextLine();

			if(!novoCpf.isEmpty()) {

				if(this.verificaSeExiste("cpf", novoCpf)) {
					System.out.println("CPF já cadastrado. Tente novamente!\n");
				}
				else if(!this.validaCpf(novoCpf)) {
					System.out.println("CPF inválido. Tente novamente!\n");
				}
				else {
					this.setCpf(novoCpf);
					valido = true;
				}

			}
			else {
				valido = true;
			}

		} while(!valido);

		valido = false;
		do {

			System.out.print("Digite seu e-mail: ");
			novoEmail = s.nextLine();

			if(!novoEmail.isEmpty()) {
				if(this.verificaSeExiste("email", novoEmail)) {
					System.out.println("Esse e-mail já está em uso, tente novamente.\n");
				}
				else if(!this.validaEmail(novoEmail)) {
					System.out.println("E-mail inválido, tente novamente.\n");
				}
				else {
					this.setEmail(novoEmail);
					valido = true;
				}
			}
			else {
				valido = true;
			}

		} while(!valido);

		valido = false;
		do {

			System.out.print("Nova senha: ");
			novaSenha = s.nextLine();

			if(!novaSenha.isEmpty()) {

				System.out.print("Confirme sua nova senha: ");
				novaSenha2 = s.nextLine();

				if (!novaSenha.equals(novaSenha2)) {
					System.out.println("As senhas não são iguais, tente novamente!\n");
				}
				else {
					this.setSenha(novaSenha);
					valido = true;
				}

			}
			else {
				valido = true;
			}

		} while(!valido);

		String sql = "UPDATE investidor SET nome = ?, cpf = ?, email = ?, senha = ? WHERE id = ?";

		this.update(sql, this);
	}

	public boolean validaCpf(String cpf) {
		String regex = "(?!(\\d)\\1{10})\\d{11}";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(cpf);
		return mat.matches();
	}

	public boolean validaEmail(String email) {
		String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
		Pattern pat = Pattern.compile(regex);
		Matcher mat = pat.matcher(email);
		return mat.matches();
	}

	public boolean verificaSeExiste(String campo, String valor) {
		Conexao conexao = new Conexao();
		String sql = "SELECT COUNT(*) AS " + campo + " FROM investidor WHERE " + campo + " = '" + valor + "'";
		return conexao.selectVerificar(sql, campo);
	}

	public InvestidorDAO login(String email, String senha) {
		
		Conexao connection = new Conexao();
		InvestidorDAO investidor = new InvestidorDAO();

		try {
			
			String sql = "SELECT * FROM investidor WHERE email = '" + email + "' AND senha = '" + senha + "'";

			connection.getConnection();
			connection.stmt = connection.conn.createStatement();
			connection.rs = connection.stmt.executeQuery(sql);

			if(connection.rs.next()) {
				investidor.setId(connection.rs.getInt("id"));
				investidor.setNome(connection.rs.getString("nome"));
				investidor.setCpf(connection.rs.getString("cpf"));
				investidor.setEmail(email);
				investidor.setSenha(senha);
				investidor.setTipoInvestidor(connection.rs.getInt("tipo"));
			}

		} catch(SQLException ex) {
			System.out.println("Erro na instrução SQL: " + ex.getMessage());
			System.out.println("Não foi possível fazer o login.");
			ex.printStackTrace();
		} finally {
			try {
				connection.stmt.close();
				connection.rs.close();
				connection.conn.close();
			} catch(Exception exception) {
				exception.getMessage();
				exception.printStackTrace();
			}

			return investidor;
		}
	}

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

			sql = "SELECT MAX(id) AS id FROM investidor";
			connection.stmt = connection.conn.createStatement();
			connection.rs = connection.stmt.executeQuery(sql);

			if (connection.rs.next()) {
				investidor.setId(connection.rs.getInt("id"));
			}

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
			connection.st.setInt(5, investidor.getId());

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