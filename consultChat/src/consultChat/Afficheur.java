package consultChat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;


public class Afficheur implements Runnable{
	public static final String URL = "http://localhost:8080/RestExample/rest";
    @XmlRootElement()
    public static class ChatMessage {
        public Long id;
        public String content;
        public String date;
        @Override
        public String toString() {
            return id + ":" + content+ " at "+ date;
        }
    }
    
    
	private int lastId = 0;
	private String url;
	private Client client;
	
	public Afficheur(String u, Client cl){
		url = u;
		client = cl;
	}
	
	@Override
	public void run() {
        
        
        
        while(true){
        	
        	
        	ChatMessage[] afterMessages = client.target(URL).path("messages/after/"+lastId).request(MediaType.APPLICATION_JSON_TYPE).get(ChatMessage[].class);
        	for (ChatMessage msg : afterMessages) {
                System.out.println(msg);
                lastId++;
            }
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}

}
