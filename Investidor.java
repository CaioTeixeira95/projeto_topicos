class Investidor {

	private int id;
	private String nome;
	private String cpf;
	private String email;
	private String senha;
	private int tipo;
	Conta contaBancaria;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setConta(Conta contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public Conta getConta() {
		return this.contaBancaria;
	}

	public void setTipoInvestidor(int tipo) {
		this.tipo = tipo;
	}

	public int getTipoInvestidor() {
		return this.tipo;
	}

}