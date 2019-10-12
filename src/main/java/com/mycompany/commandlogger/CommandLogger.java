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
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.mycompany.commandlogger.config.Config;
import com.mycompany.commandlogger.command.CLCommand;
import com.mycompany.commandlogger.tool.WriteCommand;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.kumaisulibraries.Utility;
import static com.mycompany.commandlogger.config.Config.programCode;
import static com.mycompany.kumaisulibraries.Tools.consoleMode;

/**
 *
 * @author sugichan
 */
public class CommandLogger extends JavaPlugin implements Listener {

    public static Config config;

    /**
     * 
     *
     */
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents( this, this );
        Tools.entryDebugFlag( programCode, consoleMode.print );
        config = new Config( this );
        getCommand( "logger" ).setExecutor( new CLCommand( this ) );
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

        String msgDisplay = Utility.StringBuild(
            ChatColor.DARK_GREEN.toString(), "[", programCode, "] ",
            ChatColor.DARK_AQUA.toString(), player.getDisplayName(), " ",
            ChatColor.GRAY.toString(), message
        );
        Bukkit.getOnlinePlayers().stream().filter( ( p ) -> ( p.hasPermission( "CommandLogger.view" ) || p.isOp() ) ).forEachOrdered( ( p ) -> { p.sendMessage( msgDisplay ); } );

        String msgLog = Utility.StringBuild( player.getName(), " ", message );
        Tools.Prt( msgLog, consoleMode.full, programCode );
        if ( Config.fileOut ) { WriteCommand.Output( msgLog, this.getDataFolder().toString() ); }

        return true;
    }
}
