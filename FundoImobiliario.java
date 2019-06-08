import java.util.Scanner;

public class FundoImobiliario {

    public static void main(String[] args) {
        FundoImobiliario fii = new FundoImobiliario();
        fii.iniciar();       
    }
    
    private void iniciar() {
        
        Scanner s = new Scanner(System.in);
        String resp;
        
        System.out.println("*** Bem-vindo ao Fundo Imobiliario Fatec ***");
        System.out.println("Você possui uma conta ? [S/N]");
        
        do {
            
            resp = s.next();
            resp = resp.toLowerCase();
            
            if(!"s".equals(resp) && !"n".equals(resp)) {
                System.out.println("Valor inválido, digite 's' para Sim e 'n' para Não");
            }
            
        } while(!"s".equals(resp) && !"n".equals(resp));

        //s.close();
        
        if(resp.equals("n")) {
            this.criarConta();
        }
        else {
            this.acessarConta();
        }
        
    }

    private void menu(Investidor investidor) {

        // Classes
        Scanner s = new Scanner(System.in);

        // Variáveis
        String opcao;

        do {

            System.out.println();
            System.out.println("Olá " + investidor.getNome() + ", seja bem-vindo ao Fundo de Investimento Imobiliário FATEC!\n");
            System.out.println("a - Comprar cotas");
            System.out.println("b - Consultar suas cotas");
            System.out.println("c - Vender suas cotas");
            System.out.println("d - Minha Conta");
            System.out.println("e - Alterar Dados da Conta");
            if(investidor.getTipoInvestidor() == 1) {
                System.out.println("? - Ler cotações do dia");
            }
            System.out.println("s - Sair");
            System.out.print("Selecione uma opção: ");
            opcao = s.next();

            switch(opcao) {

                case "a":
                break;          

                case "b":
                break;            
                
                case "c":
                break;            

                case "d":
                break;            

                case "e":
                break;            

                case "f":
                break;

                case "s":
                    System.out.println("Até mais " + investidor.getNome());
                break;

                default:
                    System.out.println("Opção inválida!\n");
                break;

            }
            
        } while(!opcao.equals("s"));

    }
    
    private void criarConta() {
        
        // Informações do Investidor
        String nome;
        String cpf;
        String email;
        String senha;
        String senha2;

        // Informações Bancárias
        String banco;
		String numConta;
    	String agencia;
		double saldo;

    	// Classes
    	InvestidorDAO investidor = new InvestidorDAO();
    	ContaDAO conta = new ContaDAO();
        Scanner s = new Scanner(System.in);
        
        System.out.print("Digite seu nome: ");
        nome = s.nextLine();

        do {
            
            System.out.print("Digite seu CPF: ");
            cpf = s.nextLine();

            if(investidor.verificaSeExiste("cpf", cpf)) {
                System.out.println("CPF já cadastrado. Tente novamente!\n");
            }

        } while(investidor.verificaSeExiste("cpf", cpf));

        do {

            System.out.print("Digite seu e-mail: ");
            email = s.nextLine();

            if(investidor.verificaSeExiste("email", email)) {
                System.out.println("Esse e-mail já está em uso, tente novamente.\n");
            }

        } while(investidor.verificaSeExiste("email", email));

        do {

            System.out.print("Digite sua senha: ");
            senha = s.nextLine();

            System.out.print("Confirme sua senha: ");
            senha2 = s.nextLine();
            
            if(!senha2.equals(senha)) {
                System.out.println("As senhas não são iguais, tente novamente!\n");
            }

        } while(!senha2.equals(senha));

        System.out.print("Digite o seu banco: ");
        banco = s.nextLine();

        System.out.print("Digite o seu número da conta: ");
        numConta = s.nextLine();

        System.out.print("Digite o seu agência: ");
        agencia = s.nextLine();

        System.out.print("Digite o seu saldo: ");
        saldo = s.nextDouble();

        //s.close();

        // Setando dados do Investidor
        investidor.setNome(nome);
        investidor.setCpf(cpf);
        investidor.setEmail(email);
        investidor.setSenha(senha);
        investidor.setConta(conta);

        // Setando dados da Conta
        conta.setBanco(banco);
        conta.setNumConta(numConta);
        conta.setAgencia(agencia);
        conta.setSaldo(saldo);

        // Gravando no banco de dados
        investidor.salvarInvestidor();

        conta.setIdTitular(investidor.getId());
        conta.salvarConta();

        this.menu(investidor);
    }
    
    private void acessarConta() {
        
        // Classes
        InvestidorDAO investidor = new InvestidorDAO();
        ContaDAO conta = new ContaDAO();
        String resp;
        Scanner s = new Scanner(System.in);

        // Informações do Investidor
        String email;
        String senha;

        System.out.println("******* Faça o seu Login *******\n");
        
        System.out.println("Digite seu e-mail: ");
        email = s.nextLine();

        System.out.println("Digite sua senha: ");
        senha = s.nextLine();

        investidor = investidor.login(email, senha);

        if(investidor != null) {
            investidor.setConta(conta.buscaConta(investidor.getId()));
            this.menu(investidor);
        } 
        else {
            System.out.println("Conta não encontrada!!\n");
            System.out.println("Deseja Criar uma conta? [S/N]");
            do {

                resp = s.next();
                resp = resp.toLowerCase();
                
                if(!"s".equals(resp) && !"n".equals(resp)) {
                    System.out.println("Valor inválido, digite 's' para Sim e 'n' para Não");
                }
            
            } while(!"s".equals(resp) && !"n".equals(resp));

            if(resp.equals("s")) {
                this.criarConta();
            }
            else {
                this.acessarConta();
            }

        }
    }
    
}