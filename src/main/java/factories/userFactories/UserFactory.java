package factories.userFactories;

import main.AppBrain;
import fileio.UserInput;
import users.User;

public abstract class UserFactory {
    public void addUser(final AppBrain brain, final UserInput userInput) {
        User currUser = createUser(brain, userInput);
        brain.getUsers().add(currUser);
    }

    protected abstract User createUser(final AppBrain brain, final UserInput userInput);
}
