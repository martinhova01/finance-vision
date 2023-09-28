package fileSaving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
 * @param filename the name of the file to read from
 * @throws IOException if the data.txt file is not found
 */
  public static List<User> readFromFile(String filename) throws IOException{

    InputStream is = new FileInputStream(new File("../persistence/src/main/resources/fileSaving/" + filename)); //the app is run from financeVision/ui
    BufferedReader br = new BufferedReader(new InputStreamReader(is));

    List<User> result = new ArrayList<>();
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
          String category = data[i + 2];
          double amount = Double.parseDouble(data[i + 3]);
          LocalDateTime time = LocalDateTime.parse(data[i + 4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

          if (data[i].equals("i")){
            Transaction t = new Income(description, amount, category);
            t.setTime(time);
            account.addTransaction(t);
          }
          else{
            Transaction t = new Expense(description, amount, category);
            t.setTime(time);
            account.addTransaction(t);
          }
          //i += 4;
          i += 5;

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
   * username;password;fullname;email;accountStartValue;i;description1;category1;amount1;time1;e;description2;category2;amount2;time2 ...
   * username;password.....
   * 
   * @param users a Collection of the users registered in the app.
   * @param filename the name of the file to write to
   * @throws IOException if the file is not found
   */
  public static void writeToFile(List<User> users, String filename) throws IOException{
    FileWriter fileWriter = new FileWriter(new File("../persistence/src/main/resources/fileSaving/" + filename)); //the app is run from financeVision/ui
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
            data += t.getDescription() + ";" + t.getCategory() + ";" + t.getAmount() + ";" + t.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ";";

          }

          data += "\n";

        }
        fileWriter.write(data);
        fileWriter.close();
  }

}
