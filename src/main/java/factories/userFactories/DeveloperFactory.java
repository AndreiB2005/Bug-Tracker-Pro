package factories.userFactories;

import fileio.UserInput;
import users.User;
import users.Developer;

public class DeveloperFactory extends UserFactory {
    protected User createUser(final UserInput userInput) {
        return new Developer(userInput);
    }
}
