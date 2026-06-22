package study.batch_boot_camp.section2.batch_listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SystemTerminationConfig {
    @Bean
    public Job systemTerminationJob(
            JobRepository jobRepository,
            Step scanningStep,
            Step eliminationStep
    ) {
        return new JobBuilder("systemTerminateJob", jobRepository)
                .start(scanningStep)
                .next(eliminationStep)
                .build();
    }

    @Bean
    public Step scanningStep(
            JobRepository jobRepository,
            PlatformTransactionManager txManager
            ) {
        return new StepBuilder("scanningStep", jobRepository)
                .tasklet(((contribution, chunkContext) -> {
                    String target = "판교서버실";
                    ExecutionContext stepContext = contribution.getStepExecution().getExecutionContext();
                    stepContext.put("targetSystem", target);
                    log.info("target system: {}", target);
                    return RepeatStatus.FINISHED;
                }),txManager)
                .listener(promotionListener())
                .build();
    }

    @Bean
    public Step eliminationStep(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            Tasklet eliminationTasklet
    ) {
        return new StepBuilder("eliminationStep",jobRepository)
                .tasklet(eliminationTasklet,txManager)
                .build();
    }

    @StepScope
    @Bean
    public Tasklet eliminationTasklet(
            @Value("#{jobExecutionContext['targetSystem']}") String targetSystem
    ) {
        return ((contribution, chunkContext) -> {
            log.info("target system: {}", targetSystem);
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener promotionListener = new ExecutionContextPromotionListener();
        promotionListener.setKeys(new String[]{"targetSystem"});
        return promotionListener;
    }
}
