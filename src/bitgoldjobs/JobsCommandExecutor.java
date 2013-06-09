package bitgoldjobs;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobsCommandExecutor implements CommandExecutor {
	
	private BitGoldJobs plugin;
	
	public JobsCommandExecutor(BitGoldJobs plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage("**********" + ChatColor.GOLD + "JOBS INFORMATION" + 
								ChatColor.WHITE + "*********");
			if (sender instanceof Player) {
				String jobString = plugin.getJob((Player) sender);
				if(!(jobString.equals(""))) {
					Job job = Job.valueOf(jobString);
					int level = plugin.getLevel((Player) sender);
					int progress = plugin.getProgress((Player) sender);
					int salProgress = job.getSalaryProgress(level);
					int lvlProgress = job.getLevelupProgress(level);
					int salary = job.getSalary(level);
					sender.sendMessage(String.format("%sLEVEL %d %s",
							ChatColor.GOLD, level, job.toString()) );
					sender.sendMessage(ChatColor.GOLD + "Salary: " + ChatColor.WHITE + salary);
					sender.sendMessage(getProgressMessage(progress, salProgress));
					sender.sendMessage(getProgressBar(20, progress, salProgress));
					sender.sendMessage(getProgressMessage(progress, lvlProgress));
					sender.sendMessage(getProgressBar(20, progress, lvlProgress));
				} else {
					sender.sendMessage(ChatColor.GOLD + "Please JOIN A JOB!");
				}
			}			
			sender.sendMessage("Type " + ChatColor.GOLD + "/jobs help " + ChatColor.WHITE + "for a list of commands");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(ChatColor.GOLD + "Jobs Commands");
			sender.sendMessage(ChatColor.GOLD + "/jobs list: " + ChatColor.WHITE + "List available jobs");
			sender.sendMessage(ChatColor.GOLD + "/jobs info [job]: " + ChatColor.WHITE + "More info on the job");
			sender.sendMessage(ChatColor.GOLD + "/jobs join [job]: " + ChatColor.WHITE + "Join a job");
			sender.sendMessage(ChatColor.GOLD + "/jobs leave: " + ChatColor.WHITE + "Leave your current job");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list")) {
			for (Job job : Job.values()) {
				sender.sendMessage(String.format("%s%s: %s%s",
						ChatColor.GOLD, job.toString(), ChatColor.WHITE, job.getDescription()));
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("info")) {
			if (args.length > 1) {
				try {
					Job job = Job.valueOf(args[1].toUpperCase());
					sender.sendMessage(String.format("%s%s: %s%s",
							ChatColor.GOLD, job.toString(), ChatColor.GREEN, job.getMoreInfo()));
					return true;
				} catch (IllegalArgumentException e) {
					sender.sendMessage("That job is not recognized");
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.GOLD + "Please provide a job to get more info on");
				return false;
			}
		}
		
		if (args[0].equalsIgnoreCase("join")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to join a job");
				return false;
			}
			if (args.length > 1) {
				try {
					Job job = Job.valueOf(args[1].toUpperCase());
					plugin.setJobMetadata((Player) sender, job.toString(), 1);
					sender.sendMessage(ChatColor.GREEN + "You have joined the job "
							+ ChatColor.GOLD + job.toString());
					return true;
				} catch (IllegalArgumentException e) {
					sender.sendMessage("That job is not recognized");
					return false;
				}
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("leave")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to leave a job");
				return false;
			}
			sender.sendMessage("Cannot leave job at this time");
			plugin.setJobMetadata((Player) sender, "", 1);
			return true;
		}
		
		return false;
	}
	
	private String getProgressBar(int length, int progress, int minProgress) {
		String progressBar = "[";
		int charLength = (int) (Math.min(length, length*((double)progress/(double) minProgress)));
		progressBar += ChatColor.RED;
		for(int i = 0; i < charLength; i++) {
			progressBar += "X";
		}
		for(int i = 0; i < length - charLength; i++) {
			progressBar += " ";
		}
		progressBar += ChatColor.WHITE + "]";
		return progressBar;
	}
	
	private String getProgressMessage(int progress, int minProgress) {
		
		int percentProgress = (int) (((double)progress/(double)minProgress) * 100);
		
		String message =
				String.format(
						"%sSalary Progress: %s%d/%d (%d percent)",
						ChatColor.GOLD, ChatColor.WHITE, progress, minProgress, percentProgress
						);
		
		return message;
		
	}

}
