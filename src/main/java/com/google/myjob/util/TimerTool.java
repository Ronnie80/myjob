/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package main.java.com.google.myjob.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTool {

    private static final String errorTitle = "程序出错，请查看";

    private static final String title = "准备买回家车票啦！";

    private static int count = 0;

    private static int errCount = 0;

    public static void timerTest() {
        Timer timerTask = new Timer();

        timerTask.schedule(new TimerTask() {

            @Override
            public void run() {
                Integer time = Integer.valueOf(new SimpleDateFormat("HHmm").format(new Date()));
//                if (time < 600 || time > 2330) {
//                    System.out.println("休眠中");
//                    return;
//                }
                System.out.println("开始查询！");
                String result = Pa.pa12306();
                String content;
                System.out.println(count);
                if (count > 10) {
                    System.exit(0);
                }
                try {
                    if (result != null && result.contains("网络")) {
                        if (errCount > 5) {
                            return;
                        }
                        if (errCount % 100 == 0) {
                            MailSender.sender(Const.WJQ_EMAIL, errorTitle, "错误 " + errCount + " 次");
                        }
                        System.out.println("爬取失败，请检查程序！");
                        MailSender.sender(Const.WJQ_EMAIL, errorTitle, result);
                        errCount++;
                    }
                    if (result != null && (!result.contains("列车运行图调整") || !result.contains("暂停发售"))) {
                        content = "快上订票助手，1月21日、22日、23日回家的票！";
                        System.out.println("有票啦！");
                        MailSender.sender(Const.PXL_EMAIL, title, content);
                        MailSender.sender(Const.WJQ_EMAIL, title, content + "/t/n" + result);
                        count++;
                    } else if (result != null && result.contains("G25")) {
                        content = "发现高铁票，请上12306查看！";
                        MailSender.sender(Const.PXL_EMAIL, title, content);
                        MailSender.sender(Const.WJQ_EMAIL, title, content);
                        count++;
                    }
                } catch (Exception e) {
                    try {
                        MailSender.sender(Const.WJQ_EMAIL, errorTitle, e.getMessage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }, 0, 60000);
    }

    public static void main(String[] args) {
        timerTest();
    }
}
