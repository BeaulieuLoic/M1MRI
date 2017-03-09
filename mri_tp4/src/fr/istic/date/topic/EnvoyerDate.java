package fr.istic.date.topic;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.util.Date;

import com.rabbitmq.client.Channel;

public class EnvoyerDate {

	private static final String EXCHANGE_NAME = "date_topic";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
	    //factory.setUri("amqp://guest:guest@alexrio.fr");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();


	    channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		
		while(true){
			channel.basicPublish(EXCHANGE_NAME, "date.locale", null, getDate().getBytes("UTF-8"));
			channel.basicPublish(EXCHANGE_NAME, "date.gmt", null, getDateGMT().getBytes("UTF-8"));

			System.out.println(" [x] Sent ...");
			
			Thread.sleep(1000);
		}


		
		
		//channel.close();
		//connection.close();
	}
	
	private static String getDateGMT() {
	    return (new Date()).toGMTString();
	}

	private static String getMessage(String[] strings) {
		if (strings.length < 1)
			return "info: Hello World!";
		return joinStrings(strings, " ");
	}
	
	private static String getDate() {
	    return (new Date()).toString();
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}