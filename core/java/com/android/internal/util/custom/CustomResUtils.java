/*
 * Copyright (C) 2017-2022 crDroid Android Project
 * Copyright (C) 2023 PixelBlaster-OS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.internal.util.custom;

import android.app.ActivityThread;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;

import java.util.List;

public class CustomResUtils {
    private static final String TAG = "CustomResUtils";
    private static final String customResPkg = "com.android.parasite.resources";
    private static final Context mContext = ActivityThread.systemMain().getSystemContext();

    public static Resources getCustomRes() {
        if (!isPackageInstalled(mContext, customResPkg)) {
            Log.e(TAG, "'" + customResPkg + "' is not installed.");
            return null;
        }
        PackageManager pm = mContext.getPackageManager();
        try {
            Resources resources = pm.getResourcesForApplication(customResPkg);
            return resources;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error getting resources for '" + customResPkg + "': " + e.getMessage());
            return null;
        }
    }

    public static int getCustomId(String defType, String name) {
        Resources resources = getCustomRes(mContext);
        if (resources == null) return 0;
        try {
            return resources.getIdentifier(name, defType, customResPkg);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error getting resources for '" + customResPkg + "': " + e.getMessage());
            return 0;
        }
    }
}
