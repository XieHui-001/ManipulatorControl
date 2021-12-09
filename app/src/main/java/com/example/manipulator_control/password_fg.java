package com.example.manipulator_control;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link password_fg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class password_fg extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public password_fg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment password_fg.
     */
    // TODO: Rename and change types and number of parameters
    public static password_fg newInstance(String param1, String param2) {
        password_fg fragment = new password_fg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private View view;
    private EditText password;
    private RelativeLayout fg_password_rel;
    private Button login;
    private RelativeLayout fg_password_update_rel;


    // UPdate
    private EditText update_password_ed;
    private Button update_password_but;


    // contorl
    private RelativeLayout fg_password_but_rel;
    private Button yesbut;
    private Button nobut;

    private boolean isGetData = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_password_fg, container, false);
        return view;
    }

    private View initView() {
        password = view.findViewById(R.id.password);
        fg_password_rel = view.findViewById(R.id.fg_password_rel);
        login = view.findViewById(R.id.login);
        fg_password_update_rel = view.findViewById(R.id.fg_password_update_rel);
        update_password_ed = view.findViewById(R.id.update_password_ed);
        update_password_but = view.findViewById(R.id.update_password_but);
        fg_password_but_rel = view.findViewById(R.id.fg_password_but_rel);
        yesbut = view.findViewById(R.id.yesbut);
        nobut = view.findViewById(R.id.nobut);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.Instance.Password != null) {
                    if (password.getText().toString().equals(MainActivity.Instance.Password)) {
                        fg_password_rel.setVisibility(View.GONE);
                        fg_password_but_rel.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        update_password_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!update_password_ed.getText().toString().isEmpty()) {
                    // 提交修改
                    fg_password_update_rel.setVisibility(View.GONE);
                } else {
                    Toast.makeText(mActivity, "请正确输入密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        yesbut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 开
                        yesbut.setBackgroundColor(Color.RED);
                        MainActivity.Instance.OpenStart(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        yesbut.setBackgroundResource(R.drawable.linerxml);
                        MainActivity.Instance.OpenStart(0);
                        // 关
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        nobut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 开
                        nobut.setBackgroundColor(Color.RED);
                        MainActivity.Instance.OpenStart(2);
                        break;
                    case MotionEvent.ACTION_UP:
                        nobut.setBackgroundResource(R.drawable.linerxml);
                        MainActivity.Instance.OpenStart(0);
                        // 关
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        return null;
    }

    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //这里可以做网络请求或需要的数据刷新操作
            initView();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
}