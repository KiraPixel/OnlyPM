package pixels.onlypm;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class OnlyPM extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand(("pm")).setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

                String language = getConfig().getString("language");
                String errorMessage = getConfig().getString(String.join(".","message",language,"errorMessage"));
                String playerNotFound= getConfig().getString(String.join(".","message",language,"playerNotFound"));
                String tag= getConfig().getString(String.join(".","message",language,"tag"));
                if (tag != null){
                    tag = (tag + " ");
                }
                String delimiter = getConfig().getString("delimiter");

                if(args.length <= 1){
                    sender.sendMessage(ChatColor.RED + errorMessage);
                    return false;
                }

                Player recipient = getServer().getPlayer(args[0]);
                String text = "";

                for (int i =1; i< args.length;i++){
                    text = (text + " " + args[i]);
                }

                if (recipient == null){
                    sender.sendMessage(ChatColor.RED + playerNotFound);
                    return false;
                }

                ChatColor color;
                String pmcolor = getConfig().getString("pmColor");
                if (pmcolor == null){
                    pmcolor = "GRAY";
                }
                try{
                    color = ChatColor.valueOf(pmcolor);
                } catch (IllegalArgumentException e) {
                    getServer().getLogger().info(ChatColor.RED + "[PM]: The wrong color is set! Check config.yml");
                    pmcolor = "GRAY";
                }

                color = ChatColor.valueOf(pmcolor);


                text = (color +tag+ sender.getName() + delimiter +recipient.getName() + ":"+ text);
                recipient.sendMessage(text);
                sender.sendMessage(text);
                return true;
            }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
