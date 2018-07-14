/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.proxy;

import com.Savage_Killer13.CompressedBlocks.util.handlers.TileEntityHandler;
import net.minecraft.item.Item;

/**
 *
 * @author Soren Mortimer
 */
public class CommonProxy {
    public void registerItemRenderer(Item item, int meta, String id) {}
    public void registerVariantRenderer(Item item, int meta, String filename, String id) {}
    public void registerTileEntities() {
        TileEntityHandler.registerTileEntities();
    }
}
