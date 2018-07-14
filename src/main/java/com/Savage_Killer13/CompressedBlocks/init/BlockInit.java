package com.Savage_Killer13.CompressedBlocks.init;

import com.Savage_Killer13.CompressedBlocks.objects.blocks.DenseBlockGlass;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.BlockOres;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.CompressedBlock;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.LightBlock;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.blockdeconstructor.BlockDeconstructor;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace.BlockDenseFurnace;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block COMPRESSED_BEDROCK = new CompressedBlock("compressed_bedrock", Material.ROCK, 50f, 50f);
    public static final Block DENSE_STONE = new CompressedBlock("dense_stone", Material.ROCK, 15f, 15f);
    public static final Block DENSE_GLASS = new DenseBlockGlass("dense_glass", Material.GLASS);
    public static final Block DENSE_COBBLESTONE = new CompressedBlock("dense_cobblestone", Material.ROCK, 15f, 15f);
    public static final Block DENSE_END_STONE = new CompressedBlock("dense_end_stone", Material.ROCK, 30f, 30f);
    public static final Block LIGHT_IRON_BLOCK = new LightBlock("light_iron_block", Material.IRON, 15f, 15f, "pickaxe", 2);
    public static final Block LIGHT_DIAMOND_BLOCK = new LightBlock("light_diamond_block", Material.IRON, 15f, 15f, "pickaxe", 2);
    public static final Block LIGHT_COAL_BLOCK = new LightBlock("light_coal_block", Material.IRON, 15f, 15f, "pickaxe", 2);
    public static final Block LIGHT_EMERALD_BLOCK = new LightBlock("light_emerald_block", Material.IRON, 15f, 15f, "pickaxe", 3);
    public static final Block LIGHT_GOLD_BLOCK = new LightBlock("light_gold_block", Material.IRON, 15f, 15f, "pickaxe", 2);
    public static final Block LIGHT_LAPIS_BLOCK = new LightBlock("light_lapis_block", Material.IRON, 15f, 15f, "pickaxe", 2);
    
    
    public static final Block DENSE_IRON_ORE = new BlockOres("dense_iron_ore", Material.ROCK, 15f, 15f);
    public static final Block DENSE_COAL_ORE = new BlockOres("dense_coal_ore", Material.ROCK, 15f, 15f);
    public static final Block DENSE_GOLD_ORE = new BlockOres("dense_gold_ore", Material.ROCK, 15f, 15f);
    public static final Block DENSE_DIAMOND_ORE = new BlockOres("dense_diamond_ore", Material.ROCK, 15f, 15f);
    public static final Block DENSE_LAPIS_ORE = new BlockOres("dense_lapis_ore", Material.ROCK, 15f, 15f);
    public static final Block DENSE_EMERALD_ORE = new BlockOres("dense_emerald_ore", Material.ROCK, 15f, 15f);
    
    public static final Block DENSE_DIRT = new CompressedBlock("dense_dirt", Material.ROCK, 12.5f, 12.5f);
    public static final Block DENSE_DIRT_BELOW = new CompressedBlock("dense_dirt_below", Material.ROCK, 10f, 10f);
    
    public static final Block DENSE_FURNACE = new BlockDenseFurnace("dense_furnace");
    public static final Block BLOCK_DECONSTRUCTOR = new BlockDeconstructor("block_deconstructor");
    
}
