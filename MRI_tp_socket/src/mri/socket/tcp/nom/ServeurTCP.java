package mri.socket.tcp.nom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServeurTCP {
	private static final String msgAEnvoyer = "fin";

	public static void main(String[] args) {

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
			traiterSocketCliente(socketClient);

		}
	}

	public static void traiterSocketCliente(Socket socketVersUnClient) {
		// Créer printer et reader
		BufferedReader reader = null;
		PrintWriter printer = null;
		try {
			reader = creerReader(socketVersUnClient);
			printer = creerPrinter(socketVersUnClient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Tant qu’il y’a un message à lire via recevoirMessage
		String msgClient = "";

		String nomClient = "";
		try {
			nomClient = avoirNom(reader);
			if(nomClient==null){
				System.out.println("Connection Abort : No name send");
				envoyerMessage(printer, "Connection Refused : No name send");
				socketVersUnClient.close();
				return;
			}else{
				System.out.println("nom du client connecté : " + nomClient);
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (msgClient != null) {

			try {
				msgClient = recevoirMessage(reader);

				if (msgClient == null) {
					System.out.println("client deconnecté : "
							+ socketVersUnClient.getInetAddress());
					break;
				}

				System.out.println(msgClient);
			} catch (IOException e) {
				System.out.println("client deconnecté : "
						+ socketVersUnClient.getInetAddress());
				msgClient = null;
			}

			// Envoyer message au client via envoyerMessage
			try {
				// Scanner scan = new Scanner(System.in);
				envoyerMessage(printer, nomClient + ">" + msgClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printer.close();
		// Si plus de ligne à lire fermer socket cliente
	}

	public static BufferedReader creerReader(Socket socketVersUnClient)
			throws IOException {
		// créé un BufferedReader associé à la Socket
		return new BufferedReader(new InputStreamReader(
				socketVersUnClient.getInputStream()));
	}

	public static PrintWriter creerPrinter(Socket socketVersUnClient)
			throws IOException {
		// créé un PrintWriter associé à la Socket
		return new PrintWriter(new OutputStreamWriter(
				socketVersUnClient.getOutputStream()));
	}

	public static String recevoirMessage(BufferedReader reader)
			throws IOException {
		// Récupérer une ligne
		// Retourner la ligne lue ou null si aucune ligne à lire.
		return reader.readLine();

	}

	public static void envoyerMessage(PrintWriter printer, String message)
			throws IOException {
		// Envoyer le message vers le client
		printer.println(message);
		printer.flush();
	}

	public static String avoirNom(BufferedReader reader) throws IOException {
		String msg = reader.readLine();

		if (msg.startsWith("Name:")) {
			return msg.split(":")[1];
		}else{
			return null;
		}
		
		
	}

}