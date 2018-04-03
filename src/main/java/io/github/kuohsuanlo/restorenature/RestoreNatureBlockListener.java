package io.github.kuohsuanlo.restorenature;


import java.util.HashMap;

import io.github.kuohsuanlo.restorenature.util.RestoreNatureUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.material.SpawnEgg;

public class RestoreNatureBlockListener implements Listener {
	private RestoreNaturePlugin rplugin;
	private HashMap<Player,Block> SpanwerNotification = new HashMap<Player,Block>();
	
	public RestoreNatureBlockListener(RestoreNaturePlugin plugin){
		rplugin = plugin;
		SpanwerNotification = new HashMap<Player,Block>();
		SpanwerNotification.clear();
	}
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onFurnaceSmeltEvent(FurnaceSmeltEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }   
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBrewEvent(BrewEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }   
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEnchantItemEvent(EnchantItemEvent event) {
        Block block = event.getEnchantBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }      
    
  
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }     
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockIgniteEvent(BlockIgniteEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }     
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockGrowEvent(BlockGrowEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }   
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockFormEvent(BlockFormEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockFromToEvent(BlockFromToEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    } 
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();
        rplugin.ChunkEnqueuer.setWorldsChunkUntouchedTime(block);
    }
    
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSpanwerDamageEvent(BlockDamageEvent event) {
        Block block = event.getBlock();

        Player player = event.getPlayer();
        if(player!=null){
        	if(SpanwerNotification==null){
        		SpanwerNotification = new HashMap<Player,Block>();
        	}
        	
        	
        	if(SpanwerNotification.get(player)==null  ||  !SpanwerNotification.get(player).equals(block)){
        		

            	if(block.getType()!=Material.MOB_SPAWNER) return;
            	
            	SpanwerNotification.put(player, block);
        		if(RestoreNatureUtil.hasIdenticalBlockInBackupWorld(block)){
            		player.sendMessage(RestoreNaturePlugin.PLUGIN_PREFIX+RestoreNaturePlugin.SPAWNER_UNREMOVABLE);
            	}
            	else{
            		player.sendMessage(RestoreNaturePlugin.PLUGIN_PREFIX+RestoreNaturePlugin.SPAWNER_REMOVABLE);
            	}
        	}
        	
        	
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSpanwerBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        if(block.getType()!=Material.MOB_SPAWNER) return;
        
    	event.setExpToDrop(0);
    	if(RestoreNatureUtil.hasIdenticalBlockInBackupWorld(block)){
    		//player.sendMessage(RestoreNaturePlugin.PLUGIN_PREFIX+RestoreNaturePlugin.SPAWNER_UNREMOVABLE);
    	}
    	else{
    		//player.sendMessage(RestoreNaturePlugin.PLUGIN_PREFIX+RestoreNaturePlugin.SPAWNER_REMOVABLE);
    		
    		CreatureSpawner cs = (CreatureSpawner) block.getState();
    		EntityType entityType= cs.getSpawnedType();
    		
    		if(!entityType.equals(EntityType.ZOMBIE)){
    			ItemStack egg = new ItemStack(Material.MONSTER_EGG,1);
        		egg.setDurability(entityType.getTypeId());
        		event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0,0.5f,0),egg);
    		}
    		
    		ItemStack spawner =  new ItemStack(Material.MOB_SPAWNER,1);
    		event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0,0.5f,0),spawner);
    		
    	}
        
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSpanwerPlaceEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if(block.getType()!=Material.MOB_SPAWNER) return;
        
        Player player = event.getPlayer();
        if(player!=null){
        	if(RestoreNatureUtil.hasIdenticalBlockInBackupWorld(block)){
        		player.sendMessage(RestoreNaturePlugin.PLUGIN_PREFIX+RestoreNaturePlugin.SPAWNER_UNREMOVABLE);
        	}
        	else{
        		player.sendMessage(RestoreNaturePlugin.PLUGIN_PREFIX+RestoreNaturePlugin.SPAWNER_REMOVABLE);
        	}
        }
    }
    
}