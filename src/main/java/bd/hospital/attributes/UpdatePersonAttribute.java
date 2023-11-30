package bd.hospital.attributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonAttribute {
    private Long id;
    private String firstName;
    private String lastName;
    private String patherName;
    private String diagnosisName;
}
