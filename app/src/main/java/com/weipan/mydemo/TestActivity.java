package com.weipan.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * 作者：Created by cc on 2017/5/3 11:39.
 * 邮箱：904359289@QQ.com.
 * 类 ：
 */

public class TestActivity extends Activity {

    private Pos pos;
    private ArrayList<FoodChoose> foodChoose;
    String PRINTER_IP = "192.168.6.101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView textView = (TextView) findViewById(R.id.tv_test);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                testPrint();

            }
        });

    }


    /**
     * 直接连接网络打印机打印，不需要tomcat,暂时不用
     */
    public void testPrint() {
        // 开启一个子线程
        new Thread() {
            public void run() {
                try {
                    pos = new Pos(PRINTER_IP, 9100, "GBK"); // 第一个参数是打印机网口IP

                    // 初始化打印机
                    pos.initPos();

                    // 初始化订单数据
                    initData();


                    pos.bold(true);
                    pos.printTabSpace(2);//两个tab距离，一个tab=4个汉字
                    pos.printWordSpace(1);//一个汉字距离
                    pos.printText("**测试店铺");

                    pos.printLocation(0);//打印位置调整
                    pos.printTextNewLine("-------------------------------");
                    pos.bold(false);
                    pos.printTextNewLine("订 单 号：1005199");
                    pos.printTextNewLine("用 户 名：15712937281");
                    pos.printTextNewLine("桌    号：3号桌");
                    pos.printTextNewLine("订单状态：订单已确认");
                    pos.printTextNewLine("订单日期：2016/2/19 12:34:53");
                    pos.printTextNewLine("付 款 人：线下支付（服务员：宝哥）");
                    pos.printTextNewLine("服 务 员：1001");
                    pos.printTextNewLine("订单备注：不要辣，少盐");
                    pos.printLine(2);

                    pos.printText("项目");
                    pos.printLocation(20, 1);
                    pos.printText("单价");
                    pos.printLocation(99, 1);
                    pos.printWordSpace(1);
                    pos.printText("数量");
                    pos.printWordSpace(3);
                    pos.printText("小计");
                    pos.printLocation(0);//打印位置调整
                    pos.printTextNewLine("-------------------------------");

                    for (FoodChoose foods : foodChoose) {
                        pos.printTextNewLine(foods.getFoodname());
                        pos.printLocation(20, 1);
                        pos.printText(foods.getFoodunitprice() + "");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText(foods.getFoodnum() + "");
                        pos.printWordSpace(3);
                    }

                    pos.printTextNewLine("----------------------------------------------");

                    //pos.printLocation(1);
                    pos.printLine(2);
                    // 打印二维码
                    //pos.qrCode("http://blog.csdn.net/haovip123");

                    // 切纸
                    //pos.feedAndCut();

                    pos.closeIOAndSocket();
                    pos = null;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }

    private void initData() {
        foodChoose = new ArrayList<FoodChoose>();

        for (int i = 0; i < 4; i++) {
            FoodChoose fb = new FoodChoose();
            fb.setFoodname("测试菜品" + i);
            fb.setFoodunitprice(90);
            fb.setFoodnum(2);
            foodChoose.add(fb);
        }
    }
}

