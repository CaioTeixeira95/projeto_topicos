import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.text.DecimalFormat;

public class CotaDAO extends Cota {

    public void comprarCota(InvestidorDAO investidor) {

        Conexao connection = new Conexao();
        ArrayList<Cota> cotas;
        Scanner s = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#,###.00");

        try {

            cotas = this.cotas(investidor, 1);

            if (cotas.size() > 0) {

                System.out.println("+============================================================================================================+");
                System.out.println("|       Nº COTA      |       CÓDIGO        |       IMÓVEL       |        VALOR       |        VENDEDOR       |");
                System.out.println("+============================================================================================================+");

                int i = 1;
                for (Cota c : cotas) {
                    System.out.println("|       " + i++ + "     -      " + c.getCodigo() + "       -      " + c.getNome() + "       -       " + df.format(c.getValor()) + "      -       " + investidor.getNomeVendedor(c.getIdTitular()) + "    |");
                    System.out.println("+============================================================================================================+");
                }

                try {

                    System.out.print("Digite o número da cota que deseja comprar: ");
                    int c = s.nextInt();

                    if (c <= cotas.size() && c > -1) {

                        if (investidor.contaBancaria.getSaldo() - cotas.get(c - 1).getValor() > 0) {

                            connection.getConnection();

                            String sql = "SELECT COALESCE(id_titular, 0) AS id_titular FROM cotas WHERE id = " + cotas.get(c - 1).getId();
                            connection.stmt = connection.conn.createStatement();
                            connection.rs = connection.stmt.executeQuery(sql);

                            if (connection.rs.next()) {
                                int id_titular = connection.rs.getInt("id_titular");
                                sql = "UPDATE conta SET saldo = saldo + " + cotas.get(c - 1).getValor() + " WHERE id_titular = " + id_titular;
                                connection.stmt = connection.conn.createStatement();
                                connection.stmt.executeUpdate(sql);
                            }

                            sql = "UPDATE cotas SET id_titular = " + investidor.getId() + ", vendendo = false WHERE id = " + cotas.get(c - 1).getId();
                            connection.stmt = connection.conn.createStatement();
                            connection.stmt.executeUpdate(sql);

                            investidor.contaBancaria.updateSaldo(cotas.get(c - 1).getValor() * (-1));

                        }
                        else {
                            System.out.println("Saldo insuficiente para realizar a operação");
                        }

                    } else {
                        System.out.println("Valor inválido!\n");
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("Digite apenas números.\nErro: " + ex.getMessage());
                }
            }
            else {
                System.out.println("Não há cotas para serem compradas!\n");
            }

        } catch (SQLException e) {
            System.out.println("Falhou: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(connection.stmt != null) connection.stmt.close();
                if(connection.conn != null) connection.conn.close();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }

    }

    public void listarCota(InvestidorDAO investidor) {

        ArrayList<Cota> cotas;
        DecimalFormat df = new DecimalFormat("#,###.00");

        cotas = this.cotas(investidor, 2);

        if (cotas.size() > 0) {

            System.out.println("+============================================================================================================+");
            System.out.println("|       Nº COTA      |       CÓDIGO        |       IMÓVEL       |        VALOR       |        VENDENDO ?       |");
            System.out.println("+============================================================================================================+");

            int i = 1;
            String vend;
            for (Cota c : cotas) {
                vend = c.getVendendo() ? "Sim" : "Não";
                System.out.println("|       " + i++ + "     -      " + c.getCodigo() + "     -      " + c.getNome() + "      -       " + df.format(c.getValor()) + "      -       " + vend.toUpperCase() + "    |");
                System.out.println("+============================================================================================================+");
            }

        }
        else {

            Scanner s = new Scanner(System.in);
            String resp;

            System.out.println("Você não tem nenhuma cota comprada! Deseja comprar ? [S/N]");

            do {

                resp = s.next();
                resp = resp.toUpperCase();

                if (!resp.equals("S") && !resp.equals("N")) {
                    System.out.println("Valor inválido, digite S para sim e N para não!\n");
                }

            } while (!resp.equals("S") && !resp.equals("N"));

            if (resp.equals("S")) {
                this.comprarCota(investidor);
            }

        }

    }

    public void venderCota(InvestidorDAO investidor) {

        Conexao connection = new Conexao();
        ArrayList<Cota> cotas;
        Scanner s = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#,###.00");

        try {

            cotas = this.cotas(investidor, 3);

            if (cotas.size() > 0) {

                System.out.println("+============================================================================================================+");
                System.out.println("|       Nº COTA      |       CÓDIGO        |       IMÓVEL       |        VALOR       |        VENDENDO ?       |");
                System.out.println("+============================================================================================================+");

                int i = 1;
                String vend;
                for (Cota c : cotas) {
                    vend = c.getVendendo() ? "Sim" : "Não";
                    System.out.println("|       " + i++ + "     -      " + c.getCodigo() + "     -      " + c.getNome() + "      -       " + df.format(c.getValor()) + "      -       " + vend.toUpperCase() + "    |");
                    System.out.println("+============================================================================================================+");
                }

                try {

                    System.out.print("Digite o número da cota que deseja vender: ");
                    int c = s.nextInt();

                    if (c <= cotas.size() && c > -1) {

                        connection.getConnection();
                        String sql = "UPDATE cotas SET vendendo = true WHERE id = " + cotas.get(c - 1).getId();
                        connection.stmt = connection.conn.createStatement();
                        connection.stmt.executeUpdate(sql);

                    } else {
                        System.out.println("Valor inválido!\n");
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("Digite apenas números.\nErro: " + ex.getMessage());
                }

            }
            else {
                System.out.println("Não há cotas para serem vendidas!\n");
            }

        } catch (SQLException e) {
            System.out.println("Falhou: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(connection.stmt != null) connection.stmt.close();
                if(connection.conn != null) connection.conn.close();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }



    }

    public ArrayList<Cota> cotas(InvestidorDAO investidor, int option) {

        Conexao connection = new Conexao();
        ArrayList<Cota> cotas = new ArrayList<>();

        try {

            String where = "";

            if (option == 1) {
                where += "id_titular IS NULL OR vendendo = true";
            }
            else if (option == 2) {
                where += "id_titular = " + investidor.getId();
            }
            else if (option == 3) {
                where += "id_titular = " + investidor.getId() + " AND vendendo = false";
            }

            String sql = "SELECT * FROM cotas WHERE " + where + " ORDER BY valor";

            connection.getConnection();
            connection.stmt = connection.conn.createStatement();
            connection.rs = connection.stmt.executeQuery(sql);

            while (connection.rs.next()) {

                Cota cota = new Cota();

                cota.setId(connection.rs.getInt("id"));
                cota.setCodigo(connection.rs.getString("codigo"));
                cota.setIdTitular(connection.rs.getInt("id_titular"));
                cota.setNome(connection.rs.getString("nome"));
                cota.setValor(connection.rs.getDouble("valor"));
                cota.setVendendo(connection.rs.getBoolean("vendendo"));

                cotas.add(cota);

            }

            return cotas;

        } catch (SQLException e) {
            System.out.println("Falhou: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.rs.close();
                connection.stmt.close();
                connection.conn.close();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }

        return null;

    }

    public void importarCotacoes() {

        Conexao connection = new Conexao();
        int erro = 0;

        try {

            FileReader arq = new FileReader("/home/caio/IdeaProjects/fundo_imobiliario/out/production/fundo_imobiliario/br/com/fundo_imobiliario/cotacoes.txt");
            BufferedReader bf = new BufferedReader(arq);
            Map<Integer, String> cotas = new HashMap<>();
            Map<String, String> cota_bd = new HashMap<>();
            String[] campos = {"codigo", "nome", "valor", "vendendo"};

            String linha = bf.readLine();

            int i = 0;
            while (linha != null) {
                cotas.put(i++, linha);
                linha = bf.readLine();
            }

            int g;
            for (i = 0; i < cotas.size(); i++) {

                g = 0;
                for (String c : cotas.get(i).split(" - ")) {
                    cota_bd.put(campos[g], c.trim());
                    g++;
                }

                String sql = "INSERT INTO cotas (id_titular, codigo, nome, valor, vendendo) VALUES (NULL, '" + cota_bd.get("codigo") + "', '" + cota_bd.get("nome") + "', " + cota_bd.get("valor") + ", " + cota_bd.get("vendendo") + ")";

                try {

                    connection.getConnection();
                    connection.stmt = connection.conn.createStatement();
                    connection.stmt.executeUpdate(sql);

                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                    e.printStackTrace();
                    erro = 1;
                } finally {
                    try {
                        connection.stmt.close();
                        connection.conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
            erro = 2;
        }

        if (erro == 0)
            System.out.println("Importação realizada com sucesso!");
        else
            System.out.println("Ocorreu um erro para realizar a importação!");

    }

}
