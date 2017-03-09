package mri.socket.tcp.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {

    public static void main(String[] args) {
        //créer une socket client
    	Socket socket = null;
    	try {
    		socket = new Socket("localhost", 9999);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //créer reader et writer associés
		BufferedReader reader = null;
		PrintWriter printer = null;
		try {
			reader = creerReader(socket);
			printer = creerPrinter(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    	
    	
        //Tant que le mot «fin» n’est pas lu sur le clavier,
		String msg = "";
		while(!msg.equals("fin")){
			try {
				msg = lireMessageAuClavier();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
        //Lire un message au clavier
		 //envoyer le message au serveur
		try {
			envoyerMessage(printer, msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
        //recevoir et afficher la réponse du serveur
		try {
			System.out.println(recevoirMessage(reader));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static String lireMessageAuClavier() throws IOException {
        //lit un message au clavier en utilisant par exemple un BufferedReader
        //sur System.in
    	Scanner scan = new Scanner(System.in);
    	return scan.nextLine();
    }

	public static BufferedReader creerReader(Socket socketVersUnClient) throws IOException {
		// créé un BufferedReader associé à la Socket
		return new BufferedReader(new InputStreamReader(socketVersUnClient.getInputStream()));
	}

	public static PrintWriter creerPrinter(Socket socketVersUnClient) throws IOException {
		// créé un PrintWriter associé à la Socket
		return new PrintWriter(new OutputStreamWriter(socketVersUnClient.getOutputStream()));
	}

    public static String recevoirMessage(BufferedReader reader) throws
    IOException {
        //identique serveur
    	return reader.readLine();
    }

    public static void envoyerMessage(PrintWriter p, String message) throws
    IOException {
        //identique serveur
		p.println(message);
		p.flush();
    }

}