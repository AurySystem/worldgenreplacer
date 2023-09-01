package gay.aurum.noisereplacer.mixin;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PerlinNoiseSampler.class)
public abstract class NoiseMixin {

	@Shadow
	protected abstract int getGradient(int hash);

	@Inject(method = "sample(IIIDDDD)D", at = @At("HEAD"), cancellable = true)
	public void noisereplace$sample(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX, CallbackInfoReturnable<Double> cir) {
//			int a = this.getGradient(sectionX);
//			int b = this.getGradient(sectionY);
//			int c = this.getGradient(sectionZ);
//			int why = this.getGradient(sectionZ+a);

			//cir.setReturnValue(((((double) ((b) + (a << 8) + (c << 16))) / 16777215d) + (((localX + localZ) / 2 + localY) / 2)) - 1);
			//cir.setReturnValue((localX+localY+localZ)/3);
//			cir.setReturnValue(this.getGradient(sectionY+why)/256d*2-1);
//			cir.setReturnValue(this.getGradient((int)(localX*256))/256d*2-1);

//			int xx = this.getGradient((int)(256*localX)+sectionX);
//			int zz = this.getGradient(xx+(int)(256*localZ)+sectionZ);
//			cir.setReturnValue(this.getGradient(zz+(int)(256*localY)+sectionY)/256d*2-1);

//			cir.setReturnValue(Math.sqrt((localX*2-1)*(localX*2-1)+(localY*2-1)*(localY*2-1)+(localZ*2-1)*(localZ*2-1))*2-1);

			int xx = this.getGradient(sectionX);
			int zz = this.getGradient(xx+sectionZ);
			int yy = this.getGradient(zz+sectionY);
			cir.setReturnValue ((yy/256d)*2-1);

//
//			int xx = this.getGradient(sectionX);
//			int zz = this.getGradient(xx+sectionZ);
//			int yy = this.getGradient(zz+sectionY);
//			double local = Math.sqrt( (localX-(yy/256d))*(localX-(yy/256d))+(localY-(zz/256d))*(localY-(zz/256d))+(localZ-(xx/256d))*(localZ-(xx/256d)) );
//			cir.setReturnValue( MathHelper.lerp(MathHelper.clamp(local,0d,1d),yy/256d,getGradient(zz+sectionY+3)/256d)*2-1);

		cir.cancel();
	}
//
//	@Inject(method = "sampleDerivative(IIIDDD[D)D", at = @At("HEAD"), cancellable = true)
//	public void noisereplace$sampledir(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double[] ds, CallbackInfoReturnable<Double> cir) {
//		cir.setReturnValue(0.5d);
//	}
}
