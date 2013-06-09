package bitgoldjobs.sqltasks;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import bitgoldjobs.BitGoldJobs;

public class LoadPlayerJobTask extends BukkitRunnable {
	
	private final BitGoldJobs plugin;
	private final Player player;
	
	public LoadPlayerJobTask(BitGoldJobs plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	@Override
	public void run() {
		while(!plugin.sql.open()) { ; }
		String job = "";
		int level = 0;
		try {
			job = plugin.sql
					.query("SELECT job FROM Accounts WHERE user_name='"
							+ player.getName() + "'").getString(1);
			level = plugin.sql
					.query("SELECT level FROM Accounts WHERE user_name='"
							+ player.getName() + "'").getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		plugin.setJobMetadata(player, job, level);
		while(!plugin.sql.close()) { ; }
	}

}
