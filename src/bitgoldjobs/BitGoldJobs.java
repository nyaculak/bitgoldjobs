package bitgoldjobs;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import bitgoldjobs.sqllisteners.LoginMetadataJobsListener;
import bitgoldjobs.sqllisteners.LogoutMetadataJobsListener;
import bitgoldjobs.sqllisteners.NewAccountListener;
import bitgoldjobs.sqltasks.LoadAllJobsTask;

public class BitGoldJobs extends JavaPlugin {
	
	public static final String BALANCE_KEY = "BitGoldBalance";
	public static final String JOB_KEY = "BitGoldJob";
	public static final String JOB_LEVEL_KEY = "BitGoldJobLevel";
	public static final String JOB_PROGRESS_KEY = "BitGoldJobProgress";
	
	public SQLite sql;
	
	@Override
	public void onEnable() {
		
		/* Check if the data folder exists, create it if it does not */
		File dataFolder = getDataFolder();
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}

		sql = new SQLite(getLogger(), "[BitGoldJobs] ", this.getDataFolder()
				.getAbsolutePath(), "BitGoldJobs", ".sqlite");
		
		try {
			if (sql.open()) {
				sql.query("CREATE TABLE IF NOT EXISTS Accounts " +
						"(user_name varchar(16), job varchar(16), level int)");
				sql.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getCommand("jobs").setExecutor(new JobsCommandExecutor(this));
		
		getServer().getPluginManager().registerEvents(new NewAccountListener(this), this);
		getServer().getPluginManager().registerEvents(new LoginMetadataJobsListener(this), this);
		getServer().getPluginManager().registerEvents(new LogoutMetadataJobsListener(this), this);
		
		getServer().getPluginManager().registerEvents(new JobBlockBreakListener(this), this);
		
		new LoadAllJobsTask(this).runTask(this); // Task not implemented
		// BukkitTask saveAll = new SaveAllJobsTask(this).runTaskTimer(this, 0, secondsToTicks(5));
		 
		new JobPeriodTask(this).runTaskTimer(this, 0, secondsToTicks(60 * 1));
		
	}
	
	@Override
	public void onDisable() {
		// For testing reasons, we can't just invoke a SaveAllJobsTask instance and invoke run()
		// Bukkit also won't let us invoke a new instance of a BukkitTask in the onDisable() function
		while(!sql.open()) { ; }
		if (getServer().getOnlinePlayers().length == 0)
			return;
		for (Player player : getServer().getOnlinePlayers()) {
			String job = getJob(player);
			int level = getLevel(player);
			try {
				// ToDo:  Consolidate to one SQL operation
				sql.query(String.format("UPDATE Accounts SET job='%s' WHERE user_name='%s '",
						job, player.getName()));
				sql.query(String.format("UPDATE Accounts SET level=%d WHERE user_name='%s'",
						level, player.getName()));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		while(!sql.close()) { ; }
	}
	
	public void setMetadata(Player player, String key, Object value) {
		player.setMetadata(key, new FixedMetadataValue(this, value));
	}
	
	public String getMetadataString(Player player, String key) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			//if (value.getOwningPlugin().getDescription().getName()
			//		.equals(this.getDescription().getName())) {
				return value.asString();
			//}
		}
		return "";
	}

	public int getMetadataInt(Player player, String key) {
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			//if (value.getOwningPlugin().getDescription().getName()
		//			.equals(this.getDescription().getName())) {
				return value.asInt();
		//	}
		}
		return 0;
	}
	
	public void setJobMetadata(Player player, String job, int level) {
		setMetadata(player, JOB_KEY, job);
		setMetadata(player, JOB_LEVEL_KEY, level);
	}
	
	public void setJobProgress(Player player, int progress) {
		setMetadata(player, JOB_PROGRESS_KEY, progress);
	}
	
	public String getJob(Player player) {
		return getMetadataString(player, JOB_KEY);
	}
	
	public int getLevel(Player player) {
		return getMetadataInt(player, JOB_LEVEL_KEY);
	}
	
	public int getProgress(Player player) {
		return getMetadataInt(player, JOB_PROGRESS_KEY);
	}
	
	public void addJobProgress(Player player, int incrementProgress) {
		int oldProgress = getProgress(player);
		setJobProgress(player, oldProgress + incrementProgress);
	}
	
	public long secondsToTicks(double seconds) {
		return (long) seconds * 20;
	}

}
