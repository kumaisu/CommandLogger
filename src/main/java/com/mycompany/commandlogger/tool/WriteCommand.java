/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.commandlogger.tool;

import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.kumaisulibraries.Utility;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import static com.mycompany.commandlogger.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class WriteCommand {

    /**
     * 新規の照会があった場合に、テキストファイルへ日時と共に記録する
     *
     * @param msg
     * @param DataFolder
     * @return
     */
    public static boolean Output( String msg, String DataFolder ) {
        File UKfile = new File( DataFolder, "PlayerCommand.log" );
        FileConfiguration UKData = YamlConfiguration.loadConfiguration( UKfile );

        SimpleDateFormat cdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        UKData.set( cdf.format( new Date() ), msg );

        try {
            UKData.save( UKfile );
        }
        catch ( IOException e ) {
            Tools.Prt( Utility.StringBuild( ChatColor.RED.toString(), "Could not save UnknownIP File." ), programCode );
            return false;
        }
        return true;
    }


}
