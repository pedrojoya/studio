package es.iessaladillo.pedrojoya.PR004.utils;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import java.util.List;

public class IntentUtils {

    private IntentUtils() {
    }

    // Is any activity available to use the intent.
    public static boolean isActivityAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getApplicationContext().getPackageManager();
        List<ResolveInfo> appList = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return appList.size() > 0;
    }

    // Shows apps settings activity.
    public static void startInstalledAppDetailsActivity(@NonNull final Activity context) {
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        // No track in the stack of activities.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    public static Intent newViewUriIntent(Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static Intent newWebSearchIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, text);
        return intent;
    }

    public static Intent newDialIntent(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber.trim()));
    }

    public static Intent newShowInMapIntent(double longit, double lat, int zoom) {
        return new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:" + longit + "," + lat + "?z=" + zoom));
    }

    public static Intent newSearchInMapIntent(String text) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + text));
    }

    public static Intent newContactsIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
    }

    public static Intent newCallIntent(String phoneNumber) {
        return new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
    }

}
