package visitors;

import java.util.List;
import fileio.SearchInput;
import users.Developer;
import users.Manager;

public class DeveloperSearch implements Visitor<Developer> {
    public List<Developer> returnFilteredList(final Developer developer, final SearchInput input,
                                              final List<Developer> list) {
        return null;
    }

    public List<Developer> returnFilteredList(final Manager manager, final SearchInput input,
                                              final List<Developer> list) {
        return list.stream()
                .filter(developer -> input.getExpertiseArea() == null
                        || developer.getExpertiseArea().equals(input.getExpertiseArea()))
                .filter(developer -> input.getSeniority() == null
                        || developer.getSeniority().equals(input.getSeniority()))
                .toList();
    }
}
