package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class CommandInput {
    private String command;
    private String username;
    private String timestamp;
    private TicketInput params;
    private String name;
    private String dueDate;
    private List<String> blockingFor;
    private List<Integer> tickets;
    private List<String> assignedDevs;
    private String comment;
}
