package tp.rmi.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tp.rmi.common.ChatRemote;

public class ClientServeur {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost",Registry.REGISTRY_PORT);
		ChatRemote chat = (ChatRemote) registry.lookup("ChatRemote");
		
		System.out.println(chat.echo("bob", "salut"));

	}

}
