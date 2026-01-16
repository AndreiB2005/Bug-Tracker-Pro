package factories.userFactories;

import fileio.UserInput;
import users.User;
import users.Manager;

public class ManagerFactory extends UserFactory {
    protected User createUser(final UserInput userInput) {
        return new Manager(userInput);
    }
}
