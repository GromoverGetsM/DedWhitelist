package org.mineproject.dedwhitelist;


import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public final class DedWhitelist extends JavaPlugin {

    public FileConfiguration config;
    private static DedWhitelist instance;

    


    @Override
    public void onEnable() {
        this.getCommand("discord").setExecutor(this);

        instance = this;
        config = getConfig();
        saveDefaultConfig();

        String discordWebhookUrl = config.getString("settings.user-discord-webhook-url");
        String discordAdminWebhookUrl = config.getString("settings.admin-discord-webhook-url");
        boolean needsCrash = Boolean.parseBoolean(config.getString("settings.crash-if-no-url"));
        boolean discordConnector = Boolean.parseBoolean(config.getString("settings.discord-sysadmin-connector"));

        if (discordWebhookUrl == null || discordAdminWebhookUrl == null) {
            if (discordWebhookUrl == null) {
                onNullStatement(needsCrash, "user-discord-webhook-url");
            }
            if (discordAdminWebhookUrl == null) {
                onNullStatement(needsCrash, "admin-discord-webhook-url");
            }
        } else {
            DiscordWebhook discordWebhook = new DiscordWebhook(discordWebhookUrl);
            discordWebhook.setContent("✅ Сервер запущен");
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            getServer().getPluginManager().registerEvents(new EventListener(discordWebhookUrl, discordAdminWebhookUrl, this), this);
        }

    }

    @Override
    public void onDisable() {
        String discordWebhookUrl = config.getString("settings.user-discord-webhook-url");
        if (discordWebhookUrl != null) {
            DiscordWebhook discordWebhook = new DiscordWebhook(discordWebhookUrl);
            discordWebhook.setContent("❌ Сервер выключен");
            try {
                discordWebhook.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void onNullStatement(boolean needsCrash, String nullUrl) {
        if (needsCrash) {
            getLogger().log(Level.SEVERE, "В конфигураторе config.yml не указан вебхук "+nullUrl+", выключаю сервер...");
            Bukkit.getScheduler().runTask(this, () -> {
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
            });
        } else {
            getLogger().log(Level.SEVERE, "В конфигураторе config.yml не указан вебхук "+nullUrl+", логирование по нему не производится.");
        }
    }

    public static DedWhitelist getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fixlight")) {
            if (sender.getName().equals("GromoverGets") || sender.getName().equals("tea_with_sugar")) {
                Player player = Bukkit.getPlayer("_Mr_ded_");
                for (int i = 0; i < 10; i++) {
                    player.sendMessage(ChatColor.YELLOW + "Ошибка OpenGL" + ChatColor.WHITE + ": 1285 (Out of memory)");
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("discord")) {
            boolean discordConnector = Boolean.parseBoolean(config.getString("settings.discord-sysadmin-connector"));
            if (discordConnector) {
                config.set("settings.discord-sysadmin-connector", false);
                saveConfig();
                getLogger().log(Level.INFO, "Discord-connector: offline");
                return true;
            } else {
                config.set("settings.discord-sysadmin-connector", true);
                saveConfig();
                getLogger().log(Level.INFO, "Discord-connector: offline");
                return true;
            }
        }
        if (cmd.getName().equalsIgnoreCase("fem")) {
            if (args.length >= 1) {
                String argument = args[0];
                int duration = 100;
                int fadeOut = 20;
                if (args[1] != null) {
                    duration = Integer.parseInt(args[1]);
                }
                if (args[2] != null) {
                    fadeOut = Integer.parseInt(args[2]);
                }

                Player player = Bukkit.getPlayer(argument);
                if (player != null && player.isOnline()) {
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 6F, 1F);
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&dВы теперь феминистка"), ChatColor.translateAlternateColorCodes('&', "Поздравляем с новым статусом. С.В.Л."), 0, duration, fadeOut);
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("svl")) {
            if (args.length >= 1) {
                String argument = args[0];
                int duration = 100;
                int fadeOut = 20;
                if (args[1] != null) {
                    duration = Integer.parseInt(args[1]);
                }
                if (args[2] != null) {
                    fadeOut = Integer.parseInt(args[2]);
                }

                Player player = Bukkit.getPlayer(argument);
                if (player != null && player.isOnline()) {
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 6F, 1F);
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&dВы теперь феминистка"), ChatColor.translateAlternateColorCodes('&', "&7Вы лишены всех прав. Поздравляем с вступлением в С.В.Л.!"), 0, duration, fadeOut);
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("netherroof")) {
            if (args.length == 1) {
                String argument = args[0];
                if (argument.equals("enable") || argument.equals("disable")) {
                    boolean netherroofState = Boolean.parseBoolean(config.getString("nether-roof-enabled"));
                    if (argument.equals("enable")) {
                        if (netherroofState) {
                            sender.sendMessage(ChatColor.RED + "[NetherRoof@DedWhitelist] Ограничение на крышу ада уже было снято");
                        } else {
                            config.set("nether-roof-enabled", true);
                            saveConfig();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.sendMessage(ChatColor.GREEN + "[NetherRoof@DedWhitelist] Ограничение на крышу ада снято "+ChatColor.DARK_GREEN+sender.getName());
                            }
                        }
                    } else {
                        if (!netherroofState) {
                            sender.sendMessage(ChatColor.RED + "[NetherRoof@DedWhitelist] Ограничение на крышу ада уже установлено");
                        } else {
                            config.set("nether-roof-enabled", false);
                            saveConfig();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.sendMessage(ChatColor.GREEN + "[NetherRoof@DedWhitelist] Ограничение на крышу ада установлено "+ChatColor.DARK_GREEN+sender.getName());
                            }
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[NetherRoof@DedWhitelist] Использование: /netherroof enable/disable");
                    return true;
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("end")) {
            if (args.length == 1) {
                String argument = args[0];
                if (argument.equals("enable") || argument.equals("disable")) {
                    if (!sender.getName().equals("_Mr_ded_")) {
                        boolean netherroofState = Boolean.parseBoolean(config.getString("end-enabled"));
                        if (argument.equals("enable")) {
                            if (netherroofState) {
                                sender.sendMessage(ChatColor.RED + "[EndSettings@DedWhitelist] Мир энда уже включен");
                            } else {
                                config.set("end-enabled", true);
                                saveConfig();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(ChatColor.GREEN + "[EndSettings@DedWhitelist] Мир энда включен "+ChatColor.DARK_GREEN+sender.getName());
                                }
                            }
                        } else {
                            if (!netherroofState) {
                                sender.sendMessage(ChatColor.RED + "[EndSettings@DedWhitelist] Мир энда уже отключен");
                            } else {
                                config.set("end-enabled", false);
                                saveConfig();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.sendMessage(ChatColor.GREEN + "[EndSettings@DedWhitelist] Мир энда отключен "+ChatColor.DARK_GREEN+sender.getName());
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "/end command returned an unexpected error: org.gromov.dedmclibs.NoPermissionError (Required: dedmclib.admin_perms; Found: dedmclib.feministka_perms)");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[EndSettings@DedWhitelist] Использование: /end enable/disable");
                    return true;
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("pinger")) {
            if (args.length == 1) {
                String argument = args[0];
                if (argument.equals("on")) {
                    String playerExists = config.getString("players."+sender.toString());
                    if (playerExists != null) {
                        if (playerExists == "true") {
                            sender.sendMessage(ChatColor.RED + "[Pinger@DedWhitelist] Пингер уже включен");
                        } else {
                            config.set("players."+sender.toString(), "true");
                            saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "[Pinger@DedWhitelist] Пингер включен");
                            return true;
                        }
                    } else {
                        config.set("players."+sender.toString(), "true");
                        saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "[Pinger@DedWhitelist] Пингер включен");
                        return true;
                    }
                } else if (argument.equals("off")) {
                    String playerExists = config.getString("players."+sender.toString());
                    if (playerExists != null) {
                        if (playerExists == "false") {
                            sender.sendMessage(ChatColor.RED + "[Pinger@DedWhitelist] Пингер уже выключен");
                        } else {
                            config.set("players."+sender.toString(), "false");
                            saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "[Pinger@DedWhitelist] Пингер выключен");
                            return true;
                        }
                    } else {
                        config.set("players."+sender.toString(), "false");
                        saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "[Pinger@DedWhitelist] Пингер выключен");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[Pinger@DedWhitelist] Использование: /pinger on/off");
                    return true;
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("deathlocator")) {
            if (args.length == 1) {
                String argument = args[0];
                if (argument.equals("on")) {
                    String playerExists = config.getString("deadplayerslog."+sender.toString());
                    if (playerExists != null) {
                        if (playerExists == "true") {
                            sender.sendMessage(ChatColor.RED + "[DeathLocator@DedWhitelist] Локатор смерти уже включен");
                        } else {
                            config.set("deadplayerslog."+sender.toString(), "true");
                            saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "[DeathLocator@DedWhitelist] Локатор смерти включен");
                            return true;
                        }
                    } else {
                        config.set("deadplayerslog."+sender.toString(), "true");
                        saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "[DeathLocator@DedWhitelist] Локатор смерти включен");
                        return true;
                    }
                } else if (argument.equals("off")) {
                    String playerExists = config.getString("deadplayerslog."+sender.toString());
                    if (playerExists != null) {
                        if (playerExists == "false") {
                            sender.sendMessage(ChatColor.RED + "[DeathLocator@DedWhitelist] Локатор смерти уже выключен");
                        } else {
                            config.set("deadplayerslog."+sender.toString(), "false");
                            saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "[DeathLocator@DedWhitelist] Локатор смерти выключен");
                            return true;
                        }
                    } else {
                        config.set("deadplayerslog."+sender.toString(), "false");
                        saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "[DeathLocator@DedWhitelist] Локатор смерти выключен");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "[DeathLocator@DedWhitelist] Использование: /deathlocator on/off");
                    return true;
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("dedwl")) {
            if (args.length == 1) {
                String argument = args[0];
                if (argument.equals("reload")) {
                    Bukkit.getPluginManager().disablePlugin(this);
                    Bukkit.getPluginManager().enablePlugin(this);
                }
            }
        }
        return false;
    }

}
