package mri.socket.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ServeurTCP {
	private static final String msgAEnvoyer = "fin";
	private static String charset = "UTF-8";

	private static int nbThread = 3;


	public static void main(String[] args) {
		if (args.length >= 2) {
			charset = args[1];
		}

		// Attente des connexions sur le port 9999
		int portEcoute = 9999;
		ServerSocket socketServeur = null;
		try {
			socketServeur = new ServerSocket(portEcoute);
		} catch (IOException e) {
			System.out.println("problème creation socketServer");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Executor threadPool = Executors.newFixedThreadPool(nbThread);
		
		// Traitement des exceptions

		// Dans une boucle, pour chaque socket clientes, appeler
		// traiterSocketCliente
		System.out.println("Attente de client ...");
		while (true) {
			Socket socketClient = null;
			try {
				socketClient = socketServeur.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Client avec l'adresse : "
					+ socketClient.getInetAddress() + " connecté.");
			
			threadPool.execute(new TraiteClient(socketClient, charset));


		}
	}

	

}