import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class Investidor {

    private String nome;
    private String cpf;
    private String email;
    private String senha;
    Conta contaBancaria;

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