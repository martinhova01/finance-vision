package ui;

import com.google.gson.Gson;
import core.FinanceVisionModel;
import core.User;
import filesaving.JsonFileSaving;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * FinanceVisionAccess class that uses remote endpoint.
 */
public class RemoteFinanceVisionModelAccess implements FinanceVisionModelAccess {

    private final URI endpointBaseUri;

    private static final String APPLICATION_JSON = "application/json";
  
    private static final String ACCEPT_HEADER = "Accept";
  
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private Gson gson;

    private FinanceVisionModel model;


    public RemoteFinanceVisionModelAccess(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        this.gson = new JsonFileSaving().createGson();
    }

    @Override
    public FinanceVisionModel getModel() throws Exception {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();
        
        final HttpResponse<String> response = HttpClient
            .newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        model = gson.fromJson(response.body(), FinanceVisionModel.class);
        return model;
    }

    @Override
    public void putUser(User user) throws Exception {
        
        String json = gson.toJson(user);
        URI userUri = new URI("http://localhost:8080/fv/user/" + user.getUsername());
        HttpRequest request = HttpRequest.newBuilder(userUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
            .PUT(BodyPublishers.ofString(json))
            .build();

        HttpResponse<String> response = HttpClient
            .newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());

        String responString = response.body();
        boolean success = gson.fromJson(responString, Boolean.class);
        if (success) {
            getModel();
        }     
    }

    @Override
    public void removeUser(User user) throws Exception {
        URI userUri = new URI("http://localhost:8080/fv/user/" + user.getUsername());
        HttpRequest request = HttpRequest.newBuilder(userUri)
            .DELETE()
            .build();
        HttpResponse<String> response = HttpClient
            .newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        boolean success = gson.fromJson(responseString, Boolean.class);
        if (success) {
            getModel();
        }
    }

    @Override
    public boolean containsUser(String username) throws Exception {
        getModel();
        return model.containsUser(username);
    }

    @Override
    public List<String> getUsernames() throws Exception {
        getModel();
        return model.getUsernames();
    }

    @Override
    public User getUser(String username) throws Exception {
        URI userUri = new URI("http://localhost:8080/fv/user/" + username);
        HttpRequest request = HttpRequest.newBuilder(userUri)
            .header(ACCEPT_HEADER, APPLICATION_JSON)
            .GET()
            .build();

        HttpResponse<String> response = HttpClient
            .newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        return gson.fromJson(responseString, User.class);
    }

    @Override
    public List<User> getUsers() throws Exception {
        getModel();
        return model.getUsers();
    }

    //testing http requests
    // public static void main(String[] args) {
    //   try {
    //     RemoteFinanceVisionModelAccess r = new RemoteFinanceVisionModelAccess(new URI("http://localhost:8080/fv/"));
    //     System.out.println(r.getModel().getUsernames());
    //     User u = new User("martinhøva", "password", "M H", "m@g.com", new Account(1000));
    //     r.putUser(u);
    //     System.out.println(r.getModel().getUsernames());
    //     r.removeUser(u);
    //     System.out.println(r.getModel().getUsernames());
    //     System.out.println(r.getUser("testuser").getFullName());
        
    //   } catch (Exception e) {
    //     e.printStackTrace();
    //   }
    // }
  
}
