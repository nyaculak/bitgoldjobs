package bitgoldjobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class JobBlockBreakListener implements Listener {
	
	BitGoldJobs plugin;
	
	public JobBlockBreakListener(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		String jobString = plugin.getJob(e.getPlayer());
		if (!(jobString.equals(""))) {
			Job job = Job.valueOf(jobString);
			for(int i = 0; i < job.getBlocks().length; i++) {
				if (job.getBlocks()[i].getId() == e.getBlock().getType().getId()) {
					int points = job.getBlockPoints()[i];
					plugin.addJobProgress(e.getPlayer(), points);
				}
			}
		}
	}

}
