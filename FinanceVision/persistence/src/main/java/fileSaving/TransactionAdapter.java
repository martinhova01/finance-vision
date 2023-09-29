package fileSaving;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import core.Expense;
import core.Income;
import core.Transaction;

public class TransactionAdapter extends TypeAdapter<Transaction> {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public void write(JsonWriter out, Transaction transaction) throws IOException {
    out.beginObject();
    if (transaction instanceof Income) {
        out.name("type").value("income");
        // Additional properties for income...
    } else if (transaction instanceof Expense) {
        out.name("type").value("expense");
        // Additional properties for expense...
    }
    out.name("description").value(transaction.getDescription());
    out.name("amount").value(transaction.getAmount());
    out.name("category").value(transaction.getCategory());
    out.name("date").value(formatter.format(transaction.getTime()));    
    
    out.endObject();
  }

  @Override
  public Transaction read(JsonReader in) throws IOException {
    if (in.peek() == null) {
      return null;
    }
    in.beginObject();
    in.nextName();
    if (in.nextString().equals("income")) {
      in.nextName();
      String description = in.nextString();
      in.nextName();
      double amount = Double.parseDouble(in.nextString());
      in.nextName();
      String category = in.nextString();
      in.nextName();
      in.nextString();
      //LocalDateTime date = LocalDateTime.parse(in.nextName(), formatter);
      in.endObject();
      return new Income(description, amount, category);

    } else {
      in.nextName();
      String description = in.nextString();
      in.nextName();
      double amount = Double.parseDouble(in.nextString());
      in.nextName();
      String category = in.nextString();
      in.nextName();
      in.nextString();
      //LocalDateTime date = LocalDateTime.parse(in.nextName(), formatter);
      in.endObject();
      return new Expense(description, amount, category);
    }
  }
  
}
