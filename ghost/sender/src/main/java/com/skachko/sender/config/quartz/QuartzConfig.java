package com.skachko.sender.config.quartz;

import com.skachko.sender.EventHandler;
import com.skachko.sender.config.GhostProperties;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
@ConditionalOnProperty(value = "ghost.sender.enabled", havingValue = "true")
public class QuartzConfig {

    private final GhostProperties properties;
    private final ApplicationContext ctx;

    public QuartzConfig(GhostProperties properties, ApplicationContext context) {
        this.properties = properties;
        this.ctx = context;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(this.ctx);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(@Qualifier("eventHandlerJobTriggerBean") Trigger trigger) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactory.setTriggers(trigger);

        return schedulerFactory;
    }


    @Bean (name = "eventHandlerJob")
    public JobDetailFactoryBean eventHandlerJob(){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setDurability(true);
        factoryBean.setJobClass(EventHandler.class);
        factoryBean.setName("eventHandler");
        return factoryBean;
    }


    @Bean(name = "eventHandlerJobTriggerBean")
    public SimpleTriggerFactoryBean eventHandlerJobTrigger(@Qualifier("eventHandlerJob") JobDetail jobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(this.properties.getRate() == 0 ? 10000: this.properties.getRate());
        factoryBean.setName("eventHandlerJobTrigger");
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }



}
