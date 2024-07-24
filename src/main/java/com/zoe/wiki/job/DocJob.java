package com.zoe.wiki.job;

import com.zoe.wiki.service.DocService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DocJob {

   private static final Logger LOG = LoggerFactory.getLogger(DocJob.class);
   @Resource
   private DocService docService;

   /**
    * 每隔30秒更新电子书信息
    */
   @Scheduled(cron = "5/30 * * * * ?")
   public void cron()  {
       docService.updateEbookInfo();
   }

}
