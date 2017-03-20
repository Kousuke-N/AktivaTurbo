package com.example.cyder.aktivaturbopc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * PC←→スマホの通信を行うアプリです
 * @author kousuke nezu
 *
 */
public class AktivaTurboPC {

	public static void main(String[] args) throws IOException {
		
		String sendData = null;
		for(int i = 0; i < args.length; i++){
			if(i == 0){
				sendData = args[i];
			}
			sendData += args[i];
		}
		System.out.println(sendData);
	
//        try {
//            
//        } catch (final IOException e) {
//            throw new RuntimeException(e);
//        }

		while(true){
			try (Socket socket = new Socket("localhost", 9999)) {
				// 以下、クライアントからの要求発生後
				InputStream inputStream = socket.getInputStream(); //クライアントから数値を受信
				String data = readAllLine(inputStream);
				System.out.println("データを受信しました");
				// ストリームを閉じる
				inputStream.close();
				socket.close();
			}
		}
	}
        
        private static String readAllLine(final InputStream inputStream) throws IOException {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
	
	public boolean checkJson(String data){
		boolean result = false;
		
		return result;
	}
}
