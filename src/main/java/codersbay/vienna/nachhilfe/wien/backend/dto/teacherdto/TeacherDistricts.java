package codersbay.vienna.nachhilfe.wien.backend.dto.teacherdto;

import codersbay.vienna.nachhilfe.wien.backend.model.Districts;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TeacherDistricts {
    Long teacherId;
    Set<Districts> districts = new HashSet<>();
}
