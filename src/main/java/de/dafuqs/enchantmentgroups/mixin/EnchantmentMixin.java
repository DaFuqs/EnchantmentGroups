package de.dafuqs.enchantmentgroups.mixin;

import de.dafuqs.enchantmentgroups.EnchantmentGroups;
import net.minecraft.enchantment.Enchantment;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = "canAccept(Lnet/minecraft/enchantment/Enchantment;)Z", at = @At("RETURN"), cancellable = true)
    public final void enchantmentGroups$applyEnchantmentGroups(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        EnchantmentGroups.log(Level.INFO, "mixin called");
        if(cir.getReturnValue() && EnchantmentGroups.config != null) {
            EnchantmentGroups.log(Level.INFO, "mixin in if");
            Enchantment thisEnchantment = (Enchantment) (Object) this;
            boolean canCombine = EnchantmentGroups.config.canCombine(thisEnchantment, other);
            if(!canCombine) {
                EnchantmentGroups.log(Level.INFO, "config: not combinable");
                cir.setReturnValue(false);
            }
        }
    }

}
