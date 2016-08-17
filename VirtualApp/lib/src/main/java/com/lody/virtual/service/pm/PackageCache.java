package com.lody.virtual.service.pm;

import com.lody.virtual.helper.proto.AppSetting;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lody
 */

public class PackageCache {
	public static final Map<String, PackageParser.Package> sPackageCaches = new ConcurrentHashMap<>();


	public static void put(PackageParser.Package pkg, AppSetting appSetting) {
		synchronized (PackageCache.class) {
			pkg.mExtras = appSetting;
			sPackageCaches.put(pkg.packageName, pkg);
			VPackageManagerService.getService().analyzePackageLocked(pkg);
		}
	}

	public static PackageParser.Package get(String packageName) {
		return sPackageCaches.get(packageName);
	}

	public static void remove(String packageName) {
		synchronized (PackageCache.class) {
			sPackageCaches.remove(packageName);
			VPackageManagerService.getService().deletePackageLocked(packageName);
		}
	}
}
