package me.basiqueevangelist.reelism.mixin;

import me.basiqueevangelist.reelism.blockentity.ReeNetherPortalBlockEntity;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlockEntityRenderer.class)
public class EndPortalBlockEntityRendererMixin {
    // TODO: no more ffp :(

//    @Unique private float coefficient = 0;
//
//    @Inject(method = "renderSides", at = @At("HEAD"))
//    public void injectCoefficient(EndPortalBlockEntity entity, Matrix4f matrix4f, VertexConsumer vertexConsumer, CallbackInfo ci) {
//        coefficient = g;
//    }
//
//    @ModifyArg(method = "renderSides", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/EndPortalBlockEntityRenderer;renderSide(Lnet/minecraft/block/entity/EndPortalBlockEntity;Lnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumer;FFFFFFFFLnet/minecraft/util/math/Direction;)V"), index = 11)
//    public float modifyR(EndPortalBlockEntity be, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, Direction direction) {
//        if (!(be instanceof ReeNetherPortalBlockEntity))
//            return n;
//        return n + 0.4F * coefficient;
//    }
//
//    @ModifyArg(method = "renderSides", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/EndPortalBlockEntityRenderer;renderSide(Lnet/minecraft/block/entity/EndPortalBlockEntity;Lnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumer;FFFFFFFFLnet/minecraft/util/math/Direction;)V"), index = 12)
//    public float modifyG(EndPortalBlockEntity be, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, Direction direction) {
//        if (!(be instanceof ReeNetherPortalBlockEntity))
//            return o;
//        return o - 0.4F * coefficient;
//    }
//
//    @ModifyArg(method = "renderSides", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/EndPortalBlockEntityRenderer;renderSide(Lnet/minecraft/block/entity/EndPortalBlockEntity;Lnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumer;FFFFFFFFLnet/minecraft/util/math/Direction;)V"), index = 13)
//    public float modifyB(EndPortalBlockEntity be, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, Direction direction) {
//        if (!(be instanceof ReeNetherPortalBlockEntity))
//            return p;
//        return p - 0.5F * coefficient;
//    }
}
