import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;


public class head {
    
    public static void main(String[] args) {
        String fileUrl = "https://hpac.cs.umu.se/teaching/sem-accg-16/slides/04.Khan-JIT.pdf";
        
        try {
            URL url = new URL(fileUrl);//an instance of URL 
            HttpURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("HEAD");
            con.connect();

            Map<String, List<String>> headers = con.getHeaderFields();
            System.out.println("Response-headers");
            for (Map.Entry<String ,List<String>>  entry : headers.entrySet()){
                System.out.println(entry.getKey()+ ":" + entry.getValue());
            }
            System.out.println("===========");

            String acceptRanges = con.getHeaderField("Accept-Ranges");
            if("bytes".equalsIgnoreCase(acceptRanges)){
                System.out.println("it supports");

            }else{
                System.out.println("it doesnt");
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
 