package bitgoldjobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class JobMobKillListener implements Listener {
	
	BitGoldJobs plugin;
	
	public JobMobKillListener(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerKillMob(EntityDeathEvent e) {
		
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (e.getEntity().getKiller().getName().equals(player.getName())) {
				
			}
		}
		
	}

}
