import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
public class RSSReader {
    private static final int MAX_ITEMS = 5;
    //Needed methods:
    private static String toString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream , "UTF-8"));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder ();
        while (( inputLine = bufferedReader.readLine ()) != null)
            stringBuilder.append(inputLine);

        return stringBuilder.toString ();
    }

    public static void retrieveRssContent(String rssUrl) {
        try {
            String rssXml = fetchPageSource(rssUrl);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance ();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder ();
            StringBuilder xmlStringBuilder = new StringBuilder ();
            xmlStringBuilder.append(rssXml);
            ByteArrayInputStream input = new ByteArrayInputStream(
                    xmlStringBuilder.toString ().getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(input);
            NodeList itemNodes = doc.getElementsByTagName("item");

            for (int i = 0; i < MAX_ITEMS; ++i) {
                Node itemNode = itemNodes.item(i);
                if (itemNode.getNodeType () == Node.ELEMENT_NODE) {
                    Element element = (Element) itemNode;
                    System.out.println("Title: " + element.getElementsByTagName("title").item (0).getTextContent ())
                    ;
                    System.out.println("Link: " + element.getElementsByTagName("link").item (0).getTextContent ());
                    System.out.println("Description: " + element.getElementsByTagName("description").item (0).
                            getTextContent ());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error in retrieving RSS content for " + rssUrl + ": " + e.getMessage ());
        }
    }

    public static String extractRssUrl(String url) throws IOException
    {
        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        return doc.select("[type='application/rss+xml']").attr("abs:href");
    }

    public static String extractPageTitle(String html){
        try
        {
            org.jsoup.nodes.Document doc = Jsoup.parse(html);
            return doc.select("title").first ().text();
        }
        catch (Exception e)
        {
            return "Error: no title tag found in page source!";
        }
    }

    public static String fetchPageSource(String urlString) throws Exception{

        URI uri = new URI(urlString);
        URL url = uri.toURL ();
        URLConnection urlConnection = url.openConnection ();
        urlConnection.setRequestProperty("User-Agent", "Mozilla /5.0 (X11; Linux x86_64) AppleWebKit /537.36 (KHTML , like Gecko) Chrome /108.0.0.0 Safari /537.36");
        return toString(urlConnection.getInputStream ());
    }

    //Adding a new Url
    public static void addURL() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter website URL to add:");
        String URL = scanner.nextLine();
        //To check whether if URL already exists or not
        File file = new File("data.txt");

        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        ArrayList<String>list = new ArrayList<>();
        while ((line = reader.readLine()) != null){
            list.add(line);
        }

        boolean flag = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(URL)){
                flag = false;
            }
        }

        if (flag ) {
            String html = fetchPageSource(URL);
            String title = extractPageTitle(html);
            String rss = extractRssUrl(URL);
            bufferedWriter.write(title + ";" + URL + ";" + rss + "\n");
            bufferedWriter.close();
            System.out.println("Added " + URL + " successfully.");
        }
        else{
            System.out.println(URL + " already exist");
        }
    }

    static boolean f = true;
    //To show the newest updates of a website :
    public static void showUpdate() throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileReader fileReader = new FileReader("data.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> tileList = new ArrayList<>();
        String[] split;
        String line;
        while ((line = bufferedReader.readLine())!= null){
            split = line.split(";");
            list.add(split[1]);
            tileList.add(split[0]);
        }

        int count = 0;
        System.out.println("[" + count + "] All website");

        for (int i = 0; i < list.size(); i++) {
            count++;
            System.out.println("[" + count + "]" + tileList.get(i)) ;
        }
        System.out.println("enter -1 to return");

        int website = scanner.nextInt();
        if (website == 0){
            for (int i = 0; i < count; i++) {
                retrieveRssContent(extractRssUrl(list.get(i)));
            }
        }
        else if (website == -1){
            f = false;
        }
        else if (website > 0 || website < count) {
            retrieveRssContent(extractRssUrl(list.get(website - 1)));
        }
        else {
            System.out.println("your number out of rang");
        }
    }

//To remove an url from our file
    public static void removeURL() {
        System.out.println("Pleas enter URL to remove.");
        Scanner scanner = new Scanner(System.in);
        String URL = scanner.next();
        boolean f = false;
        ArrayList<String> allURL = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] str = line.split(";");
                if (!str[1].equals(URL)) {
                    allURL.add(line);
                }
                else {
                    f = true;
                }
            }
            FileWriter writer = new FileWriter("data.txt");
            for (int i = 0; i < allURL.size(); i++) {
                writer.write(allURL.get(i) + "\n");
            }
            if (!f)
                System.out.println("Couldn't Find " + URL);
            else
                System.out.println("Removed " +  URL +  " successfully.");

            writer.close();
        }

        catch (Exception e ){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Welcome to RSS Reader!");
        int enteredNumber = 0;
        while (enteredNumber != 4)
        {
            System.out.println("Type a valid number for your desired action:");
            System.out.println("[1] Show updates");
            System.out.println("[2] Add URL");
            System.out.println("[3] Remove URL");
            System.out.println("[4] Exit");
            enteredNumber = scanner.nextInt();
            switch (enteredNumber){
                case 1:{
                    showUpdate();
                    if (!f){
                        break;
                    }
                    break;
                }
                case 2:{
                    addURL();
                    break;
                }
                case 3:{
                    removeURL();
                }
            }
        }
    }
}

