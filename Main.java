//import java.io.*;
//import java.net.URI;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.Scanner;
//import org.jsoup.*;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//public class Main {
//
//
//
//
//    public static void options(){
//        System.out.println("Type a valid number for your desired action:");
//        System.out.println("[1] Show updates");
//        System.out.println("[2] Add URL");
//        System.out.println("[3] Remove URL");
//        System.out.println("[4] Exit");
//        Scanner scanner = new Scanner(System.in);
//        int EnteredNumber = scanner.nextInt();
//        switch (EnteredNumber){
//            case 1 :
//                showUpdates();
//                break;
//            case 2 :
//                addURL();
//                break;
//            case 3:
//                removeURL();
//                break;
//            case 4 :
//
//                break;
//        }
//    }
//
//
//
//    public static void main(String[] args) {
//        System.out.println("Welcome to RSS Reader!");
//        options();
//
//
//    }
//
//
//
//
//
//
//
//    public static void showUpdates(){
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Show updates for:");
//        System.out.println("[0] All websites");
//        int counter = 1 ;
//
//        try
//        {
//            File file = new File("data.txt");
//            FileReader fileReader = new FileReader(file);
//            BufferedReader reader = new BufferedReader(fileReader);
//
//            String line;
//            while ((line = reader.readLine()) != null)
//            {
//                String[] title = line.split(";");
//                System.out.println("["+counter+"] "+title[0]);
//                counter++;
//            }
//            System.out.println("Enter -1 to return");
//            reader.close();
//
//            int EnteredNumber = scanner.nextInt();
//            if (EnteredNumber == -1){
//                options();
//            }
//        }
//
//        catch (IOException ex)
//        {
//            ex.printStackTrace();
//        }
//
//
//    }
//
//    public static void removeURL() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter website URL to remove:");
//        String URL = scanner.next();
//        try {
//            File file = new File("data.txt");
//            FileReader fileReader = new FileReader(file);
//            BufferedReader reader = new BufferedReader(fileReader);
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.equals(URL)) {
//
//                    System.out.println("Removed "+URL+" successfully.");
//                    options();
//                }
//                else if (!line.equals(URL)) {
//                    System.out.println("Couldn't find "+URL+".");
//                    options();
//                }
//            }
//            reader.close();
//        }
//
//        catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//
//
//    //To gain HTML source from URL
//    public static String fetchPageSource(String urlString) throws Exception{
//
//         URI uri = new URI(urlString);
//         URL url = uri.toURL ();
//         URLConnection urlConnection = url.openConnection ();
//         urlConnection.setRequestProperty("User-Agent", "Mozilla /5.0 (X11; Linux x86_64) AppleWebKit /537.36 (KHTML , like Gecko) Chrome /108.0.0.0 Safari /537.36");
//         return toString(urlConnection.getInputStream ());
//         }
//
//    //To gain Website address from HTML source
//    public static String extractPageTitle(String html){
//         try
//         {
//             org.jsoup.nodes.Document doc = Jsoup.parse(html);
//             return doc.select("title").first ().text();
//             }
//         catch (Exception e)
//         {
//             return "Error: no title tag found in page source!";
//             }
//         }
//
//     //To extract RSS by using URL
//     public static String extractRssUrl(String url) throws IOException{
//         org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
//         return doc.select("[type='application/rss+xml ']").attr("abs:href");
//         }
//
//
//    //To extract RSS details
//    public static void retrieveRssContent(String rssUrl) {
//         try {
//             String rssXml = fetchPageSource(rssUrl);
//             DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance ();
//             DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder ();
//             StringBuilder xmlStringBuilder = new StringBuilder ();
//             xmlStringBuilder.append(rssXml);
//             ByteArrayInputStream input = new ByteArrayInputStream(
//                     xmlStringBuilder.toString ().getBytes("UTF-8"));
//             org.w3c.dom.Document doc = documentBuilder.parse(input);
//             NodeList itemNodes = doc.getElementsByTagName("item");
//
//             for (int i = 0; i < RSSReader.MAX_ITEMS; ++i) {
//                 Node itemNode = itemNodes.item(i);
//                 if (itemNode.getNodeType () == Node.ELEMENT_NODE) {
//                     Element element = (Element) itemNode;
//                     System.out.println("Title: " + element.getElementsByTagName("title").item (0).getTextContent ())
//                    ;
//                     System.out.println("Link: " + element.getElementsByTagName("link").item (0).getTextContent ());
//                     System.out.println("Description: " + element.getElementsByTagName("description").item (0).
//                            getTextContent ());
//                     }
//                 }
//             }
//         catch (Exception e)
//         {
//             System.out.println("Error in retrieving RSS content for " + rssUrl + ": " + e.getMessage ());
//             }
//         }
//
//    //needed method
//    private static String toString(InputStream inputStream) throws IOException{
//         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream , "UTF-8"));
//         String inputLine;
//         StringBuilder stringBuilder = new StringBuilder ();
//         while (( inputLine = bufferedReader.readLine ()) != null)
//             stringBuilder.append(inputLine);
//
//         return stringBuilder.toString ();
//         }
//    }