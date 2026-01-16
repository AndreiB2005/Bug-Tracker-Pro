package factories.userFactories;

import fileio.UserInput;
import users.User;
import users.Reporter;

public class ReporterFactory extends UserFactory {
    protected User createUser(final UserInput userInput) {
        return new Reporter(userInput);
    }
}
