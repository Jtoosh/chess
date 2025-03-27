package client;

import com.google.gson.Gson;
import model.AuthData;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {
    Gson serializer = new Gson();

    public <T> T httpRequest(Record request,String authToken, String urlArg, String method, Class<T> responseClass) throws IOException{
        URL connectionURL = new URL(urlArg);

        HttpURLConnection connection = (HttpURLConnection) connectionURL.openConnection();

        if (authToken != null){
            connection.addRequestProperty("Authorization", authToken);
        }

        connection.setReadTimeout(5000);
        connection.setRequestMethod(method);
//        connection.setDoInput(true);
        connection.setDoOutput(true);
        writeBody(request,connection);

        connection.connect();

        return readBody(responseClass, connection);
    }

    private void writeBody(Object request, HttpURLConnection httpConn) throws IOException {
        if (request != null){
            httpConn.addRequestProperty("Content-Type", "application/json");
            String requestData = serializer.toJson(request);
            try(OutputStream requestBody = httpConn.getOutputStream();) {
                // Write request body to OutputStream ...
                requestBody.write(requestData.getBytes());
            }
        }
    }

    private <T> T readBody(Class<T> responseClass, HttpURLConnection httpConn) throws IOException {
        T response = null;
        if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK){
            try(InputStream errorStream = httpConn.getErrorStream()){
                BufferedInputStream errorStreamOptimized = new BufferedInputStream(errorStream);
                InputStreamReader errorStreamReader = new InputStreamReader(errorStreamOptimized);
                handleHTTPStatus(httpConn.getResponseCode());
            }
        }
        try (InputStream responseBody = httpConn.getInputStream();){
            BufferedInputStream responseBodyOptimized = new BufferedInputStream(responseBody);
            InputStreamReader responseBodyReader = new InputStreamReader(responseBodyOptimized);
            if (responseClass != null){
                response = serializer.fromJson(responseBodyReader, responseClass);
            }
        }
        return response;
    }

    private void handleHTTPStatus(int responseCode) throws IOException {
        switch (responseCode){
            case 400:
                throw new IllegalArgumentException("Error: bad request");
            case 401:
                throw new AuthorizationException("Error: unauthorized");
            case 403:
                throw new AlreadyInUseException("Error: already in use");
            default:
                throw new IOException("Error: IOException " + responseCode);
        }
    }
}
