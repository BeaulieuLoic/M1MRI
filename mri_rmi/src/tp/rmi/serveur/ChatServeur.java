package tp.rmi.serveur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tp.rmi.common.ChatRemote;

public class ChatServeur {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		try {
			ChatRemote chat = new ChatRemoteImpl();
			
			Naming.rebind("//localhost/ChatRemote", chat);

			System.out.println("chat enregistrer dans registry");
		} catch (Exception e) {
			e.printStackTrace();
		}

	
	}
	

}
