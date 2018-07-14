package com.Savage_Killer13.CompressedBlocks.objects.items;

import net.minecraft.creativetab.CreativeTabs;

public class ItemSpecial extends ItemBase {
    
    public ItemSpecial(String name) {
        super(name);
        setMaxStackSize(1);
        setContainerItem(this);
        setCreativeTab(CreativeTabs.TOOLS);
    }
    
}
