/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.world.biomes;

import com.Savage_Killer13.CompressedBlocks.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;

/**
 *
 * @author Soren Mortimer
 */
public class BiomeDensePlains extends Biome {
    
    public BiomeDensePlains() {
        super(new BiomeProperties("Dense_Plains").setBaseHeight(0.15F).setHeightVariation(0.075F).setTemperature(0.7F).setRainfall(0.2F).setWaterColor(8527445));
        
        topBlock = BlockInit.DENSE_DIRT.getDefaultState();
        fillerBlock = BlockInit.DENSE_DIRT_BELOW.getDefaultState();
        
        this.decorator.dirtGen = new WorldGenMinable(BlockInit.DENSE_DIRT_BELOW.getDefaultState(), 13);
        this.decorator.coalGen = new WorldGenMinable(BlockInit.DENSE_COAL_ORE.getDefaultState(), 7);
        this.decorator.ironGen = new WorldGenMinable(BlockInit.DENSE_IRON_ORE.getDefaultState(), 4);
        this.decorator.goldGen = new WorldGenMinable(BlockInit.DENSE_GOLD_ORE.getDefaultState(), 2);
    }
    
}
