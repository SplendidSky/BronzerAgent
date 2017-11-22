package com.splendidsky.bronzeragent;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by 伟宸 on 2017/11/22.
 */

public class ThreadReadWriterIOSocket implements Runnable {

    private Socket client;
    private Context context;

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
            ConnectService.ioThreadFlag = true;
            while (ConnectService.ioThreadFlag){
                try {

                    if(!client.isConnected()){
                        break;
                    }

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
