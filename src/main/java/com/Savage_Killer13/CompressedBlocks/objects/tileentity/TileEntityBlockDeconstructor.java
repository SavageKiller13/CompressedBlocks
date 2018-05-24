/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author Soren Mortimer
 */
public class TileEntityBlockDeconstructor extends TileEntity implements IInventory, ITickable {
    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
    private String customName;
    
    private int deconstructTime;
    private int totalDeconstructTime;
    private int burnTime;
    private int currentBurnTime;

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.blockDeconstructor";
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }
    
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack stack : this.inventory) {
            if(!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return (ItemStack) this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }
    
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }
    
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = (ItemStack) this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.inventory.set(index, stack);
        
        if(stack.getCount() > this.getInventoryStackLimit())
            stack.setCount(this.getInventoryStackLimit());
        if(index == 0 && index + 1 == 1 && !flag) {
            ItemStack stack1 = (ItemStack) this.inventory.get(index + 1);
            this.totalDeconstructTime = this.getCookTime(stack, stack1);
            this.deconstructTime = 0;
            this.markDirty();
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.burnTime = compound.getInteger("BurnTime");
        this.deconstructTime = compound.getInteger("CookTime");
        this.totalDeconstructTime = compound.getInteger("CookTimeTotal");
        this.currentBurnTime = getItemBurnTime((ItemStack) this.inventory.get(2));
        
        if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short) this.burnTime);
        compound.setInteger("DeconstructTime", (short) this.deconstructTime);
        compound.setInteger("DeconstructTimeTotal", (short) this.totalDeconstructTime);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        
        if(this.hasCustomName()) compound.setString("CustomName", this.customName);
        return compound;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isBurning() {
        return this.burnTime > 0;
    }
    
    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @Override
    public void update() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        
        if(this.isBurning()) --this.burnTime;
        
        if(!this.world.isRemote) {
            ItemStack stack = (ItemStack) this.inventory.get(burnTime);
            
            if(this.isBurning() || !stack.isEmpty() && !((ItemStack) this.inventory.get(1)).isEmpty()) {
                if(!this.isBurning() && this.canDeconstruct()) {
                    this.burnTime = getItemBurnTime(stack);
                    this.currentBurnTime = this.burnTime;
                    
                    if(this.isBurning()) {
                        flag1 = true;
                        
                        if(!stack.isEmpty()) {
                            Item item = stack.getItem();
                            stack.shrink(1);
                            
                            if(stack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(stack);
                                this.inventory.set(0, item1);
                            }
                        }
                    }
                }
                if(this.isBurning() && this.canDeconstruct()) {
                    ++this.deconstructTime;
                    
                    if(this.deconstructTime == this.totalDeconstructTime) {
                        this.deconstructTime = 0;
                        this.totalDeconstructTime = this.getDeconstructTime((ItemStack) this.inventory.get(1));
                        
                        this.deconstructItem();
                        flag1 = true;
                    }
                } else {
                    this.deconstructTime = 0;
                }
            } else if(!this.isBurning() && this.deconstructTime > 0) {
                this.deconstructTime = MathHelper.clamp(this.deconstructTime - 2, 0, this.totalDeconstructTime);
            }
            if(flag1 != this.isBurning()) {
                flag1 = true;
                BlockDeconstructor.setState(this.isBurning(), this.world, this.pos);
            }
        } if (flag1)
            this.markDirty();
    }
    
    public int getDeconstructTime(ItemStack input) {
        return 250;
    }
    
    public boolean canDeconstruct() {
        if(((ItemStack) this.inventory.get(1)).isEmpty()) return false;
        else {
            ItemStack result = BlockDeconstructerRecipes.getInstance().getDeconstructResult((ItemStack) this.inventory.get(1));
            if(result.isEmpty()) return false;
            else {
                ItemStack output = (ItemStack) this.inventory.get(2);
                if(output.isEmpty()) return true;
                if(!output.isItemEqual(result)) return false;
                int res = output.getCount() + result.getCount();
                return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
            }
        }
    }
    
    public void deconstructItem() {
        if(this.canDeconstruct()) {
            ItemStack input = (ItemStack) this.inventory.get(1);
            ItemStack result = BlockDeconstructerRecipes.getInstance().getDeconstructResult(input);
            ItemStack output = (ItemStack) this.inventory.get(2);
            
            if(output.isEmpty()) {
                this.inventory.set(3, result.copy());
            } else if(output.getItem() == result.getItem()) output.grow(result.getCount());
            
            input.shrink(1);
        }
    }
    
    public static int getItemBurnTime(ItemStack fuel) {
        if(fuel.isEmpty()) return 0;
        else {
            Item item = fuel.getItem();
            if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);
                if(block == Blocks.COAL_BLOCK) return 20000;
            }
            
            if(item == Items.COAL) return 2000;
            if(item == Items.BLAZE_POWDER) return 1600;
            if(item == Items.GUNPOWDER) return 175;
            if(item == Items.MAGMA_CREAM) return 250;
            if(item == Items.GLOWSTONE_DUST) return 125;
            
            return GameRegistry.getFuelValue(fuel);
        }
    }
    
    public static boolean isItemFuel(ItemStack fuel) {
        return getItemBurnTime(fuel) > 0;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(index == 2) return false;
        else if(index != 0) return true;
        else {
            return isItemFuel(stack);
        }
    }
    
    public String getGuiID() {
        return "skcbm:block_deconstructor";
    }

    @Override
    public int getField(int id) {
        switch(id) {
            case 0:
                return this.burnTime;
            case 1:
                return this.currentBurnTime;
            case 2:
                return this.deconstructTime;
            case 3:
                return this.totalDeconstructTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch(id) {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.currentBurnTime = value;
                break;
            case 2:
                this.deconstructTime = value;
                break;
            case 3:
                this.totalDeconstructTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }
}