package chat.mri;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatUser {

	private ReaderUser reader;
	private WriterUser writer;

	public static List<String> listChannel;
	
	public static final String EXCHANGE_NAME = "chat";

	public ChatUser(String ps, String ch, String exchange) {
		reader = new ReaderUser(ch, exchange);
		writer = new WriterUser(ps, ch, exchange);
	}

	private void launch() {
		new Thread(reader).start();
		new Thread(writer).start();
	}

	public static void main(String[] argv) throws Exception {
		listChannel = new ArrayList<String>();
		listChannel.add("chat.mri");
		listChannel.add("chat.geeks");
		listChannel.add("chat.human.normal");
		listChannel.add("chat.human.fat");
		listChannel.add("chat.roux");

		Scanner scan = new Scanner(System.in);

		String pseudo = "";
		String channelAct;

		while (pseudo.equals("")) {
			System.out.println("Entrez un pseudo :");
			pseudo = scan.nextLine();
		}

		System.out.println("Entrez une salle :");
		channelAct = scan.nextLine();
		if (!listChannel.contains(channelAct)) {
			channelAct = "chat.mri";
		}

		ChatUser user = new ChatUser(pseudo, channelAct, EXCHANGE_NAME);

		user.launch();
	}

}