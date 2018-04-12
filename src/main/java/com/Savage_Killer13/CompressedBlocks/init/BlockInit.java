package com.Savage_Killer13.CompressedBlocks.init;

import com.Savage_Killer13.CompressedBlocks.objects.blocks.DenseBlockGlass;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.BlockOres;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.CompressedBlock;
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
    
    public static final Block ORE_OVERWORLD = new BlockOres("ore_overworld", "overworld");
    
    public static final Block DENSE_DIRT = new CompressedBlock("dense_dirt", Material.ROCK, 12.5f, 12.5f);
    public static final Block DENSE_DIRT_BELOW = new CompressedBlock("dense_dirt_below", Material.ROCK, 10f, 10f);
    
    public static final Block DENSE_FURNACE = new BlockDenseFurnace("dense_furnace");
    
}
