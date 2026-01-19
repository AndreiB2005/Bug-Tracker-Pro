package fileio;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class SearchInput {
    private String searchType;
    private String businessPriority;
    private String type;
    private String createdAt;
    private String createdBefore;
    private String createdAfter;
    private boolean availableForAssignment;
    private List<String> keywords;
    private String expertiseArea;
    private String seniority;
    private int performanceScoreAbove;
    private int performanceScoreBelow;
}
