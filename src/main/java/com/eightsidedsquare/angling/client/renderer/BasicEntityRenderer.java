package com.eightsidedsquare.angling.client.renderer;

import com.eightsidedsquare.angling.client.model.BasicEntityModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BasicEntityRenderer<A extends LivingEntity & GeoEntity> extends GeoEntityRenderer<A> {

    public BasicEntityRenderer(EntityRendererFactory.Context ctx, GeoModel<A> modelProvider) {
        super(ctx, modelProvider);
    }

    public static <A extends LivingEntity & GeoEntity> EntityRendererFactory<A> create(GeoModel<A> model) {
        return ctx -> new BasicEntityRenderer<>(ctx, model);
    }

    public static <A extends LivingEntity & GeoEntity> EntityRendererFactory<A> create(String name, boolean liesOutOfWater) {
        return ctx -> new BasicEntityRenderer<>(ctx, new BasicEntityModel<>(name, liesOutOfWater));
    }

    public static <A extends LivingEntity & GeoEntity> EntityRendererFactory<A> create(String name, boolean liesOutOfWater, String head) {
        return ctx -> new BasicEntityRenderer<>(ctx, new BasicEntityModel<>(name, liesOutOfWater, head));
    }

    @Override
    public RenderLayer getRenderType(A entity, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(getTexture(entity));
    }
}
