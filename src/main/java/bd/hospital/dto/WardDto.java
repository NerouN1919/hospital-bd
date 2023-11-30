package bd.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WardDto {
    private Long id;
    private Integer fillCount;
    private Integer maxCount;
    private String wardName;
}
