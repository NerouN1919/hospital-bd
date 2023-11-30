package bd.hospital.services;

import bd.hospital.attributes.*;
import bd.hospital.dto.*;

import java.util.List;

public interface HospitalService {
    List<WardAttribute> getWardPageInfo(int pageSize, int pageNumber);
    int getWardPagesCount(int pageSize);
    AddPersonToWardDto addPersonToWard(AddPersonAttribute addPersonAttribute);
    AddWardDto createWard(AddWardAttribute addWardAttribute);
    UpdateWardDto updateWard(UpdateWardAttribute updateWardAttribute);
    void updatePerson(UpdatePersonAttribute updatePersonAttribute);
    void deletePerson(Long personId);
    void deleteWard(Long wardId);
    List<DiagnosDto> getAllDiagnoses();
    List<StatisticDto> getStatistic();
    CreateDiagnosisDto createDiagnosis(DiagnosisAttribute diagnosisAttribute);
}
