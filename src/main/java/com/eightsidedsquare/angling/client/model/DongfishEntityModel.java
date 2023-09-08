package com.eightsidedsquare.angling.client.model;

import com.eightsidedsquare.angling.common.entity.DongfishEntity;
import com.eightsidedsquare.angling.core.AnglingUtil;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class DongfishEntityModel extends BasicEntityModel<DongfishEntity> {

    public DongfishEntityModel() {
        super("dongfish", true);
    }

    @Override
    public void setCustomAnimations(DongfishEntity entity, long uniqueId, AnimationState<DongfishEntity> event) {
        super.setCustomAnimations(entity, uniqueId, event);
        if(!AnglingUtil.isReloadingResources()) {
            CoreGeoBone scungle = getAnimationProcessor().getBone("scungle");
            if(scungle != null) {
                scungle.setHidden(!entity.hasHorngus());
            }
        }
    }
}
