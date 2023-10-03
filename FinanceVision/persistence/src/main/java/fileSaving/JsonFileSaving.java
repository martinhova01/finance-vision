package filesaving;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.Transaction;
import core.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for saving userdata to a json file.
 */
public class JsonFileSaving {

    /**
     * Serialize objects to JSON.
     *
     * @param users the users to serialize
     * @param f the file to save to
     * @throws IOException if the file is not found
     */
    public static void serializeUsers(List<User> users, File f) throws IOException {
        FileWriter writer = new FileWriter(f);
      
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new TimeAdapter())
            .registerTypeAdapter(Transaction.class, new TransactionAdapter())
            .setPrettyPrinting()
            .create();
        writer.write(gson.toJson(users));
        writer.close();
    }

    /**
     * Deserialize JSON to objects.
     *
     * @param f the file to read from
     * @return a list of user objects
     * @throws FileNotFoundException if the file is not found
     */
    public static List<User> deserializeUsers(File f) throws FileNotFoundException {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new TimeAdapter())
            .registerTypeAdapter(Transaction.class, new TransactionAdapter())
            .create();
        FileReader reader = new FileReader(f);
        List<User> users = gson.fromJson(reader, new TypeToken<ArrayList<User>>(){}.getType());
        return users;
    } 

}
