package tp.rmi.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import tp.rmi.common.ChatRemote;
import tp.rmi.common.ReceiveCallback;

public class ChatClient {
	
	public static final String nom = "bob_"+Math.random()*(10000-0);
	
	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
		Registry registry = LocateRegistry.getRegistry("localhost",Registry.REGISTRY_PORT);
		ChatRemote chat = (ChatRemote) registry.lookup("ChatRemote");
		
		Scanner scan = new Scanner(System.in);
		ReceiveCallback aff = new Afficheur();
		
		chat.registerCallback(aff);
		
		while(true){
			String msg = scan.nextLine();
			chat.send(nom,msg);
		}
		
	}

}
