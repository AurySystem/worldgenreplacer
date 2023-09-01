package gay.aurum.noisereplacer.mixin;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimplexNoiseSampler.class)
public abstract class SimNoiseMixin {

	@Shadow
	protected abstract int getGradient(int hash);

	@Shadow
	@Final
	private static double SKEW_FACTOR_2D;

	@Inject(method = "sample(DD)D", at = @At("HEAD"), cancellable = true)
	public void noisereplace$sample(double x, double y, CallbackInfoReturnable<Double> cir) {
//		double d = (x + y) * SKEW_FACTOR_2D;
//
//		int i = MathHelper.floor(x + d);
//		int j = MathHelper.floor(y + d);
//
//		int r = i & 0xFF;
//		int s = j & 0xFF;
//
//		int xx = this.getGradient(r);
//		int yy = this.getGradient(xx+s);
//		cir.setReturnValue ((yy/512d)*2-1);

		double skew = (x + y) * SKEW_FACTOR_2D;

		int i = MathHelper.floor(x + skew);
		int j = MathHelper.floor(y + skew);

		int k = MathHelper.floor(x) & 0xFF;
		int g = MathHelper.floor(x) & 0xFF;

		int r = i & 0xFF;
		int s = j & 0xFF;

		int xx = this.getGradient(r);
		int yy = this.getGradient(xx+s);
		int ff = this.getGradient(yy+k);
		int gg = this.getGradient(ff+g);
		cir.setReturnValue((gg/256d)*2-1);
		cir.cancel();
	}


}
