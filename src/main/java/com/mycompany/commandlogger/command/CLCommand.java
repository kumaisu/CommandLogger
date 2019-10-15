/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.commandlogger.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.commandlogger.CommandLogger;
import com.mycompany.commandlogger.config.Config;
import static com.mycompany.commandlogger.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class CLCommand implements CommandExecutor {

    private final CommandLogger instance;

    public CLCommand( CommandLogger instance ) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand( CommandSender sender,Command cmd, String commandLabel, String[] args ) {
        Player player = ( ( sender instanceof Player ) ? ( Player ) sender : ( Player ) null );

        //  if ( !( sender instanceof Player ) ) { return false; }

        if ( player == null || player.hasPermission( "CommandLogger.admin" ) ) {
            if ( args.length > 0 ) {
                switch ( args[0] ) {
                    case "Reload":
                        instance.config = new Config( instance );
                        Tools.Prt( player, ChatColor.GREEN + "Command Logger Config Reloaded.", Tools.consoleMode.max, programCode );
                        return true;
                    case "Status":
                        Config.Status( player );
                        return true;
                    case "Console":
                        if ( !Tools.setDebug( args[1], programCode ) ) {
                            Tools.entryDebugFlag( programCode, Tools.consoleMode.normal );
                            Tools.Prt( ChatColor.RED + "Config Debugモードの指定値が不正なので、normal設定にしました", programCode );
                        }
                        Tools.Prt( player,
                            ChatColor.GREEN + "System Debug Mode is [ " +
                            ChatColor.RED + Tools.consoleFlag.get( programCode ).toString() +
                            ChatColor.GREEN + " ]",
                            programCode
                        );
                        return true;
                    default:
                }
            }
        } else Tools.Prt( player, "You do not have permission.", programCode );
        Tools.Prt( player, "Logger Reload", programCode );
        Tools.Prt( player, "Logger Status", programCode );
        Tools.Prt( player, "Logger Console [max,full,normal,none]", programCode );
        return false;
    }
    
}
