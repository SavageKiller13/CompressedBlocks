/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.world.gen;

import com.Savage_Killer13.CompressedBlocks.init.BlockInit;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.BlockOres;
import com.Savage_Killer13.CompressedBlocks.util.handlers.EnumHandler;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 *
 * @author Soren Mortimer
 */
public class WorldGenCustomOres implements IWorldGenerator {
    private final WorldGenerator dense_iron_ore, dense_gold_ore, dense_coal_ore, dense_diamond_ore, dense_emerald_ore, dense_lapis_ore;
    
    public WorldGenCustomOres() {
        dense_iron_ore = new WorldGenMinable(BlockInit.DENSE_IRON_ORE.getDefaultState(), 5, BlockMatcher.forBlock(Blocks.STONE));
        dense_gold_ore = new WorldGenMinable(BlockInit.DENSE_GOLD_ORE.getDefaultState(), 3, BlockMatcher.forBlock(Blocks.STONE));
        dense_coal_ore = new WorldGenMinable(BlockInit.DENSE_COAL_ORE.getDefaultState(), 7, BlockMatcher.forBlock(Blocks.STONE));
        dense_diamond_ore = new WorldGenMinable(BlockInit.DENSE_DIAMOND_ORE.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.STONE));
        dense_emerald_ore = new WorldGenMinable(BlockInit.DENSE_EMERALD_ORE.getDefaultState(), 1, BlockMatcher.forBlock(Blocks.STONE));
        dense_lapis_ore = new WorldGenMinable(BlockInit.DENSE_LAPIS_ORE.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.STONE));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(world.provider.getDimension()) {
            case 0:
                runGenerator(dense_gold_ore, world, random, chunkX, chunkZ, 100, 0, 40);
                runGenerator(dense_iron_ore, world, random, chunkX, chunkZ, 100, 0, 256);
                runGenerator(dense_coal_ore, world, random, chunkX, chunkZ, 100, 0, 256);
                runGenerator(dense_diamond_ore, world, random, chunkX, chunkZ, 100, 0, 16);
                runGenerator(dense_emerald_ore, world, random, chunkX, chunkZ, 100, 4, 14);
                runGenerator(dense_lapis_ore, world, random, chunkX, chunkZ, 100, 0, 30);
                break;
        }
    }
    
    private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight) {
        if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");
        
        int heightDiff = maxHeight - minHeight + 1; 
        for(int i=0; i < chance; i++) {
            int x = chunkX * 16 + rand.nextInt(16);
            int z = chunkZ * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            
            gen.generate(world, rand, new BlockPos(x, y, z));
        }
    }
}