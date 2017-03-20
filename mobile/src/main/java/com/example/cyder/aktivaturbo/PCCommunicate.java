package com.example.cyder.aktivaturbo;

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
    /** スレッドが実行中かどうかを示す変数 */
    private boolean running = false;
    /** ポート番号 */
    private final int mPort;
    /** ハンドラ */
    private final Handler mHandler;

    /** データを受信するスレッド */
    private final Runnable mAcceptTask = new Runnable() {
        public void run() {
            running = true;
            /** サーバソケット */
            final ServerSocket serverSocket;

            try {
                // サーバのインスタンス化
                serverSocket = new ServerSocket(mPort);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }

            try {
                while (running) {
                    /** ソケット */
                    final Socket socket;
                    // ソケットのインスタンス化
                    socket = serverSocket.accept();
                    final InputStream inputStream = socket.getInputStream();
                    final String data = readAllLine(inputStream);
                    mHandler.post(new Runnable() {
                        public void run() {
                            onReceivedData(data);
                            if ( data == null || data.length() == 0 ){
                                System.out.println("0");
                            } else {
                                System.out.println("1");
                            }

                        }
                    });
                }
            } catch (final IOException e){
                throw new RuntimeException(e);
            } finally {
              try {
                  serverSocket.close();
              }catch (final IOException e){
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

    public void setRunning(boolean running){
        this.running = running;
    }
}
