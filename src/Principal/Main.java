package Principal;

import Accounts.Account;
import Accounts.AccountDataBase;
import Services.Session;

public class Main {
    private static final AccountDataBase database = new AccountDataBase();
    private static final Session session = new Session();
    private static int numOptionsController = 0;

    public static void main(String[] args) {


        while (numOptionsController != 3) {
            //inicio de sessão
            database.insertData(new Account("mateus", "2409", 99));
            database.insertData(new Account("lucas", "3101", 10));
            numOptionsController = session.startSession();

            if (numOptionsController == 1) {
                //Cadastro de conta e registro no DB
                session.signUpAccount(database);

            } else if (numOptionsController == 2) {
                //Login de conta
                numOptionsController = session.signInAccount(database);

                if(numOptionsController == 1) {
                    // Menu principal da conta
                    while (numOptionsController != 4) {
                        numOptionsController = session.StartAccountMenu();

                        if(numOptionsController == 1){
                            // Verificar dados da conta
                            session.getActiveAccountData();
                        }else if(numOptionsController == 2) {
                            // Fazer pagamento
                            session.accountPaymentTransaction(database.getDataList());
                        }else if(numOptionsController == 3) {
                            // Verificar histórico de pagamentos
                            session.getTransactionList();
                        }else if (numOptionsController == 4){
                            session.accountExit();
                        }else {
                            System.out.println("Comando inválido! Tente novamente.");
                        }
                    }
                }

            } else if (numOptionsController != 3) {
                System.out.println("Comando inválido! Tente novamente.");
            }
        }
        System.out.println("Encerrando serviços. Obrigado por nos utilizar!");

    }
}