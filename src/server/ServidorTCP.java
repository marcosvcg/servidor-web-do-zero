package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Servidor TCP implementado seguindo o passo a passo do artigo do DevMedia
 * 
 * leiam, o artigo esta sensacional!!
 * Link: https://www.devmedia.com.br/java-socket-entendendo-a-classe-socket-e-a-serversocket-em-detalhes/31894
 */

public class ServidorTCP {

	public static final int PORT = 33;
	
	public static void main(String[] args) {
		try {
			ServerSocket servidor = new ServerSocket(PORT);
			System.out.println("Servidor iniciado na porta " + PORT);

			Socket cliente = servidor.accept();
			System.out.println("Cliente conectado do IP " + cliente.getInetAddress().getHostAddress());
			
			PrintStream saida = new PrintStream(cliente.getOutputStream());
			Scanner entrada = new Scanner(cliente.getInputStream());
			
			while(entrada.hasNextLine()) {
				String mensagem = entrada.nextLine();
				
				if(mensagem.equals("/exit")) {
					System.out.println("Conexão encerrada!");
					saida.println("Conexão encerrada!");
					break;
				}
				
				System.out.println(mensagem);
			}
			
			entrada.close();
			saida.close();
			cliente.close();
			servidor.close();
			
		} catch (IOException e) {
			Logger.getLogger(ServidorTCP.class.getName())
				.log(Level.SEVERE, null, e);
		}
	}
}
