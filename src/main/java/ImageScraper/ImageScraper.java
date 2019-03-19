//This Class creates an array list of Urls of images from google images depending on what user searches
package ImageScraper;

import Global.AlertDialogue;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ImageScraper {
    public ImageScraper(){}
    //Returns the array of image urls
    public ArrayList<String> getImageArray(String search, int numOfSearchResults){
        ArrayList<String> resultUrls = new ArrayList<String>();
        String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
        //URL that is being searched
        String url = "https://www.google.com/search?site=imghp&tbm=isch&source=hp&q="+search+"&gws_rd=cr";

        try {
            //Creates Document object for google.com
            Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.google.com/").get();
            // Creates Elements object from the doc
            Elements elements = doc.select("div.rg_meta");
            int count =0;
            //creates a json object
            JSONObject jsonObject;
            //reacive each image
            for (Element element : elements) {
                if (count==numOfSearchResults){
                    break;
                }
                if (element.childNodeSize() > 0) {
                    //Parsing from the json result from google to only receive images
                    jsonObject = (JSONObject) new JSONParser().parse(element.childNode(0).toString());
                    String tempURL = (String) jsonObject.get("ou");
                    //accepted only jpg jpeg and jpg
                    boolean urlAccepted= tempURL.endsWith(".png")
                            || tempURL.endsWith(".jpeg")
                            ||tempURL.endsWith(".jpg");
                    if(urlAccepted)
                        resultUrls.add(tempURL);
                }
                count++;
            }

            System.out.println("number of results: " + resultUrls.size());
            // Print URLS Debug
            //for (String imageUrl : resultUrls) System.out.println(imageUrl);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
            AlertDialogue alertDialogue = new AlertDialogue();
            alertDialogue.getAlert(e);
        }
        return resultUrls;
    }
}
