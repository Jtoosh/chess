import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {

    public void httpGET(String urlArg) throws IOException {
        URL connectionURL = new URL(urlArg);

        HttpURLConnection connection = (HttpURLConnection) connectionURL.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream responseBody = connection.getInputStream();
            BufferedInputStream responseBodyOptimized = new BufferedInputStream(responseBody);
        } else{
            InputStream errorStream = connection.getErrorStream();
        }
    }
}
