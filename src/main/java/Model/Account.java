package Model;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Account {
    private String username;
    private ArrayList <Portfolio> folios;
    public Account (String username){
        this.username=username;
        this.folios=new ArrayList<>();
    }
    public boolean addPortfolio(Portfolio folio){
        if(!this.folios.contains(folio)) {
            this.folios.add(folio);
            return true;
        }else{
            return false;
        }
    }
    public ArrayList<Portfolio> getPortfolioList(){
        return this.folios;
    }

    public boolean isPortfolioSaved(Portfolio portfolio) {
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            Optional<String> result = walk.map(Path::toString)
                    .filter(f -> f.contains(username + "_" + portfolio.getName() + ".saveFile"))
                    .findFirst();
            walk.close();
            System.gc();
       return result.isPresent();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
        public void savePortfolio(String folioName) throws Exception {
        Portfolio toSave=null;
        for (Portfolio folio: folios) {
            if(folio.getName().equals(folioName)){
                toSave=folio;
                break;
            }
        }
        if(toSave==null){
            throw new Exception("No such portfolioName found!");
        }else {

            try {
                String filename = username + "_" + toSave.getName() + ".saveFile";
                FileWriter fw = new FileWriter(filename, false); // append to text file
                Gson gson = new Gson();
                fw.write(gson.toJson(toSave));
                fw.flush();
                fw.close();
                //Even after closing all the streams, the file wouldn't delete on request, but calling garbage collection helps
                System.gc();

            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());
            }
        }

    }


    public void loadPortfolios() {
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            List<String> result = walk.map(Path::toString)
                    .filter(f -> f.contains(".saveFile"))
                    .filter(f-> f.contains(this.username))
                    .collect(Collectors.toList());
            result.forEach(System.out::println);
            walk.close();
            //Even after closing all the streams, the file wouldn't delete on request, but calling garbage collection helps
            System.gc();
            Gson gson=new Gson();
            for(String folioName:result){
                FileReader reader=new FileReader(folioName);
                Portfolio portfolio= gson.fromJson(reader,Portfolio.class);
                addPortfolio(portfolio);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean removePortfolio(Portfolio portfolio) {

        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("user.dir")))) {
            Optional<String> result = walk.map(Path::toString)
                    .filter(f -> f.contains(username + "_" + portfolio.getName() + ".saveFile"))
                    .findFirst();
            walk.close();
            //Even after closing all the streams, the file wouldn't delete on request, but calling garbage collection helps

            System.gc();
            if (result.isPresent()) {

                File file = new File(result.get());
                try {
                    //also, file.canWrite(), file.canRead(), file.canExecute() all return true, but without System.gc after every stream-> delete() returns false
                    folios.remove(portfolio);
                    int limit = 20; //Only try for 5 seconds, for safety
                    while(!file.delete() && limit > 0){
                        synchronized(this){
                            try {
                                this.wait(250); //Wait for 250 milliseconds
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        limit--;
                    }
                   return true;
                } catch (SecurityException e) {
                    folios.remove(portfolio);
                    return false;
                }
            }else{
                folios.remove(portfolio);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }






    public String getUsername(){
        return this.username;
    }

}