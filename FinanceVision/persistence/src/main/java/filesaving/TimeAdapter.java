package filesaving;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Typeadapter for LocalDateTime objects.
 */
public class TimeAdapter extends TypeAdapter<LocalDateTime> {
  
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.name("date").value(formatter.format(value));
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String dateStr = in.nextString();
        return LocalDateTime.parse(dateStr, formatter);
    }
  
}
