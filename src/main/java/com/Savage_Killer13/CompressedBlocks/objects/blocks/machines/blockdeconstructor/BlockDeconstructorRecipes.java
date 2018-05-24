/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.blockdeconstructor;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Soren Mortimer
 */
public class BlockDeconstructorRecipes {
    private static final BlockDeconstructorRecipes INSTANCE = new BlockDeconstructorRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    
    public BlockDeconstructorRecipes getInstance() {
        return INSTANCE;
    }
    
    private BlockDeconstructorRecipes() {
        
    }
    
    public void addDeconstructRecipe(ItemStack input, ItemStack result) {
        if(getDeconstructResult(input) != ItemStack.EMPTY) return;
        this.smeltingList.put(input, result);
    }
}
