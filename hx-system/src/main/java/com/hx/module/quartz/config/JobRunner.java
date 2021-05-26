package com.hx.module.quartz.config;

import com.hx.module.quartz.dao.QuartzJobDao;
import com.hx.module.quartz.domain.entity.QuartzJob;
import com.hx.module.quartz.util.QuartzManage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(JobRunner.class);
    private final QuartzJobDao quartzJobDao;
    private final QuartzManage quartzManage;

    /**
     * 项目启动时重新激活启用的定时任务
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        log.info("----- 开始注入定时任务 -----");
        List<QuartzJob> quartzJobs = quartzJobDao.findByIsPauseIsFalse();
        quartzJobs.forEach(quartzManage::addJob);
        log.info("----- 定时任务注入完成 -----");
    }
}
