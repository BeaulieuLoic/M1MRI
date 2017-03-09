package chat.mri;

import java.util.Scanner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class WriterUser implements Runnable {

	private String pseudo;
	private String nameChannel;
	private String exchange;
	
	public WriterUser(String ps,String ch, String exchange) {
		pseudo=ps;
		nameChannel=ch;
		this.exchange = exchange;
	}
	
	@Override
	public void run() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.exchangeDeclare(exchange, "topic");
			
			
			while(true){
				Scanner scan = new Scanner(System.in);
				String msg = nameChannel+"#"+pseudo+">"+scan.nextLine();
				channel.basicPublish(exchange, nameChannel, null, msg.getBytes("UTF-8"));	
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

}