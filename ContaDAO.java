import java.sql.SQLException;
import java.util.Scanner;

class ContaDAO extends Conta {

    public void updateSaldo(double valor) {

        Conexao connection = new Conexao();

        try {

            String sql = "UPDATE conta SET saldo = saldo + (" + valor + ") WHERE id_titular = " + this.getIdTitular();

            connection.getConnection();
            connection.stmt = connection.conn.createStatement();
            connection.stmt.executeUpdate(sql);

            this.saldo += valor;

            System.out.println("Saldo atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Falhou: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.stmt.close();
                connection.conn.close();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }

    }

    public void salvarConta() {
        String sql = "INSERT INTO conta(banco, num_conta, agencia, id_titular, saldo) VALUES (?, ?, ?, ?, ?)";
        this.insert(sql, this);
    }

    public void alterarConta(InvestidorDAO investidor) {

        Scanner s = new Scanner(System.in);

        // Variáveis
        String novoBanco;
        String novaNumConta;
        String novaAgencia;

        System.out.println("Alterar dados da Conta. As informações que deseja manter basta deixar em branco.\n");
        System.out.println("****** Dados do Atuais da Conta Bancária *******");
        System.out.println("Banco: " + investidor.contaBancaria.getBanco());
        System.out.println("Nº Conta: " + investidor.contaBancaria.getNumConta());
        System.out.println("Agência: " + investidor.contaBancaria.getAgencia() + "\n\n");

        System.out.print("Novo banco: ");
        novoBanco = s.nextLine();

        if(!novoBanco.isEmpty()) {
            investidor.contaBancaria.setBanco(novoBanco);
        }

        System.out.println("Nova número de conta: ");
        novaNumConta = s.nextLine();

        if (!novaNumConta.isEmpty()) {
            investidor.contaBancaria.setNumConta(novaNumConta);
        }

        System.out.println("Nova agência: ");
        novaAgencia = s.nextLine();

        if (!novaAgencia.isEmpty()) {
            investidor.contaBancaria.setAgencia(novaAgencia);
        }

        if(!novoBanco.isEmpty() || !novaNumConta.isEmpty() || !novaAgencia.isEmpty()) {
            String sql = "UPDATE conta SET banco = ?, num_conta = ?, agencia = ? WHERE id = ?";
            this.update(sql, investidor.contaBancaria);
        }

    }

    public ContaDAO buscaConta(int id_titular) {

        Conexao connection = new Conexao();
        ContaDAO conta = new ContaDAO();

        try {

            String sql = "SELECT * FROM conta WHERE id_titular = " + id_titular;

            connection.getConnection();
            connection.stmt = connection.conn.createStatement();
            connection.rs = connection.stmt.executeQuery(sql);

            if(connection.rs.next()) {
                conta.setId(connection.rs.getInt("id"));
                conta.setSaldo(connection.rs.getDouble("saldo"));
                conta.setNumConta(connection.rs.getString("num_conta"));
                conta.setAgencia(connection.rs.getString("agencia"));
                conta.setBanco(connection.rs.getString("banco"));
                conta.setIdTitular(id_titular);
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
        }

        return conta;

    }

    public void insert(String sql, Conta conta) {

        Conexao connection = new Conexao();

        try {

            connection.getConnection();
            connection.st = connection.conn.prepareStatement(sql);
            connection.st.setString(1, conta.getBanco());
            connection.st.setString(2, conta.getNumConta());
            connection.st.setString(3, conta.getAgencia());
            connection.st.setInt(4, conta.getIdTitular());
            connection.st.setDouble(5, conta.getSaldo());

            connection.st.executeUpdate();

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

    public void delete() {

        Conexao connection = new Conexao();

        try {

            connection.getConnection();
            String sql = "DELETE FROM conta WHERE id = " + this.getId();
            connection.stmt = connection.conn.createStatement();
            connection.stmt.executeUpdate(sql);

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
            connection.st.setInt(4, conta.getIdTitular());

            connection.st.executeUpdate();

            System.out.println("Conta alterada com sucesso!");

        } catch(SQLException ex) {
            System.out.println("Erro na instrução SQL: " + ex.getMessage());
            System.out.println("Não foi possível alterar a Conta");
            ex.printStackTrace();
        } finally {
            try {
                connection.st.close();
                connection.conn.close();
            } catch(Exception exception) {
                exception.printStackTrace();
            }
        }

    }

}