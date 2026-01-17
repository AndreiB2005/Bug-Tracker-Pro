package factories.userFactories;

import fileio.UserInput;
import main.AppBrain;
import users.User;
import users.Manager;

public class ManagerFactory extends UserFactory {
    protected User createUser(final AppBrain brain, final UserInput userInput) {
        Manager manager = new Manager(userInput);
        brain.getManagers().add(manager);
        return manager;
    }
}
