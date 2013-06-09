package bitgoldjobs.sqltasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgoldjobs.BitGoldJobs;

public class LoadAllJobsTask extends BukkitRunnable {
	
	private final BitGoldJobs plugin;
	
	public LoadAllJobsTask(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.getServer().broadcastMessage("Loading BitGold Jobs to Metadata");
		while(!plugin.sql.open()) { ; }
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			String job = "";
			int level = 0;
			try {
				// ToDo:  Find a way to do this with one SQL operation
				job = plugin.sql.query("SELECT job FROM Accounts WHERE user_name='"
					+ player.getName() + "'").getString(1);
				level = plugin.sql.query("SELECT level FROM Accounts WHERE user_name='"
						+ player.getName() + "'").getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			plugin.setJobMetadata(player, job, level);
		}
		
		while(!plugin.sql.close()) { ; }
	}

}
