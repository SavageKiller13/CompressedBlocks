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
    private final WorldGenerator ore_overworld_dense_iron, ore_overworld_dense_gold, ore_overworld_dense_coal, ore_overworld_dense_diamond;
    
    public WorldGenCustomOres() {
        ore_overworld_dense_iron = new WorldGenMinable(BlockInit.ORE_OVERWORLD.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.DENSE_IRON), 4, BlockMatcher.forBlock(Blocks.STONE));
        ore_overworld_dense_gold = new WorldGenMinable(BlockInit.ORE_OVERWORLD.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.DENSE_GOLD), 2, BlockMatcher.forBlock(Blocks.STONE));
        ore_overworld_dense_coal = new WorldGenMinable(BlockInit.ORE_OVERWORLD.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.DENSE_COAL), 6, BlockMatcher.forBlock(Blocks.STONE));
        ore_overworld_dense_diamond = new WorldGenMinable(BlockInit.ORE_OVERWORLD.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.DENSE_DIAMOND), 1, BlockMatcher.forBlock(Blocks.STONE));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch(world.provider.getDimension()) {
            case 0:
                runGenerator(ore_overworld_dense_gold, world, random, chunkX, chunkZ, 50, 0, 40);
                runGenerator(ore_overworld_dense_iron, world, random, chunkX, chunkZ, 100, 0, 256);
                runGenerator(ore_overworld_dense_coal, world, random, chunkX, chunkZ, 100, 0, 256);
                runGenerator(ore_overworld_dense_diamond, world, random, chunkX, chunkZ, 100, 0, 20);
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