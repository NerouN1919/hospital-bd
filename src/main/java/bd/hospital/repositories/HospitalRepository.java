package bd.hospital.repositories;

import bd.hospital.attributes.AddWardAttribute;
import bd.hospital.attributes.UpdatePersonAttribute;
import bd.hospital.attributes.UpdateWardAttribute;
import bd.hospital.dto.*;

import java.util.List;

public interface HospitalRepository {
    List<WardDto> getWardShortInfoPage(int pageSize, int pageCount);
    List<PeopleDto> getPeopleByWardId(long wardId);
    int getWardsPagesCountBySize(int pageSize);
    AddPersonToWardDto addPersonToWard(String firstName, String lastName,
                                       String patherName, Long wardId, String diagnosisName);
    AddWardDto createWard(String wardName, Long maxCount);
    UpdateWardDto updateWard(UpdateWardAttribute updateWardAttribute);
    void updatePerson(UpdatePersonAttribute updatePersonAttribute);
    void deletePerson(Long personId);
    void deleteWard(Long wardId);
    List<DiagnosDto> getAllDiagnoses();
    List<StatisticDto> getStatistic();
    CreateDiagnosisDto createDiagnosis(String diagnosisName);
}
