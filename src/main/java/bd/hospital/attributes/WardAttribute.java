package bd.hospital.attributes;

import bd.hospital.dto.PeopleDto;
import bd.hospital.dto.WardDto;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardAttribute extends WardDto {
    private List<PeopleDto> peoples;
}
