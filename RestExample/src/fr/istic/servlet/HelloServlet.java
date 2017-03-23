package fr.istic.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@WebServlet("/client")
public class HelloServlet extends HttpServlet {

    //TODO : Adapter l'URL en fonction de votre resource :
    public static final String URL = "http://localhost:8080/";

    @XmlRootElement
    public static class SomeMessage{
        @XmlElement(name="name")
        public String nom;
        @XmlElement(name="greetings")
        public String salutation;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/plain");
        try{
            Client client = ClientBuilder.newClient();
            String name = "Toto";
            SomeMessage serverResponse = client.target(URL).path("RestExample").path("rest").path("hello").path(name).request(MediaType.APPLICATION_JSON_TYPE).get(SomeMessage.class);
            out.println(serverResponse.salutation + " "+ serverResponse.nom);

        }catch(Exception e){
            out.println("No server response");
        }

    }

}