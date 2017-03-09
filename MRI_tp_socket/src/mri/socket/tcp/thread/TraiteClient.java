package mri.socket.tcp.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TraiteClient implements Runnable {
	private Socket socketVersUnClient;
	private String charset;
	
	
	public TraiteClient(Socket so,String charset){
		socketVersUnClient=so;
		this.charset=charset;
	}
	
	public void setSocketVersUnClient(Socket so){
		socketVersUnClient=so;
	}
	
	@Override
	public void run() {
		traiteUnClient(socketVersUnClient);
	}	
	
	public void traiteUnClient(Socket so){
		// Créer printer et reader
				BufferedReader reader = null;
				PrintWriter printer = null;
				try {
					reader = creerReader(so, charset);
					printer = creerPrinter(so, charset);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Tant qu’il y’a un message à lire via recevoirMessage
				String msgClient = "";

				String nomClient = "";
				try {

					nomClient = avoirNom(reader);
					if (nomClient == null) {
						System.out.println("Connection Abort : No name send");
						envoyerMessage(printer, "Connection Refused : No name send");
						so.close();
						return;
					} else {
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
									+ so.getInetAddress());
							break;
						}

						System.out.println(msgClient);
					} catch (IOException e) {
						System.out.println("client deconnecté : "
								+ so.getInetAddress());
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

	public static BufferedReader creerReader(Socket socketVersUnClient,
			String charset) throws IOException {
		// créer un reader qui utilise le bon charset
		return new BufferedReader(new InputStreamReader(
				socketVersUnClient.getInputStream(), charset));
	}

	public static PrintWriter creerPrinter(Socket socketVersUnClient,
			String charset) throws IOException {
		// créer un printer qui utilise le bon charset
		return new PrintWriter(new OutputStreamWriter(
				socketVersUnClient.getOutputStream(), charset));
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
		} else {
			return null;
		}

	}

}
