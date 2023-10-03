package filesaving;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import core.Expense;
import core.Income;
import core.Transaction;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TypeAdapter for Transaction objects.
 */
public class TransactionAdapter extends TypeAdapter<Transaction> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void write(JsonWriter out, Transaction transaction) throws IOException {
        out.beginObject();
        if (transaction instanceof Income) {
            out.name("type").value("income");
        } else if (transaction instanceof Expense) {
            out.name("type").value("expense");
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
        Transaction transaction;
        if (in.nextString().equals("income")) {
            transaction = new Income();
        } else {
            transaction = new Expense();
        }
        in.nextName();
        String description = in.nextString();
        transaction.setDescription(description);
        in.nextName();
        double amount = Double.parseDouble(in.nextString());
        transaction.setAmount(amount);
        in.nextName();
        String category = in.nextString();
        transaction.setCategory(category);
        in.nextName();
        LocalDateTime date = LocalDateTime.parse(in.nextString(), formatter);
        transaction.setTime(date);
        in.endObject();
        return transaction;
    }
  
}
