package com.splendidsky.bronzeragent;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 伟宸 on 2017/11/22.
 */

public class ThreadReadWriterIOSocket implements Runnable {

    private Socket client;
    private Context context;

    private final int BUFFER_SIZE = 1024;

    public static final String TAG = "ReadWriterIOSocket";
    public static final String WELCOME_MSG = "Connected successful";

    public ThreadReadWriterIOSocket(Context context, Socket client)
    {
        this.client = client;
        this.context = context;
    }

    @Override
    public void run() {
        BufferedOutputStream out;
        BufferedInputStream in;

        try {

            out = new BufferedOutputStream(client.getOutputStream());
            in = new BufferedInputStream(client.getInputStream());

            out.write(WELCOME_MSG.getBytes());
            out.flush();

            ConnectService.ioThreadFlag = true;
            while (ConnectService.ioThreadFlag){
                try {

                    if(!client.isConnected()){
                        break;
                    }

                    byte[] bytes = new byte[BUFFER_SIZE];
                    int n = in.read(bytes);
                    String cmd = new String(bytes, 0, n);
                    Log.d(TAG, cmd);

                    if (cmd.equalsIgnoreCase("exit")) {
                        ConnectService.ioThreadFlag = false;
                    }

                    String response = RemoteCmdHandler.handle(cmd);
                    out.write(response.getBytes());
                    out.flush();

                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (client != null)
                {
                    client.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
