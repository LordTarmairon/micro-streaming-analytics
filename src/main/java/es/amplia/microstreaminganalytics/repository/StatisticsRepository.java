package es.amplia.microstreaminganalytics.repository;

import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@Component
public interface StatisticsRepository extends MongoRepository<Statistics, Long> {
    List<Statistics> findByEntityName(EntityName entityName);
    List<Statistics> findByDate(Date fecha);
    List<Statistics> findByMeanGreaterThan(double valor);

    List<Statistics> findByMaxValue(double maxValue);
    List<Statistics> findByMinValue(double minValue);

    List<Statistics> findByStandardDeviation(double valor);

    List<Statistics> findByMaxValueGreaterThanAndMinValueLessThan(double maxValor, double minValor);


}
