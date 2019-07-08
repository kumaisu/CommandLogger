/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.commandlogger.tool;

import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.entity.Player;
import com.mycompany.kumaisulibraries.Tools;
import com.mycompany.commandlogger.config.Config;
import static com.mycompany.commandlogger.config.Config.programCode;

/**
 *
 * @author sugichan
 */
public class MySQLControl {

    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    private Connection connection;

    public static String name = "Unknown";
    public static Date logout;
    public static int offset = 0;
    
    /**
     * ライブラリー読込時の初期設定
     *
     */
    public MySQLControl() {
    }

    /**
     * MySQLへのコネクション処理
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void openConnection() throws SQLException, ClassNotFoundException {

        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized ( this ) {
            if ( connection != null && !connection.isClosed() ) {
                return;
            }
            Class.forName( "com.mysql.jdbc.Driver" );
            connection = DriverManager.getConnection( "jdbc:mysql://" + Config.host + ":" + Config.port + "/" + Config.database, Config.username, Config.password );

            //  テーブルの作成
            //		uuid : varchar(36)	player uuid
            //		name : varchar(20)	player name
            //		logiut : DATETIME	last Logout Date
            //		offset : int 		total Login Time
            //  存在すれば、無視される
            String sql = "CREATE TABLE IF NOT EXISTS player( uuid varchar(36), name varchar(20), logout DATETIME, offset int )";
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.executeUpdate();
        }
    }

    /**
     * プレイヤー情報を新規追加する
     *
     * @param player
     */
    public void AddSQL( Player player ) {
        try {
            openConnection();

            String sql = "INSERT INTO player (uuid, name, logout, offset) VALUES (?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, player.getUniqueId().toString() );
            preparedStatement.setString( 2, player.getName() );
            preparedStatement.setString( 3, sdf.format( new Date() ) );
            preparedStatement.setInt( 4, 0 );

            preparedStatement.executeUpdate();
            
            this.name = player.getName();
            this.logout = new Date();
            this.offset = 0;
            
            Tools.Prt( "Add Data to SQL Success.", Tools.consoleMode.full , programCode );

        } catch ( ClassNotFoundException | SQLException e ) {
            Tools.Prt( "Error AddToSQL", programCode );
        }
    }

    /**
     * プレイヤー情報を削除する
     *
     * @param uuid
     * @return
     */
    public boolean DelSQL( UUID uuid ) {
        try {
            openConnection();
            String sql = "DELETE FROM player WHERE uuid = '" + uuid.toString() + "'";
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.executeUpdate();
            Tools.Prt( "Delete Data from SQL Success.", Tools.consoleMode.full , programCode );
            return true;
        } catch ( ClassNotFoundException | SQLException e ) {
            Tools.Prt( "Error DelFromSQL", programCode );
            return false;
        }
    }

    /**
     * UUIDからプレイヤー情報を取得する
     *
     * @param uuid
     * @return
     */
    public boolean GetSQL( UUID uuid ) {
        try {
            openConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM player WHERE uuid = '" + uuid.toString() + "';";
            ResultSet rs = stmt.executeQuery( sql );
            if ( rs.next() ) {
                this.name = rs.getString( "name" );
                this.logout = rs.getDate( "logout" );
                this.offset = rs.getInt( "offset" );
                Tools.Prt( "Get Data from SQL Success.", Tools.consoleMode.full , programCode );
                return true;
            }
        } catch ( ClassNotFoundException | SQLException e ) {
            Tools.Prt( "Error GetPlayer", programCode );
        }
        return false;
    }

    /**
     * UUIDからプレイヤーのログアウト日時を更新する
     *
     * @param uuid
     */
    public void SetLogoutToSQL( UUID uuid ) {
        try {
            openConnection();

            String sql = "UPDATE player SET logout = " + sdf.format( new Date() ) + " WHERE uuid = '" + uuid.toString() + "';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            Tools.Prt( "Set logout Date to SQL Success.", Tools.consoleMode.full , programCode );
        } catch ( ClassNotFoundException | SQLException e ) {
            Tools.Prt( "Error ChangeStatus", programCode );
        }
    }

    /**
     * UUIDからプレイヤーのオフセット値を設定する
     *
     * @param uuid
     * @param offset
     */
    public void SetOffsetToSQL( UUID uuid, int offset ) {
        try {
            openConnection();

            String sql = "UPDATE player SET offset = " + offset + " WHERE uuid = '" + uuid.toString() + "';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            Tools.Prt( "Set Offset Data to SQL Success.", Tools.consoleMode.full , programCode );
        } catch ( ClassNotFoundException | SQLException e ) {
            Tools.Prt( "Error ChangeStatus", programCode );
        }
    }

}
