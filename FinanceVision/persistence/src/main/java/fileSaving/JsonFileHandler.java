package fileSaving;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import core.Account;
import core.User;

public class JsonFileHandler {
    private static final String JSON_FILE_PATH = "persistence\\src\\main\\resources\\fileSaving\\";
    private static Gson gson = new Gson();

    public static void saveUsers(List<User> users, String filename) throws IOException {
        FileWriter writer = new FileWriter(JSON_FILE_PATH + filename);
        gson.toJson(users, writer);
    }

/*     public List<User> loadUsers(String filename) {
        try (FileReader reader = new FileReader(JSON_FILE_PATH + filename)) {
            return gson.fromJson(reader, core.UserList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } */

    public static void main(String[] args){

        User u1 = new User("martinhova", "password", "Martin Høva", "martirho@stud.ntnu.no", new Account(1000));
        User u2 = new User("doejohn", "agreatPassword!", "John Doe", "johndoe@example.com", new Account(2500));
        //u1.getAccount().addTransaction(new Income("mat", 100.0, "food"));
        List<User> users = new ArrayList<>(List.of(u1, u2));
        try {
        saveUsers(users, "users.json");
/*         List<User> readUsers = readFromFile("users.json");
        for (User user : readUsers) {
            System.err.println(user.getUsername());
        } */
        } catch (IOException e) {
        e.printStackTrace();
        }
	}
}

/* import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.io.Reader;

public class JsonFileHandler {
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  public static void serialize(Object object, String filePath) throws IOException {
    try (Writer writer = new FileWriter(filePath)) {
        gson.toJson(object, writer);
    }
  }

  public static <T> T deserialize(Class<T> clazz, String filePath) throws IOException {
    try (Reader reader = new FileReader(filePath)) {
        return gson.fromJson(reader, clazz);
    }
  }
} */
