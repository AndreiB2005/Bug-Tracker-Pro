package factories.userFactories;

import fileio.UserInput;
import main.AppBrain;
import users.User;
import users.Developer;

public class DeveloperFactory extends UserFactory {
    protected User createUser(final AppBrain brain, final UserInput userInput) {
        Developer developer = new Developer(userInput);
        brain.getDevelopers().add(developer);
        brain.getMembers().add(developer);
        return developer;
    }
}
