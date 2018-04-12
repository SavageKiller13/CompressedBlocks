/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.util.handlers;

import com.Savage_Killer13.CompressedBlocks.objects.tileentity.TileEntityDenseFurnace;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 *
 * @author Soren Mortimer
 */
public class TileEntityHandler {
    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityDenseFurnace.class, "dense_furnace");
    }
}
