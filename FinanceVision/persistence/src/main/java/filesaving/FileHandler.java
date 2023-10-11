package filesaving;

import core.User;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Interface for filehandling.
 *  Has one method for saving to file and one for reading from file.
 */
public interface FileHandler {

    public List<User> deserializeUsers(File f) throws IOException;

    public void serializeUsers(List<User> users, File f) throws IOException;
    
}
