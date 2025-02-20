package com.eightsidedsquare.angling.client.renderer;

import com.eightsidedsquare.angling.client.model.StarfishBlockEntityModel;
import com.eightsidedsquare.angling.common.entity.StarfishBlockEntity;
import com.eightsidedsquare.angling.core.AnglingBlocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class StarfishBlockEntityRenderer extends GeoBlockRenderer<StarfishBlockEntity> {

    public StarfishBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(new StarfishBlockEntityModel());
    }

    @Override
    public RenderLayer getRenderType(StarfishBlockEntity entity, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(entity));
    }

    @Override
    public Color getRenderColor(StarfishBlockEntity entity, float tickDelta, int packedLight) {
        return entity.getCachedState().isOf(AnglingBlocks.DEAD_STARFISH) ? Color.WHITE : Color.ofOpaque(entity.isRainbow() ? StarfishBlockEntity.getRainbowColor() : entity.getColor());
    }

    @Override
    public void preRender(MatrixStack poseStack, StarfishBlockEntity entity, BakedGeoModel model, VertexConsumerProvider vertexConsumers, VertexConsumer buffer, boolean isReRender, float tickDelta, int light, int overlay, float red, float green, float blue, float alpha) {
        if (!entity.isRemoved()) {
            poseStack.push();
            poseStack.translate(0.5, 0.01, 0.5);

            super.preRender(poseStack, entity, model, vertexConsumers, buffer, isReRender, tickDelta, light, overlay, red, green, blue, alpha);

            poseStack.pop();
        }
    }
}
