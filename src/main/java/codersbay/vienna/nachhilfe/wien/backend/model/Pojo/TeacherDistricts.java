package codersbay.vienna.nachhilfe.wien.backend.model.Pojo;

import codersbay.vienna.nachhilfe.wien.backend.model.Entity.Districts;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TeacherDistricts {

    Long teacherId;
    Set<Districts> districts;

}
