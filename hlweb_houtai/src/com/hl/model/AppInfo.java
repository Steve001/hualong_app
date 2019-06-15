package com.hl.model;

import java.util.List;

/**
 * 应用信息，如果客户端需要升级，返回这些信息
 *
 * @author wux
 */

public class AppInfo {

    private String version; // 可升级的最新版本号

    private String minVersion; // 必须升级的版本号，低于此版本号的应用必须升级

    private String apk; // apk 的下载、升级地址

    private List<String> improvements; // 改进的功能

    private List<String> newFeatures; // 新特性（功能）

    private List<String> fixedBugs; // 修改的 bug

    private List<String> tips; // 提示信息

    private String ipaManifest; // ipa 的下载、升级地址

    public String getVersion() {

        return version;
    }

    public void setVersion(String version) {

        this.version = version;
    }

    public String getMinVersion() {

        return minVersion;
    }

    public void setMinVersion(String minVersion) {

        this.minVersion = minVersion;
    }

    public String getApk() {

        return apk;
    }

    public void setApk(String apk) {

        this.apk = apk;
    }

    public List<String> getImprovements() {

        return improvements;
    }

    public void setImprovements(List<String> improvements) {

        this.improvements = improvements;
    }

    public List<String> getNewFeatures() {

        return newFeatures;
    }

    public void setNewFeatures(List<String> newFeatures) {

        this.newFeatures = newFeatures;
    }

    public List<String> getFixedBugs() {

        return fixedBugs;
    }

    public void setFixedBugs(List<String> fixedBugs) {

        this.fixedBugs = fixedBugs;
    }

    public List<String> getTips() {

        return tips;
    }

    public void setTips(List<String> tips) {

        this.tips = tips;
    }

    public String getIpaManifest() {

        return ipaManifest;
    }

    public void setIpaManifest(String ipaManifest) {

        this.ipaManifest = ipaManifest;
    }

    @Override
    public String toString() {

        return "AppInfo [version=" + version + ", minVersion=" + minVersion + ", apk=" + apk + ", improvements=" +
                improvements + ", newFeatures=" + newFeatures + ", fixedBugs=" + fixedBugs + ", tips=" + tips +
                ", ipaManifest=" + ipaManifest + "]";
    }
}
