package com.splendidsky.bronzeragent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 伟宸 on 2017/11/22.
 */

public class ConnectService extends Service {

    public static final String TAG = "ConnectService";
    public static Boolean mainThreadFlag = true;
    public static Boolean ioThreadFlag = true;
    ServerSocket serverSocket = null;
    final int SERVER_PORT = 10086;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(TAG, "androidService--->onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mainThreadFlag = true;
        new Thread()
        {
            public void run()
            {
                doListen();
            }
        }.start();
        return START_NOT_STICKY;
    }

    private void doListen()
    {
        serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(SERVER_PORT);
            while (mainThreadFlag)
            {
                Socket socket = serverSocket.accept();
                new Thread(new ThreadReadWriterIOSocket(this, socket)).start();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mainThreadFlag = false;
        ioThreadFlag = false;
        try
        {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

