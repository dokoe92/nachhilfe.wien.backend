package codersbay.vienna.nachhilfe.wien.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TeacherDistricts {

    Long teacherId;
    Set<District> districts;

}
