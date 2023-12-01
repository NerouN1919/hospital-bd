package bd.hospital.repositories;

import bd.hospital.mappers.Mapper;
import bd.hospital.attributes.UpdatePersonAttribute;
import bd.hospital.attributes.UpdateWardAttribute;
import bd.hospital.dto.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("repository")
public class HospitalRepositoryImpl implements HospitalRepository {
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private Mapper mapper;

    @PostConstruct
    private void openSessionAndInitializeMapper() {
        this.sqlSession = sqlSessionFactory.openSession(true);
        this.mapper = sqlSession.getMapper(Mapper.class);
    }

    @PreDestroy
    public void closeSession() {
        this.sqlSession.close();
    }

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }


    @Override
    public List<WardDto> getWardShortInfoPage(int pageSize, int pageCount) {
        Map<String, Object> map = new HashMap<>();
        map.put("page_size", pageSize);
        map.put("page_number", pageCount);
        return mapper.getWardPageData(map);
    }

    @Override
    public List<PeopleDto> getPeopleByWardId(long wardId) {
        Map<String, Object> map = new HashMap<>();
        map.put("ward_id_param", wardId);
        return mapper.getPeopleByWardId(map);
    }

    @Override
    public int getWardsPagesCountBySize(int pageSize) {
        Map<String, Object> request = new HashMap<>();
        request.put("page_size", pageSize);
        mapper.calculateWardsPagesCount(request);
        return (Integer) request.get("pages_count");
    }

    @Override
    public AddPersonToWardDto addPersonToWard(String firstName, String lastName,
                                              String patherName, Long wardId, String diagnosisName) {
        Map<String, Object> request = new HashMap<>();
        request.put("first_name", firstName);
        request.put("last_name", lastName);
        request.put("pather_name", patherName);
        request.put("ward_id", wardId);
        request.put("diagnosis_name", diagnosisName);
        AddPersonToWardDto result = mapper.addPersonToWard(request);
        sqlSession.commit(true);
        return result;
    }

    @Override
    public AddWardDto createWard(String wardName, Long maxCount) {
        Map<String, Object> request = new HashMap<>();
        request.put("p_ward_name", wardName);
        request.put("p_max_count", maxCount);
        mapper.createWard(request);
        sqlSession.commit(true);
        return new AddWardDto((Integer) request.get("result_type"), (String) request.get("result_message"));
    }

    @Override
    public UpdateWardDto updateWard(UpdateWardAttribute updateWardAttribute) {
        Map<String, Object> request = new HashMap<>();
        request.put("ward_id", updateWardAttribute.getWardId());
        request.put("ward_name", updateWardAttribute.getWardName());
        request.put("ward_max_count", updateWardAttribute.getMaxCount());
        mapper.updateWard(request);
        sqlSession.commit();
        return new UpdateWardDto((Integer) request.get("result_code"), (String) request.get("result_message"));
    }

    @Override
    public void updatePerson(UpdatePersonAttribute updatePersonAttribute) {
        Map<String, Object> request = new HashMap<>();
        request.put("person_id", updatePersonAttribute.getId());
        request.put("first_name", updatePersonAttribute.getFirstName());
        request.put("last_name", updatePersonAttribute.getLastName());
        request.put("pather_name", updatePersonAttribute.getPatherName());
        request.put("diagnosis_name", updatePersonAttribute.getDiagnosisName());
        mapper.updatePerson(request);
        sqlSession.commit();
    }

    @Override
    public void deletePerson(Long personId) {
        Map<String, Object> request = new HashMap<>();
        request.put("person_id", personId);
        mapper.deletePerson(request);
        sqlSession.commit();
    }

    @Override
    public void deleteWard(Long wardId) {
        Map<String, Object> request = new HashMap<>();
        request.put("ward_id", wardId);
        mapper.deleteWard(request);
        sqlSession.commit();
    }

    @Override
    public List<DiagnosDto> getAllDiagnoses() {
        return mapper.getAllDiagnoses();
    }

    @Override
    public List<StatisticDto> getStatistic() {
        return mapper.getStatistic();
    }

    @Override
    public CreateDiagnosisDto createDiagnosis(String diagnosisName) {
        Map<String, Object> request = new HashMap<>();
        request.put("diagnosis_name", diagnosisName);
        mapper.createDiagnosis(request);
        sqlSession.commit();
        return new CreateDiagnosisDto((Long) request.get("result_code"), (String) request.get("result_message"));
    }

    @Override
    public UserDto getUser(String userName) {
        Map<String, Object> request = new HashMap<>();
        request.put("_username", userName);
        mapper.getUser(request);
        return new UserDto(userName, (String) request.get("_password"), (String) request.get("_rolename"));
    }
}
