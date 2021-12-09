package com.example.manipulator_control;

import android.widget.Toast;

public class SetDat {

    public static byte[] SetData(int value) {
        byte Function = (byte) 0XA0;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：SetData", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }

    // 总机械臂
    public static byte[] master_control(int value) {
        byte Function = (byte) 0xA1;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：master_control", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }

    // A 机械臂
    public static byte[] Amaster_control(int value) {
        byte Function = (byte) 0x03;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：Amaster_control", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }

    // B 机械臂
    public static byte[] Bmaster_control(int value) {
        byte Function = (byte) 0x04;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：Bmaster_control", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }

    // 横杆控制
    public static byte[] Bar_control(int value) {
        byte Function = (byte) 0xA2;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：Bar_control", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }

    // Power
    public static byte[] turn_on(int value) {
        byte Function = (byte) 0x31;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：turn_on", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }

    // 急停
    public static byte[] StopAll(int value) {
        byte Function = (byte) 0x30;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：StopAll", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }


    // 开锁解锁急停
    public static byte[] unlocking(int value) {
        byte Function = (byte) 0xFF;
        byte target_device = 0X05;
        byte FH = (byte) 0xAA;
        byte transmitting_apparatus = (byte) 0XAF;
        short data = (short) value;
        byte b = (byte) (data & 0xff);//低八位
        byte a = (byte) ((data & 0xff00) >> 8);

        int SumData = (int) FH + transmitting_apparatus + target_device + Function + b + a;
        byte Values = (byte) SumData;
        byte[] ReturnValue = {FH, transmitting_apparatus, target_device, Function, a, b, Values};
        Logs.e("数据：unlocking", FH + "   " + transmitting_apparatus + "   " + target_device + "   " + Function + "   " + b + "   " + a + "   " + Values);
        return ReturnValue;
    }


//    public static byte[] shoryo(short number) {
//        int temp = number;
//        byte[] b = new byte[2];
//        for (int i = 0; i < b.length; i++) {
//            b[i] = new Integer(temp & 0xff).byteValue();
//            temp = temp >> 8;
//        }
//        return b;
//    }
}
