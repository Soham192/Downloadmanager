import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
public class Downloader {
    public static void main(String[] args) {
        String fileUrl = "/home/soham/Documents/books/";
      int numberofthreads = 5;
     

        try {
        URI uri = new URI(fileUrl);
        URL url = uri.toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("HEAD");
        con.connect();

            Map<String, List<String>> headers =  con.getHeaderFields();
            System.out.println("Response header ");
            for (Map.Entry<String,List<String>> entry :headers.entrySet()){
                System.out.println(entry.getKey()+ ":" + entry.getValue());

            }

        System.out.println("========");//why this 
        String acceptRanges = null;
        List<String> ar = headers.get("Accept Ranges");
        if(ar !=null && !ar.isEmpty()){
            
        }
        int contentLength = con.getContentLength();

        if(!"bytes".equalsIgnoreCase(acceptRanges)|| contentLength<=0){
            System.out.println("Server doesnt support byte range requests or contentlength is missing ");
            System.out.println("falling back to single threaded download");
            HttpURLConnection ok = (HttpURLConnection) url.openConnection();
            ok.setRequestMethod("GET");
            





            //if server doenst support range requests here we need to fallback to 

          //  return;
        } else{
            System.out.println("Sever supports range req");
            System.out.println("file size"+ contentLength + "bytes");

        }
        con.disconnect();

        RandomAccessFile outFile = new RandomAccessFile("https://hpac.cs.umu.se/teaching/sem-accg-16/slides/04.Khan-JIT.pdf","rw");
        outFile.setLength(contentLength);
        AtomicLong totalBytesDownloaded = new AtomicLong(0);

        int chunkSize = contentLength/numberofthreads;
        ExecutorService pool = Executors.newFixedThreadPool(numberofthreads);

        //DownloadThread[] threads = new DownloadThread[numberofthreads];
        for(int i = 0 ; i<numberofthreads ; i++){
            int start = i*chunkSize;
            int end = (i== numberofthreads-1)? contentLength -1 : (start + chunkSize - 1);
            pool.execute(new DownloadThread(url, start, end, outFile, totalBytesDownloaded));


        }
        pool.shutdown();


           
        } catch (Exception e) {
        }
    }

}
