package tp.rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tp.rmi.common.ReceiveCallback;

public class Afficheur extends UnicastRemoteObject implements ReceiveCallback{

	protected Afficheur() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void newMessage(String message) throws RemoteException {
		System.out.println(message);
		
	}
	
}
