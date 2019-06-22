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
                    case "reload":
                        instance.config = new Config( instance );
                        Tools.Prt( player, ChatColor.GREEN + "Command Logger Config Reloaded.", Tools.consoleMode.max, programCode );
                        return true;
                    case "status":
                        Config.Status( player );
                        return true;
                    case "console":
                        try {
                            Tools.setDebug( args[1], programCode );
                        } catch ( Exception e ) {}
                        Tools.Prt( player,
                            ChatColor.GREEN + "System Debug Mode is [ " +
                            ChatColor.RED + Tools.consoleFlag.get( programCode ) +
                            ChatColor.GREEN + " ]",
                            programCode
                        );
                        return true;
                    default:
                        return false;
                }
            } else return false;
        } else return false;
    }
    
}