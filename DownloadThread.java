import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;
import javax.net.ssl.HttpsURLConnection;

class DownloadThread extends Thread{
    private  AtomicLong totalbytesDownloaded;
    private int startByte , endByte;
    private RandomAccessFile outFile;
    private URL url;


    public DownloadThread(URL url ,int start,int end, RandomAccessFile outFile, AtomicLong TotalBytesDownloaded )
{
    this.totalbytesDownloaded = totalbytesDownloaded;
    this.startByte = start;
    this.endByte = end ;
    this.url = url;
    this.outFile = outFile;
}

@Override

public void run(){
    try {
        HttpURLConnection conn = (HttpsURLConnection) url.openConnection();
        String byteRange = "bytes=" + startByte + "-" + endByte;  
        conn.setRequestProperty("Range", byteRange);    
        conn.connect();
        
        byte[] buffer = new byte[4096];
        int bytesRead;
        InputStream in = conn.getInputStream();
        outFile.seek(startByte);

        while((bytesRead = in.read(buffer)) !=-1){
            outFile.write(buffer,0,bytesRead);
            totalbytesDownloaded.addAndGet(bytesRead);
        }

        conn.disconnect();

    } catch (Exception e) {
        e.printStackTrace();
    }
}




}   




































