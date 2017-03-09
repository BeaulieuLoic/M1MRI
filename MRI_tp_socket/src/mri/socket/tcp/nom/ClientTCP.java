package mri.socket.tcp.nom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {

	static String nom = "bob";
	static String serverAddress = "localhost";

	public static void main(String[] args) {
		// nom = args[0];
		
		if (args.length >= 1) {
			nom = args[0];
		}

		// créer une socket client
		Socket socket = null;
		try {
			socket = new Socket(serverAddress, 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// créer reader et writer associés
		BufferedReader reader = null;
		PrintWriter printer = null;
		try {
			reader = creerReader(socket);
			printer = creerPrinter(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Connecté au serveur : " + socket.getInetAddress());
		try {
			envoyerNom(printer, nom);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (true) {
			Scanner scan = new Scanner(System.in);

			try {
				envoyerMessage(printer,scan.nextLine());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			// recevoir et afficher la réponse du serveur
			String msgServ = "";
			try {
				msgServ = recevoirMessage(reader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!msgServ.split(">")[0].equals(nom)) {
				System.out
						.println("erreur, nom attribué par le serveur incorecte");
			}
			System.out.println(msgServ);
		}

	}

	public static String lireMessageAuClavier() throws IOException {
		// lit un message au clavier en utilisant par exemple un BufferedReader
		// sur System.in
		Scanner scan = new Scanner(System.in);
		return scan.nextLine();
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
		// identique serveur
		return reader.readLine();
	}

	public static void envoyerMessage(PrintWriter p, String message)
			throws IOException {
		// identique serveur
		p.println(message);
		p.flush();
	}

	public static void envoyerNom(PrintWriter printer, String nom)
			throws IOException {
		// envoi « NAME: nom » au serveur
		envoyerMessage(printer, "Name:" + nom);
	}
	

}