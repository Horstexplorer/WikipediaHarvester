package de.netbeacon.wikipediaharvester;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileHandler {

    String[] readinputfile(){

        List<String> lines = new ArrayList<String>();
        try{
            File inputfile = new File("./input.txt");
            if (!inputfile.exists()) {
                inputfile.createNewFile();
            }

            FileReader fileReader = new FileReader("./input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
            bufferedReader.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return lines.toArray(new String[lines.size()]);
    }

    void writefile(String filename, String input){

        BufferedWriter bw = null;
        FileWriter fw = null;

        //Check if dir exists
        File directory = new File("./output/");
        if (!directory.exists()) {
            directory.mkdir();
        }
        filename = filename.replaceAll("[<>:\"\\\\\\|\\?\\*]", "-");
        if(!input.equals("") || !input.isEmpty()){
            try{
                fw = new FileWriter(filename+".txt");
                bw = new BufferedWriter(fw);
                bw.write(input);
            }catch (Exception e){
                System.out.println(" << Failed");
            }finally {
                try{
                    bw.flush();
                    bw.close();
                }catch (Exception ex){
                    System.out.println(" << Failed");
                }
                System.out.println(" << Done");
            }
        }
    }
}
