package com.Savage_Killer13.CompressedBlocks.objects.items;

import net.minecraft.creativetab.CreativeTabs;

public class ItemBlockCompressor extends ItemBase {
    
    public ItemBlockCompressor(String name) {
        super(name);
        setMaxStackSize(1);
        setContainerItem(this);
        setCreativeTab(CreativeTabs.TOOLS);
    }
    
}
