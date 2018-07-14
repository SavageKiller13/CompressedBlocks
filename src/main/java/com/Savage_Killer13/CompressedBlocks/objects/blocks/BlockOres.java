package com.Savage_Killer13.CompressedBlocks.objects.blocks;

import com.Savage_Killer13.CompressedBlocks.init.BlockInit;
import com.Savage_Killer13.CompressedBlocks.init.ItemInit;
import com.Savage_Killer13.CompressedBlocks.objects.blocks.item.ItemBlockVariants;
import com.Savage_Killer13.CompressedBlocks.util.interfaces.IHasModel;
import com.Savage_Killer13.CompressedBlocks.util.interfaces.IMetaName;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockOres extends BlockBase {
    
    public BlockOres(String name, Material material, float hardness, float resistance){
        super(name, material);
        
        setHardness(hardness);
        setResistance(resistance);
    }
}
