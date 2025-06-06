package Service;

import DAO.AccountDao;
import DAO.MessageDao;
import Model.Message;

import java.util.List;

public class MessageService {
    private final MessageDao messageDao;
    private final AccountDao accountDao;

    public MessageService() {
        this.messageDao = new MessageDao();
        this.accountDao = new AccountDao();
    }
    
    public MessageService(MessageDao messageDao, AccountDao accountDao){
        this.messageDao = messageDao;
        this.accountDao = accountDao;
    }
    
    //Creates a new message.

    public Message createMessage(Message message) {
        String text = message.getMessage_text();
        if (text == null || text.isBlank() || text.length() > 255) {
            return null;
        }
        if (accountDao.getAccountById(message.getPosted_by()) == null) {
            return null;
        }
        return messageDao.createMessage(message);
    }

    //Retrieves all messages.

    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    //Retrieves a message by its ID.

    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    //Deletes a message by its ID.

    public Message deleteMessageById(int messageId) {
        Message messageToDelete = messageDao.getMessageById(messageId);
        if (messageToDelete != null) {
            messageDao.deleteMessageById(messageId);
        }
        return messageToDelete;
    }

    //Updates the text of a message.

    public Message updateMessage(int messageId, Message message) {
        String newText = message.getMessage_text();
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return null;
        }
        Message existingMessage = messageDao.getMessageById(messageId);
        if (existingMessage == null) {
            return null;
        }
        int rowsAffected = messageDao.updateMessage(messageId, newText);
        if (rowsAffected > 0) {
            return messageDao.getMessageById(messageId);
        }
        return null;
    }

    //Retrieves all messages from a specific user.

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDao.getMessagesByAccountId(accountId);
    }
}