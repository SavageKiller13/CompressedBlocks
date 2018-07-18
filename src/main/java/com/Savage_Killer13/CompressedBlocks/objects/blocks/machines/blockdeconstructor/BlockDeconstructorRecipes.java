/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.blockdeconstructor;

import com.Savage_Killer13.CompressedBlocks.init.BlockInit;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Soren Mortimer
 */
public class BlockDeconstructorRecipes {
    private static final BlockDeconstructorRecipes INSTANCE = new BlockDeconstructorRecipes();
    private final Map<ItemStack, ItemStack> deconstructingList = Maps.<ItemStack, ItemStack>newHashMap();
    
    public static BlockDeconstructorRecipes getInstance() {
        return INSTANCE;
    }
    
    private BlockDeconstructorRecipes() {
        addDeconstructRecipe(new ItemStack(BlockInit.DENSE_COBBLESTONE), new ItemStack(Blocks.COBBLESTONE, 8));
        addDeconstructRecipe(new ItemStack(BlockInit.DENSE_DIRT), new ItemStack(Blocks.DIRT, 8));
    }
    
    public void addDeconstructRecipe(ItemStack input, ItemStack result) {
        if(getDeconstructResult(input) != ItemStack.EMPTY) return;
        this.deconstructingList.put(input, result);
    }
    
    public ItemStack getDeconstructResult(ItemStack input) {
        for(Entry<ItemStack, ItemStack> entry : this.deconstructingList.entrySet()) {
            if (this.compareItemStacks(input, entry.getKey())) {
                return entry.getValue();
            }
        }
        
        return ItemStack.EMPTY;
    }
    
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
    
    public Map getDeconstructList() {
        return this.deconstructingList;
    }
}
