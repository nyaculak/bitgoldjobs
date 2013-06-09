package bitgoldjobs.sqltasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgoldjobs.BitGoldJobs;


public class SaveAllJobsTask extends BukkitRunnable {
	
	private final BitGoldJobs plugin;
	
	public SaveAllJobsTask(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		//plugin.getServer().broadcastMessage("Saving BitGold accounts");
		while(!plugin.sql.open()) { ; }
		if (plugin.getServer().getOnlinePlayers().length == 0)
			return;
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			String job = plugin.getJob(player);
			int level = plugin.getLevel(player);
			try {
				// ToDo:  Consolidate to one SQL operation
				plugin.sql.query("UPDATE Accounts SET job='"
						+ job + "' WHERE user_name='"
						+ player.getName() + "'");
				plugin.sql.query("UPDATE Accounts SET level="
						+ level + " WHERE user_name='"
						+ player.getName() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		while(!plugin.sql.close()) { ; }
		//plugin.getServer().broadcastMessage("Done saving BitGold accounts");
	}

}
