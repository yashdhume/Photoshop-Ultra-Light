package ImageScraper;

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
    public ArrayList<String> getImageArray(String search, int numOfSearchResults){
        ArrayList<String> resultUrls = new ArrayList<String>();
        String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
        String url = "https://www.google.com/search?site=imghp&tbm=isch&source=hp&q="+search+"&gws_rd=cr";

        try {
            Document doc = Jsoup.connect(url).userAgent(userAgent).referrer("https://www.google.com/").get();

            Elements elements = doc.select("div.rg_meta");
            int count =0;
            JSONObject jsonObject;
            for (Element element : elements) {
                if (count==numOfSearchResults){
                    break;
                }
                if (element.childNodeSize() > 0) {
                    jsonObject = (JSONObject) new JSONParser().parse(element.childNode(0).toString());
                    resultUrls.add((String) jsonObject.get("ou"));
                }
                count++;
            }

            System.out.println("number of results: " + resultUrls.size());

            //for (String imageUrl : resultUrls) System.out.println(imageUrl);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return resultUrls;
    }
}
