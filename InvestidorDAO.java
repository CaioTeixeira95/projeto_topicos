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
        Conexao conexao = new Conexao();
        String sql = "INSERT INTO investidor (nome, cpf, email, senha) VALUES (?, ?, ?, ?)";
        conexao.insert(sql, this);
    }

    public boolean verificaSeExiste(String campo, String valor) {
        Conexao conexao = new Conexao();
        String sql = "SELECT " + campo + " FROM investidor WHERE " + campo + " = '" + valor + "'";
        return conexao.selectVerificar(sql);
    }

    public boolean login(String email, String senha) {
        Conexao conexao = new Conexao();
        String sql = "SELECT * FROM investidor WHERE email = '" + email + "' AND senha = '" + senha + "'";
    }
    
}