package fr.istic.chat.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageList {

  private static MessageList INSTANCE = new MessageList();

  public static final MessageList getInstance() {
    return INSTANCE;
  }

  private static long ACTUAL_ID = 0;

  private synchronized static Long getNextID() {
    return ACTUAL_ID++;
  }

  public synchronized static Long getHighestID() {
    return ACTUAL_ID;
  }

  /* L'utilisation d'un bloc static est une très mauvaise idée de façon générale.
  * Ce code est appelé au chargement de la classe par le Classloader
  * On l'utilise ici pour remplir les messages :
  */
  static {

    Message m = new Message();
    m.setContent("ceci est un test");
    INSTANCE.createMessage(m);

    m = new Message();
    m.setContent("test 2");
    INSTANCE.createMessage(m);

    m = new Message();
    m.setContent("test 3");
    INSTANCE.createMessage(m);

  }

  private Map<Long, Message> messagesMap = new HashMap<Long, Message>();

  public synchronized Message createMessage(Message message) {
    message.setId(getNextID());
    message.setDate(new Date().toString());
    messagesMap.put(message.getId(), message);
    return message;
  }

  public synchronized Message getMessage(Long id) {
    return messagesMap.get(id);
  }

  public synchronized void delMessage(Long id) {
    messagesMap.remove(id);
  }

  public synchronized List<Message> getMessages() {
    return new ArrayList<Message>(messagesMap.values());
  }

  public synchronized List<Message> getMessagesBetween(Long id1, Long id2) {
    List<Message> messages = new ArrayList<Message>();
    for (Long i = id1; i <= id2; i++) {
      messages.add(messagesMap.get(i));
    }
    return messages;
  }

  public synchronized List<Message> getMessagesAfter(Long id1) {
    List<Message> messages = new ArrayList<Message>();
    for (Long i = id1; i <= getHighestID(); i++) {
      if (messagesMap.containsKey(i)) {
        messages.add(messagesMap.get(i));
      }
    }
    return messages;
  }

}