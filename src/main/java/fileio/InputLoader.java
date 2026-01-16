package fileio;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@Getter
public final class InputLoader {
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<UserInput> users;
    private final List<CommandInput> commands;

    public InputLoader(final String userFilePath, final String commandFilePath)
            throws IOException {
        users = loadList(userFilePath, UserInput.class);
        commands = loadList(commandFilePath, CommandInput.class);
    }

    private <T> List<T> loadList(final String filePath, Class<T> elementType)
            throws IOException {
        return mapper.readValue(
                new File(filePath),
                mapper.getTypeFactory()
                        .constructCollectionType(ArrayList.class, elementType)
        );
    }
}
