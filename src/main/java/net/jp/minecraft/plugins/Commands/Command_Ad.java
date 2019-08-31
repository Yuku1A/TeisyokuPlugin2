package net.jp.minecraft.plugins.Commands;

import com.google.common.base.Joiner;
import net.jp.minecraft.plugins.TeisyokuPlugin2;
import net.jp.minecraft.plugins.Utility.CoolDown;
import net.jp.minecraft.plugins.Utility.Msg;
import net.jp.minecraft.plugins.Utility.Permission;
import net.jp.minecraft.plugins.Utility.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;

/**
 * TeisyokuPlugin2
 * adコマンド
 *
 * @author syokkendesuyo
 */
public class Command_Ad implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        TeisyokuPlugin2 plugin = TeisyokuPlugin2.getInstance();

        //コマンドが有効化されているかどうか検出
        if (!plugin.TeisyokuConfig.getBoolean("commands.ad")) {
            Msg.warning(sender, "adコマンドは有効化されていません");
            return true;
        }

        //引数が0だった場合
        if (args.length == 0) {
            help(sender, commandLabel);
            return true;
        }

        //ヘルプ
        if (args[0].equalsIgnoreCase("help")) {
            help(sender, commandLabel);
            return true;
        }

        //パーミッションの確認コマンドを追加
        if (args[0].equalsIgnoreCase("perm") || args[0].equalsIgnoreCase("perms") || args[0].equalsIgnoreCase("permission")) {
            Msg.checkPermission(sender,
                    Permission.USER,
                    Permission.AD,
                    Permission.AD_COOLTIME,
                    Permission.ADMIN
            );
            return true;
        }

        //実行コマンドのパーミッションを確認
        if (!(sender.hasPermission(Permission.USER.toString()) || sender.hasPermission(Permission.AD.toString()) || sender.hasPermission(Permission.ADMIN.toString()))) {
            Msg.noPermissionMessage(sender, Permission.AD);
            return true;
        }

        //クールタイムを確認
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (CoolDown.is(player)) {
                if (!CoolDown.cooldown(player)) {
                    return true;
                }
            }
        }

        //コマンドの引数を結合する
        String arg = Joiner.on(' ').join(args);

        //カラーコードを適用
        String argReplace = arg.replaceAll("&", "§");

        //ブロードキャストでメッセージを送信
        Msg.info(sender, ChatColor.GOLD + "" + ChatColor.BOLD + "お知らせ" + ChatColor.GRAY + " >> " + ChatColor.RESET + argReplace, true);

        //オンラインプレイヤー全員に音を鳴らす
        for (Player player : Bukkit.getOnlinePlayers()) {
            Sounds.sound_note(player);
        }

        //クールタイムを設定
        if (sender instanceof Player) {
            Player player = (Player) sender;

            //権限保有者はクールタイム設定なし
            if (player.hasPermission(Permission.AD_COOLTIME.toString()) || player.hasPermission(Permission.ADMIN.toString())) {
                return true;
            }

            //現在時刻を取得
            Date nowDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            CoolDown.put(player, calendar);
        }

        return true;
    }

    /**
     * adコマンドのヘルプ
     *
     * @param sender       送信者
     * @param commandLabel コマンドラベル
     */
    private void help(CommandSender sender, String commandLabel) {
        Msg.info(sender, "コマンドのヘルプ");
        Msg.commandFormat(sender, commandLabel + " <メッセージ>", "広告を表示");
        Msg.commandFormat(sender, commandLabel + " <permission|perms|perm>", "パーミッションを表示");
    }
}
