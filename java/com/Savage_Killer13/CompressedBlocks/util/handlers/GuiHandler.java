/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.util.handlers;

import com.Savage_Killer13.CompressedBlocks.objects.GUI.GuiDenseFurnace;
import com.Savage_Killer13.CompressedBlocks.objects.container.ContainerDenseFurnace;
import com.Savage_Killer13.CompressedBlocks.objects.tileentity.TileEntityDenseFurnace;
import com.Savage_Killer13.CompressedBlocks.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 *
 * @author Soren Mortimer
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Reference.GUI_DENSE_FURNACE) return new ContainerDenseFurnace(player.inventory, (TileEntityDenseFurnace)world.getTileEntity(new BlockPos(x, y, z)));
        return null;
   }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Reference.GUI_DENSE_FURNACE) return new GuiDenseFurnace(player.inventory, (TileEntityDenseFurnace)world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
    
    
}
