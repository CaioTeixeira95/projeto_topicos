class Conta {
    
    protected String banco;
    protected String numConta;
    protected String agencia;
    protected double saldo;
    protected String cpf_titular;
    
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

    public String getCpfTitular() {
        return this.cpf_titular;
    }

    public void setCpfTitular(String cpf) {
        this.cpf_titular = cpf;
    }
    
    public void depositar(double valor) {
        this.saldo += valor;
    }

    public void salvarConta() {
        Conexao conexao = new Conexao();
        String sql = "INSERT INTO conta(banco, num_conta, agencia, cpf_titular, saldo) VALUES (?, ?, ?, ?, ?)";
        conexao.insert(sql, this);
    }
    
}