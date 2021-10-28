package com.example.manipulator_control;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

public class NowData {

//    public void Decode(byte[] rbuf, int len) {
//
//        int needMaxLen = 60;
//        if (len < needMaxLen) {
//            return;
//        }
//
//        int firstIdx = -1;
//        {
//            for (int j = 0; j < len - 1; j++) {
//                boolean b = rbuf[j] == (byte) 0xAA && rbuf[j + 1] == (byte) 0xAF;
//                if (b) {
//                    firstIdx = j;
//                    break;
//                }
//            }
//            if (firstIdx < 0) {
//                return;
//            }
//
//            if (len - firstIdx < needMaxLen) {
//                return;
//            }
//
//            boolean crc = false;
//            byte crcVal = rbuf[firstIdx + needMaxLen - 1];
//            byte cCrc = 0;
//            for (int j = firstIdx; j < firstIdx + needMaxLen - 1; j++) {
//                cCrc += rbuf[j];
//            }
//            crc = crcVal == cCrc;
//            if (!crc) {
//                return;
//            }
//
//        }
//
//        SerData serData = new SerData();
//        serData.Init();
//
//        int idFirstIndx = firstIdx + 2;
//        int idLastIndx = idFirstIndx + 12;
//        {
//            byte[] dBuf = Arrays.copyOfRange(rbuf, idFirstIndx, idLastIndx);
//            serData.ID = this.IDData(dBuf);//id
//        }
//
//        int dFirstIndx = idLastIndx;
//        int dLastIndx = dFirstIndx + 14;
//        {
//            byte[] dBuf = Arrays.copyOfRange(rbuf, dFirstIndx, dLastIndx);
//            serData.DianROng = this.DianRongData(dBuf);//电容
//            MyErrorLog.e("电容数据", serData.DianROng);
//        }
//
//
//        int wenduFirstIndx = dLastIndx;
//        int wenduLastIndx = wenduFirstIndx + 31;
//        {
//            byte[] dBuf = Arrays.copyOfRange(rbuf, wenduFirstIndx, wenduLastIndx);
//            String s = new String(dBuf);
//            serData.Temptur = s;//温度
//        }
//
//        {
//            //跳过 crc
//            byte[] dBuf = Arrays.copyOfRange(rbuf, wenduLastIndx + 1, len);
//            this.Decode(dBuf, dBuf.length);
//        }
//
//        //发送按钮状态
//        EventBus.getDefault().post(new SwitchStatusEvent(serData.ID, serData.DianROng, serData.DateTime, serData.Temptur));
//
//    }
}
