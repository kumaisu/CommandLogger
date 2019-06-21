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

    public Config(Plugin plugin) {
        this.plugin = plugin;
        Tools.entryDebugFlag( programCode, consoleMode.none );
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

        host = config.getString( "mysql.host" );
        port = config.getString( "mysql.port" );
        database = config.getString( "mysql.database" );
        username = config.getString( "mysql.username" );
        password = config.getString( "mysql.password" );

        consoleMode DebugFlag;
        try {
            DebugFlag = consoleMode.valueOf( config.getString( "Debug" ) );
        } catch( IllegalArgumentException e ) {
            Tools.Prt( ChatColor.RED + "Config Debugモードの指定値が不正なので、normal設定にしました", programCode );
            DebugFlag = consoleMode.normal;
        }
        Tools.entryDebugFlag( programCode, DebugFlag );
    }

    public static void Status( Player p ) {
        consoleMode consolePrintFlag = ( ( p == null ) ? consoleMode.none:consoleMode.stop );
        Tools.Prt( p, ChatColor.GREEN + "=== Player Command Logger Status ===", consolePrintFlag, programCode );
        Tools.Prt( p, ChatColor.WHITE + "Degub Mode : " + ChatColor.YELLOW + Tools.consoleFlag.get( programCode ).toString(), consolePrintFlag, programCode );
        Tools.Prt( p, ChatColor.WHITE + "Mysql : " + ChatColor.YELLOW + host + ":" + port, consolePrintFlag, programCode );
        Tools.Prt( p, ChatColor.WHITE + "DB Name : " + ChatColor.YELLOW + database, consolePrintFlag, programCode );
        Tools.Prt( p, ChatColor.GREEN + "==========================", consolePrintFlag, programCode );
    }
}
