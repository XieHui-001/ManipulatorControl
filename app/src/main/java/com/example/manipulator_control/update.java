package com.example.manipulator_control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class update extends AppCompatActivity {
    private EditText password;
    private Button update_pwd;
    private EditText oud_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
    }

    private View initView() {
        password = findViewById(R.id.password);
        update_pwd = findViewById(R.id.update_pwd);
        oud_password = findViewById(R.id.oud_password);
        update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (oud_password.getText().toString().equals(MainActivity.Instance.Password)) {
                    CreaPassowrdXml(password.getText().toString());
                    Toast.makeText(update.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(update.this, "原有密码输入错误", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return null;
    }

    private void CreaPassowrdXml(String val) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(Environment.getExternalStorageDirectory(),
                            "LocalAppLogs/User_Password.xml");
                    if (file.exists()) {
                        file.delete();
                        FileOutputStream fos = new FileOutputStream(file);
                        XmlSerializer serializer = Xml.newSerializer();
                        serializer.setOutput(fos, "UTF-8");
                        Map<String, Object> json = new HashMap<>();
                        json.put("Password", val);
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(json);
                        serializer.text(jsonObject.toString());
                        serializer.endDocument();
                        fos.close();
                        MainActivity.Instance.Password = val;
                    } else {
                        FileOutputStream fos = new FileOutputStream(file);
                        XmlSerializer serializer = Xml.newSerializer();
                        serializer.setOutput(fos, "UTF-8");
                        Map<String, Object> json = new HashMap<>();
                        json.put("Password", "admin");
                        JSONObject jsonObject;
                        jsonObject = new JSONObject(json);
                        serializer.text(jsonObject.toString());
                        serializer.endDocument();
                        fos.close();
                        MainActivity.Instance.Password = "admin";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(update.this, "请使用默认密码    admin   ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}