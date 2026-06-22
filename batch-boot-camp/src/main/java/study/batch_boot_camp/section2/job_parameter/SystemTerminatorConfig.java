package study.batch_boot_camp.section2.job_parameter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SystemTerminatorConfig {

    @Bean
    public Job systemTerminatorJob(JobRepository jobRepository, Step terminatorStep, Step terminatorStepWithPojo) {
        return new JobBuilder("systemTerminatorJob", jobRepository)
                .start(terminatorStep)
                .next(terminatorStepWithPojo)
                .build();
    }

    @Bean
    public Step terminatorStep(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            Tasklet terminatorTasklet
            ) {
        return new StepBuilder("terminatorStep", jobRepository)
                .tasklet(terminatorTasklet, txManager)
                .build();
    }

    @Bean
    public Step terminatorStepWithPojo(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            Tasklet terminatorTaskletWithPojo
    ) {
        return new StepBuilder("terminatorStepWithPojo", jobRepository)
                .tasklet(terminatorTaskletWithPojo, txManager)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet terminatorTasklet(
            @Value("#{jobParameters['terminatorId']}") String terminatorId,
            @Value("#{jobParameters['targetCount']}") int targetCount
    ) {
        return ((contribution, chunkContext) -> {
            log.info("System Information- terminatorId: {}, targetCount: {}", terminatorId, targetCount);
            log.info("starting terminating step...");
            for(int i=0; i<targetCount; i++) {
                log.info("terminating process: {}/{}", i,targetCount);
            }
            log.info("terminating step finished");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Tasklet terminatorTaskletWithPojo(SystemTerminatorParameters params) {
        return ((contribution, chunkContext) -> {
            log.info("System Information- terminatorId: {}, targetCount: {}", params.getTerminatorId(), params.getTargetCount());
            log.info("starting terminating step...");
            for (int i = 0; i < params.getTargetCount(); i++) {
                log.info("terminating process: {}/{}", i, params.getTargetCount());
            }
            log.info("terminating step finished");
            return RepeatStatus.FINISHED;
        });
    }

}
