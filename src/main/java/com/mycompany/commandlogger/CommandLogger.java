/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.commandlogger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.mycompany.commandlogger.config.Config;
import com.mycompany.kumaisulibraries.Tools;

import static com.mycompany.commandlogger.config.Config.programCode;
import static com.mycompany.kumaisulibraries.Tools.consoleMode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 *
 * @author sugichan
 */
public class CommandLogger extends JavaPlugin implements Listener {

    private Config config;

    /**
     * 
     *
     */
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents( this, this );
        Tools.entryDebugFlag( programCode, consoleMode.none );
        config = new Config( this );
    }

    /**
     * 
     *
     */
    @Override
    public void onDisable() {
        super.onDisable(); //To change body of generated methods, choose Tools | Templates.
    }

    @EventHandler
    public boolean onPreprocess( PlayerCommandPreprocessEvent event ) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        if ( player == null ) { return false; }

        String msg = 
            ChatColor.DARK_GREEN + "[" + programCode + "] " +
            ChatColor.DARK_AQUA + player.getDisplayName() +
            ChatColor.DARK_GRAY + message;
        
        Bukkit.getOnlinePlayers().stream().filter( ( p ) -> ( p.hasPermission( "CommandLogger.view" ) || p.isOp() ) ).forEachOrdered( ( p ) -> { p.sendMessage( msg ); } );

        Tools.Prt( msg, consoleMode.full, programCode );

        return true;
    }
    
    @Override
    public boolean onCommand( CommandSender sender,Command cmd, String commandLabel, String[] args ) {
        Player player = ( ( sender instanceof Player ) ? ( Player ) sender : ( Player ) null );

        //  if ( !( sender instanceof Player ) ) { return false; }

    if ( cmd.getName().equalsIgnoreCase( "logger" ) && ( player == null || player.hasPermission( "CommandLogger.admin" ) ) ) {

        if ( args.length > 0 ) {
            switch ( args[0] ) {
                case "reload":
                    config = new Config( this );
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

            
    } else {
        return false;
    }

    }
}
