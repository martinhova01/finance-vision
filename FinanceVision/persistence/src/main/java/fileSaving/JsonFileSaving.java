package fileSaving;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.Account;
import core.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileSaving {
  static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Parses the data saved in the data.txt file
   * 
   * @return a Collection of the users that are registered in the application
   * @param filename the name of the file to read from
   * @throws IOException if the data.txt file is not found
   */
  public static List<User> readFromFile(String filename) throws IOException{
    List<User> loadedUserList = objectMapper.readValue(new File("persistence\\src\\main\\resources\\fileSaving\\" + filename), new TypeReference<List<User>>() {});
    return loadedUserList;
  }

  /**
   * @param users a Collection of the users registered in the app.
   * @param filename the name of the file to write to
   * @throws IOException if the file is not found
   */
  public static void writeToFile(List<User> users, String filename) throws IOException{
    objectMapper.writeValue(new File("persistence\\src\\main\\resources\\fileSaving\\" + filename), users);
  }

    public static void main(String[] args){

    User u1 = new User("martinhova", "password", "Martin Høva", "martirho@stud.ntnu.no", new Account(1000));
    User u2 = new User("doejohn", "agreatPassword!", "John Doe", "johndoe@example.com", new Account(2500));
    List<User> users = new ArrayList<>(List.of(u1, u2));
    try {
      writeToFile(users, "users.json");
    } catch (IOException e) {
      e.printStackTrace();
    }

	
	}
}
