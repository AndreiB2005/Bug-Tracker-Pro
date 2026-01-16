package commandCenter.errors;

import java.util.List;
import java.util.Iterator;

public class NotAllowedError extends CommandError {
    private StringBuilder message = null;
    private final List<String> requiredRole;
    private final String userRole;

    public NotAllowedError(final List<String> requiredRole, final String userRole) {
        this.requiredRole = requiredRole;
        this.userRole = userRole;
    }

    public String getMessage() {
        message = new StringBuilder("The user does not have permission to execute this command: "
                + "required role ");
        Iterator<String> iterator = requiredRole.iterator();
        while (iterator.hasNext()) {
            String role = iterator.next();
            message.append(role);
            if (iterator.hasNext()) {
                message.append(", ");
            } else {
                message.append("; ");
            }
        }
        message.append("user role ").append(userRole).append(".");
        return message.toString();
    }
}
