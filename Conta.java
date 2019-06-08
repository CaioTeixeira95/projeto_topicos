class Conta {
	
	protected String banco;
	protected String numConta;
	protected String agencia;
	protected double saldo;
	protected int id_titular;
	
	public double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public String getNumConta() {
		return this.numConta;
	}

	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}
	
	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getBanco() {
		return this.banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public int getIdTitular() {
		return this.id_titular;
	}

	public void setIdTitular(int id_titular) {
		this.id_titular = id_titular;
	}
	
}