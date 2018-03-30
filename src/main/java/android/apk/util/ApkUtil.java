package android.apk.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public class ApkUtil {
    private static final Namespace NS = Namespace.getNamespace("http://schemas.android.com/apk/res/android");

    public static Map<String, Object> getApkInfo(String apkPath) {
        ApkInfo apkInfo = new ApkInfo();
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        try {
            document = builder.build(getXmlInputStream(apkPath));
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
        Element root = document.getRootElement();// 跟节点-->manifest
        apkInfo.setVersionCode(root.getAttributeValue("versionCode", NS));
        apkInfo.setVersionName(root.getAttributeValue("versionName", NS));
        apkInfo.setApkPackage(root.getAttributeValue("package", NS));
        Element elemUseSdk = root.getChild("uses-sdk");// 子节点-->uses-sdk
        apkInfo.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", NS));
        List listPermission = root.getChildren("uses-permission");// 子节点是个集合
        List permissions = new ArrayList();
        for (Object object : listPermission) {
            String permission = ((Element) object).getAttributeValue("name", NS);
            permissions.add(permission);
        }
        apkInfo.setUses_permission(permissions);

        String s = root.getAttributes().toString();
        String c[] = s.split(",");
        String versionCode = null;
        String versionName = null;
        String packageName = null;
        for (String a : c) {
            if (a.contains("versionCode")) {
                versionCode = a.substring(a.indexOf("versionCode=\"") + 13, a.lastIndexOf("\""));
            }
            if (a.contains("versionName")) {
                versionName = a.substring(a.indexOf("versionName=\"") + 13, a.lastIndexOf("\""));
            }
            if (a.contains("package")) {
                packageName = a.substring(a.indexOf("package=\"") + 9, a.lastIndexOf("\""));
            }
        }
        String str = "\n版本号:" + versionCode + "\n版本名:" + versionName + "\n包名:" + packageName;

        Map<String, Object> map = new HashMap<>();
        map.put("versionCode", versionCode);
        map.put("versionName", versionName);
        map.put("packageName", packageName);

        return map;
    }

    private static InputStream getXmlInputStream(String apkPath) {
        InputStream inputStream = null;
        InputStream xmlInputStream = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(apkPath);
            ZipEntry zipEntry = new ZipEntry("AndroidManifest.xml");
            inputStream = zipFile.getInputStream(zipEntry);
            AXMLPrinter xmlPrinter = new AXMLPrinter();
            xmlPrinter.startPrinf(inputStream);
            xmlInputStream = new ByteArrayInputStream(xmlPrinter.getBuf().toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                inputStream.close();
                zipFile.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return xmlInputStream;
    }

    /**
     * 四舍五入
     *
     * @param value
     * @return
     */
    public static double convert(double value) {
        long l1 = Math.round(value * 100); // 四舍五入
        double ret = l1 / 100.0; // 注意：使用 100.0 而不是 100
        return ret;
    }

    /**
     * 文件生成md5值
     *
     * @param in
     * @return
     * @throws FileNotFoundException
     */
    public static String getMd5ByFile(FileInputStream in) {
        MessageDigest digest = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16).toUpperCase();

    }

    /**
     * 传入软件的路径，获取软件信息
     *
     * @param apkPath
     * @return
     */
    public static Map<String, Object> getApkInfoUtils(String apkPath) {
        File file = new File(apkPath);
        return getApkInfoUtils(file);
    }

    /**
     * 传入软件的路径，获取软件信息
     *
     * @param file
     * @return
     */
    public static Map<String, Object> getApkInfoUtils(File file) {

        float size = (float) ((float) file.length() / 1024 * 1.0 / 1024 * 1.0);
        size = (float) ApkUtil.convert(size);
        Map<String, Object> map = ApkUtil.getApkInfo(file.getPath());
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("fileName", file.getName());
        map.put("size", size);
        String md5 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            md5 = getMd5ByFile(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map.put("md5", md5);
        return map;
    }


    public static void main(String[] args) {
        String path = "D:\\1.apk";
        System.out.println(getApkInfoUtils(path));

    }


}
