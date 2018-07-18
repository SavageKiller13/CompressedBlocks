/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.container;

import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.blockdeconstructor.BlockDeconstructorRecipes;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.blockdeconstructor.slots.SlotBlockDeconstructorFuel;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.blockdeconstructor.slots.SlotBlockDeconstructorOutput;
import com.Savage_Killer13.CompressedBlocks.objects.tileentity.TileEntityBlockDeconstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author Soren Mortimer
 */
public class ContainerBlockDeconstructor extends Container {
    private final IInventory tileentity;
    private int burnTime, currentBurnTime, deconstructTime, totalDeconstructTime;
    
    public ContainerBlockDeconstructor(InventoryPlayer player, IInventory tileentity) {
        this.tileentity = tileentity;
        
        this.addSlotToContainer(new Slot(tileentity, 0, 62, 33));
        this.addSlotToContainer(new SlotBlockDeconstructorFuel(tileentity, 1, 27, 33));
        this.addSlotToContainer(new SlotBlockDeconstructorOutput(player.player, tileentity, 2, 122, 33));
        
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
            
            if(this.deconstructTime != this.tileentity.getField(2)) listener.sendWindowProperty(this, 2, this.tileentity.getField(2));
            if(this.burnTime != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
            if(this.currentBurnTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
            if(this.totalDeconstructTime != this.tileentity.getField(3)) listener.sendWindowProperty(this, 3, this.tileentity.getField(3));
        }
        
        this.deconstructTime = this.tileentity.getField(2);
        this.burnTime = this.tileentity.getField(0);
        this.currentBurnTime = this.tileentity.getField(1);
        this.totalDeconstructTime = this.tileentity.getField(3);
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
            
            if(index == 2) {
                if(!this.mergeItemStack(stack1, 3, 39, true)) return ItemStack.EMPTY;
                slot.onSlotChange(stack1, stack);
            }
            else if(index != 1 || index != 0) {
                if(!BlockDeconstructorRecipes.getInstance().getDeconstructResult(stack1).isEmpty()) {
                    if(!this.mergeItemStack(stack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if(TileEntityBlockDeconstructor.isItemFuel(stack1)) {
                    if(!this.mergeItemStack(stack1, 1, 2, false)) return ItemStack.EMPTY;
                }
                else if(index >= 3 && index < 30) {
                    if(!this.mergeItemStack(stack1, 30, 39, false)) return ItemStack.EMPTY;
                }
                else if(index >= 31 && index < 40 && !this.mergeItemStack(stack1, 4, 31, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if(!this.mergeItemStack(stack1, 3, 39, false)) {
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
