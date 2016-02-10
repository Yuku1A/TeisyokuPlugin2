package net.jp.minecraft.plugins.Commands;

import net.jp.minecraft.plugins.Listener.Listener_TicksPerSecond;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_TPS implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        String s1 = Listener_TicksPerSecond.doubleToString(Listener_TicksPerSecond.getTps(1));
        String s2 = Listener_TicksPerSecond.doubleToString(Listener_TicksPerSecond.getTps(2));
        String s3 = Listener_TicksPerSecond.doubleToString(Listener_TicksPerSecond.getTps(3));

        double d1 = Double.parseDouble(s1);
        double d2 = Double.parseDouble(s2);
        double d3 = Double.parseDouble(s3);

        double dAll = d1+d2+d3;

        String ds1 = doubleToString(d1);
        String ds2 = doubleToString(d2);
        String ds3 = doubleToString(d3);

        Msg.success(sender, ChatColor.GOLD + "現在のラグ状況"+ ChatColor.DARK_GRAY +" ： " + color(d1) + ds1 + ChatColor.RESET + "% , " + color(d2) + ds2 + ChatColor.RESET + "% , " + color(d3) + ds3+ ChatColor.RESET + "%");
        Msg.success(sender, ChatColor.GOLD + "診断結果" + ChatColor.DARK_GRAY + " ： " + status(dAll));
        Msg.success(sender, "近況1分、3分、5分のデータで、/tps のデータを元に算出");
        return true;
    }

    public ChatColor color(double num){
        if(num >= 18){
            return ChatColor.GREEN;
        }
        else if(num >= 12){
            return  ChatColor.YELLOW;
        }
        else{
            return ChatColor.RED;
        }
    }

    public String status(double num){
        if(num >= 18*3){
            return ChatColor.GREEN + "良好";
        }
        else if(num >= 12*3){
            return  ChatColor.YELLOW + "異常";
        }
        else{
            return ChatColor.RED + "危険";
        }
    }

    public static String doubleToString(double num){
        num = 100-num*5;
        BigDecimal bd = new BigDecimal(num);
        BigDecimal bd2 = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
        return bd2.toString();
    }
}