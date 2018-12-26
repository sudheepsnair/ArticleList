package mycompany.com.scrollablelist.view.ui.util;

        import android.content.Context;
        import android.content.res.Resources;
        import android.util.DisplayMetrics;
        import android.util.TypedValue;
        import androidx.annotation.Nullable;

public class UiUtils {


    public static int getDisplayWidth(@Nullable Context context) {

        Resources resources = context.getResources();
        int width = resources.getDisplayMetrics().widthPixels;
        return width;
    }

    public static int dpToPixel(@Nullable Context context, int dpValue) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, displayMetrics);
    }

}
