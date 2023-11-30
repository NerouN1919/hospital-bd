package bd.hospital.attributes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddWardAttribute {
    private String wardName;
    private Long maxCount;
}
