package users;

import java.util.List;
import fileio.SearchInput;
import visitors.Visitor;

public interface SearchMember {
    <T> List<T> acceptList(Visitor<T> visitor, SearchInput input, List<T> list);

    String getMemberName();
}
