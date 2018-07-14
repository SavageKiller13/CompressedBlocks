/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.tileentity;

import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace.BlockDenseFurnace;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.machines.densefurnace.DenseFurnaceRecipes;
import static com.Savage_Killer13.CompressedBlocks.util.helpers.EnumHelper.*;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
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
public class TileEntityDenseFurnace extends TileEntity implements IInventory, ITickable, ISidedInventory {
    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
    int[] inv = new int[4];
    private String customName;
    
    private int burnTime;
    private int currentBurnTime;
    private int cookTime;
    private int totalCookTime;

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.dense_furnace";
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
            this.totalCookTime = this.getCookTime(stack, stack1);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        this.burnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentBurnTime = getItemBurnTime((ItemStack) this.inventory.get(2));
        
        if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short) this.burnTime);
        compound.setInteger("CookTime", (short) this.cookTime);
        compound.setInteger("CookTimeTotal", (short) this.totalCookTime);
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
            ItemStack stack = (ItemStack) this.inventory.get(2);
            
            if(this.isBurning() || !stack.isEmpty() && !((((ItemStack) this.inventory.get(0)).isEmpty()) || ((ItemStack) this.inventory.get(1)).isEmpty())) {
                if(!this.isBurning() && this.canSmelt()) {
                    this.burnTime = getItemBurnTime(stack);
                    this.currentBurnTime = this.burnTime;
                    
                    if(this.isBurning()) {
                        flag1 = true;
                        
                        if(!stack.isEmpty()) {
                            Item item = stack.getItem();
                            stack.shrink(1);
                    
                            if(stack.isEmpty()) {
                                ItemStack item1 = item.getContainerItem(stack);
                                this.inventory.set(2, item1);
                            }
                        }
                    }
                }
                if(this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;
                    
                    if(this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime((ItemStack) this.inventory.get(0), (ItemStack) this.inventory.get(1));
                        
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else this.cookTime = 0;
            }
            else if(!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }
            if(flag != this.isBurning()) {
                flag1 = true;
                BlockDenseFurnace.setState(this.isBurning(), this.world, this.pos);
            }
        } if(flag1) 
            this.markDirty();
    }
    
    public int getCookTime(ItemStack input1, ItemStack input2) {
        return 800;
    }
    
    private boolean canSmelt() {
        if(((ItemStack) this.inventory.get(0)).isEmpty() || ((ItemStack) this.inventory.get(1)).isEmpty()) return false;
        else {
            ItemStack result = DenseFurnaceRecipes.getInstance().getDenseResult((ItemStack) this.inventory.get(0), (ItemStack) this.inventory.get(1));
            if(result.isEmpty()) return false;
            else {
                ItemStack output = (ItemStack) this.inventory.get(3);
                if(output.isEmpty()) return true;
                if(!output.isItemEqual(result)) return false;
                int res = output.getCount() + result.getCount();
                return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
            }
        }
    }
    
    public void smeltItem() {
        if(this.canSmelt()) {
            ItemStack input1 = (ItemStack) this.inventory.get(0);
            ItemStack input2 = (ItemStack) this.inventory.get(1);
            ItemStack result = DenseFurnaceRecipes.getInstance().getDenseResult(input1, input2);
            ItemStack output = (ItemStack) this.inventory.get(2);
            
            if(output.isEmpty()) {
                this.inventory.set(3, result.copy()); 
                System.out.println("Smelted");
            }
            else if(output.getItem() == result.getItem()) output.grow(result.getCount());
            
            input1.shrink(1);
            input2.shrink(1);
        }
    }
    
    public static int getItemBurnTime(ItemStack fuel) {
        if(fuel.isEmpty()) return 0;
        else {
            Item item = fuel.getItem();
            
            if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);
                
                if(block == Blocks.WOODEN_SLAB) return 200;
                if(block.getDefaultState().getMaterial() == Material.WOOD) return 400;
                if(block == Blocks.COAL_BLOCK) return 20000;
            }
            
            if(item == Items.STICK) return 150;
            if(item == Items.COAL) return 2000;
            if(item == Items.BLAZE_ROD) return 3000;
            if(item == Items.BLAZE_POWDER) return 1000;
            
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
        if(index == 3) return false;
        else if(index != 2) return true;
        else {
            return isItemFuel(stack);
        }
    }
    
    public String getGuiID() {
        return "skcbm:dense_furnace";
    }

    @Override
    public int getField(int id) {
        switch(id) {
            case 0:
                return this.burnTime;
            case 1:
                return this.currentBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
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
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return getSlotIntForFace(getOffsetFacingWithProperty(side, getFacing(pos)));
    }
    
    public int[] getSlotIntForFace(EnumFacing side) {
        switch(side) {
            case NORTH:
            case SOUTH:
                return new int[] {};
            case WEST:
                return new int[] {0};
            case EAST:
                return new int[] {1};
            case UP:
                return new int[] {2};
            case DOWN:
                return new int[] {3};
        }
        return new int[]{};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return canInsert(index, itemStackIn, getOffsetFacingWithProperty(direction, getFacing(pos)));
    }
    
    private boolean canInsert(int index, ItemStack stack, EnumFacing direction) {
        return direction == EnumFacing.UP && isItemFuel(stack) ? isItemValidForSlot(index, stack) : (direction == EnumFacing.EAST && index == 1) || (direction == EnumFacing.WEST && index == 0);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return canExtract(index, stack, getOffsetFacingWithProperty(direction, getFacing(pos)));
    }
    
    private boolean canExtract(int index, ItemStack stack, EnumFacing direction) {
        return direction == EnumFacing.DOWN && index == 3;
    }
    
    @Nullable
    private EnumFacing getFacing(BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if(state.getProperties().containsKey(BlockDenseFurnace.FACING)) {
            EnumFacing facing = state.getValue(BlockDenseFurnace.FACING);
            return facing;
        }
        return null;
    }
    
}
