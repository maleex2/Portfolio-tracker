package Model;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class AccountManager {

    private AccountManagerSaveClass accounts=new AccountManagerSaveClass();
    private static String FILE_NAME="accountManager.am";

    public AccountManager(){
        LoadFromFile();
    }


    private void SaveToFile() {
        try{
            FileWriter fw = new FileWriter(FILE_NAME, false); // append to text file
            Gson gson = new Gson();
            fw.write(gson.toJson(accounts));
            fw.flush();
            fw.close();
            //Even after closing all the streams, the file wouldn't delete on request, but calling garbage collection helps
           System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadFromFile(){
        Gson gson=new Gson();
        try{
        FileReader reader=new FileReader(FILE_NAME);
        accounts= gson.fromJson(reader,AccountManagerSaveClass.class);
        if(accounts==null){
            accounts=new AccountManagerSaveClass();
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Account registerAccount(String name) {
        for (String a : accounts.getList()) {
            if (a.toUpperCase().equals(name.toUpperCase())) {
                return null;
            }
        }
        accounts.getList().add(name);
        SaveToFile();
        return new Account(name);
    }

    public Account loginAccount(String name){

        for (String a : accounts.getList()) {
            if (a.toUpperCase().equals(name.toUpperCase())) {
                return new Account(a);
            }
        }
        return null;
    }
}
