package action.chat;

import model.ChatMessage;

public interface ChatNotify {
	void notify(ChatMessage message);
	void notifynowrite(ChatMessage message);
}
