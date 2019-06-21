/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.commandlogger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import static com.mycompany.kumaisulibraries.Tools.consoleMode;
import org.bukkit.event.EventHandler;

/**
 *
 * @author sugichan
 */
public class CommandLogger extends JavaPlugin implements Listener {

    private final String programCode = "CL";

    /**
     * 
     *
     */
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents( this, this );
        Tools.entryDebugFlag( programCode, consoleMode.none );
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
    public boolean onCommand( PlayerCommandPreprocessEvent event ) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        Tools.Prt( "CommandLogger Catch !!", programCode );

        if ( player == null ) { return false; }

        this.getServer().broadcastMessage( "[Broadcast] " + message );
        Tools.Prt( player, message, consoleMode.full, programCode);

        return true;
    }
}
