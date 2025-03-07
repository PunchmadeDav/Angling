package com.eightsidedsquare.angling.client.model;

import com.eightsidedsquare.angling.common.entity.NautilusEntity;
import com.eightsidedsquare.angling.core.AnglingUtil;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class NautilusEntityModel extends BasicEntityModel<NautilusEntity> {
    public NautilusEntityModel() {
        super("nautilus", true);
    }

    @Override
    public void setCustomAnimations(NautilusEntity entity, long uniqueId, AnimationState<NautilusEntity> event) {
        if(!AnglingUtil.isReloadingResources()) {
            super.setCustomAnimations(entity, uniqueId, event);
            CoreGeoBone root = getAnimationProcessor().getBone("root");
            if(!entity.isTouchingWater() && root != null) {
                root.setRotZ((float) (Math.PI / -2d));
                root.setPosY(-1.5f);
            }
        }
    }
}
