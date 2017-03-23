package fr.istic.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

@Path("/hello")
public class Hello
{

    @XmlRootElement
    public static class Message{
        public String name;
        public String greetings;
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message getDescription(@PathParam("name") String name)
    {
        Message m = new Message();
        m.name = name;
        m.greetings = "Hello";
        return m;
    }
}