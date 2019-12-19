import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class TelegramBot extends TelegramLongPollingBot{
	static ChatBot bot;
	public static TelegramBot Tbot;
	public static ArrayList<Long> users = new ArrayList<>();

	public static void main(ChatBot bot) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		Tbot = new TelegramBot(bot);
		try {
			telegramBotsApi.registerBot(Tbot);
			Thread t = new chreak();
			t.start();
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}

	public TelegramBot(ChatBot bot)
	{
		super();
		this.bot = bot;
	}

	public static class chreak extends Thread{
		@Override
		public void run() {
			while (true){
				for (Long a : users) {
					GregorianCalendar now = new GregorianCalendar();
					if (now.compareTo(bot.thisdate) == 1 && bot.isRunTimer) {
						SendMessage sendMessage = new SendMessage().setChatId(a);
						bot.isRunTimer = false;
						sendMessage.setText("Время вышло!");
						try {
							Tbot.execute(sendMessage);
						} catch (TelegramApiException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		return "OptimalCookBot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		String message = update.getMessage().getText();
		String name = message.split(" ")[0].toLowerCase();
		Long id = update.getMessage().getChatId();
		if (!users.contains(id)){
			users.add(id);
		}
		String arg = "";
		if (message.length() >= 2)
			arg = message.substring(message.indexOf(" ") + 1);
		String result = "what?";
		if (bot.commands.containsKey(name)) {
			result = bot.commands.get(name).func.apply(arg.toLowerCase());
		}
		sendMsg(update.getMessage().getChatId().toString(), result);
	}

	public void sendMsg(String chatId, String s) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(chatId);
		sendMessage.setText(s);
		try {
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotToken() {
		return "1068011791:AAGXnnhwJ1xDkWwRhzcQEYzw2y-ftuzZXts";
	}
}