import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class head {
    
    public static void main(String[] args) {
        String fileUrl = "https://www.w3.org/Protocols/";
        
        try {
            URL url = new URL(fileUrl);//an instance of URL 
            HttpURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");
            con.connect();

            String acceptRanges = con.getHeaderField("Accept-Ranges");
            if("bytes".equalsIgnoreCase(acceptRanges)){
                System.out.println("it supports");

            }else{
                System.out.println("it doesnt");
            }
            con.disconnect();
        } catch (Exception e) {
        }
    }
}
 