package gay.aurum.noisereplacer;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.random.LegacySimpleRandom;
import net.minecraft.util.random.RandomGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class test {
	public static void main(String[] args) throws IOException {
		LegacySimpleRandom ran = new LegacySimpleRandom(8008);
		BufferedImage a = new BufferedImage(1024,1024,10);
		WritableRaster ras = a.getRaster();
		PerlinNoiseSampler noise = new PerlinNoiseSampler( ran);
		for (int x = 0; x < 1024; x++) {
			for (int y = 0; y < 1024; y++) {
				double aa = noise.sample(((double)x)/16d,60d,((double)y)/16d);
				ras.setPixel(x,y,new double[]{(aa+1)/2*256});
			}
		}
		File outputfile = new File("image.png");
		ImageIO.write(a, "png", outputfile);

		LegacySimpleRandom ran2 = new LegacySimpleRandom(8008);
		PerlinNoiseSamplerin noise2 = new PerlinNoiseSamplerin( ran2);
		for (int x = 0; x < 1024; x++) {
			for (int y = 0; y < 1024; y++) {
				double aa = noise2.sample(((double)x)/16d,60d,((double)y)/16d);
				ras.setPixel(x,y,new double[]{(aa+1)/2*256});
			}
		}
		File outputfile2 = new File("image2.png");
		ImageIO.write(a, "png", outputfile2);
//
//		LegacySimpleRandom ran3 = new LegacySimpleRandom(8008);
//		SimplexNoiseSampler noise3 = new SimplexNoiseSampler( ran3);
//		for (int x = 0; x < 1024; x++) {
//			for (int y = 0; y < 1024; y++) {
//				double aa = noise3.sample(((double)x)/16d,((double)y)/16d);
//				ras.setPixel(x,y,new double[]{(aa+1)/2*256});
//			}
//		}
//		File outputfile3 = new File("image3.png");
//		ImageIO.write(a, "png", outputfile3);
//		System.out.println(SimplexNoiseSampler.maxx);
//		System.out.println(SimplexNoiseSampler.minn);
	}


		public static final class PerlinNoiseSamplerin {
			private static final float SHIFT_UP_EPSILON = 1.0E-7F;
			private final byte[] permutations;
			public final double originX;
			public final double originY;
			public final double originZ;

			public PerlinNoiseSamplerin(RandomGenerator random) {
				this.originX = random.nextDouble() * 256.0;
				this.originY = random.nextDouble() * 256.0;
				this.originZ = random.nextDouble() * 256.0;
				this.permutations = new byte[256];

				for (int i = 0; i < 256; ++i) {
					this.permutations[i] = (byte) i;
				}

				for (int i = 0; i < 256; ++i) {
					int j = random.nextInt(256 - i);
					byte b = this.permutations[i];
					this.permutations[i] = this.permutations[i + j];
					this.permutations[i + j] = b;
				}
			}

			public double sample(double x, double y, double z) {
				return this.sample(x, y, z, 0.0, 0.0);
			}

			@Deprecated
			public double sample(double x, double y, double z, double yScale, double yMax) {
				double d = x + this.originX;
				double e = y + this.originY;
				double f = z + this.originZ;
				int i = MathHelper.floor(d);
				int j = MathHelper.floor(e);
				int k = MathHelper.floor(f);
				double g = d - (double) i;
				double h = e - (double) j;
				double l = f - (double) k;
				double n;
				if (yScale != 0.0) {
					double m;
					if (yMax >= 0.0 && yMax < h) {
						m = yMax;
					}else {
						m = h;
					}

					n = (double) MathHelper.floor(m / yScale + 1.0E-7F) * yScale;
				}else {
					n = 0.0;
				}

				return this.sample(i, j, k, g, h - n, l, h);
			}

			private int getGradient(int hash) {
				return this.permutations[hash & 0xFF] & 0xFF;
			}

			private double sample(int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX) {
//				int a = this.getGradient(sectionX);
//				int b = this.getGradient(sectionY);
//				int c = this.getGradient(sectionZ);
//				int why = this.getGradient(sectionZ+a);

//				return (((((double) ((b) + (a << 8) + (c << 16))) / 16777215d) + (((localX + localZ) / 2 + localY) / 2)) - 1);
				//return ((localX+localY+localZ)/3);
				//return localX*localZ;
//				//return (this.getGradient(sectionY+why)/256d*2-1);

				int xx = this.getGradient(sectionX);
				int zz = this.getGradient(xx+sectionZ);
				int yy = this.getGradient(zz+sectionY);
				double local = Math.sqrt( (localX-(yy/256d))*(localX-(yy/256d))+(localY-(zz/256d))*(localY-(zz/256d))+(localZ-(xx/256d))*(localZ-(xx/256d)) );
				return MathHelper.lerp(MathHelper.clamp(local,0d,1d),yy/256d,getGradient(zz+sectionY+1)/256d)*2-1;
			}
		}
//
//		public static class SimplexNoiseSampler {
//			protected static final int[][] GRADIENTS = new int[][]{
//					{1, 1, 0},
//					{-1, 1, 0},
//					{1, -1, 0},
//					{-1, -1, 0},
//					{1, 0, 1},
//					{-1, 0, 1},
//					{1, 0, -1},
//					{-1, 0, -1},
//					{0, 1, 1},
//					{0, -1, 1},
//					{0, 1, -1},
//					{0, -1, -1},
//					{1, 1, 0},
//					{0, -1, 1},
//					{-1, 1, 0},
//					{0, -1, -1}
//			};
//			private static final double SQRT_3 = Math.sqrt(3.0);
//			private static final double SKEW_FACTOR_2D = 0.5 * (SQRT_3 - 1.0);
//			private static final double UNSKEW_FACTOR_2D = (3.0 - SQRT_3) / 6.0;
//			private final int[] permutations = new int[512];
//			public final double originX;
//			public final double originY;
//			public final double originZ;
//
//			public SimplexNoiseSampler(RandomGenerator random) {
//				this.originX = random.nextDouble() * 256.0;
//				this.originY = random.nextDouble() * 256.0;
//				this.originZ = random.nextDouble() * 256.0;
//				int i = 0;
//
//				while(i < 256) {
//					this.permutations[i] = i++;
//				}
//
//				for(int ix = 0; ix < 256; ++ix) {
//					int j = random.nextInt(256 - ix);
//					int k = this.permutations[ix];
//					this.permutations[ix] = this.permutations[j + ix];
//					this.permutations[j + ix] = k;
//				}
//			}
//
//			private int getGradient(int hash) {
//				return this.permutations[hash & 0xFF];
//			}
//
//			protected static double dot(int[] gArr, double x, double y, double z) {
//				return (double)gArr[0] * x + (double)gArr[1] * y + (double)gArr[2] * z;
//			}
//
//			private double grad(int hash, double x, double y, double z, double distance) {
//				double d = distance - x * x - y * y - z * z;
//				double e;
//				if (d < 0.0) {
//					e = 0.0;
//				} else {
//					d *= d;
//					e = d * d * dot(GRADIENTS[hash], x, y, z);
//				}
//
//				return e;
//			}
//
//			public static double maxx = 0d;
//			public static double minn = 1d;
//			public double sample(double x, double y) {
//				double skew = (x + y) * SKEW_FACTOR_2D;
//
//				int i = MathHelper.floor(x + skew);
//				int j = MathHelper.floor(y + skew);
//				int k = MathHelper.floor(x) & 0xFF;
//				int g = MathHelper.floor(x) & 0xFF;
//
//				int r = i & 0xFF;
//				int s = j & 0xFF;
//
//				int xx = this.getGradient(r);
//				int yy = this.getGradient(xx+s);
//				int ff = this.getGradient(yy+k);
//				int gg = this.getGradient(ff+g);
//				double out = ((gg/256d)*2-1);
//				maxx = Math.max(maxx, out);
//				minn = Math.min(minn, out);
//				return out;
//
////				double d = (x + y) * SKEW_FACTOR_2D;
////				int i = MathHelper.floor(x + d);
////				int j = MathHelper.floor(y + d);
////				double e = (double)(i + j) * UNSKEW_FACTOR_2D;
////				double f = (double)i - e;
////				double g = (double)j - e;
////				double h = x - f;
////				double k = y - g;
////				int l;
////				int m;
////				if (h > k) {
////					l = 1;
////					m = 0;
////				} else {
////					l = 0;
////					m = 1;
////				}
////
////				double n = h - (double)l + UNSKEW_FACTOR_2D;
////				double o = k - (double)m + UNSKEW_FACTOR_2D;
////				double p = h - 1.0 + 2.0 * UNSKEW_FACTOR_2D;
////				double q = k - 1.0 + 2.0 * UNSKEW_FACTOR_2D;
////				int r = i & 0xFF;
////				int s = j & 0xFF;
////				int t = this.getGradient(r + this.getGradient(s)) % 12;
////				int u = this.getGradient(r + l + this.getGradient(s + m)) % 12;
////				int v = this.getGradient(r + 1 + this.getGradient(s + 1)) % 12;
////				double w = this.grad(t, h, k, 0.0, 0.5);
////				double z = this.grad(u, n, o, 0.0, 0.5);
////				double aa = this.grad(v, p, q, 0.0, 0.5);
////				double out = 70.0 * (w + z + aa);
////				maxx = Math.max(maxx, out);
////				minn = Math.min(minn, out);
////				return out;
//			}
//		}


	}
