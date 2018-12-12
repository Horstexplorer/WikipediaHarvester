package de.netbeacon.wikipediaharvester;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class WikipediaIndex {

    void download(String url, String file){
        System.out.println();
        System.out.print("[INFO] Downloading file: "+file+" | ");
        int count = 0;
        int bytez = 0;
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOS = new FileOutputStream(file)) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
                if(count == 1024){
                    count = 0;
                    System.out.print("#");
                }
                count++;
                bytez++;
            }
            System.out.println(" | ~"+bytez+" Byte");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void unzip(String fileinput, String fileoutput){
        byte[] buffer = new byte[1024];
        try{
            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(fileinput));
            FileOutputStream out = new FileOutputStream(fileoutput);
            int len;
            int count = 0;
            int bytez = 0;
            System.out.print("[INFO] Unzip file: "+fileinput+" | ");
            while ((len = gzis.read(buffer))>0){
                out.write(buffer, 0, len);
                if(count == 5012){
                    count = 0;
                    System.out.print("#");
                }
                count++;
                bytez++;
            }
            System.out.println(" | ~"+bytez+" Byte");
            gzis.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void tweak(String inputfile, String outputfile){
        String inputstring;
        System.out.print("[INFO] Tweaking file... ");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputfile));
            String         line = null;
            StringBuilder  stringBuilder = new StringBuilder();
            String         ls = System.getProperty("line.separator");

            try {
                while((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                }

                inputstring = stringBuilder.toString();
            } finally {
                reader.close();
            }
            inputstring = inputstring.substring(inputstring.indexOf("\n"+1));
            inputstring = inputstring.replaceAll("(?m)^\\t*\\n+", "");
            inputstring = inputstring.replaceAll("(?m)^\\d*\\s", "");

            FileHandler fh = new FileHandler();
            fh.writefile("input",inputstring);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    void clean(String filea, String fileb){
        System.out.print("[INFO] Cleaning... ");
        File gz = new File(filea);
        File txt = new File(fileb);
        gz.delete();
        txt.delete();
        System.out.println("<< Done");
    }
}
