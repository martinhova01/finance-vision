package fileSaving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import core.Account;
import core.Expense;
import core.Income;
import core.Transaction;
import core.User;

public class JsonFileSaving {
  private static final String JSON_FILE_PATH = "persistence\\src\\main\\resources\\fileSaving\\";

  public static void serializeUsers(List<User> users, String filename) throws JsonIOException, IOException {
    PrintWriter writer = new PrintWriter(new File(JSON_FILE_PATH + filename));
    
    Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDateTime.class, new TimeAdapter())
    .registerTypeAdapter(Transaction.class, new TransactionAdapter())
    .setPrettyPrinting()
    .create();
    //gson.toJson(users, new FileWriter(new File(JSON_FILE_PATH + filename)));
    String jsonString = gson.toJson(users);
    System.out.println(jsonString);
    writer.write(gson.toJson(users));
    writer.close();
  }

  public static List<User> deserializeUsers(String filename) throws FileNotFoundException {
    Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDateTime.class, new TimeAdapter())
    .registerTypeAdapter(Transaction.class, new TransactionAdapter())
    .create();
    FileReader reader = new FileReader(new File(JSON_FILE_PATH + filename));
    List<User> users = gson.fromJson(reader, new TypeToken<ArrayList<User>>(){}.getType());
    return users;
  } 


  public static void main(String[] args){

    User u1 = new User("martinhova", "password", "Martin Høva", "martirho@stud.ntnu.no", new Account(1000));
    User u2 = new User("doejohn", "agreatPassword!", "John Doe", "johndoe@example.com", new Account(2500));
    u1.getAccount().addTransaction(new Income("mat", 100.0, "food"));
    u2.getAccount().addTransaction(new Expense("klaer", 350.0, "Other"));
    u2.getAccount().addTransaction(new Income("mat", 25.0, "food"));
    u1.getAccount().addTransaction(new Expense("leie", 5500.0, "Rent"));

    List<User> users = new ArrayList<>(List.of(u1, u2));
    try {
    serializeUsers(users, "users.json");
    List<User> readUsers = deserializeUsers("users.json");
    for (User user : readUsers) {
        System.err.println(user.getUsername());
    }
    } catch (Exception e) {
    e.printStackTrace();
    System.out.println("Feil");
    }
	}
}
