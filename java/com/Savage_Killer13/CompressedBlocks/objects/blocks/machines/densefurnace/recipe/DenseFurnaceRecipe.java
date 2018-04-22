/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace.recipe;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Soren Mortimer
 */
public class DenseFurnaceRecipe {
    ItemStack[] INPUTS = new ItemStack[2];
    private final ItemStack OUTPUT;
    private final float XP;
    
    public DenseFurnaceRecipe(@Nonnull ItemStack[] inputs, ItemStack output, float experience) throws IllegalArgumentException {
        if(inputs.length != 2) 
            throw new IllegalArgumentException("You should give two inputs", new IllegalArgumentException(inputs.toString()));
        
        this.INPUTS[0] = inputs[0];
        this.INPUTS[1] = inputs[1];
        this.OUTPUT = output;
        this.XP = experience;
    }
    
    public ItemStack[] getInputs() {
        return INPUTS;
    }
    
    public ItemStack getOutput() {
        return OUTPUT;
    }
    
    public float getExperience() {
        return XP;
    }
    
    public boolean matches(ItemStack[] inputs) {
        int index = 0;
        boolean flag = inputs.length == 2;
        for(ItemStack stack : inputs) {
            if(flag) {
                flag = (stack.getItem() == this.INPUTS[index].getItem() ? stack.getCount() <= this.INPUTS[index].getCount() : false);
            }
        }
        return flag;
    }
}
