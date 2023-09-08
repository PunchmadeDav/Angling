package com.eightsidedsquare.angling.client.renderer;

import com.eightsidedsquare.angling.client.model.BasicEntityModel;
import com.eightsidedsquare.angling.common.entity.AnglerfishEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import static com.eightsidedsquare.angling.core.AnglingMod.MOD_ID;

public class AnglerfishEntityRenderer extends GeoEntityRenderer<AnglerfishEntity> {

    private static final Identifier OVERLAY = new Identifier(MOD_ID, "textures/entity/anglerfish/anglerfish_overlay.png");

    public AnglerfishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BasicEntityModel<>("anglerfish", true));
        addRenderLayer(new AnglerfishLayerRenderer(this));
    }

    @Override
    public RenderLayer getRenderType(AnglerfishEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(getTexture(animatable));
    }

    static class AnglerfishLayerRenderer extends GeoRenderLayer<AnglerfishEntity> {

        public AnglerfishLayerRenderer(GeoRenderer<AnglerfishEntity> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, AnglerfishEntity entity, BakedGeoModel bakedModel, RenderLayer renderType,
                           VertexConsumerProvider bufferIn, VertexConsumer buffer, float partialTicks,
                           int packedLight, int packedOverlay) {

            int overlay = OverlayTexture.getUv(0,
                    entity.hurtTime > 0 || entity.deathTime > 0);

            this.getRenderer().actuallyRender(matrixStackIn, entity, this.getGeoModel().getBakedModel(getGeoModel().getModelResource(entity)), this.getRenderType(OVERLAY), bufferIn,
                    bufferIn.getBuffer(this.getRenderType(OVERLAY)), true, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, 1, 1, 1, 1, 1);
        }

        public RenderLayer getRenderType(Identifier texture) {
            return RenderLayer.getEntityTranslucent(texture);
        }
    }
}
