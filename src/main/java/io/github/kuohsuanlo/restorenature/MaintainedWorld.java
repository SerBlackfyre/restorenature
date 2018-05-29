package io.github.kuohsuanlo.restorenature;

import java.util.ArrayList;

public class MaintainedWorld {
	public String world_name = "";
	public ArrayList<String> nature_factions;
	public int chunk_radius;
	public boolean only_restore_air;
	public MaintainedWorld(String name,ArrayList<String> factions,int radius, boolean or_air){
		world_name =name;
		nature_factions = factions;
		chunk_radius = radius;
		only_restore_air = or_air;
	}


}
