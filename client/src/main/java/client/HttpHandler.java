package client;

import java.io.IOException;

public interface HttpHandler {
    Record makeRequest() throws IOException;
}
