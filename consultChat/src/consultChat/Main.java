package consultChat;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class Main {

	public static final String URL = "http://localhost:8080/RestExample/rest";
	public static void main(String[] args) {
		
		
		Scanner scan = new Scanner(System.in);
		Client client = ClientBuilder.newClient();
		
		Thread afficheur = new Thread(new Afficheur(URL,client));
		afficheur.start();
		while(true){
			String msg = scan.nextLine();
			
			Afficheur.ChatMessage m = new Afficheur.ChatMessage();
	        m.content=msg;
	        client.target(URL).path("messages").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(m, MediaType.APPLICATION_XML));
		}
		

	}

}
