package com.sanrenx.funny.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;

public class BitmapUtils {
	/**
	 * Drawable转换为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap DrawableToBitmap(Drawable drawable) {
		try {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			// canvas.setBitmap(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
			drawable.draw(canvas);

			return bitmap;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * 读取图片的旋转的角度
	 * 
	 * @param path图片绝对路径
	 * @return 图片的旋转角度
	 */
	private static int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("degree--->", "degree : " + degree);
		return degree;
	}

	/**
	 * 旋转图片并保存
	 * 
	 * @param bitmap图片
	 * @param rotate旋转角度
	 * @param path
	 *            保存文件路径
	 * @return
	 */

	private static Bitmap resetRotate(Bitmap bitmap, int rotate, String path) {
		Matrix m = new Matrix();
		m.setRotate(rotate, bitmap.getWidth(), bitmap.getHeight());
		Bitmap b2 = null;
		try {
			b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
		} catch (OutOfMemoryError e) {
			b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 2,
					bitmap.getHeight() / 2, m, true);
		}

		if (bitmap != b2) {
			bitmap.recycle();
			bitmap = null;
			bitmap = b2;

		}
		if (path != null) {
			try {
				FileOutputStream b = new FileOutputStream(path);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return bitmap;

	}

	/**
	 * 读取路径旋转图片
	 * 
	 * @param uri路径
	 * @param bitmap图片
	 * @return
	 */

	public static Bitmap roateBitmapFromUri(Uri uri, Bitmap bitmap) {
		String path = uri.getPath();
		int rotate = 0;
		rotate = getBitmapDegree(path);
		if (rotate != 0) {
			bitmap = resetRotate(bitmap, rotate, path);
		}
		return bitmap;

	}

	public static Bitmap getBitmapFromFile(String path, int width, int height) {
		File dst = new File(path);
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(path, opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			Log.d("test", "will creat bit map");
			try {
				Log.d("test", "creat bit map");
				return resetRotate(BitmapFactory.decodeFile(path, opts),
						getBitmapDegree(path), null);
				// return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				Log.d("test", " creat bit map oom");
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

		int originWidth = bitmap.getWidth();
		int originHeight = bitmap.getHeight();

		// no need to resize
		if (originWidth < maxWidth && originHeight < maxHeight) {
			return bitmap;
		}

		int width = originWidth;
		int height = originHeight;

		// 若图片过宽, 则保持长宽比缩放图片
		if (originWidth > maxWidth) {
			width = maxWidth;

			double i = originWidth * 1.0 / maxWidth;
			height = (int) Math.floor(originHeight / i);

			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		}

		// 若图片过长, 则从上端截取
		if (height > maxHeight) {
			height = maxHeight;
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
		}

		// Log.i(TAG, width + " width");
		// Log.i(TAG, height + " height");

		return bitmap;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * 转换成字节流
	 * 
	 * @param bitmap图片
	 * @return byte[] 字节流
	 */

	public static byte[] getByteBufferByBitmap(Bitmap bitmap) {
		// TODO Auto-generated method stub
		byte[] buffer = null;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 98, stream);
		buffer = stream.toByteArray();
		return buffer;
	}
}
