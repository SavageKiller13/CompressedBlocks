/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.container;

import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace.DenseFurnaceRecipes;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace.slots.SlotDenseFurnaceFuel;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace.slots.SlotDenseFurnaceOutput;
import com.Savage_Killer13.CompressedBlocks.objects.tileentity.TileEntityDenseFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author Soren Mortimer
 */
public class ContainerDenseFurnace extends Container {
    private final TileEntityDenseFurnace tileentity;
    private int cookTime, totalCookTime, burnTime, currentBurnTime;
    
    public ContainerDenseFurnace(InventoryPlayer player, TileEntityDenseFurnace tileentity) {
        this.tileentity = tileentity;
        
        this.addSlotToContainer(new Slot(tileentity, 0, 44, 17));
        this.addSlotToContainer(new Slot(tileentity, 1, 68, 17));
        this.addSlotToContainer(new SlotDenseFurnaceFuel(tileentity, 2, 56, 53));
        this.addSlotToContainer(new SlotDenseFurnaceOutput(player.player, tileentity, 3, 116, 34));
        
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        
        for(int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
        
        
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileentity);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        
        for(int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener listener = (IContainerListener) this.listeners.get(i);
            
            if(this.cookTime != this.tileentity.getField(2)) listener.sendWindowProperty(this, 2, this.tileentity.getField(2));
            if(this.burnTime != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
            if(this.currentBurnTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
            if(this.totalCookTime != this.tileentity.getField(3)) listener.sendWindowProperty(this, 3, this.tileentity.getField(3));
        }
        
        this.cookTime = this.tileentity.getField(2);
        this.burnTime = this.tileentity.getField(0);
        this.currentBurnTime = this.tileentity.getField(1);
        this.totalCookTime = this.tileentity.getField(3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tileentity.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileentity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot) this.inventorySlots.get(index);
        
        if(slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            
            if(index == 3) {
                if(!this.mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
                slot.onSlotChange(stack1, stack);
            }
            else if(index != 2 && index != 1 && index != 0) {
                Slot slot1 = (Slot) this.inventorySlots.get(index + 1);
                
                if(!DenseFurnaceRecipes.getInstance().getDenseResult(stack1, slot1.getStack()).isEmpty()) {
                    if(!this.mergeItemStack(stack1, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                    else if(TileEntityDenseFurnace.isItemFuel(stack1)) {
                        if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    }
                    else if(TileEntityDenseFurnace.isItemFuel(stack1)) {
                        if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    }
                    else if(TileEntityDenseFurnace.isItemFuel(stack1)) {
                        if(!this.mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    }
                    else if(index >= 4 && index < 31) {
                        if(!this.mergeItemStack(stack1, 31, 40, false)) return ItemStack.EMPTY;
                    }
                    else if(index >= 31 && index < 40 && !this.mergeItemStack(stack1, 4, 31, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if(!this.mergeItemStack(stack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }
            if(stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
            if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, stack1);
        }
        return stack;
    }
}