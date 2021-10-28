package com.example.manipulator_control;

import static android.view.KeyEvent.KEYCODE_BACK;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class control extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        try {
            without_code();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Button button = (Button) findViewById(R.id.UpdateData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateValue();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            finish();
            return false;
        }
        return false;
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
                EditText speed = (EditText) findViewById(R.id.upstream_speed);
                speed.setText(Isspeed);

                EditText descent = (EditText) findViewById(R.id.rate_of_descent);
                String Getdescent = jsonObject.getString("ascending");
                String Istdescent = jsonObject.toJSONString(Getdescent).replace("\"", "");
                descent.setText(Istdescent);

                EditText Aascending = (EditText) findViewById(R.id.ascending);
                String GetAascending = jsonObject.getString("Aascending");
                String IsAascending = jsonObject.toJSONString(GetAascending).replace("\"", "");
                Aascending.setText(IsAascending);

                EditText Bascending = (EditText) findViewById(R.id.B_rate_of_descent);
                String GetBascending = jsonObject.getString("Bascending");
                String IsBascending = jsonObject.toJSONString(GetBascending).replace("\"", "");
                Bascending.setText(IsBascending);

                EditText crossbar = (EditText) findViewById(R.id.rail);
                String Getcrossbar = jsonObject.getString("crossbar");
                String Iscrossbar = jsonObject.toJSONString(Getcrossbar).replace("\"", "");
                crossbar.setText(Iscrossbar);
                MainActivity.Instance.valuesList.clear();
                Values values = new Values(speed.getText().toString(), descent.getText().toString(), Aascending.getText().toString(), Bascending.getText().toString(), crossbar.getText().toString());
                MainActivity.Instance.valuesList.add(values);
            } else {

            }
        } catch (Exception exception) {

        }
    }


    private void UpdateValue() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EditText speed = (EditText) findViewById(R.id.upstream_speed);
                    EditText descent = (EditText) findViewById(R.id.rate_of_descent);
                    EditText Aascending = (EditText) findViewById(R.id.ascending);
                    EditText Bascending = (EditText) findViewById(R.id.B_rate_of_descent);
                    EditText crossbar = (EditText) findViewById(R.id.rail);
                    File file = new File(Environment.getExternalStorageDirectory(),
                            "LocalAppLogs/Data.xml");
                    if (file.exists()) {
                        FileOutputStream fos = new FileOutputStream(file);
                        XmlSerializer serializer = Xml.newSerializer();
                        serializer.setOutput(fos, "UTF-8");
                        Map<String, Object> json = new HashMap<>();
                        json.put("To_speed", speed.getText().toString());
                        json.put("ascending", descent.getText().toString());
                        json.put("Aascending", Aascending.getText().toString());
                        json.put("Bascending", Bascending.getText().toString());
                        json.put("crossbar", crossbar.getText().toString());
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(json);
                        serializer.text(jsonObject.toString());
                        serializer.endDocument();
                        fos.close();
                        MainActivity.Instance.valuesList.clear();
                        Values values = new Values(speed.getText().toString(), descent.getText().toString(), Aascending.getText().toString(), Bascending.getText().toString(), crossbar.getText().toString());
                        MainActivity.Instance.valuesList.add(values);
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}