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

    void writefile(String filepath, String filename, String input){
        boolean failed = false;
        BufferedWriter bw = null;
        FileWriter fw = null;

        filename = filename.replaceAll("[<>:\"\\\\\\|\\?\\*\\/]", "-");

        //Check if dir exists
        File directoryo = new File("./output/");
        if (!directoryo.exists()) {
            directoryo.mkdir();
        }
        File directory = new File(filepath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        if(!input.equals("") || !input.isEmpty()) {
            try {
                fw = new FileWriter(filepath + filename + ".txt");
                bw = new BufferedWriter(fw);
                bw.write(input);
            } catch (Exception e) {
                System.err.println("[ERROR][FileHandler] " + e);
            } finally {
                try {
                    bw.flush();
                    bw.close();
                } catch (Exception ex) {
                    System.err.println("[ERROR][FileHandler] " + ex);
                }
            }
        }
    }
}
