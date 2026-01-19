package visitors;

import java.util.List;
import fileio.SearchInput;
import users.Developer;
import users.Manager;

public interface Visitor<T> {
    List<T> returnFilteredList(Developer developer, SearchInput input, List<T> list);
    List<T> returnFilteredList(Manager manager, SearchInput input, List<T> list);
}
