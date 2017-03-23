package fr.istic.chat.message;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

@Path("/messages")
public class MessageResource {
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Message> getAllMsg(){
	  System.out.println("/messages");
	  return MessageList.getInstance().getMessages();
	}
	
	@Path("/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Message getMessage(@PathParam("id") Long id){
	  System.out.println("/message/" + id);
	  return MessageList.getInstance().getMessage(id);
	}
	
	@Path("/{id}")
	@DELETE
	//@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void deleteMessage(@PathParam("id") Long id){
	  System.out.println("delete /message/" + id);
	  MessageList.getInstance().delMessage(id);
	  //return 1;
	}


	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	//@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public void addMessage(JAXBElement<Message> msgJaxb){
	  System.out.println("post /messages");
	  Message message = msgJaxb.getValue();

	  MessageList.getInstance().createMessage(message);
	}
	
	//En partant du principe que l'annotation Path sur la classe contient "messages"
	@Path("/after/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Message> getMessagesAfter(@PathParam("id") Long id){
	  System.out.println("message after" + id);
	  return MessageList.getInstance().getMessagesAfter(id);
	}
}
