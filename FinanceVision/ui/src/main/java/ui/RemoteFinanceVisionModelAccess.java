package ui;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;

import com.google.gson.Gson;

import core.Account;
import core.FinanceVisionModel;
import core.User;
import filesaving.JsonFileSaving;

public class RemoteFinanceVisionModelAccess implements FinanceVisionModelAccess{

    private final URI endpointBaseURI;
    private static final String APPLICATION_JSON = "application/json";

    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
  
    private static final String ACCEPT_HEADER = "Accept";
  
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private Gson gson;

    private FinanceVisionModel model;


    public RemoteFinanceVisionModelAccess(URI endpointBaseURI) {
        this.endpointBaseURI = endpointBaseURI;
        this.gson = new JsonFileSaving().createGson();
    }

    private String uriParam(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

    private URI getUserURI(String username) {
      return endpointBaseURI.resolve("user/").resolve(uriParam(username));
    }

  @Override
  public FinanceVisionModel getModel() throws Exception {
      if (model == null) {
          HttpRequest request = HttpRequest.newBuilder(endpointBaseURI)
              .header(ACCEPT_HEADER, APPLICATION_JSON)
              .GET()
              .build();
          
              try {
                  final HttpResponse<String> response = 
                      HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
                  this.model = gson.fromJson(response.body(), FinanceVisionModel.class);
              } catch (Exception e) {
                  e.printStackTrace();
              }
      }
      return model;
  }

  @Override
  public void putUser(User user) throws Exception {
      try {
          String json = gson.toJson(user);
          HttpRequest request = HttpRequest.newBuilder(getUserURI(user.getUsername()))
              .header(ACCEPT_HEADER, APPLICATION_JSON)
              .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
              .PUT(BodyPublishers.ofString(json))
              .build();

              HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
          //keeps the local model updated
          this.model = getModel();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  @Override
  public void removeUser(User user) throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeUser'");
  }

  @Override
  public boolean containsUser(String username) throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'containsUser'");
  }

  @Override
  public List<String> getUsernames() throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsernames'");
  }

  @Override
  public User getUser(String username) throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUser'");
  }

  @Override
  public List<User> getUsers() throws Exception {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsers'");
  }

      //testing http requests
  public static void main(String[] args) {
    try {
      RemoteFinanceVisionModelAccess r = new RemoteFinanceVisionModelAccess(new URI("http://localhost:8080/fv"));
      System.out.println(r.getModel().getUsernames());
      User u = new User("martinhova", "password", "M H", "m@g.com", new Account(1000));
      r.putUser(u);
      System.out.println(r.getModel().getUsernames());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
