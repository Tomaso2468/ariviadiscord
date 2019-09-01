package arivia.discord;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import arivia.Connection;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DiscordConnection implements Connection {
	private final MessageReceivedEvent first;
	private final List<String> queue = Collections.synchronizedList(new LinkedList<String>());
	
	public DiscordConnection(MessageReceivedEvent first) {
		super();
		this.first = first;
	}

	@Override
	public boolean hasNextLine() {
		return queue.size() > 0;
	}

	@Override
	public String nextLine() {
		synchronized(queue) {
			String s = queue.get(0);
			queue.remove(0);
			return s;
		}
	}

	@Override
	public void out(String s) {
		MessageBuilder mb = new MessageBuilder();
		mb.append(first.getAuthor());
		mb.append(" " + s);
		first.getChannel().sendMessage(mb.build()).queue();
		AriviaDiscord.time = System.currentTimeMillis();
	}

	@Override
	public String getUserName() {
		return first.getMember().getEffectiveName();
	}

	@Override
	public Locale getLocale() {
		return Locale.ENGLISH;
	}

	@Override
	public void prompt() {
		
	}
	
	public void queue(String s) {
		queue.add(s);
	}

}
