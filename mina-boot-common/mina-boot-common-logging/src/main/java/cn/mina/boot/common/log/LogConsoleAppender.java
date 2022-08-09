package cn.mina.boot.common.log;

/**
 * @author Created by haoteng on 2022/6/17.
 */
public class LogConsoleAppender extends LogBaseAppender {

    public void output(String log) {
        System.out.println(log);
    }



}
