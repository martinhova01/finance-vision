package ui;

import com.google.gson.Gson;
import core.User;
import filesaving.JsonFileSaving;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

/**
 * FinanceVisionAccess class that uses remote endpoint.
 */
public class RemoteFinanceVisionModelAccess implements FinanceVisionModelAccess {

    private final URI endpointBaseUri;

    private static final String APPLICATION_JSON = "application/json";
  
    private static final String ACCEPT_HEADER = "Accept";
  
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private Gson gson;


    public RemoteFinanceVisionModelAccess(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        this.gson = new JsonFileSaving().createGson();
    }

    /**
     * Checks if the client is connected to the server.
     *
     * @return true if connected
     * @throws Exception if not connected
     */
    public boolean isConnected() throws Exception {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        HttpResponse<String> response = HttpClient
            .newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        return gson.fromJson(responseString, Boolean.class);
    }

    @Override
    public void putUser(User user) throws Exception {
        
        String json = gson.toJson(user);
        URI userUri = new URI(endpointBaseUri + "user/" + user.getUsername());
        HttpRequest request = HttpRequest.newBuilder(userUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
            .PUT(BodyPublishers.ofString(json))
            .build();

        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void removeUser(User user) throws Exception {
        URI userUri = new URI(endpointBaseUri + "user/" + user.getUsername());
        HttpRequest request = HttpRequest.newBuilder(userUri)
            .DELETE()
            .build();
        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public boolean containsUser(String username) throws Exception {
        URI userUri = new URI(endpointBaseUri + "user/" + username + "/exists");
        HttpRequest request = HttpRequest.newBuilder(userUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();

        HttpResponse<String> response = HttpClient
            .newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        return gson.fromJson(responseString, Boolean.class);
    }


    @Override
    public User getUser(String username, String password) throws Exception {
        URI userUri = new URI(endpointBaseUri + "user/" + username + "?password=" + password);
        HttpRequest request = HttpRequest.newBuilder(userUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();

        HttpResponse<String> response = HttpClient
            .newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        return gson.fromJson(responseString, User.class);
    }
  
}
