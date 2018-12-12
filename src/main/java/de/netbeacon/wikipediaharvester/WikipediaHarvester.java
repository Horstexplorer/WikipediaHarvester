package de.netbeacon.wikipediaharvester;

import static java.lang.Thread.sleep;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class WikipediaHarvester {

    private static int count = 1;
    private static int size = 0;

    public static void main(String[] array) throws Exception{


        System.out.println();
        System.out.println("WikipediaHarvester");
        System.out.println(">> Wikipedia to text <<");
        System.out.println();
        File inputfile = new File("./input.txt");
        if (!inputfile.exists()) {
            index();
        }
        download();
    }

    static void index(){
        WikipediaIndex wi = new WikipediaIndex();
        wi.download("https://dumps.wikimedia.org/dewiki/latest/dewiki-latest-all-titles.gz", "dewiki-latest-all-titles.gz");
        wi.unzip("dewiki-latest-all-titles.gz","dewiki-latest-all-titles.txt");
        wi.tweak("dewiki-latest-all-titles.txt","input.txt");
        wi.clean("dewiki-latest-all-titles.gz", "dewiki-latest-all-titles.txt");
    }

    static void download(){
        DecimalFormat df = new DecimalFormat("000.000");
        int count = 1000;
        FileHandler fh = new FileHandler();

        try{
            //read input file
            System.out.println("[INFO] Array << File");
            String[] input = fh.readinputfile();
            size = input.length;
            System.out.println("[INFO] File contains "+size+" elements. Processing may take a while...");
            System.out.println();
            sleep(2500);

            List in = Arrays.asList(input);
            in.parallelStream().forEachOrdered((value)->
                    execute((String)value,fh,df)
            );
        }catch (Exception e){
            System.err.println();
            System.err.println("[ERROR][Main] "+e);
            e.printStackTrace();
        }
    }

    static void execute(String line, FileHandler fh, DecimalFormat df){
        double pers = (double)count/size;
        float progress = (float)pers*100;
        //get
        System.out.print("[INFO]["+df.format(progress)+"%] Wikipedia << "+line);
        WikipediaWorker ww = new WikipediaWorker();
        String wikistring = ww.reline(ww.clean(ww.getstring(line)));
        //Write to file
        fh.writefile("./output/"+line, wikistring);
        count++;
    }
}
