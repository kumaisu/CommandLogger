# CommandLogger
Minecraft Plugin Player Command Logger Repository

## Overview  
  
CommandLogger は、オンライン中のプレイヤーが使ったコマンドを記録するプラグインです。  
(CommandLogger is a plug-in that records the commands used by players online.)  
  
## Operation check  
Spigot Server 1.12.2 and 1.14.4  

## Support  
Open a new issue here: [https//github.com/kumaisu/CommandLogger/issues](https://github.com/kumaisu/CommandLogger/issues)  
  
## Features  
none  
  
## Releases  
Github projects have a "releases" link on their home page.  
If you still don't see it, [click here](https://github.com/kumaisu/CommandLogger/releases) for CommandLogger releases.  
  
## Wikis  
[Player Command Logger Wiki](https://github.com/kumaisu/CommandLogger/wiki)  
  
## Function
1.オンライン中のプレイヤーが実行したコマンドをパーミッション設定者にリアルタイムに表示します  
  (Display the command executed by the online player to the permission setter in real time)  
2.使われたコマンドの情報をファイルまたはMySQLデータベースに登録します  
  (Register information of used command to file or MySQL database)  
3.MySQLを使用している場合、各種データ参照機能が可能になります  
  (f you are using MySQL, various data reference functions become possible)  

## Usage  
  
/Logger reload  
/Logger status  
/Logger console [max/full/normal/none]  

MySQL指定がある場合に有効コマンド (Valid command when MySQL specified)  
/Logger             直近の使用ログ (Most recent usage log)  
/Logger u:player    指定プレイヤーのみの使用ログ (Usage log for designated players only)  
/Logger d:yymmdd    指定日の使用ログ (Usage log of specified day)  
  
**How to Install**  
1.サーバーのプラグインディレクトリに CommandLogger.jar を入れて起動します  
2.一旦終了し、作成されたConfig.ymlを編集します  
3.config 設定の通り MySQL にデーターベースを Create します(任意)  
4.再度サーバーを起動  
  
Contact is Discord Kitsune#5955  
Discord Server https://discord.gg/AgX3UxR  
