package companion.challeculum.batch.job;

import companion.challeculum.batch.config.Constants;
import companion.challeculum.batch.entity.UserMission;
import companion.challeculum.batch.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

/**
 * Created by jonghyeon on 2023/02/26,
 * Package : companion.challeculum.batch.job
 */
@Configuration
@RequiredArgsConstructor
public class MissionAcceptedJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final UserMissionRepository userMissionRepository;


    @Bean
    public Job missionAcceptedJob() {
        return jobBuilderFactory.get("missionAcceptedJob")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<UserMission, UserMission>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public JpaPagingItemReader<UserMission> reader() {
        JpaPagingItemReader<UserMission> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString(String.format("SELECT um FROM UserMission um WHERE um.isAccepted = '%s'", Constants.USER_MISSION_WAITING));
        reader.setPageSize(10);
        return reader;
    }

    @Bean
    public ItemProcessor<UserMission, UserMission> processor() {
        return item -> item;
    }

    @Bean
    public ItemWriter<UserMission> writer(){
        return list -> list.stream().filter(item -> item.getIsAccepted().equals(Constants.USER_MISSION_WAITING)).forEach(item -> {
            item.setIsAccepted(Constants.USER_MISSION_ACCEPTED);
            userMissionRepository.save(item);
        });
    }
}
