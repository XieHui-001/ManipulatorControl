package com.example.manipulator_control;

import android.provider.FontRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetDat {
    private static String Values;
    private static List<String> Tovalue = new ArrayList<>();
    private static String ld;
    private static int index = 0;
    private static String dc;
    private static int Sumindex = 19;

    public static void GetDeviceValue(byte[] val, int lenght) {
        try {


            Values = null;
            index = 0;
            for (int i = 0; i < 35; i++) {
                char mt = (char) val[i];
                ld = mt + "";
                if (ld.equals("N")) {
                    index = i;
                    dc = val[i - 2] + "";
                }
                if (index > 0) {
                    if (i <= index + 21) {
                        Values = Values + "" + ld;
                    } else {
                        break;
                    }
                }
            }
            if (Values != null) {
                String QutNull = Values.replace(" ", "");
                String QutStr = QutNull.replace("￠", "");
                String QutNullStr = QutStr.replace("null", "");
                String Quts = QutNullStr.replace("ﾪ", "");
                String Quts_1 = Quts.replace("ﾯ", "");
                try {
                    String str3 = new String(Quts_1.getBytes("gbk"), "utf-8");
                    str3 = URLDecoder.decode(str3, "utf-8");
                    MainActivity.Instance.SetValue(str3, dc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception exception) {
            Logs.e("Error", exception);
        }
    }
}
