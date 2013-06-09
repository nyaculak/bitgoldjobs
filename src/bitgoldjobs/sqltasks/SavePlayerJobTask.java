package bitgoldjobs.sqltasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgoldjobs.BitGoldJobs;

public class SavePlayerJobTask extends BukkitRunnable {
	
	private final BitGoldJobs plugin;
	private final Player player;
	
	public SavePlayerJobTask(BitGoldJobs plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	@Override
	public void run() {
		while(!plugin.sql.open()) { ; }
		String job = plugin.getJob(player);
		int level = plugin.getLevel(player);
		try {
			plugin.sql.query("UPDATE Accounts SET job='"
					+ job + "' WHERE user_name='"
					+ player.getName() + "'");
			plugin.sql.query("UPDATE Accounts SET level="
					+ level + " WHERE user_name='"
					+ player.getName() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while(!plugin.sql.close()) { ; }
	}

}
