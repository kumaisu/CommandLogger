/*
 *  Copyright (c) 2018 sugichan. All rights reserved.
 */
package com.mycompany.commandlogger.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.kumaisulibraries.Tools.consoleMode;

/**
 * 設定ファイルを読み込む
 *
 * @author sugichan
 */
public class Config {

    public static String programCode = "CL";

    private final Plugin plugin;
    private FileConfiguration config = null;

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;

    public static boolean fileOut;
    public static boolean dbOut;
    
    public Config(Plugin plugin) {
        this.plugin = plugin;
        Tools.entryDebugFlag( programCode, consoleMode.print );
        Tools.Prt( "Config Loading now...", programCode );
        load();
    }

    /*
     * 設定をロードします
     */
    public void load() {
        // 設定ファイルを保存
        plugin.saveDefaultConfig();
        if (config != null) { // configが非null == リロードで呼び出された
            Tools.Prt( "Config Reloading now...", programCode );
            plugin.reloadConfig();
        }
        config = plugin.getConfig();

        dbOut = config.getBoolean( "mysql.enabled", false );
        host = config.getString( "mysql.host" );
        port = config.getString( "mysql.port" );
        database = config.getString( "mysql.database" );
        username = config.getString( "mysql.username" );
        password = config.getString( "mysql.password" );

        fileOut = config.getBoolean( "File", false );

        if ( !Tools.setDebug( config.getString( "Debug" ), programCode ) ) {
            Tools.entryDebugFlag( programCode, Tools.consoleMode.normal );
            Tools.Prt( ChatColor.RED + "Config Debugモードの指定値が不正なので、normal設定にしました", programCode );
        }
    }

    public static void Status( Player p ) {
        Tools.Prt( p, ChatColor.GREEN + "=== Player Command Logger Status ===", programCode );
        Tools.Prt( p, ChatColor.WHITE + "Degub Mode : " + ChatColor.YELLOW + Tools.consoleFlag.get( programCode ).toString(), programCode );
        Tools.Prt( p, ChatColor.WHITE + "Log File   : " + ChatColor.YELLOW + ( fileOut ? "True":"False" ), programCode );
        Tools.Prt( p, ChatColor.WHITE + "Database   : " + ChatColor.YELLOW + ( dbOut ? "True":"False" ), programCode );
        if ( dbOut ) {
            Tools.Prt( p, ChatColor.WHITE + "Mysql      : " + ChatColor.YELLOW + host + ":" + port, programCode );
            Tools.Prt( p, ChatColor.WHITE + "DB Name    : " + ChatColor.YELLOW + database, programCode );
            Tools.Prt( p, ChatColor.WHITE + "username   : " + ChatColor.YELLOW + username, programCode );
            Tools.Prt( p, ChatColor.WHITE + "password   : " + ChatColor.YELLOW + password, programCode);
        }
        Tools.Prt( p, ChatColor.GREEN + "====================================", programCode );
    }
}
