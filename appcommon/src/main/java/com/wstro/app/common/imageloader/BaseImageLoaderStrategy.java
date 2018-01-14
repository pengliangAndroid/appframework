package com.wstro.app.common.imageloader;

import android.content.Context;

/**
 * abstract class/interface defined to load image
 * (Strategy Pattern used here)
 * @author pengl
 */
public interface BaseImageLoaderStrategy {
   void loadImage(Context ctx, ImageLoaderConfig img);
}
