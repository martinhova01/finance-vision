package fileSaving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import core.Account;
import core.Expense;
import core.Income;
import core.Transaction;
import core.User;

public class FileSaving {

  /**
 * Parses the data saved in the data.txt file
 * 
 * @return a Collection of the users that are registered in the application
 * @throws IOException if the data.txt file is not found
 */
  static Collection<User> readFromFile() throws IOException{

    InputStream is = FileSaving.class.getResourceAsStream("data.txt");
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    Collection<User> result = new ArrayList<>();
      while(br.ready()){
        String line = br.readLine();
        String[] data = line.split(";");

        String username = data[0];
        String password = data[1];
        String fullName = data[2];
        String email = data[3];
        
        Account account = new Account(Double.parseDouble(data[4]));
        
        int i = 5;
        while(i < data.length){
          String description = data[i + 1];
          double amount = Double.parseDouble(data[i + 2]);
          LocalDateTime time = LocalDateTime.parse(data[i + 3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

          if (data[i].equals("i")){
            Transaction t = new Income(description, amount);
            t.setTime(time);
            account.addTransaction(t);
          }
          else{
            Transaction t = new Expense(description, amount);
            t.setTime(time);
            account.addTransaction(t);
          }
          i += 4;

        }
        User newUser = new User(username, password, fullName, email, account);
        result.add(newUser);
      }


    
    
    return result;

  }
  /**
   * 
   * Write the user information to a file in the following format:
   * 
   * username;password;fullname;email;accountStartValue;i;description1;amount1;time1;e;description2;amount2;time2 ...
   * username;password.....
   * 
   * @param users a Collection of the users registered in the app.
   * @throws IOException if the file is not found
   */
  static void writeToFile(Collection<User> users) throws IOException{
    FileWriter fileWriter = new FileWriter(new File("src/main/java/fileSaving/data.txt"));
        String data = "";
        for (User u : users) {
          data += u.getUsername() + ";" + u.getPassword() + ";" + u.getFullName() + ";" + u.getEmail() + ";" + u.getAccount().getStartValue() + ";";

          for (Transaction t : u.getAccount().getTransactions()){
            if (t instanceof Income){
              data += "i;";
            }
            else{
              data += "e;";
            }
            data += t.getDescription() + ";" + t.getAmount() + ";" + t.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ";";

          }

          data += "\n";

        }
        fileWriter.write(data);
        fileWriter.close();
  }


  // public static void main(String[] args) {
  //   Account a = new Account(1000);
  //   a.addTransaction(new Income("lonn", 500.0));
  //   a.addTransaction(new Expense("skatt", 200.0));
  //   User u = new User("martinhova", "heiheihei", "martin hova ", "martirho@stud.ntnu.no", a);
  //   Collection<User> users = new ArrayList<>();
  //   users.add(u);

  //   try {
  //     writeToFile(users);

  //   } catch (IOException e) {
  //     e.printStackTrace();
  //   }
  // }
}
