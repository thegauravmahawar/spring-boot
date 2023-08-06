package imdbapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TitleSearch implements Serializable {

    private UUID id;
    private String name;
    private String type;
    @JsonIgnore
    private Integer startYear;
    @JsonIgnore
    private Integer endYear;

    public String getYear() {
        if (Objects.nonNull(startYear) && Objects.nonNull(endYear)) {
            return String.format("%d-%d", startYear, endYear);
        }
        if (Objects.nonNull(startYear)) {
            return String.valueOf(startYear);
        }
        return StringUtils.EMPTY;
    }
}
