//package cn.mina.boot.example.job.task;
//
//import cn.mina.boot.job.xxl.anno.JobAutoRegister;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Slf4j
//public class XxlJobHandler {
//
//    @XxlJob("mina-boot-example-test-job")
//    public void execute(String param) throws Exception {
//// 接收参数代码
//        String param1 = XxlJobHelper.getJobParam();
//        log.info("-----------{}", param1);
//    }
//
//    @XxlJob("mina-boot-example-test-job1")
//    @JobAutoRegister(cron = "* * * * * ?")
//    public void execute1(String param) throws Exception {
//// 接收参数代码
//        String param1 = XxlJobHelper.getJobParam();
//        log.info("-----------{}", param1);
//    }
//}
