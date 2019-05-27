class ContaDAO extends Conta {

	public void depositar(double valor) {
        this.saldo += valor;
    }

    public void salvarConta() {
        Conexao conexao = new Conexao();
        String sql = "INSERT INTO conta(banco, num_conta, agencia, cpf_titular, saldo) VALUES (?, ?, ?, ?, ?)";
        conexao.insert(sql, this);
    }
    
}