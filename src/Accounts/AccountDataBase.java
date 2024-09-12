package Accounts;

import java.util.ArrayList;

public class  AccountDataBase {

    private  ArrayList<Account> datalist = new ArrayList<>();

    public void insertData (Account account){
        this.datalist.add(account);
    }

    public  ArrayList<Account> getDataList (){
        return this.datalist;
    }

}
