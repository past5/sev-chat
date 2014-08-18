package dao;

import model.ChatSettings;

public interface ChatSettingsDao extends Dao<ChatSettings,Integer> {
    public ChatSettings doread(Integer id);
}