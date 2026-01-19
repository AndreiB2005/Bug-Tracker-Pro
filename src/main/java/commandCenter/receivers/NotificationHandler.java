package commandCenter.receivers;

import java.util.ArrayList;
import java.util.List;
import users.Developer;

public class NotificationHandler {
    private final List<Developer> developerList;

    public NotificationHandler(final List<Developer> developerList) {
        this.developerList = developerList;
    }

    public Developer getDeveloper(final String username) {
        for (Developer currDev : developerList) {
            if (currDev.getUsername().equals(username)) {
                return currDev;
            }
        }
        return null;
    }

    public List<String> showNotifications(final Developer developer) {
        List<String> notifications = new ArrayList<>(developer.getNotifications());
        developer.getNotifications().clear();
        return notifications;
    }
}
