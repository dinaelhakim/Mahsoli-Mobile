package web_services_connections_handlers;

import android.content.Context;
import android.graphics.Bitmap;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Lenovo on 28/05/2016.
 */
public class UniversalImageLoaderConfiguration {

    Context context;

    public UniversalImageLoaderConfiguration(Context context) {
        this.context = context;
    }

    public static void setConfig(Context context){

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3).diskCacheExtraOptions(480, 320, null).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
    }

}
