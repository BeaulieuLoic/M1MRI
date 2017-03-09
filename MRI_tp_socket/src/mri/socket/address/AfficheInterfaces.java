package mri.socket.address;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class AfficheInterfaces {

	public static void main(String[] args) {
		boolean stop = false;
		int  i = 1;
		while(!stop){
			NetworkInterface net = null;
			try {
				net = NetworkInterface.getByIndex(i);
				if (net == null) {
					stop = true;
				}else{
					System.out.println("------------------------------");
					System.out.println("nom interface : "+ net.getDisplayName());
					Enumeration<InetAddress> listAdresse = net.getInetAddresses();
					
					while(listAdresse.hasMoreElements()){
						InetAddress adr = listAdresse.nextElement();
						System.out.println("adresse interface : "+adr);
					}
					
				}
				
				
			} catch (SocketException e) {
				e.printStackTrace();
			}

			i++;
		}
	}

}
