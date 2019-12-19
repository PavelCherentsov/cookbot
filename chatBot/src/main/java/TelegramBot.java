import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.api.methods.send.SendMessage;


public class TelegramBot extends TelegramLongPollingBot{
	ChatBot bot;

	public static void main(ChatBot bot) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new TelegramBot(bot));
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}
	}

	public TelegramBot(ChatBot bot)
	{
		super();
		this.bot = bot;
	}

	@Override
	public String getBotUsername() {
		return "OptimalCookBot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		String message = update.getMessage().getText();
		String name = message.split(" ")[0].toLowerCase();
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