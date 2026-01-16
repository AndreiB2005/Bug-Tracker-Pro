package factories.userFactories;

import java.util.List;
import fileio.UserInput;
import users.User;

public abstract class UserFactory {
    public void addUser(final List<User> userList, final UserInput userInput) {
        User currUser = createUser(userInput);
        userList.add(currUser);
    }

    protected abstract User createUser(final UserInput userInput);
}
