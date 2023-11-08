package filesaving;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.FinanceVisionModel;
import core.Transaction;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Class for saving userdata to a json file.
 */
public class JsonFileSaving implements FileHandler {

    private Gson gson;

    private static String filepath;
    

    /**
     * Constructor that sets the savefile to user.home directory by default.
     */
    public JsonFileSaving() {
        gson = createGson();
        String path = System.getProperty("user.home")
            + System.getProperty("file.separator") + "data.json";
        setFilepath(path);
        
    }

    public JsonFileSaving(String filepath) {
        this();
        setFilepath(filepath);
    }

    public static void setFilepath(String filepath) {
        JsonFileSaving.filepath = filepath;
    }

    /**
     * Serialize objects to JSON.
     *
     * @param model the FinanceVision model to serialize
     * @throws IOException if the file is not found
     */
    public void writeModel(FinanceVisionModel model) throws IOException {
        try (FileWriter writer = new FileWriter(new File(filepath), StandardCharsets.UTF_8);) {
            writer.write(gson.toJson(model));
            writer.close();
        }
    }

    /**
     * Deserialize JSON to objects.
     *
     * @return the FinanceVision model stored in the file
     * @throws IOException if file not found
     */
    public FinanceVisionModel readModel() throws IOException {
        FileReader reader = new FileReader(new File(filepath), StandardCharsets.UTF_8);
        FinanceVisionModel model = gson.fromJson(reader, FinanceVisionModel.class);
        reader.close();
        return model;
    } 

    /**
     * Create the Gson object with custom typeAdapters.
     *
     * @return the gson object
     */
    public Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new TimeAdapter())
                .registerTypeAdapter(Transaction.class, new TransactionAdapter())
                .setPrettyPrinting()
                .create();
    }

}
