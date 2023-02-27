package companion.challeculum.batch.job.reader;

import companion.challeculum.batch.entity.Mission;
import companion.challeculum.batch.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.reader
 */
@Component
@RequiredArgsConstructor
public class TodayMissionReader implements ItemReader<Mission> {
    private final MissionRepository missionRepository;
    private Iterator<Mission> missionIterator;

    @Override
    public Mission read() {
        if (missionIterator == null) {
            List<Mission> todayMissionList = missionRepository.findByMissionAtIs(LocalDate.now().minusDays(1));
            missionIterator = todayMissionList.iterator();
        }
        if (missionIterator.hasNext()) return missionIterator.next();
        else return null;
    }
}
