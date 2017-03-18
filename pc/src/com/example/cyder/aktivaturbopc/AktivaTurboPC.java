package com.example.cyder.aktivaturbopc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * PC←→スマホの通信を行うアプリです
 * @author kousuke nezu
 *
 */
public class AktivaTurboPC {

	public static void main(String[] args) throws IOException {
		try (final Socket socket = new Socket("localhost", 9999)) {
			final OutputStream outputStream = socket.getOutputStream();
			final BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(outputStream));
			writer.append("Hello!");
			writer.flush();
		}
	}
}
