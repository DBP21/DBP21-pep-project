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

    /**
     * Creates a new message.
     *
     * @param message The message to be created.
     * @return The created message with its ID, or null if creation fails.
     */
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

    /**
     * Retrieves all messages.
     *
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    /**
     * Retrieves a message by its ID.
     *
     * @param messageId The ID of the message to retrieve.
     * @return The message if found, otherwise null.
     */
    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    /**
     * Deletes a message by its ID.
     *
     * @param messageId The ID of the message to delete.
     * @return The deleted message if it existed, otherwise null.
     */
    public Message deleteMessageById(int messageId) {
        Message messageToDelete = messageDao.getMessageById(messageId);
        if (messageToDelete != null) {
            messageDao.deleteMessageById(messageId);
        }
        return messageToDelete;
    }

    /**
     * Updates the text of a message.
     *
     * @param messageId The ID of the message to update.
     * @param message   A message object containing the new text.
     * @return The updated message, or null if the update fails.
     */
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

    /**
     * Retrieves all messages from a specific user.
     *
     * @param accountId The ID of the user.
     * @return A list of messages from the user.
     */
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDao.getMessagesByAccountId(accountId);
    }
}
