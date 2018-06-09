package io.github.kuohsuanlo.restorenature.util;

import io.github.kuohsuanlo.restorenature.RestoreNaturePlugin;

import org.bukkit.Chunk;
import org.bukkit.Material;

public class MapGenerationUtil {
	public static Material SEAWEED_DEFAULT = Material.COBBLE_WALL;
	public static Material SEAWEED_1 = Material.LEAVES;
	public static Material SEAWEED_2 = Material.ANVIL;
	public static Material SEAWEED_3 = Material.ACACIA_FENCE;
	public static Material SEAWEED_4 = Material.BIRCH_FENCE;
	public static Material SEAWEED_5 = Material.JUNGLE_FENCE;
	public static boolean isWaterBlock(Material m){
		if(m.equals(Material.STATIONARY_WATER)) return true;
		if(m.equals(Material.WATER)) return true;
		if(m.equals(SEAWEED_DEFAULT)) return true;
		if(m.equals(SEAWEED_1)) return true;
		if(m.equals(SEAWEED_2)) return true;
		if(m.equals(SEAWEED_3)) return true;
		if(m.equals(SEAWEED_4)) return true;
		if(m.equals(SEAWEED_5)) return true;
		return false;
	}
	public static void attemptPlacingSeaWeed(Chunk chunk){
		int seaLevel = chunk.getWorld().getSeaLevel();
		int seaweedMinLength = 1;
		int seaweedMaxLength = 10;
		int pureWaterLayerDepth = 1;
		for(int x=0;x<16;x++){
			for(int z=0;z<16;z++){
				int waterLength = 0;
				boolean[] shouldPlace = new boolean[256];
				
				int blockx = chunk.getBlock(x, 0, z).getLocation().getBlockX();
				int blockz = chunk.getBlock(x, 0, z).getLocation().getBlockZ();
				int seaweedRandomType = RestoreNaturePlugin.h.generateHeight(blockx, blockz);
				Material currentSeaWeedType = SEAWEED_DEFAULT;
				if(seaweedRandomType==1){
					currentSeaWeedType = Material.LEAVES;
				}
				else if(seaweedRandomType==2){
					currentSeaWeedType = Material.ANVIL;
				}
				else if(seaweedRandomType==3){
					currentSeaWeedType = Material.ACACIA_FENCE;
				}
				else if(seaweedRandomType==4){
					currentSeaWeedType = Material.BIRCH_FENCE;
				}
				else if(seaweedRandomType==5){
					currentSeaWeedType = Material.JUNGLE_FENCE;
				}
				
				if(seaweedRandomType>0){
					boolean contactWater = false;
					for(int y=seaLevel;y>0;y--){
						if(isWaterBlock(chunk.getBlock(x, y, z).getType())){
							contactWater = true;
							shouldPlace[y]= true;
							waterLength++;
						}
						else if(contactWater){
							break;
						}
					}
					
					if(waterLength>seaweedMinLength){
						int finalLength = (int) Math.round( RestoreNaturePlugin.h.generateDoubleRandom(blockx, blockz)*(seaweedMaxLength-seaweedMinLength))+seaweedMinLength;
						for(int y=0;y<seaLevel-pureWaterLayerDepth;y++){
							if(finalLength>0){
								if(shouldPlace[y]){
									finalLength--;
									chunk.getBlock(x, y, z).setType(currentSeaWeedType);
								}
							}
							else{
								break;
							}
						}
					}
					else{
						
					}
				}
				
				
			}
		}
		return;
	}
	public static void attemptPlacingCoral(Chunk chunk){
		int seaLevel = chunk.getWorld().getSeaLevel();
		int coralMinLength = 0;
		int coralMaxLength = 5;
		int pureWaterLayerDepth = 15;
		for(int x=0;x<16;x++){
			for(int z=0;z<16;z++){
				int waterLength = 0;
				boolean[] shouldPlace = new boolean[256];
				
				boolean contactWater = false;
				for(int y=seaLevel;y>0;y--){
					if(isWaterBlock(chunk.getBlock(x, y, z).getType())){
						contactWater = true;
						shouldPlace[y]= true;
						waterLength++;
					}
					else if(contactWater){
						break;
					}
				}
				int blockx = chunk.getBlock(x, 0, z).getLocation().getBlockX();
				int blockz = chunk.getBlock(x, 0, z).getLocation().getBlockZ();
				int coralRatioLength = RestoreNaturePlugin.h.generateHeight(blockx, blockz);
				int coralLength = (int) Math.round(Math.random()*(coralMaxLength-coralMinLength)+coralMinLength);
				coralLength = (int) (coralLength*(coralRatioLength/40.0));
				
				if(waterLength>coralLength){
					for(int y=0;y<seaLevel-pureWaterLayerDepth;y++){
						
						if(coralLength>0){
							if(shouldPlace[y]){
								
								coralLength--;
								
								int color = (int) Math.round(Math.random()*4);
								switch(color){
								case 0:
									chunk.getBlock(x, y, z).setType(Material.YELLOW_GLAZED_TERRACOTTA);
									break;
								case 1:
									chunk.getBlock(x, y, z).setType(Material.PURPLE_GLAZED_TERRACOTTA);
									break;
								case 2:
									chunk.getBlock(x, y, z).setType(Material.GREEN_GLAZED_TERRACOTTA);
									break;
								case 3:
									chunk.getBlock(x, y, z).setType(Material.RED_GLAZED_TERRACOTTA);
									break;
								case 4:
									chunk.getBlock(x, y, z).setType(Material.WHITE_GLAZED_TERRACOTTA);
									break;
								}
							}
							
						}
						else{
							break;
						}
						
					}
				}
				
			}
		}
		return;
		
	}
}
