package arivia.discord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import arivia.AriviaCore;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AriviaDiscord extends ListenerAdapter {
	private static JDA jda;
	private static AriviaCore core = new AriviaCore();
	private static Map<String, DiscordConnection> map = Collections.synchronizedMap(new HashMap<>());
	private static PrintStream data;
	public static long time = System.currentTimeMillis();
	public static void main(String[] args) throws LoginException, IOException, InterruptedException {
		data = new PrintStream(new FileOutputStream(new File("learning.txt"), true), true);
		core.start(args[1]);
		
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(args[0]);
		builder.addEventListeners(new AriviaDiscord());
		jda = builder.build();
		
		jda.awaitReady();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		
		jda.getPresence().setActivity(new Activity() {
			@Override
			public boolean isRich() {
				return false;
			}

			@Override
			public RichPresence asRichPresence() {
				return null;
			}

			@Override
			public String getName() {
				return "Loading Screen";
			}

			@Override
			public String getUrl() {
				return null;
			}

			@Override
			public ActivityType getType() {
				return ActivityType.DEFAULT;
			}

			@Override
			public Timestamps getTimestamps() {
				return null;
			}
		});
		
//		Calendar cal = new GregorianCalendar();
//		List<Guild> guilds = jda.getGuilds();
//		for (Guild g : guilds) {
//			List<TextChannel> channels = g.getTextChannels();
//			boolean found = false;
//			for (TextChannel c : channels) {
//				if (c.getName().equals("botchat")) {
//					if (cal.get(Calendar.HOUR_OF_DAY) < 12) {
//						c.sendMessage("Good morning!").queue();
//					} else {
//						if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
//							c.sendMessage("Good afternoon!").queue();
//						} else {
//							c.sendMessage("Good evening!").queue();
//						}
//					}
//					found = true;
//				}
//			}
//			if (!found) {
//				for (TextChannel c : channels) {
//					if (c.getName().equals("offtopic")) {
//						if (cal.get(Calendar.HOUR_OF_DAY) < 12) {
//							c.sendMessage("Good morning!").queue();
//						} else {
//							if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
//								c.sendMessage("Good afternoon!").queue();
//							} else {
//								c.sendMessage("Good evening!").queue();
//							}
//						}
//						found = true;
//					}
//				}
//			}
//			if (!found) {
//				for (TextChannel c : channels) {
//					if (c.getName().equals("general")) {
//						if (cal.get(Calendar.HOUR_OF_DAY) < 12) {
//							c.sendMessage("Good morning!").queue();
//						} else {
//							if (cal.get(Calendar.HOUR_OF_DAY) < 17) {
//								c.sendMessage("Good afternoon!").queue();
//							} else {
//								c.sendMessage("Good evening!").queue();
//							}
//						}
//						found = true;
//					}
//				}
//			}
//		}
		
		new Thread("Input") {
			public void run() {
				Scanner s = new Scanner(System.in);
				boolean maintenence = false;
				System.out.println("Waiting for input.");
				while(true) {
					while (!s.hasNextLine()) {
						if (System.currentTimeMillis() - time > 1000 * 60 * 10) {
							if (jda.getPresence().getStatus() != OnlineStatus.IDLE) {
								System.out.println("IDLE");
								jda.getPresence().setStatus(OnlineStatus.IDLE);
							}
						} else {
							if (jda.getPresence().getStatus() != OnlineStatus.ONLINE) {
								System.out.println("ONLINE");
								jda.getPresence().setStatus(OnlineStatus.ONLINE);
							}
						}
						if ((long) (System.currentTimeMillis() / 1000 % (60 * 60 * 3 * Math.random())) == 0) {
							System.out.println("Changing Activity");
							final String[] activities = {
									"for messages",
									"to data",
									"with megabytes",
									"The internet",
							};
							final ActivityType[] types = {
									ActivityType.LISTENING,
									ActivityType.LISTENING,
									ActivityType.DEFAULT,
									ActivityType.WATCHING,
							};
							int index = (int) Math.floor(Math.random() / types.length);
							jda.getPresence().setActivity(new Activity() {
								@Override
								public boolean isRich() {
									return false;
								}

								@Override
								public RichPresence asRichPresence() {
									return null;
								}

								@Override
								public String getName() {
									return activities[index];
								}

								@Override
								public String getUrl() {
									return null;
								}

								@Override
								public ActivityType getType() {
									return types[index];
								}

								@Override
								public Timestamps getTimestamps() {
									return null;
								}
							});
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					String ln = s.nextLine();
					System.out.println("Found input: " + ln);
					if (ln.equals("exit")) {
						jda.getPresence().setStatus(OnlineStatus.ONLINE);
						break;
					}
					if (ln.equals("fix")) {
						jda.getPresence().setStatus(OnlineStatus.ONLINE);
						maintenence = true;
						break;
					}
					if (ln.equals("help")) {
						System.out.println("exit - Exits the application.");
						System.out.println("help - Displays help.");
						System.out.println("fix - Exits the application for maintenence.");
					}
					if (ln.equals("reload")) {
						core = new AriviaCore();
						try {
							core.start(args[1]);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				s.close();
				System.out.println("Going offline");
				jda.getPresence().setPresence(maintenence ? OnlineStatus.DO_NOT_DISTURB : OnlineStatus.OFFLINE, null);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				jda.shutdown();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(0);
			};
		}.start();
	}
	
	public static DiscordConnection getConnection(MessageReceivedEvent event) {
		String id = event.getMember().getId() + " on " + event.getChannel().getName() + " on " + event.getGuild().getId();
		System.out.println("Getting connection ID: " + id);
		if (map.containsKey(id)) {
			return map.get(id);
		} else {
			System.out.println("New connection ID: " + id);
			DiscordConnection c = new DiscordConnection(event);
			
			new Thread() {
				public void run() {
					map.put(id, c);
					core.acceptConnection(c);
				};
			}.start();
			
			return c;
		}
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		try {
			if (event.getAuthor() == jda.getSelfUser()) return;
			if (!event.getAuthor().isBot()) data.println(event.getMessage().getContentDisplay().replace("@Arivia", "").trim());
			if (event.getMessage().isMentioned(jda.getSelfUser(), MentionType.USER) || event.getMessage().getContentStripped().toLowerCase().contains("@arivia")) {
				System.out.println("Message recieved " + event.getAuthor().getName() + " " + event.getMessage().getContentStripped().trim());
				
				getConnection(event).queue(event.getMessage().getContentDisplay().replace("@Arivia", "").trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEmoteAdded(EmoteAddedEvent event) {
		time = System.currentTimeMillis();
		try {
			List<TextChannel> channels = event.getGuild().getTextChannels();
			for (TextChannel c : channels) {
				if (c.getName().equals("botchat")) {
					c.sendMessage("Yay! New emote: " + event.getEmote().getAsMention()).queue();
				}
				if (c.getName().equals("general")) {
					c.sendMessage("Yay! New emote: " + event.getEmote().getAsMention()).queue();
				}
				if (c.getName().equals("offtopic")) {
					c.sendMessage("Yay! New emote: " + event.getEmote().getAsMention()).queue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		time = System.currentTimeMillis();
		super.onGuildMemberJoin(event);
		List<TextChannel> channels = event.getGuild().getTextChannels();
		for (TextChannel c : channels) {
			if (c.getName().equals("welcome")) {
				c.sendMessage("Welcome " + event.getMember().getAsMention()).queue();
				return;
			}
		}
		for (TextChannel c : channels) {
			if (c.getName().equals("general")) {
				c.sendMessage("Welcome " + event.getMember().getAsMention()).queue();
			}
			if (c.getName().equals("offtopic")) {
				c.sendMessage("Welcome " + event.getMember().getAsMention()).queue();
			}
		}
	}
}
