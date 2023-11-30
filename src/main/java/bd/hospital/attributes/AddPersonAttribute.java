package bd.hospital.attributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPersonAttribute {
    private String firstName;
    private String lastName;
    private String patherName;
    private Long wardId;
    private String diagnosisName;
}
