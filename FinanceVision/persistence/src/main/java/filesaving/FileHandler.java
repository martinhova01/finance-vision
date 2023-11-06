package filesaving;

import core.FinanceVisionModel;

import java.io.IOException;

/**
 * Interface for filehandling.
 *  Has one method for saving to file and one for reading from file.
 */
public interface FileHandler {

    public FinanceVisionModel readModel() throws IOException;

    public void writeModel(FinanceVisionModel model) throws IOException;
    
}
