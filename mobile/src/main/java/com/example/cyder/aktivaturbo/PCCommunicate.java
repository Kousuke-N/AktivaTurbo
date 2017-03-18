package com.example.cyder.aktivaturbo;

import android.app.Service;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * pcと通信をするクラスです
 * Created by kousuke nezu on 2017/03/18.
 */

public abstract class PCCommunicate {
    /** ポート番号 */
    private final int mPort;
    /** ハンドラ */
    private final Handler mHandler;
    /** データを受信するスレッド */
    private final Runnable mAcceptTask = new Runnable() {
        public void run() {
            final ServerSocket serverSocket;

            try {
                serverSocket = new ServerSocket(mPort);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }

            final Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    serverSocket.close();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                final InputStream inputStream = socket.getInputStream();
                final String data = readAllLine(inputStream);

                mHandler.post(new Runnable() {
                    public void run() {
                        onReceivedData(data);
                    }
                });
            } catch (final IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    public PCCommunicate(final int port) {
        mPort = port;
        mHandler = new Handler();
    }

    public void start() {
        Executors.newSingleThreadExecutor().execute(mAcceptTask);
    }

    protected abstract void onReceivedData(String data);

    private static String readAllLine(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
