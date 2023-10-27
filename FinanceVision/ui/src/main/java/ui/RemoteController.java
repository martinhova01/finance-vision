package ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;

/**
 * A controller that sets the endpoint URI and inits model access class.
 */
public class RemoteController extends AbstractController {

    @FXML
    private String endpointUri;

    @Override
    public void init() {
        try {
            this.modelAccess = new RemoteFinanceVisionModelAccess(new URI(endpointUri));
            System.out.println("using remote endpoint @ " + endpointUri);
            switchScene("login.fxml");

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
