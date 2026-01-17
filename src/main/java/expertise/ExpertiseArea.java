package expertise;

import java.util.List;

public enum ExpertiseArea {
    FRONTEND,
    BACKEND,
    DEVOPS,
    DESIGN,
    DB,
    FULLSTACK;

    public List<String> getExpertiseList() {
        return switch (this) {
            case FRONTEND -> List.of("FRONTEND", "DESIGN");
            case BACKEND -> List.of("BACKEND", "DB");
            case DEVOPS -> List.of("DEVOPS");
            case DESIGN -> List.of("DESIGN", "FRONTEND");
            case DB -> List.of("DB");
            case FULLSTACK -> List.of(
                    "FRONTEND",
                    "BACKEND",
                    "DEVOPS",
                    "DESIGN",
                    "DB"
            );
        };
    }
}
