package bitgoldjobs.sqllisteners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import bitgoldjobs.BitGoldJobs;
import bitgoldjobs.sqltasks.SavePlayerJobTask;

public class LogoutMetadataJobsListener implements Listener {
	
	private BitGoldJobs plugin;
	
	public LogoutMetadataJobsListener(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		new SavePlayerJobTask(plugin, event.getPlayer()).runTask(plugin);
	}

}
