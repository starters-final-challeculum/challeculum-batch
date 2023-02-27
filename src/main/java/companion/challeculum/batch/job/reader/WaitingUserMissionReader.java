package companion.challeculum.batch.job.reader;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.UserMission;
import companion.challeculum.batch.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * Created by jonghyeon on 2023/02/27,
 * Package : companion.challeculum.batch.job.reader
 */
@Component
@RequiredArgsConstructor
public class WaitingUserMissionReader implements ItemReader<UserMission> {

    private final UserMissionRepository userMissionRepository;
    private Iterator<UserMission> userMissionIterator;

    @Override
    public UserMission read() {
        if (userMissionIterator == null) {
            List<UserMission> userMissionList = userMissionRepository.findByIsAcceptedIs(Constants.USER_MISSION_WAITING);
            userMissionIterator = userMissionList.iterator();
        }
        if (userMissionIterator.hasNext()) return userMissionIterator.next();
        else return null;
    }
}
