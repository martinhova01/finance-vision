package ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

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
            try {
                modelAccess.isConnected();
            } catch (Exception e) {
                notify("Could not connect to remote endpoint", AlertType.ERROR);
            }
            switchScene("login.fxml");

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
