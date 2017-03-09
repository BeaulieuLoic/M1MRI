package chat.mri;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReaderUser implements Runnable {

	private String nameChannel;
	private String exchange;
	
	public ReaderUser(String ch, String exchange) {
		nameChannel = ch;
		this.exchange = exchange;
	}

	@Override
	public void run() {
		try {

			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection;
			connection = factory.newConnection();
			Channel channel = connection.createChannel();

			
		    channel.exchangeDeclare(exchange, "topic");
		    String queueName = channel.queueDeclare().getQueue();
		    channel.queueBind(queueName, exchange, nameChannel);
		    
		    Consumer consumer = new DefaultConsumer(channel) {
		        @Override
		        public void handleDelivery(String consumerTag, Envelope envelope,
		                                   AMQP.BasicProperties properties, byte[] body) throws IOException {
		          String message = new String(body, "UTF-8");
		          System.out.println(message);
		        }
		      };
		      channel.basicConsume(queueName, true, consumer);
		    
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}