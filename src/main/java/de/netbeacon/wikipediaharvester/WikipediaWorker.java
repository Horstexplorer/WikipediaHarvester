package de.netbeacon.wikipediaharvester;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WikipediaWorker {

    String getstring(String word){
        String resultstring = "";
        String url = "https://de.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=xml&&titles="+word;
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());

            doc.getDocumentElement().normalize();
            if(doc.getElementsByTagName("rev") != null){
                NodeList nList = doc.getElementsByTagName("rev");
                resultstring = nList.item(0).getTextContent();
                System.out.print(" << Found");
            }


        }catch (Exception e){
            System.out.println(" << Not Found");
        }
        return resultstring;
    }

    String clean(String input){

        //Müll entfernen

        input = input.replaceAll("&.*;", "");
        input = input.replaceAll("\\*", "");
        input = input.replaceAll("\\'", "");
        input = input.replaceAll("\\(", "");
        input = input.replaceAll("\\)", "");
        input = input.replaceAll("\\[", "");
        input = input.replaceAll("\\]", "");

        input = input.replaceAll("(www|http:|https:)+[^\\s]+[\\w]", "");


        input = input.replaceAll("\\<\\!\\-\\-(.*?)\\-\\-\\>", "");
        input = input.replaceAll("(?s)<gallery>.*?<\\/gallery>", "");
        input = input.replaceAll("(?s)<references>.*?<\\/references>", "");
        input = input.replaceAll(".*Datei:.*", "");
        input = input.replaceAll("\\{.*\\}", "");
        input = input.replaceAll("\\<.*\\>", "");
        input = input.replaceAll("(?s)\\{.*?\\}", "");

        //Sonderzeichen umwandeln
        input = input.replaceAll("[ä|Ä]", "ae");
        input = input.replaceAll("[ö|Ö]", "oe");
        input = input.replaceAll("[ü|Ü]", "ue");
        input = input.replaceAll("[ß]", "sz");

        //replace numbers

        Pattern pattern = Pattern.compile("(?m)\\d+");
        for(int i = 0; i < 5;i++){ //should be enough to convert everything.
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                NumberToText ntt = new NumberToText();
                String number;
                if(Long.parseLong(matcher.group())<=Integer.MAX_VALUE){
                    number = ntt.intToText(Integer.parseInt(matcher.group()));
                }else{
                    number = "null";
                }
                input = input.replaceAll(matcher.group(), number);
            }
        }


        //replace others
        input = input.replaceAll("[^\\s\\w\\. \\n\\r]", "");


        return input;
    }

    String reline(String input){


        input = input.replaceAll("(?m)((?<=[a-zA-Z]{4})|(?<=\\ ))\\.", "\n");
        input = input.replaceAll("(?m)^ *", "");
        input = input.replaceAll("(?m)^\\t*\\n+", "");
        input = input.replaceAll(" {2}", " ");

        return input;
    }
}
