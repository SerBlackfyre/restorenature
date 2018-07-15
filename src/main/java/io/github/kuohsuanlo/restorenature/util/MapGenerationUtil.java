package io.github.kuohsuanlo.restorenature.util;

import io.github.kuohsuanlo.restorenature.RestoreNaturePlugin;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.material.MaterialData;

public class MapGenerationUtil {
	public static Material SEAWEED_DEFAULT = Material.KELP_PLANT;
	//public static BlockData SEAGRASS_UPPER = Bukkit.getServer().createBlockData("half:\"upper\"");
	//public static BlockData SEAGRASS_LOWER = Bukkit.getServer().createBlockData("half:\"lower\"");
	public static boolean isWaterBlock(Material m){
		if(m.equals(Material.WATER)) return true;
		if(m.equals(SEAWEED_DEFAULT)) return true;
		if(m.equals(Material.KELP)) return true;
		if(m.equals(Material.TALL_SEAGRASS)) return true;
		if(m.equals(Material.SEAGRASS)) return true;
		if(m.equals(Material.SEA_PICKLE)) return true;
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
				int currentSeaObjectInt = RestoreNaturePlugin.h.generateHeight(blockx, blockz);
				Material currentSeaObjectType = SEAWEED_DEFAULT;
				if(currentSeaObjectInt==1){
					currentSeaObjectType = Material.KELP_PLANT;
				}
				else if(currentSeaObjectInt==2){
					currentSeaObjectType = Material.SEAGRASS;
				}
				else if(currentSeaObjectInt==3){
					currentSeaObjectType = Material.TALL_SEAGRASS;
				}
				else if(currentSeaObjectInt==4){
					currentSeaObjectType = Material.SEA_PICKLE;
				}
				if(currentSeaObjectInt>0){
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
						if(currentSeaObjectType==Material.KELP_PLANT){
							int finalLength = (int) Math.round( RestoreNaturePlugin.h.generateDoubleRandom(blockx, blockz)*(seaweedMaxLength-seaweedMinLength))+seaweedMinLength;
							for(int y=0;y<seaLevel-pureWaterLayerDepth;y++){
								if(finalLength>=1){
									if(shouldPlace[y]){
										finalLength--;
										chunk.getBlock(x, y, z).setType(Material.KELP_PLANT);
									}
								}
								else if (finalLength==0){
									if(shouldPlace[y]){
										finalLength--;
										chunk.getBlock(x, y, z).setType(Material.KELP);
									}
								}
								else{
									break;
								}
							}
						}
						else if(currentSeaObjectType == Material.TALL_SEAGRASS){
							for(int y=0;y<seaLevel-pureWaterLayerDepth-1;y++){
								if(shouldPlace[y]){
									//todo: need to add half:lower / half:upper in the future.
									chunk.getBlock(x, y, z).setType(currentSeaObjectType);
									//chunk.getBlock(x, y, z).setBlockData(SEAGRASS_LOWER);
									chunk.getBlock(x, y+1, z).setType(currentSeaObjectType);
									//chunk.getBlock(x, y, z).setBlockData(SEAGRASS_UPPER);
									break;
								}
							}
							
						}
						else if(currentSeaObjectType == Material.SEAGRASS){
							for(int y=0;y<seaLevel-pureWaterLayerDepth;y++){
								if(shouldPlace[y]){
									chunk.getBlock(x, y, z).setType(currentSeaObjectType);
									break;
								}
							}
							
						}
						else if(currentSeaObjectType == Material.SEA_PICKLE){
							for(int y=0;y<seaLevel-pureWaterLayerDepth;y++){
								if(shouldPlace[y]){
									chunk.getBlock(x, y, z).setType(currentSeaObjectType);
									
									BlockData blockData = chunk.getBlock(x, y, z).getState().getBlockData();
									if (blockData instanceof SeaPickle) {
										//todo: need to update pickles number
										SeaPickle w = (SeaPickle) blockData;
									    w.setPickles((int) Math.ceil(Math.random()*4));
									    chunk.getBlock(x, y, z).getState().setBlockData(w);
									    break;
									} 
								}
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
