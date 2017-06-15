package com.example.clientapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.aidlapp.ISumInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEt1;
    private EditText mEt2;
    /**
     * result
     */
    private TextView mTvResult;
    /**
     * 计算
     */
    private Button mBtnAdd;
    private ISumInterface iSumInterface;

    private ServiceConnection conn = new ServiceConnection() {
        //绑定上服务
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取远程服务
            iSumInterface = ISumInterface.Stub.asInterface(service);
            Log.i("chenqi","服务绑定成功"+iSumInterface);
        }

        //断开服务
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //回收资源
            iSumInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();
    }

    private void initView() {
        mEt1 = (EditText) findViewById(R.id.et_1);
        mEt2 = (EditText) findViewById(R.id.et_2);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                try {
                    int sum = iSumInterface.sum(
                            Integer.valueOf(mEt1.getText().toString()),
                            Integer.valueOf(mEt2.getText().toString()));
                    mTvResult.setText(sum + "");

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.administrator.aidlapp",
                "com.example.administrator.aidlapp.MyService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
