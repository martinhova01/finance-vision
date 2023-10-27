package ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.fxml.FXML;

public class RemoteController extends AbstractController{

    @FXML
    private String endpointURI;

    @Override
    public void init() {
      try {
            this.modelAccess = new RemoteFinanceVisionModelAccess(new URI(endpointURI));
            System.out.println("using remote endpoint @ " + endpointURI);
            switchScene("login.fxml");


        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void initialize() {
        
  }

  
  
}
