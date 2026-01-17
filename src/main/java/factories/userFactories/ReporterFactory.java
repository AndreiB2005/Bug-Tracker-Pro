package factories.userFactories;

import fileio.UserInput;
import main.AppBrain;
import users.User;
import users.Reporter;

public class ReporterFactory extends UserFactory {
    protected User createUser(final AppBrain brain, final UserInput userInput) {
        return new Reporter(userInput);
    }
}
