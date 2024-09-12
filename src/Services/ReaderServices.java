package Services;

import java.util.Scanner;

public interface ReaderServices {

     static int getValidOption(Scanner reader){
        while(!reader.hasNextInt()){
            System.out.println("Comando inválido. Tente novamente.");
            reader.next();
        }
        return reader.nextInt();
    }

     static float getValidValue(Scanner reader){
        while(!reader.hasNextFloat()){
            System.out.println("Valor inválido. Tente novamente.");
            reader.next();
        }
        return reader.nextFloat();
    }

     static void ConfirmReturn(Scanner reader){

            System.out.println("[Envie qualquer caractere para retornar]");
            reader.next();
    }

}
