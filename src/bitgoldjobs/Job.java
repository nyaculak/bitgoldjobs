package bitgoldjobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum Job {
		
	DIGGER("Terraforming anyone?  Get paid to break dirt, sand, and gravel",
		   "Dirt: 1 pt; Sand: 1 pt; Gravel: 2 pt",
		   new Material[] { Material.DIRT,
							Material.GRASS,
							Material.SAND, 
							Material.GRAVEL },
		   new int[] { 1, 1, 1, 2 },
		   new EntityType[] { }, new int[] { }),
	MINER("Ever find yourself waving your pickaxe in midair?  Get paid to break various stones and ores",
			"Stone: 1 pt; Coal: 3 pt; Diamond: 10 pt; Emerald: 15 pt",
			new Material[] { Material.STONE, 
							 Material.COAL_ORE, 
							 Material.DIAMOND_ORE, 
							 Material.EMERALD_ORE },
			new int[] { 1, 3, 10, 15},
			new EntityType[] { }, new int[] { }),
	LUMBERJACK("Got wood? Get paid to cut down tall and short trees alike",
				"Log: 5 pt",
				new Material[] { Material.LOG }, new int[] { 5 },
				new EntityType[] { }, new int[] { }) ; /*,
	HUNTER("Are you a blood thirsty killer? Get paid to kill animals and hostile mobs",
			"NO ADDITIONAL INFO",
			new Material[] {}, new int[] {},
			new EntityType[] { EntityType.CREEPER, EntityType.SKELETON, EntityType.ZOMBIE },
			new int[] { 8, 6, 6 }),
	FARMER("Are you fat and like food? Get paid to harvest crops and kill livestock");
	*/
	
	public static final double BASE_SALARY = 20;
	public static final double BASE_POINTS = 40;
	
	private String description;
	private String moreInfo;
	private Material[] blocks;
	private int[] blockPoints;
	private EntityType[] mobs;
	private int[] mobPoints;
	
	private Job(String description, String moreInfo, Material[] blocks, int[] blockPoints,
				EntityType[] mobs, int[] mobPoints) {
		this.description = description;	this.moreInfo = moreInfo;
		this.blocks = blocks; this.blockPoints = blockPoints;
		this.mobs = mobs; this.mobPoints = mobPoints;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getMoreInfo() {
		return moreInfo;
	}		
	
	public Material[] getBlocks() {
		return blocks;
	}
	
	public int[] getBlockPoints() {
		return blockPoints;
	}
	
	public EntityType[] getMobs() {
		return mobs;
	}
	
	public int[] getMobPoints() {
		return mobPoints;
	}
	
	public int getSalary(int level) {
		if (level == 0)
			return (int) BASE_SALARY;
		return (int) (BASE_SALARY * Math.log(level + 1));
	}
	
	public int getSalaryProgress(int level) {
		if (level == 0) 
			return (int) BASE_POINTS;
		return (int) (BASE_POINTS * Math.log(level + 1));
	}
	
	public int getLevelupProgress(int level) {
		return (int) (BASE_POINTS * Math.pow(Math.E/2, level));
	}
	
}
