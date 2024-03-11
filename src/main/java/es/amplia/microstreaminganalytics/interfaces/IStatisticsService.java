package es.amplia.microstreaminganalytics.interfaces;

import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IStatisticsService {
    Statistics getStatisticsByID(Long id);

    List<Statistics> getStatisticsByEntity(EntityName entity);

    List<Statistics> getAllStatistics();

    List<Statistics> getStatisticsByDate(Date date);

    Statistics saveStatistics(Statistics statistics);

    Map<EntityName, Statistics> getStatisticsUntilNow();
}
