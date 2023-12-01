package bd.hospital.mappers;

import bd.hospital.dto.*;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.impl.PerpetualCache;

import java.util.List;
import java.util.Map;

@CacheNamespace(implementation = PerpetualCache.class)
@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    List<WardDto> getWardPageData(Map<String, Object> request);
    List<PeopleDto> getPeopleByWardId(Map<String, Object> request);
    void calculateWardsPagesCount(Map<String, Object> request);
    AddPersonToWardDto addPersonToWard(Map<String, Object> request);
    void createWard(Map<String, Object> request);
    void updateWard(Map<String, Object> request);
    void updatePerson(Map<String, Object> request);
    void deletePerson(Map<String, Object> request);
    void deleteWard(Map<String, Object> request);
    List<DiagnosDto> getAllDiagnoses();
    List<StatisticDto> getStatistic();
    void createDiagnosis(Map<String, Object> request);
    void getUser(Map<String, Object> request);
    List<SummaryDto> getSummary();
}

