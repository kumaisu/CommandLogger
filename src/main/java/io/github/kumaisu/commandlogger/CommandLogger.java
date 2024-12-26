/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kumaisu.commandlogger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import io.github.kumaisu.commandlogger.config.Config;
import io.github.kumaisu.commandlogger.command.CLCommand;
import io.github.kumaisu.commandlogger.tool.WriteCommand;
import io.github.kumaisu.commandlogger.Lib.Tools;
import io.github.kumaisu.commandlogger.Lib.Utility;
import static io.github.kumaisu.commandlogger.config.Config.programCode;
import static io.github.kumaisu.commandlogger.Lib.Tools.consoleMode;

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
    public void onPreprocess( PlayerCommandPreprocessEvent event ) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        if ( player == null ) { return; }

        String msgDisplay = Utility.StringBuild(
            ChatColor.DARK_GREEN.toString(), "[", programCode, "] ",
            ChatColor.DARK_AQUA.toString(), player.getDisplayName(), " ",
            ChatColor.GRAY.toString(), message
        );
        String msgLog = Utility.StringBuild( player.getName(), " ", message );
        Tools.Prt( msgLog, consoleMode.full, programCode );

        if ( Config.NoPlayer.contains( player.getName() ) ) { return; }

        Bukkit.getOnlinePlayers().stream().filter( ( p ) ->
            ( p.hasPermission( "CommandLogger.view" ) ) ).forEachOrdered( ( p ) ->
                { p.sendMessage( msgDisplay ); }
        );

        for ( String key : Config.Aleart ) {
            if ( message.toLowerCase().contains( key.toLowerCase() ) ) { return; }
        }

        if ( Config.fileOut ) {
            WriteCommand.Output( msgLog, this.getDataFolder().toString() );
        }
    }
}
