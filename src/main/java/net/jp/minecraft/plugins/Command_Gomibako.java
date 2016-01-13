package net.jp.minecraft.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * TeisyokuPlugin2
 *
 * @auther syokkendesuyo
 */
public class Command_Gomibako implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(! (sender.hasPermission(Permissions.getGomibakoCommandPermisson()))){
            sender.sendMessage(Messages.getNoPermissionMessage(Permissions.getGomibakoCommandPermisson()));
            return true;
        }

        if(args.length != 1){
            Listener_Gomibako.openGomibako(sender);
        }
        else if(args.length == 1){
            if(args[0].equalsIgnoreCase("perm")||args[0].equalsIgnoreCase("permission")){
                sender.sendMessage(Messages.getNormalPrefix() + "パーミッション(通常利用): " + Permissions.getGomibakoPermisson());
                sender.sendMessage(Messages.getNormalPrefix() + "パーミッション(コマンド): " + Permissions.getGomibakoCommandPermisson());
            }
           else{
                sender.sendMessage(Messages.getDenyPrefix() + "引数「" + args[0] + "」は存在しません");
            }
        }
        return true;
    }
}
