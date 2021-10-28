package com.example.manipulator_control;

import static android.view.KeyEvent.KEYCODE_BACK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.greenrobot.eventbus.EventBus;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.com.prolific.driver.pl2303.PL2303Driver;

public class MainActivity extends AppCompatActivity {
    private PL2303Driver mSerial;
    private PL2303Driver.BaudRate mBaudrate = PL2303Driver.BaudRate.B9600;
    private PL2303Driver.DataBits mDataBits = PL2303Driver.DataBits.D8;
    private PL2303Driver.Parity mParity = PL2303Driver.Parity.NONE;
    private PL2303Driver.StopBits mStopBits = PL2303Driver.StopBits.S1;
    private PL2303Driver.FlowControl mFlowControl = PL2303Driver.FlowControl.OFF;
    private static final String ACTION_USB_PERMISSION = "com.prolific.pl2303hxdsimpletest.USB_PERMISSION";
    private String TAG = "PL2303HXD_APLog";
    private Handler handler = new Handler();
    private Runnable runnable;
    public List<Values> valuesList = new ArrayList();

    public static MainActivity Instance = null;

    public MainActivity() {
        MainActivity.Instance = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        verifyStoragePermissions(MainActivity.this);
        CreaXml();
        CreatFile();
        Button control = (Button) findViewById(R.id.But_control);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Href = new Intent();
                Href.setClass(MainActivity.this, com.example.manipulator_control.control.class);
                startActivity(Href);
            }
        });
        // 掉头
        Button retreat = (Button) findViewById(R.id.retreat);
        retreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).speed);
                        String SumSpeed = "-" + Speed;
                        int Data = Integer.parseInt(SumSpeed);
                        mSerial.write(SetDat.SetData(Data), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
//                    onStop();
//                    onStart();
//                    onResume();
//                    for (int i = 0; i < valuesList.size(); i++) {
//                        int Speed = Integer.parseInt(valuesList.get(i).speed);
//                        String SumSpeed = "-" + Speed;
//                        int Data = Integer.parseInt(SumSpeed);
//                        mSerial.write(SetDat.SetData(Data), 7);
//                        break;
//                    }
                }
            }
        });

        // 停止
        Button Stop = (Button) findViewById(R.id.stop);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    mSerial.write(SetDat.SetData(0));
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
//                    onStop();
//                    onStart();
//                    onResume();
//                    mSerial.write(SetDat.SetData(0));
                }
            }
        });
        // 前进
        Button advance = (Button) findViewById(R.id.advance);
        advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).speed);
                        mSerial.write(SetDat.SetData(Speed), 7);
                        mSerial.end();
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
//                    onStop();
//                    onStart();
//                    onResume();
//                    for (int i = 0; i < valuesList.size(); i++) {
//                        int Speed = Integer.parseInt(valuesList.get(i).speed);
//                        mSerial.write(SetDat.SetData(Speed), 7);
//                        mSerial.end();
//                        break;
//                    }
                }

            }
        });

        // 机械臂下降
        Button Arm_descent = (Button) findViewById(R.id.Arm_descent);
        Arm_descent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).ascending);
                        String SumSpeed = "-" + Speed;
                        int Data = Integer.parseInt(SumSpeed);
                        mSerial.write(SetDat.master_control(Data), 7);
                        mSerial.end();
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 机械臂暂停
        Button Arm_suspension = (Button) findViewById(R.id.Arm_suspension);
        Arm_suspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    mSerial.write(SetDat.master_control(0));
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 机械臂上升
        Button Lifting_of_manipulator = (Button) findViewById(R.id.Lifting_of_manipulator);
        Lifting_of_manipulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).ascending);
                        mSerial.write(SetDat.master_control(Speed), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // A机械臂下降
        ImageView A_arm_down = (ImageView) findViewById(R.id.ANewxj);
        A_arm_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).Aascending);
                        String SumSpeed = "-" + Speed;
                        int Data = Integer.parseInt(SumSpeed);
                        mSerial.write(SetDat.Amaster_control(Data), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // A机械臂暂停
        ImageView A_arm_suspension = (ImageView) findViewById(R.id.ANewStop);
        A_arm_suspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    mSerial.write(SetDat.Amaster_control(0));
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // A机械臂上升
        ImageView A_rising_arm = (ImageView) findViewById(R.id.ANewss);
        A_rising_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).Aascending);
                        mSerial.write(SetDat.Amaster_control(Speed), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // B机械臂下降
        ImageView B_arm_down = (ImageView) findViewById(R.id.BNewxj);
        B_arm_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).Bascending);
                        String SumSpeed = "-" + Speed;
                        int Data = Integer.parseInt(SumSpeed);
                        mSerial.write(SetDat.Bmaster_control(Data), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // B机械臂暂停
        ImageView B_arm_suspension = (ImageView) findViewById(R.id.BNewStop);
        B_arm_suspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    mSerial.write(SetDat.Bmaster_control(0));
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // B机械臂上升
        ImageView B_arm_rise = (ImageView) findViewById(R.id.BNewss);
        B_arm_rise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).Bascending);
                        mSerial.write(SetDat.Bmaster_control(Speed), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // 横杆向内
        Button Within_the_bar_to = (Button) findViewById(R.id.Rail_outward);
        Within_the_bar_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).crossbar);
                        String SumSpeed = "-" + Speed;
                        int Data = Integer.parseInt(SumSpeed);
                        mSerial.write(SetDat.Bar_control(Data), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 横杆停止
        Button Bar_to_stop = (Button) findViewById(R.id.Bar_to_stop);
        Bar_to_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    mSerial.write(SetDat.Bar_control(0));
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 横杆向外
        Button Rail_outward = (Button) findViewById(R.id.Within_the_bar_to);
        Rail_outward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < valuesList.size(); i++) {
                        int Speed = Integer.parseInt(valuesList.get(i).crossbar);
                        mSerial.write(SetDat.Bar_control(Speed), 7);
                        break;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 急停
        Button scram = (Button) findViewById(R.id.scram);
        scram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSerial.isConnected()) {
                    for (int i = 0; i < 3; i++) {
                        if (i == 0) {
                            mSerial.write(SetDat.SetData(0));
                        } else if (i == 1) {
                            mSerial.write(SetDat.master_control(0));
                        } else if (i == 2) {
                            mSerial.write(SetDat.Bar_control(0));
                            break;
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "串口没有正常连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSerial = new PL2303Driver((UsbManager) getSystemService(Context.USB_SERVICE),
                this, ACTION_USB_PERMISSION);

        // check USB host function.
        if (!mSerial.PL2303USBFeatureSupported()) {
            Logs.d(TAG, "No Support USB host API");
            mSerial = null;

        }
        handler.postDelayed(runnable, 1000);


    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            return true;
        }
        return true;
    }

    protected void onStop() {
        handler.removeCallbacks(runnable);//停止计时器
        Log.d(TAG, "Enter onStop");
        super.onStop();
        Log.d(TAG, "Leave onStop");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Enter onDestroy");

        if (mSerial != null) {
            mSerial.end();
            mSerial = null;
        }
        EventBus.getDefault().unregister(this);//注销消息监听
        super.onDestroy();
        Log.d(TAG, "Leave onDestroy");
    }

    public void onStart() {
        Logs.d(TAG, "Enter onStart");
        super.onStart();
        Logs.d(TAG, "Leave onStart");
    }


    public void onResume() {
        Logs.d(TAG, "Enter onResume");
        super.onResume();
        String action = getIntent().getAction();
        Logs.d(TAG, "onResume:" + action);

        //if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action))
        if (!mSerial.isConnected()) {
            if (true) {
                Logs.d(TAG, "New instance : " + mSerial);
            }

            if (!mSerial.enumerate()) {
                Toast.makeText(this, "没有接收到有效设备信息，请刷新", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Logs.d(TAG, "onResume:enumerate succeeded!");
            }
            try {
                Thread.sleep(1500);
                openUsbSerial();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }//if isConnected
        //Toast.makeText(this, "attached", Toast.LENGTH_SHORT).show();

        Logs.d(TAG, "Leave onResume");
    }

    private void openUsbSerial() {
        try {


            Logs.d(TAG, "Enter  openUsbSerial");

            if (mSerial == null) {
                Logs.d(TAG, "No mSerial");
                return;

            }
            if (mSerial.isConnected()) {
                if (true) {
                    Logs.d(TAG, "openUsbSerial : isConnected ");
                }
                int baudRate = 115200;
                switch (baudRate) {
                    case 9600:
                        mBaudrate = PL2303Driver.BaudRate.B9600;
                        break;
                    case 19200:
                        mBaudrate = PL2303Driver.BaudRate.B19200;
                        break;
                    case 115200:
                        mBaudrate = PL2303Driver.BaudRate.B115200;
                        break;
                    default:
                        mBaudrate = PL2303Driver.BaudRate.B9600;
                        break;
                }
                Log.d(TAG, "baudRate:" + baudRate);
                // if (!mSerial.InitByBaudRate(mBaudrate)) {
                if (!mSerial.InitByBaudRate(mBaudrate, 700)) {
                    if (!mSerial.PL2303Device_IsHasPermission()) {
                        Toast.makeText(this, "cannot open, maybe no permission", Toast.LENGTH_SHORT).show();
                    }
                    if (mSerial.PL2303Device_IsHasPermission() && (!mSerial.PL2303Device_IsSupportChip())) {
                        Toast.makeText(this, "cannot open, maybe this chip has no support, please use PL2303HXD / RA / EA chip.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "connected : OK", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //要做的事情
                            if (!mSerial.isConnected()) {
                                onStart();
                                onResume();
                            }
                            ReadDtata();
                            handler.postDelayed(this, 1000);
                        }
                    };
                    handler.postDelayed(runnable, 1000);
                }
            }//isConnected
            else {
                Toast.makeText(this, "Connected failed, Please plug in PL2303 cable again!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception exception) {
            Logs.e("Open USB Serial Error : ", exception);
        }
    }

    private void ReadDtata() {
        try {

            int len;
            // byte[] rbuf = new byte[4096];
            byte[] rbuf = new byte[512];
            StringBuffer sbHex = new StringBuffer();
            Log.d(TAG, "Enter readDataFromSerial");

            if (null == mSerial)
                return;

            if (!mSerial.isConnected())
                return;

            len = mSerial.read(rbuf);
            if (len < 0) {
                Logs.e("len < 0:", "Fail to bulkTransfer(read data)");
                Log.d(TAG, "Fail to bulkTransfer(read data)");
                return;
            }

            GetDat.GetDeviceValue(rbuf, len);
        } catch (Exception exception) {
            Logs.e("Read bug Eroor", exception);
        }

    }

    // 动态权限
    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    // 创建
    private void CreaXml() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(Environment.getExternalStorageDirectory(),
                            "LocalAppLogs/Data.xml");
                    if (!file.exists()) {
                        FileOutputStream fos = new FileOutputStream(file);
                        XmlSerializer serializer = Xml.newSerializer();
                        serializer.setOutput(fos, "UTF-8");
                        Map<String, Object> json = new HashMap<>();
                        json.put("To_speed", 500);
                        json.put("ascending", 999);
                        json.put("Aascending", 999);
                        json.put("Bascending", 999);
                        json.put("crossbar", 500);
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(json);
                        serializer.text(jsonObject.toString());
                        serializer.endDocument();
                        fos.close();
                        Values values = new Values("500", "999", "999", "999", "500");
                        valuesList.add(values);
                    } else {
                        without_code();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 读取Xml 获取数据
    private void without_code() throws FileNotFoundException {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "LocalAppLogs/Data.xml");
            File dirs_ = new File(file.getPath());
            if (dirs_.exists()) {
                FileInputStream fileIS = null;
                try {
                    fileIS = new FileInputStream(dirs_.getPath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                StringBuffer sb = new StringBuffer();
                BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
                String readString = new String();
                while (true) {
                    try {
                        if (!((readString = buf.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sb.append(readString);
                }
                String GetXmlValues = sb.toString();
                JSONObject jsonObject = null;
                jsonObject = JSON.parseObject(GetXmlValues);
                //
                String GetIsspeed = jsonObject.getString("To_speed");
                String Isspeed = jsonObject.toJSONString(GetIsspeed).replace("\"", "");

                String Getdescent = jsonObject.getString("ascending");
                String Istdescent = jsonObject.toJSONString(Getdescent).replace("\"", "");

                String GetAascending = jsonObject.getString("Aascending");
                String IsAascending = jsonObject.toJSONString(GetAascending).replace("\"", "");


                String GetBascending = jsonObject.getString("Bascending");
                String IsBascending = jsonObject.toJSONString(GetBascending).replace("\"", "");


                String Getcrossbar = jsonObject.getString("crossbar");
                String Iscrossbar = jsonObject.toJSONString(Getcrossbar).replace("\"", "");


                valuesList.clear();
                Values values = new Values(Isspeed, Istdescent, IsAascending, IsBascending, Iscrossbar);
                valuesList.add(values);
            } else {

            }
        } catch (Exception exception) {

        }
    }

    public void SetValue(String val, String dl) {
        try {
            TextView Name = (TextView) findViewById(R.id.DeviceName);
            if (val != null && val.length() >= 19) {
                Name.setText(val);
            }
            Setdl(dl);
        } catch (Exception exception) {
            Logs.e("UI View Error", exception);
        }


    }

    public void Setdl(String val) {
        TextView dl = (TextView) findViewById(R.id.dl);
        ImageView img = (ImageView) findViewById(R.id.dl_img);

        if (val != null && val.length() > 0) {
            dl.setText("当前电量 " + val);
        }
        int count = Integer.parseInt(val);
        if (count <= 10) {
            img.setImageResource(R.drawable.dc_3);
        } else if (count > 10 && count <= 50) {
            img.setImageResource(R.drawable.dc_2);
        } else if (count > 50) {
            img.setImageResource(R.drawable.dc_1);
        }
    }

    public void CreatFile() {
        String MYLOG_PATH_SDCARD_DIR = "/sdcard/LocalAppLogs/Control";
        File dirsFile = new File(MYLOG_PATH_SDCARD_DIR);
        if (!dirsFile.exists()) {
            dirsFile.mkdirs();
        }
    }
    // 读取

}