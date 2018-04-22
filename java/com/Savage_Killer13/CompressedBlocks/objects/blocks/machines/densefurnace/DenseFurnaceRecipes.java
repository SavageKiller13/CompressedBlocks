/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace;

import com.Savage_Killer13.CompressedBlocks.init.BlockInit;
import com.Savage_Killer13.CompressedBlocks.init.ItemInit;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.BlockOres;
import com.Savage_Killer13.CompressedBlocks.util.handlers.EnumHandler;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Soren Mortimer
 */
public class DenseFurnaceRecipes {
    private static final DenseFurnaceRecipes INSTANCE = new DenseFurnaceRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
    
    public static DenseFurnaceRecipes getInstance() {
        return INSTANCE;
    }
    
    private DenseFurnaceRecipes() {
        addDenseRecipe(new ItemStack(BlockInit.ORE_OVERWORLD), new ItemStack(ItemInit.SMELTING_CATALYST), new ItemStack(Items.IRON_INGOT, 8), 10.0f);
    }
    
    public void addDenseRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) {
        if(getDenseResult(input1, input2) != ItemStack.EMPTY) return;
        this.smeltingList.put(input1, input2, result);
        this.experienceList.put(result, Float.valueOf(experience));
    }
    
    public ItemStack getDenseResult(ItemStack input1, ItemStack input2) {
        for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet()) {
            if(this.compareItemStacks(input1, (ItemStack)entry.getKey())) {
                for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
                    if(this.compareItemStacks(input2, (ItemStack)ent.getKey())) {
                        return (ItemStack)ent.getValue();
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }
    
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
    
    public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList() {
        return this.smeltingList;
    }
    
    public float getDenseExperience(ItemStack stack) {
        for(Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
            if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) {
                return((Float)entry.getValue()).floatValue();
            }
        }
        return 0.0f;
    }
    
}
