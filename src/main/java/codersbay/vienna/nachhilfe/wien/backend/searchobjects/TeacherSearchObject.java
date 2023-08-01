package codersbay.vienna.nachhilfe.wien.backend.searchobjects;

import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import codersbay.vienna.nachhilfe.wien.backend.model.Level;
import codersbay.vienna.nachhilfe.wien.backend.model.Subject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeacherSearchObject {

    private List<Districts> districts = new ArrayList<>();
    private Subject subject;
    private Long minRate;
    private Long maxRate;
    private Level level;
    private Double rating;

    public boolean isLevelSet() {return this.level != null;}

    public boolean isRatingSet() {return this.rating > 0;}

    public boolean isMinRateSet() {
        return this.minRate != null;
    }

    public boolean isMaxRateSet() {
        return this.maxRate != null;
    }

    public boolean isSubjectSet() {
        return this.subject != null;
    }

    public boolean isDistrictSet() {
        return this.districts.size() > 0;
    }
}
