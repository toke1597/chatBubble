package com.toke1597.chatbubble;

import com.toke1597.chatbubble.event.chat;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatBubble extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        setConfig();
        getLogger().info("Loaded config file");
        this.getServer().getPluginManager().registerEvents(new chat(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setConfig(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
}
