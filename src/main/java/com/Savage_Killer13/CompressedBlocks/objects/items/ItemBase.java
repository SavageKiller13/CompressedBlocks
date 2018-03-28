package com.Savage_Killer13.CompressedBlocks.objects.items;

import com.Savage_Killer13.CompressedBlocks.Main;
import com.Savage_Killer13.CompressedBlocks.init.ItemInit;
import com.Savage_Killer13.CompressedBlocks.proxy.ClientProxy;
import com.Savage_Killer13.CompressedBlocks.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name) {
       setUnlocalizedName(name);
       setRegistryName(name);
       
       setCreativeTab(CreativeTabs.TOOLS);
       ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
