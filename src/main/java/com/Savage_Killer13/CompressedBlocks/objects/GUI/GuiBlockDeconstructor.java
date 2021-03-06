/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Savage_Killer13.CompressedBlocks.objects.GUI;

import com.Savage_Killer13.CompressedBlocks.objects.container.ContainerBlockDeconstructor;
import com.Savage_Killer13.CompressedBlocks.objects.tileentity.TileEntityBlockDeconstructor;
import com.Savage_Killer13.CompressedBlocks.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author Soren Mortimer
 */
public class GuiBlockDeconstructor extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/block_deconstructor.png");
    private final InventoryPlayer player;
    private final TileEntityBlockDeconstructor tileentity;
    
    public GuiBlockDeconstructor(InventoryPlayer player, TileEntityBlockDeconstructor tileentity) {
        super(new ContainerBlockDeconstructor(player, tileentity));
        this.player = player;
        this.tileentity = tileentity;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String tileName = this.tileentity.getDisplayName().getUnformattedText();
        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName)), 8, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        
        if(TileEntityBlockDeconstructor.isBurning(tileentity)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(this.guiLeft + 27, this.guiTop + 47 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        
        int l = this.getDeconstructProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 86, this.guiTop + 33, 176, 14, l + 1, 14);
    }
    
    private int getBurnLeftScaled(int pixels) {
        int i = this.tileentity.getField(1);
        if(i == 0) i = 400;
        return this.tileentity.getField(0) * pixels / i;
    }
    
    private int getDeconstructProgressScaled(int pixels) {
        int i = this.tileentity.getField(2);
        int j = this.tileentity.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}
