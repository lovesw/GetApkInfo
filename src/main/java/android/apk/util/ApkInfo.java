package android.apk.util;

import java.util.List;


@SuppressWarnings("unchecked")
public class ApkInfo {

	private String versionCode;
	private String versionName;
	private String apkPackage;
	private String minSdkVersion;
	private String apkName;
	private List uses_permission;

	public ApkInfo() {
		versionCode = null;
		versionName = null;
		apkPackage = null;
		minSdkVersion = null;
		apkName = null;
		uses_permission = null;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkPackage() {
		return apkPackage;
	}

	public void setApkPackage(String apkPackage) {
		this.apkPackage = apkPackage;
	}

	public String getMinSdkVersion() {
		return minSdkVersion;
	}

	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public List getUses_permission() {
		return uses_permission;
	}

	public void setUses_permission(List uses_permission) {
		this.uses_permission = uses_permission;
	}

	@Override
	public String toString() {
		return "ApkInfo [versionCode=" + versionCode + ", versionName=" + versionName + ", apkPackage=" + apkPackage
				+ ", minSdkVersion=" + minSdkVersion + ", apkName=" + apkName + ", uses_permission=" + uses_permission
				+ "]";
	}


	
	
}
