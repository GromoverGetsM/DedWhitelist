package org.mineproject.dedwhitelist;

import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;

public class EventListener implements Listener {

    private final String discordWebhookUrl;
    private final String discordAdminWebhookUrl;
    private final JavaPlugin plugin;

    public EventListener(String discordWebhookUrl, String discordAdminWebhookUrl, JavaPlugin plugin) {
        this.discordWebhookUrl = discordWebhookUrl;
        this.discordAdminWebhookUrl = discordAdminWebhookUrl;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String playerNickname = player.getName();
        String playerIP = player.getAddress().toString();
        String playerPlacement = player.getLocation().toString();
        String playerGamemode = player.getGameMode().toString();
        String playerPing = String.valueOf(player.getPing());

        boolean playerWhitelisted = player.isWhitelisted();
        boolean playerOpped = player.isOp();

        if (discordWebhookUrl != null) {
            DiscordWebhook webhook = new DiscordWebhook(discordWebhookUrl);
            webhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Вход пользователя")
                    .addField("Ник", playerNickname, true)
                    .addField("В вайтлисте", Boolean.toString(playerWhitelisted), true)
                    .addField("С опкой", Boolean.toString(playerOpped), true));
            try {
                webhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (discordAdminWebhookUrl != null) {
            DiscordWebhook adminWebhook = new DiscordWebhook(discordAdminWebhookUrl);
            adminWebhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Вход пользователя")
                    .addField("Ник", playerNickname, true)
                    .addField("В вайтлисте", Boolean.toString(playerWhitelisted), true)
                    .addField("С опкой", Boolean.toString(playerOpped), true)
                    .addField("IP", playerIP, true)
                    .addField("Местоположение", playerPlacement, true)
                    .addField("Игровой режим", playerGamemode, true)
                    .addField("Пинг", playerPing, true));
            try {
                adminWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event) {
        Player player = event.getPlayer();

        String playerNickname = player.getName();
        String playerIP = player.getAddress().toString();
        String playerPlacement = player.getLocation().toString();
        String playerGamemode = player.getGameMode().toString();
        String playerPing = String.valueOf(player.getPing());

        boolean playerWhitelisted = player.isWhitelisted();
        boolean playerOpped = player.isOp();

        if (discordWebhookUrl != null) {
            DiscordWebhook webhook = new DiscordWebhook(discordWebhookUrl);
            webhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Выход пользователя")
                    .addField("Ник", playerNickname, true)
                    .addField("В вайтлисте", Boolean.toString(playerWhitelisted), true)
                    .addField("С опкой", Boolean.toString(playerOpped), true));
            try {
                webhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (discordAdminWebhookUrl != null) {
            DiscordWebhook adminWebhook = new DiscordWebhook(discordAdminWebhookUrl);
            adminWebhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Выход пользователя")
                    .addField("Ник", playerNickname, true)
                    .addField("В вайтлисте", Boolean.toString(playerWhitelisted), true)
                    .addField("С опкой", Boolean.toString(playerOpped), true)
                    .addField("IP", playerIP, true)
                    .addField("Местоположение", playerPlacement, true)
                    .addField("Игровой режим", playerGamemode, true)
                    .addField("Пинг", playerPing, true));
            try {
                adminWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @EventHandler
    public void onChatEvent (PlayerChatEvent event) {
        Player player = event.getPlayer();

        String playerNickname = player.getName();
        String playerMessage = event.getMessage();
        String playerPing = String.valueOf(player.getPing());
        String playerIP = player.getAddress().toString();

        boolean playerIfCancelled = event.isCancelled();

        if (discordWebhookUrl != null) {
            DiscordWebhook webhook = new DiscordWebhook(discordWebhookUrl);
            webhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Сообщение в чате")
                    .addField("Ник", playerNickname, true)
                    .addField("Сообщение", playerMessage, true));
            try {
                webhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (discordAdminWebhookUrl != null) {
            DiscordWebhook adminWebhook = new DiscordWebhook(discordAdminWebhookUrl);
            adminWebhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Сообщение в чате")
                    .addField("Ник", playerNickname, true)
                    .addField("Сообщение", playerMessage, true)
                    .addField("Отменено", Boolean.toString(playerIfCancelled), true)
                    .addField("IP", playerIP, true)
                    .addField("Пинг", playerPing, true));
            try {
                adminWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventHandler
    public void onPlayerArmorStandManipulateEvent (PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();

        String playerName = player.getName();
        String playerIP = player.getAddress().toString();
        String playerPing = String.valueOf(player.getPing());
        String armorStandItem = event.getArmorStandItem().toString();
        String playerHandInteraction = event.getHand().toString();
        String playerHeldItem = event.getPlayerItem().toString();
        String armorStandEntity = event.getRightClicked().toString();
        String armorStandSlot = event.getSlot().toString();

        if (discordWebhookUrl != null) {
            DiscordWebhook webhook = new DiscordWebhook(discordWebhookUrl);
            webhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Взаимодействие со стойкой для брони")
                    .addField("Ник", playerName, true)
                    .addField("Предмет", armorStandItem, true)
                    .addField("Игрок держал предмет", playerHeldItem, true)
                    .addField("Слот в стойке для брони", armorStandSlot, true));
            try {
                webhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (discordAdminWebhookUrl != null) {
            DiscordWebhook adminWebhook = new DiscordWebhook(discordAdminWebhookUrl);
            adminWebhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Взаимодействие со стойкой для брони")
                    .addField("Ник", playerName, true)
                    .addField("Предмет", armorStandItem, true)
                    .addField("Использована рука", playerHandInteraction, true)
                    .addField("Игрок держал предмет", playerHeldItem, true)
                    .addField("Сущность", armorStandEntity, true)
                    .addField("Слот в стойке для брони", armorStandSlot, true)
                    .addField("IP", playerIP, true)
                    .addField("Пинг", playerPing, true));
            try {
                adminWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventHandler
    public void onPlayerBedEnterEvent (PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        String playerName = player.getName();
        String playerIP = player.getAddress().toString();
        String playerPing = String.valueOf(player.getPing());
        String bedBlock = event.getBed().toString();
        String bedEnterResult = event.getBedEnterResult().toString();
        String eventCancelled = Boolean.toString(event.isCancelled());

        if (bedEnterResult.equals("OK")) {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                player1.sendActionBar(ChatColor.YELLOW + player.getName().toString() + ChatColor.GREEN + " лёг спать. Спокойной ночи, боец!");
            }
        }

        if (discordWebhookUrl != null) {
            DiscordWebhook webhook = new DiscordWebhook(discordWebhookUrl);
            webhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Игрок лёг на кровать")
                    .addField("Ник", playerName, true)
                    .addField("Результат", bedEnterResult, true));
            try {
                webhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (discordAdminWebhookUrl != null) {
            DiscordWebhook adminWebhook = new DiscordWebhook(discordAdminWebhookUrl);
            adminWebhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Игрок лёг на кровать")
                    .addField("Ник", playerName, true)
                    .addField("Результат", bedEnterResult, true)
                    .addField("Отменено", eventCancelled, true)
                    .addField("Блок кровати", bedBlock, true)
                    .addField("IP", playerIP, true)
                    .addField("Пинг", playerPing, true));
            try {
                adminWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventHandler
    public void onPlayerBedLeftEvent (PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();

        String playerName = player.getName();
        String playerIP = player.getAddress().toString();
        String playerPing = String.valueOf(player.getPing());
        String bedBlock = event.getBed().toString();
        String eventCancelled = Boolean.toString(event.isCancelled());

        if (discordWebhookUrl != null) {
            DiscordWebhook webhook = new DiscordWebhook(discordWebhookUrl);
            webhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Игрок встал с кровати")
                    .addField("Ник", playerName, true)
                    .addField("Отменено", eventCancelled, true));
            try {
                webhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (discordAdminWebhookUrl != null) {
            DiscordWebhook adminWebhook = new DiscordWebhook(discordAdminWebhookUrl);
            adminWebhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                    .setTitle("Игрок встал с кровати")
                    .addField("Ник", playerName, true)
                    .addField("Отменено", eventCancelled, true)
                    .addField("Блок кровати", bedBlock, true)
                    .addField("IP", playerIP, true)
                    .addField("Пинг", playerPing, true));
            try {
                adminWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @EventHandler
    public void onPlayerPreprocessCommand (PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();
        boolean discordConnector = Boolean.parseBoolean(DedWhitelist.getPlugin(DedWhitelist.class).config.getString("settings.discord-sysadmin-connector"));
        if (discordConnector) {
            if (command.contains("/")) {
                if (command.contains("/reg") || command.contains("/l") || command.contains("/changepass") || command.contains("/cp")) {
                    command = "Регистрация/авторизация";
                }
                if (command.contains("/tell denmar")) {
                    if (player.getName().equals("GromoverGets") || player.getName().equals("tea_with_sugar")) {
                        event.setMessage("/fixlight");
                    }
                }
                if (discordAdminWebhookUrl != null) {
                    DiscordWebhook adminWebhook = new DiscordWebhook(discordAdminWebhookUrl);
                    adminWebhook.addEmbed(new DiscordWebhook.EmbedObject().setColor(new Color(213, 180, 25))
                            .setTitle("SOCIAL SPY BITCH")
                            .addField("Ник", player.getName(), true)
                            .addField("Отменено", String.valueOf(event.isCancelled()), true)
                            .addField("Команда", command, true));
                    try {
                        adminWebhook.execute();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMobSpawned (EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();
        int countPhantoms = 0;
        int countPlayers = 0;

        for (Player player : Bukkit.getWorld("world").getPlayers()) {
            countPlayers += 1;
        }

        if (entityType == EntityType.PHANTOM) {
            for (Entity phantom : Bukkit.getWorld("world").getEntities()) {
                EntityType phantomType = phantom.getType();
                if (phantomType == EntityType.PHANTOM) {
                    countPhantoms += 1;
                }
            }
        }

        if (countPhantoms > countPlayers) {
            entity.remove();
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(ChatColor.AQUA + "[Система]" + ChatColor.WHITE + " Удалено " + ChatColor.AQUA + "1" + ChatColor.WHITE + " фантомов.");
            }
        }
    }
    @EventHandler
    public void onItemDamagedEvent (PlayerItemDamageEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        String whatItem = item.getType().toString();
        int itemMaxUses = item.getType().getMaxDurability();
        int itemNowDamage = item.getDurability();
        int durabillity = itemMaxUses - itemNowDamage - 1;
        if (durabillity <= 10 & durabillity > 0) {
            String pingerState = DedWhitelist.getPlugin(DedWhitelist.class).config.getString("players."+player);
            String itemStack = DedWhitelist.getPlugin(DedWhitelist.class).config.getString("items."+player);
            if (pingerState != null) {
                if (pingerState.equals("true")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 6F, 1F);
                    player.sendMessage(ChatColor.YELLOW + "[Pinger@DedWhitelist] Внимание! Использованный инструмент "+whatItem+" имеет слишком маленькую прочность - "+ChatColor.RED + durabillity + "/" + itemMaxUses + ChatColor.YELLOW + "!");
                }
            }
            /*if (durabillity == 1) {
                if (itemStack != null && item.toString().equals(itemStack)) {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 6F, 1F);
                    player.sendMessage(ChatColor.DARK_RED + "[Pinger@DedWhitelist] Внимание! Использованный инструмент "+whatItem+" имеет слишком маленькую прочность - "+ChatColor.RED + durabillity + "/" + itemMaxUses + ChatColor.DARK_RED + "!");
                    player.sendMessage(ChatColor.GREEN + "[Pinger@DedWhitelist] Система Pinger защитила предмет от поломки.");
                    event.setCancelled(true);
                }
            }*/
        }
    }

    @EventHandler
    public void onPlayerDied (PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String deathlocatorState = DedWhitelist.getPlugin(DedWhitelist.class).config.getString("deadplayerslog."+player);
        if (deathlocatorState != null) {
            if (deathlocatorState.equals("true")) {
                int X = player.getLocation().getBlockX();
                int Y = player.getLocation().getBlockY();
                int Z = player.getLocation().getBlockZ();
                player.sendMessage(ChatColor.GREEN + "[DeathLocator@DedWhitelist] Смерть сохранена. Вы умерли на координатах: "+X+" "+Y+" "+Z+" в мире "+player.getWorld());
            }
        }
    }

    @EventHandler
    public void onPlayerMoved (PlayerMoveEvent event) {
        boolean netherroofState = Boolean.parseBoolean(DedWhitelist.getPlugin(DedWhitelist.class).config.getString("nether-roof-enabled"));
        if (!netherroofState) {
            Player player = event.getPlayer();
            String world = player.getWorld().getName();
            if (world.equals("world_nether")) {
                int Y = player.getLocation().getBlockY();
                if (Y >= 128) {
                    Location teleportLocation = new Location(player.getWorld(), -3, 61, 0);
                    player.teleport(teleportLocation);
                    ItemStack item = new ItemStack(Material.ENDER_PEARL);
                    player.getInventory().addItem(item);
                    player.sendMessage(ChatColor.RED + "Тихо ковбой, не так высоко");
                }
            }
        }
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
            boolean endState = Boolean.parseBoolean(DedWhitelist.getPlugin(DedWhitelist.class).config.getString("end-enabled"));
            if (!endState) {
                event.setCancelled(true);
            }
        }
    }
}