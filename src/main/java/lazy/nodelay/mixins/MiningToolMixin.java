package lazy.nodelay.mixins;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(MiningToolItem.class)
public class MiningToolMixin {

    @Shadow
    @Final
    private float attackDamage;

    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true)
    public void getAttributeModifiers(EquipmentSlot slot, CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {
        UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
        UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", 100, EntityAttributeModifier.Operation.ADDITION));
        if (slot == EquipmentSlot.MAINHAND) {
            cir.setReturnValue(builder.build());
        }
    }
}