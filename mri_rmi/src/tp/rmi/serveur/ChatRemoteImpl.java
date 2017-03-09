package tp.rmi.serveur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import tp.rmi.common.ChatRemote;
import tp.rmi.common.ReceiveCallback;

public class ChatRemoteImpl extends UnicastRemoteObject implements ChatRemote {

	private List<ReceiveCallback> listCallBack;
	protected ChatRemoteImpl() throws RemoteException {
		super();
		listCallBack = new ArrayList<>();	
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String echo(String name, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		return name + "->" + msg;
	}

	@Override
	public void send(String name, String message) throws RemoteException {
		for (ReceiveCallback receiveCallback : listCallBack) {
			receiveCallback.newMessage(name+"->"+message);
		}

	}

	@Override
	public void registerCallback(ReceiveCallback callback)
			throws RemoteException {
		listCallBack.add(callback);

	}

}
