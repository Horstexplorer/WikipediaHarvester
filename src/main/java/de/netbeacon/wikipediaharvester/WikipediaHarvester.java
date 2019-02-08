package de.netbeacon.wikipediaharvester;

import static java.lang.Thread.sleep;

import java.io.File;
import java.text.DecimalFormat;

public class WikipediaHarvester {

    private static int count = 1;
    private static int size = 0;


    public static void main(String[] array){
        System.out.println(">> Wikipedia to text <<");
        System.out.println();
        File inputfile = new File("./input.txt");
        if (!inputfile.exists()) {
            index();
        }
        download();
    }

    private static void index(){
        WikipediaIndex wi = new WikipediaIndex();
        wi.download("https://dumps.wikimedia.org/dewiki/latest/dewiki-latest-all-titles.gz", "dewiki-latest-all-titles.gz");
        wi.unzip("dewiki-latest-all-titles.gz","dewiki-latest-all-titles.txt");
        wi.tweak("dewiki-latest-all-titles.txt","input.txt");
        wi.clean("dewiki-latest-all-titles.gz", "dewiki-latest-all-titles.txt");
    }

    private static void download(){
        DecimalFormat df = new DecimalFormat("0.000");
        FileHandler fh = new FileHandler();

        try{
            //read input file
            System.out.println("[INFO] Array << File");
            String[] input = fh.readinputfile();
            size = input.length;
            System.out.println("[INFO] File contains "+size+" elements. Processing may take a while...");
            sleep(2500);
            System.out.println("[INFO] Processing started.");
            System.out.println();
            System.out.println("[INFO][ 0,000% ]");

            for (String l : input){
                execute(l,fh,df);
            }

        }catch (Exception e){
            System.err.println();
            System.err.println("[ERROR][Main] "+e);
            e.printStackTrace();
        }
    }

    private static void execute(String line, FileHandler fh, DecimalFormat df) throws Exception{

        double pers = (double)count/size;
        float progress = (float)pers*100;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //real work
                WikipediaWorker ww = new WikipediaWorker();
                String wikistring = ww.reline(ww.clean(ww.getstring(line, "de")));
                //Write to file
                fh.writefile("./output/"+lien(line).charAt(0)+"/",line, wikistring);
            }
        }).start();

        //print status every 1000 processed
        if((count%1000)==0){
            System.out.println("[INFO][ "+df.format(progress)+"% ]");
            Thread.sleep(500);
        }
        count++;
    }

    private static String lien(String line){
        return line.replaceAll("[<>:\"\\\\\\|\\?\\*\\/]", "-");
    }
}
