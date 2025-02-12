package com.de013.service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import jakarta.annotation.PostConstruct;

public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.adminBot.token}")
    private String botToken;

    @Value("${telegram.adminBot.chatId}")
    private Long chatId;
    
    @PostConstruct
    public void registerBot() {
//        try {
//        	TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(this);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }
    
	@Override
	public void onUpdateReceived(Update update) {
	    if (update.hasMessage() && update.getMessage().hasText()) {
	        String message_text = update.getMessage().getText();
	        long chat_id = update.getMessage().getChatId();

	        this.sendText(chat_id, String.valueOf(chat_id) + " - " + message_text);
	    }
	}

    @Override
    public String getBotUsername() {
        // TODO
        return "bfun_dev_bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    
    public void sendText(String type, String content, String status) {
    	String text = "Type: " + type + "\n" +
						"Content: " + content + "\n" +
						"Status: " + status;
    	SendMessage sm = SendMessage.builder()
    					.chatId(chatId)
    					.text(text)
    					.build();
    	try {
    		execute(sm);
    	} catch (TelegramApiException e) {
    		e.printStackTrace();
    	}
    }
    
    public void sendText(Long ChatId, String text) {
    	SendMessage sm = SendMessage.builder()
    					.chatId(chatId)
    					.text(text)
    					.build();
    	try {
    		execute(sm);
    	} catch (TelegramApiException e) {
    		e.printStackTrace();
    	}
    }
    
    public void sendExceptionMessage(Exception ex) {
		String fullStackTrace = Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));

		String regex = "io\\.bfun\\.\\S+\\.(\\S+)\\.(\\S+)\\(\\S+:(\\d+)\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fullStackTrace);
		
		StringBuilder messageBuilder = new StringBuilder();
		while (matcher.find()) {
			String className = matcher.group(1); // class
			String methodName = matcher.group(2); // method
			String lineNumber = matcher.group(3); // line
			
			messageBuilder.append(className).append(" - ")
			    .append(methodName).append(" - ")
			    .append(lineNumber)
			    .append("\n       ");
		}
		
		String message = messageBuilder.toString();
		
		this.sendText("SYSTEM", message, ex.getClass().getSimpleName());
	}   
}
