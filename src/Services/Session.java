package Services;

import Accounts.Account;
import Accounts.AccountDataBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static java.lang.System.*;

public class Session implements ReaderServices {

    private Account activeAccount = null;
    Scanner reader = new Scanner(in);
    private int numOptionsController = 0;

    public int startSession() {
            out.println("""
                    
                    =============== Bem vindo ao Banco Vitalis! ===============
                    Para iniciar nossos serviços, escolha uma opção abaixo:
                    1. Cadastrar conta
                    2. Acessar conta
                    3. Finalizar serviço
                    """);
            return numOptionsController = ReaderServices.getValidOption(reader);

    }

    public int StartAccountMenu(){
        out.println(String.format("""
                
                ======== Seja bem vindo, %s! ========
                Selecione a opção que deseja:
                1. Verificar dados/saldo da conta
                2. Fazer pagamento para outra conta
                3. Verificar histórico de transferências
                4. Sair da conta atual
                """, activeAccount.getName()));
        return numOptionsController = ReaderServices.getValidOption(reader);
    }

    public void getActiveAccountData (){
        out.println("=============== Dados da sua conta ===============");
        out.println("-> Nome de usuário: "+ activeAccount.getName());
        out.println("-> ID para pagamentos: "+ activeAccount.getAccountId());
        out.println("-> Saldo atual: R$"+ activeAccount.getBalance());
        ReaderServices.ConfirmReturn(reader);
    }

    public void accountPaymentTransaction (ArrayList<Account> accountsDataBase){
        float transactionValue;
        int targetAccountId;
        Account selectedexistingAccount = null;
        //nextLine() para limpar buffer.
        reader.nextLine();
        numOptionsController = 0;

        while (numOptionsController != 2){
            out.println("Digite o ID de pagamentos da conta alvo.");
            targetAccountId = ReaderServices.getValidOption(reader);

            out.println("Digite o valor a ser enviado para a conta alvo.");
            transactionValue = ReaderServices.getValidValue(reader);

            // Verifica se está enviando valor pertinente.
            if (transactionValue > 0 && transactionValue <= activeAccount.getBalance()) {
                for (Account account : accountsDataBase) {
                    if (account.getAccountId() == targetAccountId) {
                        selectedexistingAccount = account;
                        break;
                    }
                }
                // Opera com base na conta que foi encontrada / Nega se é nula ou é o mesmo código da conta ativa
                    if (selectedexistingAccount != null && selectedexistingAccount.getAccountId() != activeAccount.getAccountId()){
                        out.println(String.format("Você está enviando R$%.2f para a conta de %s", transactionValue, selectedexistingAccount.getName()));
                        out.println("Se deseja confirmar esse pagamento, digite 1. Se quer retornar, digite 2.");
                        numOptionsController = ReaderServices.getValidOption(reader);

                        if (numOptionsController == 2) {
                            out.println("Cancelando operação de pagamento.");
                        }else
                        if (numOptionsController == 1) {
                            //Percorre DB e troca valor entre saldos;
                            activeAccount.setBalance(activeAccount.getBalance() - transactionValue);
                            selectedexistingAccount.setBalance(selectedexistingAccount.getBalance() + transactionValue);
                            System.out.println("Valor enviado com sucesso!");

                            // Define transação no histórico
                            selectedexistingAccount.addTransactionToHistory(activeAccount.getAccountId(), transactionValue, new Date(), "Recebido(a)");
                            activeAccount.addTransactionToHistory(targetAccountId, transactionValue, new Date(), "Enviado(a)");
                            numOptionsController = 2;
                        }
                    }else {
                            out.println("O ID informado é inválido!");
                            numOptionsController = 2;
                        }
            } else {
                out.println("O valor informado é inválido!");
                numOptionsController = 2;
            }
        }

    }

    public void getTransactionList () {
        out.println("============= Histórico de transações =============");
        activeAccount.viewTransactionHistory().forEach(transaction ->
                {
                    out.println("Tipo de transação: " + transaction.getTransactionType() + "  //  Data da operação: " + transaction.getTransactionDate());
                    out.println("ID da conta que recebeu/enviou: "+ transaction.getTargetAccount()+"  //  Valor da transação: R$" + transaction.getTransactionValue());
                    out.println("----------------------------------------------------------------------------------");
                }
        );
        ReaderServices.ConfirmReturn(reader);
    }

    public void signUpAccount(AccountDataBase database){
        String username;
        String password;

        while(numOptionsController != 3) {
            //nextLine() para limpar buffer.
            reader.nextLine();
            boolean accountAlreadyExists = false;

            out.println("Para cadastrar uma conta, digite primeiro seu nome de usuário para login");
            username = reader.nextLine();

            out.println("Agora, digite uma credencial para login");
            password = reader.nextLine();

            out.println("Digite 1 se estiver OK com as informações. Digite 2 para mudá-las. Digite 3 se quiser voltar a tela anterior. ");
            out.println("-> [Usuário: " + username+"]");
            out.println("-> [Senha: " + password+"]");
            numOptionsController = ReaderServices.getValidOption(reader);

            if (numOptionsController == 1) {

                for (Account account: database.getDataList()){
                    // Usuário verificado sem case sensitive
                    if (account.getName().equalsIgnoreCase(username)){
                        out.println("Este username já está sendo utilizado! Tente outro.");
                        accountAlreadyExists = true;
                    }
                    }

                if(!accountAlreadyExists){
                    // Cria e adiciona conta no DB.
                    Account newAccount = new Account(username, password);
                    database.insertData(newAccount);
                    out.println("Cadastro realizado com sucesso!");
                    numOptionsController = 3;
                }

            }else if (numOptionsController != 3 && numOptionsController != 2) {
                System.out.println("Comando inválido! Tente novamente.");
            }
            //Opção 2 recomeça registro de conta
        }
        //Opção 3 retorna ao menu principal
    }

    public int signInAccount(AccountDataBase database){
        //nextLine() para limpar buffer.
        reader.nextLine();
        String username;
        String password;

        out.println("Para logar em sua conta, insira seu nome de usuário");
        username = reader.nextLine();

        out.println("Agora, insira sua credencial de acesso");
        password = reader.nextLine();

        for(Account account: database.getDataList()){
            // Verifica usuário (username sem case sensitive)
            if (account.getName().equalsIgnoreCase(username) && account.getPassword().equals(password)){
                out.println("Login feito com sucesso. Seja bem vindo, " + username+"!");
                activeAccount = account;
                return numOptionsController = 1;
            }else {
                numOptionsController = 2;
            }
        }
        if(numOptionsController == 2) {
            out.println("Login falho. Verifique seus dados novamente");
        }
        return numOptionsController;

    }

    public void accountExit () {
        out.println(String.format("Deslogando de sua conta. Até mais, %s!", activeAccount.getName()));
        activeAccount = null;
    }

}
