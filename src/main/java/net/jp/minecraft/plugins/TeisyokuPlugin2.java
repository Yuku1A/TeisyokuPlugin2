package net.jp.minecraft.plugins;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import net.jp.minecraft.plugins.Commands.Command_TabName;
import net.jp.minecraft.plugins.Listener.Listener_Horse;
import net.jp.minecraft.plugins.Listener.Listener_MobGrief;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class TeisyokuPlugin2 extends JavaPlugin implements Listener {
    File newConfig_teisyoku;
    File newConfig_last;
    File newConfig_nick;
    File newConfig_cart;
    File newConfig_tpoint;
    File newConfig_tpoint_settings;
    File newConfig_horse;
    FileConfiguration TeisyokuConfig;
    FileConfiguration LastJoinPlayerConfig;
    FileConfiguration NickConfig;
    FileConfiguration CartConfig;
    FileConfiguration TPointConfig;
    FileConfiguration TPointSettingsConfig;
    FileConfiguration HorseConfig;
    private static TeisyokuPlugin2 instance;

    public void onEnable()
    {
        PluginManager pm = Bukkit.getServer().getPluginManager();

        instance = this;

        pm.registerEvents(new Listener_JoinEvent(), this);
        pm.registerEvents(new Listener_NetherGateEvent(), this);
        pm.registerEvents(new Listener_DeathEvent(), this);
        pm.registerEvents(new Listener_EntityDamage(), this);
        pm.registerEvents(new Listener_WitherSpawmCancel(), this);
        pm.registerEvents(new Listener_MineCartEvent(), this);
        pm.registerEvents(new Listener_Gomibako(), this);
        pm.registerEvents(new Listener_LastJoin(), this);
        pm.registerEvents(new Listener_Chat(), this);
        pm.registerEvents(new Listener_Tab(), this);
        pm.registerEvents(new Listener_Sign(), this);
        pm.registerEvents(new GUI_YesNo(), this);
        pm.registerEvents(new GUI_ClickEvent(), this);
        pm.registerEvents(new Listener_SignColor(), this);
        pm.registerEvents(new Listener_TPoint(), this);
        pm.registerEvents(new Listener_MobGrief(), this);
        pm.registerEvents(new Listener_Horse(), this);

        getCommand("help").setExecutor(new Command_Help());

        getCommand("fly").setExecutor(new Command_Fly());

        getCommand("t").setExecutor(new Command_Teisyoku());
        getCommand("teisyoku").setExecutor(new Command_Teisyoku());

        getCommand("gomi").setExecutor(new Command_Gomibako());
        getCommand("gomibako").setExecutor(new Command_Gomibako());

        getCommand("p").setExecutor(new Command_Players());
        getCommand("players").setExecutor(new Command_Players());

        getCommand("last").setExecutor(new Command_Last());

        getCommand("ad").setExecutor(new Command_Ad());
        getCommand("notice").setExecutor(new Command_Ad());

        getCommand("call").setExecutor(new Command_Call());

        getCommand("cart").setExecutor(new Command_Cart());
        getCommand("minecart").setExecutor(new Command_Cart());

        getCommand("nick").setExecutor(new Command_Nick());
        getCommand("nickname").setExecutor(new Command_Nick());

        getCommand("tt").setExecutor(new Command_TPoint());
        getCommand("point").setExecutor(new Command_TPoint());
        getCommand("tpoint").setExecutor(new Command_TPoint());

        getCommand("ri").setExecutor(new Command_RailwayInfo());
        getCommand("railwayinfo").setExecutor(new Command_RailwayInfo());

        getCommand("tabname").setExecutor(new Command_TabName());

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable()
        {
            public void run() {
                List<String> ad = TeisyokuConfig.getStringList("ad");
                for (String s : ad){
                    Bukkit.getServer().broadcastMessage(Messages.getNormalPrefix() + color(s));
                }
            }
        }, 0L, 54000L);

        TeisyokuConfig();
        saveTeisyokuConfig();

        LastJoinPlayerConfig();
        saveLastPlayerJoinConfig();

        NickConfig();
        saveNickConfig();

        TPointConfig();
        saveTPointConfig();

        CartConfig();
        saveCartConfig();

        HorseConfig();
        saveHorseConfig();

        if(TeisyokuConfig.get("title") == null){
            TeisyokuConfig.set("title","§bWelcome to My server" +
                    "  ");
            saveTeisyokuConfig();
        }
        if(TeisyokuConfig.get("subtitle") == null){
            TeisyokuConfig.set("subtitle","§aWebsite:http://example.com/");
            saveTeisyokuConfig();
        }
        if(TeisyokuConfig.get("ad") == null){
            List<String> list = Arrays.asList("Welcome to My server", "Github : https://github.com/syokkendesuyo/TeisyokuPlugin2/", "Create by syokkendesuyo");
            TeisyokuConfig.set("ad",list);
            saveTeisyokuConfig();
        }
        if(TeisyokuConfig.get("joinMessage") == null){
            List<String> list = Arrays.asList("Welcome to My server", "TeisyokuPlugin2 is enabled! ", "Create by syokkendesuyo");
            TeisyokuConfig.set("joinMessage",list);
            saveTeisyokuConfig();
        }
    }

    public void TeisyokuConfig()
    {
        this.newConfig_teisyoku = new File(getDataFolder(), "Teisyoku.yml");
        this.TeisyokuConfig = YamlConfiguration.loadConfiguration(this.newConfig_teisyoku);
        saveTeisyokuConfig();
    }

    public void saveTeisyokuConfig()
    {
        try {
            this.TeisyokuConfig.save(this.newConfig_teisyoku);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadTeisyokuConfig()
    {
        try {
            this.TeisyokuConfig.load(this.newConfig_teisyoku);
            this.TeisyokuConfig.save(this.newConfig_teisyoku);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LastJoinPlayerConfig()
    {
        this.newConfig_last = new File(getDataFolder(), "LastJoinPlayersData.yml");
        this.LastJoinPlayerConfig = YamlConfiguration.loadConfiguration(this.newConfig_last);
        saveLastPlayerJoinConfig();
    }

    public void saveLastPlayerJoinConfig()
    {
        try {
            this.LastJoinPlayerConfig.save(this.newConfig_last);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadLastPlayerJoinConfig()
    {
        try {
            this.LastJoinPlayerConfig.load(this.newConfig_last);
            this.LastJoinPlayerConfig.save(this.newConfig_last);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void NickConfig()
    {
        this.newConfig_nick = new File(getDataFolder(), "NickData.yml");
        this.NickConfig = YamlConfiguration.loadConfiguration(this.newConfig_nick);
        saveLastPlayerJoinConfig();
    }

    public void saveNickConfig()
    {
        try {
            this.NickConfig.save(this.newConfig_nick);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadNickConfig()
    {
        try {
            this.NickConfig.load(this.newConfig_nick);
            this.NickConfig.save(this.newConfig_nick);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TpointSettingsConfig()
    {
        this.newConfig_tpoint_settings = new File(getDataFolder(), "TPoint_Settings.yml");
        this.TPointSettingsConfig = YamlConfiguration.loadConfiguration(this.newConfig_tpoint);
        saveLastPlayerJoinConfig();
    }

    public void saveTpointSettingsConfig()
    {
        try {
            this.TPointSettingsConfig.save(this.newConfig_tpoint_settings);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadTpointSettingsConfig()
    {
        try {
            this.TPointSettingsConfig.load(this.newConfig_tpoint_settings);
            this.TPointSettingsConfig.save(this.newConfig_tpoint_settings);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TPointConfig()
    {
        this.newConfig_tpoint = new File(getDataFolder(), "TPoint.yml");
        this.TPointConfig = YamlConfiguration.loadConfiguration(this.newConfig_tpoint);
        saveLastPlayerJoinConfig();
    }

    public void saveTPointConfig()
    {
        try {
            this.TPointConfig.save(this.newConfig_tpoint);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadTPointConfig()
    {
        try {
            this.TPointConfig.load(this.newConfig_tpoint);
            this.TPointConfig.save(this.newConfig_tpoint);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CartConfig()
    {
        this.newConfig_cart = new File(getDataFolder(), "Cart.yml");
        this.CartConfig = YamlConfiguration.loadConfiguration(this.newConfig_cart);
        saveLastPlayerJoinConfig();
    }

    public void saveCartConfig() {
        try {
            this.CartConfig.save(this.newConfig_cart);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadCartConfig() {
        try {
            this.CartConfig.load(this.newConfig_cart);
            this.CartConfig.save(this.newConfig_cart);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HorseConfig()
    {
        this.newConfig_horse = new File(getDataFolder(), "Horses.yml");
        this.HorseConfig = YamlConfiguration.loadConfiguration(this.newConfig_horse);
        saveLastPlayerJoinConfig();
    }

    public void saveHorseConfig() {
        try {
            this.HorseConfig.save(this.newConfig_horse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadHorseConfig() {
        try {
            this.HorseConfig.load(this.newConfig_horse);
            this.HorseConfig.save(this.newConfig_horse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TeisyokuPlugin2 getInstance()
    {
        return instance;
    }

    public static String color(String str){
        return str.replaceAll("&","§");
    }
}