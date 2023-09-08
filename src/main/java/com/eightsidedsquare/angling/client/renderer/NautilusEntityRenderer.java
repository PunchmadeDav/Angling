package com.eightsidedsquare.angling.client.renderer;

import com.eightsidedsquare.angling.client.model.NautilusEntityModel;
import com.eightsidedsquare.angling.common.entity.NautilusEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class NautilusEntityRenderer extends BasicEntityRenderer<NautilusEntity> {
    public NautilusEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new NautilusEntityModel());
    }

    @Override
    public void render(NautilusEntity entity, float entityYaw, float partialTick, MatrixStack matrices, VertexConsumerProvider bufferSource, int packedLight) {
        matrices.push();
        matrices.scale(0.65f, 0.65f, 0.65f);
        super.render(entity, entityYaw, partialTick, matrices, bufferSource, packedLight);
        matrices.pop();
    }
}
