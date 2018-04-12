package com.Savage_Killer13.CompressedBlocks.init;

import com.Savage_Killer13.CompressedBlocks.objects.items.ItemBase;
import com.Savage_Killer13.CompressedBlocks.objects.items.ItemBlockCompressor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<Item>();
    
    public static final Item INGOT_BEDROCK = new ItemBase("ingot_bedrock");
    public static final Item ITEM_BLOCK_COMPRESSOR = new ItemBlockCompressor("item_block_compressor");
    public static final Item CLAY_PILE = new ItemBase("clay_pile");
}
