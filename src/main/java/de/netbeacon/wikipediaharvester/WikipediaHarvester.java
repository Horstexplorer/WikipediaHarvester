package de.netbeacon.wikipediaharvester;

import static java.lang.Thread.sleep;
import java.text.DecimalFormat;

public class WikipediaHarvester {

    public static void main(String[] array) throws Exception{

        DecimalFormat df = new DecimalFormat("0.00");

        System.out.println();
        System.out.println("WikipediaHarvester");
        System.out.println(">> Wikipedia to text <<");
        System.out.println();
        int count = 1;
        FileHandler fh = new FileHandler();
        //read input file
        System.out.println("[INFO] Array << File");
        String[] input = fh.readinputfile();
        int size = input.length;
        System.out.println("[INFO] File contains "+size+" elements.");
        System.out.println();
        sleep(2500);


        for(String line:input){
            //Calculate progress
            double pers = (double)count/size;
            float progress = (float)pers*100;
            //get
            System.out.print("[INFO]["+df.format(progress)+"%] Wikipedia << "+line);
            WikipediaWorker ww = new WikipediaWorker();
            String wikistring = ww.reline(ww.clean(ww.getstring(line)));

            //Write to file
            fh.writefile(line, wikistring);
            count++;
        }
    }

}
