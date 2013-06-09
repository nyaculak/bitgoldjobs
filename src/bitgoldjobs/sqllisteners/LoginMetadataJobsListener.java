package bitgoldjobs.sqllisteners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import bitgoldjobs.BitGoldJobs;
import bitgoldjobs.sqltasks.LoadPlayerJobTask;

public class LoginMetadataJobsListener implements Listener {
	
	private BitGoldJobs plugin;
	
	public LoginMetadataJobsListener(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		new LoadPlayerJobTask(plugin, event.getPlayer()).runTask(plugin);
	}

}
