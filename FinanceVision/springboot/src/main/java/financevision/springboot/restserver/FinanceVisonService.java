package financevision.springboot.restserver;

import core.FinanceVisionModel;
import filesaving.JsonFileSaving;
import java.io.IOException;
import org.springframework.stereotype.Service;

/**
 * Stores and generates the model in the remote endpoint.
 */
@Service
public class FinanceVisonService {

    private FinanceVisionModel model;

    private JsonFileSaving filesaving;

    /**
     * Constructor. Reads the model from file or creates en empty model
     */
    public FinanceVisonService() {
        filesaving = new JsonFileSaving();
        readModel();
    }

    public FinanceVisionModel getModel() {
        return model;
    }


    /**
     * Updates the model by reading from file.
     */
    public void readModel() {
        try {
            model = filesaving.readModel();
        } catch (IOException e) {
            model = new FinanceVisionModel();
            saveModel();
        }
    }

    /**
     * Saves model to file.
     */
    public void saveModel() {
        try {
            filesaving.writeModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
