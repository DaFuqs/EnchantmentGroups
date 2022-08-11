package de.dafuqs.enchantmentgroups.mixin;

import de.dafuqs.enchantmentgroups.EnchantmentGroups;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = "canAccept(Lnet/minecraft/enchantment/Enchantment;)Z", at = @At("RETURN"), cancellable = true)
    public final void enchantmentGroups$applyEnchantmentGroups(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if(cir.getReturnValue() && EnchantmentGroups.config != null) {
            Enchantment thisEnchantment = (Enchantment) (Object) this;
            boolean canCombine = EnchantmentGroups.config.canCombine(thisEnchantment, other);
            if(!canCombine) {
                cir.setReturnValue(false);
            }
        }
    }

}
