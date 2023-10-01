package de.dafuqs.enchantmentgroups.mixin;

import de.dafuqs.enchantmentgroups.*;
import net.minecraft.enchantment.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
	
	@Inject(method = "canAccept(Lnet/minecraft/enchantment/Enchantment;)Z", at = @At("HEAD"), cancellable = true)
	public final void enchantmentGroups$applyEnchantmentGroups(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
		Enchantment thisEnchantment = (Enchantment) (Object) this;
		boolean canCombine = Config.canCombine(thisEnchantment, other);
		if (!canCombine) {
			cir.setReturnValue(false);
		}
	}
	
	@Inject(method = "isTreasure()Z", at = @At("HEAD"), cancellable = true)
	public final void enchantmentGroups$applyTreasure(CallbackInfoReturnable<Boolean> cir) {
		Enchantment thisEnchantment = (Enchantment) (Object) this;
		if (Config.isTreasure(thisEnchantment)) {
			cir.setReturnValue(true);
		}
	}
	
}
