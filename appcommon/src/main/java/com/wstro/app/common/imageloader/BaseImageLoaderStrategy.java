package com.wstro.app.common.imageloader;

import android.content.Context;

/**
 * Class Note:
 * abstract class/interface defined to load image
 * (Strategy Pattern used here)
 */
public interface BaseImageLoaderStrategy {
   void loadImage(Context ctx, ImageLoaderConfig img);
}
