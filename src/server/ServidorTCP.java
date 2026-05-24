package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Servidor TCP implementado e adaptado seguindo o artigo do DevMedia como referencia
 * 
 * leiam, o artigo esta sensacional!!
 * Link: https://www.devmedia.com.br/java-socket-entendendo-a-classe-socket-e-a-serversocket-em-detalhes/31894
 */

public class ServidorTCP implements Runnable {

	public static final int PORT = 33;
	private Socket cliente;

	public ServidorTCP(Socket cliente) {
		this.cliente = cliente;
	}
	
	public static void main(String[] args) {
		try {
			ServerSocket servidor = new ServerSocket(PORT);
			System.out.println("Servidor iniciado na porta " + PORT);

			while(true) {
				Socket cliente = servidor.accept();
				ServidorTCP handler = new ServidorTCP(cliente);
				
				Thread conexao = new Thread(handler);
				conexao.start();
			}

			//servidor.close();

		} catch (IOException e) {
			Logger.getLogger(ServidorTCP.class.getName())
			.log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void run() {
		try {

			String clienteIP = cliente.getInetAddress().getHostAddress();
			System.out.println("Cliente conectado do IP " + clienteIP);

			Scanner entrada = new Scanner(cliente.getInputStream());
			PrintStream saida = new PrintStream(cliente.getOutputStream());

			while(entrada.hasNextLine()) {
				String mensagem = entrada.nextLine();

				if(mensagem.equals("/exit")) {
					System.out.printf("\nConexão com [%s] encerrada", clienteIP);
					saida.println("Conexão encerrada!");
					break;
				}

				System.out.printf("\n[%s]: %s", clienteIP, mensagem);
			}

			entrada.close();
			saida.close();
			cliente.close();
			
		} catch (IOException e) {
			Logger.getLogger(ServidorTCP.class.getName())
			.log(Level.SEVERE, null, e);
		}
	}
}
