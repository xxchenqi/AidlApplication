package com.example.administrator.aidlapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chenqi on 2017/6/6.
 */

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new ISumInterface.Stub() {
        @Override
        public int sum(int a, int b) throws RemoteException {
            Log.i("chenqi", "收到了远程的请求,输入的是" + a + "和" + b);
            return a + b;
        }
    };

}
