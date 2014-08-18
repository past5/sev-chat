//Vsevolod Geraskin
//Final Project
package dao.impl;

import dao.ChatSettingsDao;
import model.ChatSettings;

public class ChatSettingsDaoImpl extends BaseDaoImpl<ChatSettings,Integer> implements ChatSettingsDao
{
    public ChatSettings doread(Integer id) {
		 return read(id);
	 }
}
