package me.eccentric_nz.tardissonicblaster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The sonic blaster uses digital technology to create a sonic wave, projected
 * into the form of pulsing squares of blue light, which can cut through thick
 * walls. It also has a reverse function which can replace the removed chunk of
 * material afterwards. This is deemed a "special feature" of the blaster, and
 * is said to use up a lot of the batteries on which it ran.
 *
 * In the hands of the Doctor and his friends, the blaster is never seen to be
 * used as an offensive weapon, but rather as a tool for a means of escape or
 * forced entry. However, Jack brandished the gun at those infected by the
 * nanogenes and explained that the blaster could also function as a sonic
 * disruptor and a sonic cannon.
 *
 * When Rose used it to create a hole in a floor, it did not injure her, Jack's
 * or the Doctor's feet in the process.
 *
 * Captain Jack Harkness brought a sonic blaster with him to the year 1941
 * during his con man days. He used it to open a door by removing the lock.
 * After switching it with a banana, the Ninth Doctor used it to blast away part
 * of the wall to escape the gas mask zombies. Rose, who called it a "squareness
 * gun" because of its blast pattern, used it to create a hole in the floor.
 *
 * @author eccentric_nz
 */
public class TARDISSonicBlasterCommand implements CommandExecutor {

    private final TARDISSonicBlaster plugin;

    public TARDISSonicBlasterCommand(TARDISSonicBlaster plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tardissonicblaster")) {
            Player player = null;
            if (sender instanceof Player) {
                player = (Player) sender;
            }
            if (player == null) {
                sender.sendMessage(plugin.getPluginName() + "Command can only be used by a player!");
                return true;
            }
            // do stuff
            return true;
        }
        return false;
    }
}
