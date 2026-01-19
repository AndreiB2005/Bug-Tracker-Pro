package factories.userFactories;

import fileio.UserInput;
import main.AppBrain;
import users.User;
import users.Manager;

public class ManagerFactory extends UserFactory {
    protected User createUser(final AppBrain brain, final UserInput userInput) {
        Manager manager = new Manager(userInput);
        manager.addSubordinates(brain.getDevelopers(), userInput.getSubordinates());
        brain.getManagers().add(manager);
        brain.getMembers().add(manager);
        return manager;
    }
}
