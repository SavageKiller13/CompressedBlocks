/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.init;

import com.Savage_Killer13.CompressedBlocks.world.biomes.BiomeDensePlains;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 *
 * @author Soren Mortimer
 */
public class BiomeInit {
    
    public static final Biome DENSE_PLAINS = new BiomeDensePlains();
    
    public static void registerBiomes() {
        initBiome(DENSE_PLAINS, "Dense Plains", BiomeType.COOL, Type.PLAINS, Type.HILLS, Type.RARE);
    }
    
    private static Biome initBiome(Biome biome, String name, BiomeType biomeType, Type... types) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        System.out.println("Biome Registered");
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 8));
        BiomeManager.addSpawnBiome(biome);
        System.out.println("Biome Registered");
        
        return biome;
    }
}
