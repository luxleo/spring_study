package study.batch_boot_camp.section1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SystemTerminatingConfig {
    private final AtomicInteger processKilled = new AtomicInteger(0);
    private final int TERMINATION_TARGET = 10;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    @Bean
    public Job systemTerminatingJob() {
        return new JobBuilder("systemTerminationJob", jobRepository)
                .start(enterWorldStep())
                .next(receiveTask())
                .next(proceedTaskKillJob())
                .build();
    }

    @Bean
    public Step proceedTaskKillJob() {
        return new StepBuilder("proceedTaskKillStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    int killedProcesses = processKilled.incrementAndGet();
                    log.info("Killed process count: {} / {}", killedProcesses, TERMINATION_TARGET);
                    if (killedProcesses >= TERMINATION_TARGET) {
                        return RepeatStatus.FINISHED;
                    }
                    return RepeatStatus.CONTINUABLE;
                }), txManager)
                .build();
    }

    @Bean
    public Step receiveTask() {
        return new StepBuilder("receiveTaskStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    log.info("Got task to do");
                    log.info("number of process to kill count: {}", TERMINATION_TARGET);
                    return RepeatStatus.FINISHED;
                }), txManager)
                .build();
    }

    @Bean
    public Step enterWorldStep() {
        return new StepBuilder("enterWorldStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    log.info("System Terminating Job Started");
                    return RepeatStatus.FINISHED;
                }), txManager)
                .build();
    }
}
