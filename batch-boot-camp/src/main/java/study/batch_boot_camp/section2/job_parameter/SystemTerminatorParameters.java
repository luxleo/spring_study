package study.batch_boot_camp.section2.job_parameter;

import lombok.Getter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@StepScope
@Component
public class SystemTerminatorParameters {
    @Value("#{jobParameters['terminatorId']}")
    private String terminatorId;
    private int targetCount;
    private final LocalDate startDate;

    public SystemTerminatorParameters(@Value("#{jobParameters['startDate']}") LocalDate startDate) {
        this.startDate = startDate;
    }

    @Value("#{jobParameters['targetCount']}")
    public void setTargetCount(int targetCount) {
        this.targetCount = targetCount;
    }
}
