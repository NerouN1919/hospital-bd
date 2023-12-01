package bd.hospital.services;

import bd.hospital.attributes.*;
import bd.hospital.dto.*;
import bd.hospital.exporter.ExcelExporter;
import bd.hospital.repositories.HospitalRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("service")
@Getter
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    public void setHospitalRepository(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    private HospitalRepository hospitalRepository;

    @Override
    public List<WardAttribute> getWardPageInfo(int pageSize, int pageNumber) {
        List<WardDto> wardDtoList = hospitalRepository.getWardShortInfoPage(pageSize, pageNumber);
        List<WardAttribute> wardAttributeList = new ArrayList<>();
        for (WardDto wardDto : wardDtoList) {
            WardAttribute wardAttribute = new WardAttribute();
            wardAttribute.setId(wardDto.getId());
            wardAttribute.setWardName(wardDto.getWardName());
            wardAttribute.setFillCount(wardDto.getFillCount());
            wardAttribute.setMaxCount(wardDto.getMaxCount());
            wardAttribute.setPeoples(hospitalRepository.getPeopleByWardId(wardDto.getId()));
            wardAttributeList.add(wardAttribute);
        }
        return wardAttributeList;
    }

    @Override
    public int getWardPagesCount(int pageSize) {
        return hospitalRepository.getWardsPagesCountBySize(pageSize);
    }

    @Override
    public AddPersonToWardDto addPersonToWard(AddPersonAttribute addPersonAttribute) {
        return hospitalRepository.addPersonToWard(addPersonAttribute.getFirstName(), addPersonAttribute.getLastName(),
                addPersonAttribute.getPatherName(), addPersonAttribute.getWardId(),
                addPersonAttribute.getDiagnosisName());
    }

    @Override
    public AddWardDto createWard(AddWardAttribute addWardAttribute) {
        return hospitalRepository.createWard(addWardAttribute.getWardName(), addWardAttribute.getMaxCount());
    }

    @Override
    public UpdateWardDto updateWard(UpdateWardAttribute updateWardAttribute) {
        return hospitalRepository.updateWard(updateWardAttribute);
    }

    @Override
    public void updatePerson(UpdatePersonAttribute updatePersonAttribute) {
        hospitalRepository.updatePerson(updatePersonAttribute);
    }

    @Override
    public void deletePerson(Long personId) {
        hospitalRepository.deletePerson(personId);
    }

    @Override
    public void deleteWard(Long wardId) {
        hospitalRepository.deleteWard(wardId);
    }

    @Override
    public List<DiagnosDto> getAllDiagnoses() {
        return hospitalRepository.getAllDiagnoses();
    }

    @Override
    public List<StatisticDto> getStatistic() {
        return hospitalRepository.getStatistic();
    }

    @Override
    public CreateDiagnosisDto createDiagnosis(DiagnosisAttribute diagnosisAttribute) {
        return hospitalRepository.createDiagnosis(diagnosisAttribute.getDiagnosisName());
    }

    @Override
    public byte[] getSummaryExcel() {
        List<SummaryDto> summaryList = hospitalRepository.getSummary();
        try {
            return new ExcelExporter().exportToExcel(summaryList);
        } catch (IOException e) {
            return new byte[0];
        }
    }
}
