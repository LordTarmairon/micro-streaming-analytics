package es.amplia.microstreaminganalytics.interfaces;

import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;

import java.util.List;
import java.util.Map;

public interface IStatisticsService {
    Statistics getStatisticsByID(Long id);

    List<Statistics> getStatisticsByEntity(EntityName entity);

    List<Statistics> getAllStatistics();

    Statistics saveStatistics(Statistics statistics);

    Map<EntityName, Statistics> getStatisticsUntilNow();
}
