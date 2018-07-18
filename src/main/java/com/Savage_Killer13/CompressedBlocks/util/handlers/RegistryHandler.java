/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.util.handlers;

import com.Savage_Killer13.CompressedBlocks.Main;
import com.Savage_Killer13.CompressedBlocks.init.BiomeInit;
import com.Savage_Killer13.CompressedBlocks.init.BlockInit;
import com.Savage_Killer13.CompressedBlocks.init.ItemInit;
import com.Savage_Killer13.CompressedBlocks.util.interfaces.IHasModel;
import com.Savage_Killer13.CompressedBlocks.world.gen.WorldGenCustomOres;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 *
 * @author Soren Mortimer
 */

@EventBusSubscriber
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }
    
    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
        BlockInit.registerTileEntities();
    }
    
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for(Item item : ItemInit.ITEMS) {
            if(item instanceof IHasModel) {
                ((IHasModel)item).registerModels();
            }
        }
        
        for(Block block : BlockInit.BLOCKS) {
            if(block instanceof IHasModel) {
                ((IHasModel)block).registerModels();
            }
        }
    }
    
    public static void preInitRegistries() {
        GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
        
        BiomeInit.registerBiomes();
    }
    
    public static void initRegistries() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
    }
    
    public static void otherRegistries() {
        GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
        
        BiomeInit.registerBiomes();
    }
}
