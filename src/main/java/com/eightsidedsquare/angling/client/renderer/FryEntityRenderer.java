package com.eightsidedsquare.angling.client.renderer;

import com.eightsidedsquare.angling.client.model.FryEntityModel;
import com.eightsidedsquare.angling.common.entity.FryEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import static com.eightsidedsquare.angling.core.AnglingMod.MOD_ID;

public class FryEntityRenderer extends GeoEntityRenderer<FryEntity> {
    public FryEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new FryEntityModel());
        addRenderLayer(new FryEntityLayerRenderer(this));
    }

    @Override
    public RenderLayer getRenderType(FryEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(getTexture(animatable));
    }

     static class FryEntityLayerRenderer extends GeoRenderLayer<FryEntity> {
         private static final Identifier OUTSIDE_LAYER = new Identifier(MOD_ID, "textures/entity/fry/fry.png");
         private static final Identifier INSIDE_LAYER = new Identifier(MOD_ID, "textures/entity/fry/fry_innards.png");
         private static final Identifier MODEL = new Identifier(MOD_ID, "geo/fry.geo.json");

        public FryEntityLayerRenderer(GeoRenderer<FryEntity> entityRendererIn) {
            super(entityRendererIn);
        }

        private void render(Identifier layer, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, int overlay, FryEntity entity, float partialTicks, float r, float g, float b) {
            RenderLayer renderLayer = RenderLayer.getEntityTranslucent(layer);
            matrixStackIn.push();
            this.getRenderer().actuallyRender(matrixStackIn, entity, this.getGeoModel().getBakedModel(getGeoModel().getModelResource(entity)), renderLayer, bufferIn,
                    bufferIn.getBuffer(renderLayer), true, partialTicks, packedLightIn, overlay, r, g, b, 1f);
            matrixStackIn.pop();
        }

         @Override
         public void render(MatrixStack matrixStackIn, FryEntity entity, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferIn, VertexConsumer buffer, float partialTicks, int packedLightIn, int packedOverlay) {
             int color = entity.getColor();
             float r = ((color >> 16) & 0xFF) / 255f;
             float g = ((color >> 8) & 0xFF) / 255f;
             float b = (color & 0xFF) / 255f;
             int overlay = OverlayTexture.getUv(0,
                     entity.hurtTime > 0 || entity.deathTime > 0);
             render(INSIDE_LAYER, matrixStackIn, bufferIn, packedLightIn, overlay, entity, partialTicks, r, g, b);
             render(OUTSIDE_LAYER, matrixStackIn, bufferIn, packedLightIn, overlay, entity, partialTicks, 1f, 1f, 1f);
         }
    }
}
