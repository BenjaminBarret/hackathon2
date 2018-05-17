package fr.wcs.hackathon2;

import java.util.Date;

public class ChatMessage {
    private String textMessage;
    private String messageUser;
    private long messageTime;

    public ChatMessage(String textMessage, String messageUser) {
        this.textMessage = textMessage;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();
    }

    public ChatMessage() {
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
