package bitgoldjobs;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class JobPeriodTask extends BukkitRunnable {

	BitGoldJobs plugin;
	
	public JobPeriodTask(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.getServer().broadcastMessage(ChatColor.GOLD + "**********Job Payday!**********");
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			String jobString = plugin.getJob(player);
			if(!(jobString.equals(""))) {
				Job job = Job.valueOf(jobString);
				int level = plugin.getLevel(player);
				int progress = plugin.getProgress(player);
				if (progress >= job.getLevelupProgress(level)) {
					plugin.setJobMetadata(player, jobString, level + 1);
					player.sendMessage("You are now a " + ChatColor.GOLD + 
							"LEVEL " + (level + 1) + " " + jobString);
				}
				if (progress >= job.getSalaryProgress(level)) {
					int oldMoney = plugin.getMetadataInt(player, BitGoldJobs.BALANCE_KEY);
					int newMoney = oldMoney + job.getSalary(level);
					//plugin.setMetadata(player, BitGoldJobs.BALANCE_KEY, oldMoney + job.getSalary(level));
					// Need to find way to alter metadata from another plugin
					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
													   String.format("setbalance %s %d",
															   player.getName(), newMoney));
					player.sendMessage("You earned " + ChatColor.GOLD + 
							job.getSalary(level) + " Nuggets");
				} else {
					player.sendMessage("Sorry, you didn't earn any money today");
				}
				plugin.setJobProgress(player, 0);
			} else {
				player.sendMessage("You do not have a job!  No money for you!");
			}
		}
	}
	
}
