package de.netbeacon.wikipediaharvester;

import static java.lang.Thread.sleep;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WikipediaHarvester {

    private static int count = 1;
    private static int size = 0;
    private static long averageduration = 0;


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
            System.out.println("[INFO][Remaining: ~~:~~:~~][ 0,000% ]");
            List in = Arrays.asList(input);
            in.parallelStream().forEach((value)->
                {
                    try{
                        execute((String)value,fh,df);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            );
        }catch (Exception e){
            System.err.println();
            System.err.println("[ERROR][Main] "+e);
            e.printStackTrace();
        }
    }

    static void execute(String line, FileHandler fh, DecimalFormat df){
        //progress calculations
        long remainingtime = 0;
        long currentms = System.currentTimeMillis();
        remainingtime = averageduration*((size-count));
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(remainingtime),
                TimeUnit.MILLISECONDS.toMinutes(remainingtime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remainingtime)),
                TimeUnit.MILLISECONDS.toSeconds(remainingtime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingtime)));
        double pers = (double)count/size;
        float progress = (float)pers*100;

        //real work
        WikipediaWorker ww = new WikipediaWorker();
        String wikistring = ww.reline(ww.clean(ww.getstring(line)));
        //Write to file
        fh.writefile("./output/",line, wikistring);

        //print status every 200 processed
        if((count%200)==0){
            System.out.println("[INFO][Remaining: "+hms+"][ "+df.format(progress)+"% ]");
        }
        count++;
        averageduration = (((averageduration*count)+(System.currentTimeMillis()-currentms))/(count+1));
    }
}
