package bd.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patherName;
    private String diagnosisName;
    private String wardName;
    private Long maxCount;
}
