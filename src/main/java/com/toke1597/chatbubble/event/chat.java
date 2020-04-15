package com.toke1597.chatbubble.event;

import com.toke1597.chatbubble.ChatBubble;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class chat implements Listener {

    private Plugin plugin = ChatBubble.getPlugin(ChatBubble.class);
    double count = 0.0;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String ms = e.getMessage();
        Player p = e.getPlayer();

        plugin.getConfig().set("Users." + p.getUniqueId().toString() + ".chat", ms);
        plugin.saveConfig();

        makeChatBubble(p);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        makeChatBubble(p);
    }

    public void makeChatBubble(Player p) {
        try {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    ArmorStand armorStand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().add(0, 0 + count, 0), EntityType.ARMOR_STAND);
                    armorStand.setCustomNameVisible(true);
                    armorStand.setCustomName(p.getDisplayName()+" : "+ChatColor.GREEN + plugin.getConfig().getString("Users." + p.getUniqueId().toString() + ".chat"));
                    armorStand.setVisible(false);
                    armorStand.setGravity(false);
                    count = count + 0.3;

                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            armorStand.remove();
                            count = count - 0.3;
                            if (count < 0) count = 0.0;
                        }
                    }, 20 * 5);
                }
            }, 1);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
