# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# 表示混淆时不使用大小写混合类名
-dontusemixedcaseclassnames
# 表示不跳过library中的非public的类
-dontskipnonpubliclibraryclasses
# 指定不去忽略包可见的库类的成员
# 打印混淆的详细信息
-verbose
 #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
# 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
# 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
#指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
#-allowaccessmodification
#当有优化和使用-repackageclasses时才适用。
#-repackageclasses ''

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
# 表示不进行校验,这个校验作用 在java平台上的
-dontpreverify

# 屏蔽警告
-ignorewarnings

#保证是独立的jar,没有任何项目引用,如果不写就会认为我们所有的代码是无用的,从而把所有的代码压缩掉,导出一个空的jar
-dontshrink
#保护泛型
-keepattributes Signature
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保留注解
-keepattributes *Annotation*

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

#保留内部类
-keep class com.kingsoft.shiyou.adworks.AdWorks$*
#保留内部类  成员及方法


#保留 sdk
-keep class com.kingsoft.shiyou.adworks.AdWorks
#保留内部类  成员及方法
-keepclassmembers class com.kingsoft.shiyou.adworks.AdWorks {
    public  *;
}
-keep class com.kingsoft.shiyou.adworks.IAdLoadListener
-keepclassmembers class com.kingsoft.shiyou.adworks.IAdLoadListener {
    public  *;
}

-keep class com.kingsoft.shiyou.adworks.IAdworksInitializeCallback
-keepclassmembers class com.kingsoft.shiyou.adworks.IAdworksInitializeCallback {
    public  *;
}

-keep class com.kingsoft.shiyou.adworks.bean.** { *; }
-keep class com.kingsoft.shiyou.adworks.offline.** { *; }
-keep class com.kingsoft.shiyou.adworks.launcher.task.Worker
-keep class com.kingsoft.shiyou.adworks.proxy.AdClusterDelegate

-keep class * implements com.kingsoft.shiyou.adworks.launcher.task.Worker {
}
-dontwarn com.google.**
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

-keep class com.google.firebase.** {
    public <fields>;
    public <methods>;
}
-keep public final class com.google.firebase.FirebaseOptions { public <fields>;
public <methods>;
}
#Fabric
-keep class io.fabric.sdk.android.** { *; }
-keep public class com.seasun.data.client.** { *;}
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile, LineNumberTable, *Annotation*

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}
-keepattributes Signature
-keepclassmembers class * implements java.io.Serializable { *; }

#Admob
-keep public class com.google.ads.**{
   public *;
}
-keep class com.google.ads.** { *; }

#IronSource
-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}

-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-dontwarn com.moat.**
-keep class com.moat.** { public protected private *; }
#applovin
-keepattributes Signature,InnerClasses,Exceptions,Annotation
-keep public class com.applovin.sdk.AppLovinSdk{ *; }
-keep public class com.applovin.sdk.AppLovin* { public protected *; }
-keep public class com.applovin.nativeAds.AppLovin* { public protected *; }
-keep public class com.applovin.adview.* { public protected *; }
-keep public class com.applovin.mediation.* { public protected *; }
-keep public class com.applovin.mediation.ads.* { public protected *; }
-keep public class com.applovin.impl.*.AppLovin { public protected *; }
-keep public class com.applovin.impl.**.*Impl { public protected *; }
-keepclassmembers class com.applovin.sdk.AppLovinSdkSettings { private java.util.Map localSettings; }
-keep class com.applovin.mediation.adapters.** { *; }
-keep class com.applovin.mediation.adapter.**{ *; }
-keep class com.applovin.communicator.**{ *; }

#facebook
-dontwarn com.facebook.ads.internal.**
-keeppackagenames com.facebook.*
-keep public class com.facebook.ads.**
{ public protected *; }

#unityAd
# Keep filenames and line numbers for stack traces
-keepattributes SourceFile,LineNumberTable
# Keep JavascriptInterface for WebView bridge
-keepattributes JavascriptInterface
# Sometimes keepattributes is not enough to keep annotations
-keep class android.webkit.JavascriptInterface {
   *;
}
# Keep all classes in Unity Ads package
-keep class com.unity3d.ads.** {
   *;
}
# Keep all classes in Unity Services package
-keep class com.unity3d.services.** {
   *;
}
-dontwarn com.google.ar.core.**
-dontwarn com.unity3d.services.**
-dontwarn com.ironsource.adapters.unityads.**


# Vungle
-keep class com.vungle.warren.** { *; }
-dontwarn com.vungle.warren.error.VungleError$ErrorCode
# Moat SDK
-keep class com.moat.** { *; }
-dontwarn com.moat.**
# Okio+OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keepclassmembers class * extends com.vungle.warren.persistence.Memorable {
   public <init>(byte[]);
}
# Retrofit
-keepattributes Signature, InnerClasses
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.-KotlinExtensions
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Google Android Advertising ID
-keep class com.google.android.gms.internal.** { *; }
-dontwarn com.google.android.gms.ads.identifier.**

# For communication with AdColony's WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
#mintegral
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.mintegral.** {*; }
-keep interface com.mintegral.** {*; }
-keep interface androidx.** { *; }
-keep class androidx.** { *; }
-keep public class * extends androidx.** { *; }
-dontwarn com.mintegral.**
-keep class **.R$* { public static final int mintegral*; }
-keep class com.alphab.** {*; }
-keep interface com.alphab.** {*; }

#tapjoy
-keep class com.tapjoy.** { *; }
-keep class com.moat.** { *; }
-keepattributes JavascriptInterface
-keepattributes *Annotation*
-keep class * extends java.util.ListResourceBundle {
protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
@com.google.android.gms.common.annotation.KeepName *;
}
-keepnames class * implements android.os.Parcelable {
public static final ** CREATOR;
}
-keep class com.google.android.gms.ads.identifier.** { *; }
-dontwarn com.tapjoy.**

#inmobi
-keepattributes SourceFile,LineNumberTable
-keep class com.inmobi.** { *; }
-keep public class com.google.android.gms.**
-dontwarn com.google.android.gms.**
-dontwarn com.squareup.picasso.**
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient{
     public *;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info{
     public *;
}
# skip the Picasso library classes
-keep class com.squareup.picasso.** {*;}
-dontwarn com.squareup.okhttp.**
# skip Moat classes
-keep class com.moat.** {*;}
-dontwarn com.moat.**
# skip IAB classes
-keep class com.iab.** {*;}
-dontwarn com.iab.**

#chartboost
-keep class com.chartboost.** { *; }

#Pangle
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

#clickad
-keep class xlfj.internal.dynamicloading.Mod { *** ***(***);*** ***();}
-keep class xlfj.internal.dynamicloading.IDynamicLoader { public *; }
-keep class xlfj.internal.dynamicloading.IComponent { public *; }
-keep class xlfj.internal.dynamicloading.MPreferencesManager { public *; }
-keep class xlfj.internal.dynamicloading.LogUtil { public *; }
-keep class xlfj.internal.dynamicloading.IXRewardedVideoAd** {*;}
-keep class xlfj.internal.dynamicloading.IXInterstitialAd** {*;}
-keep class xlfj.internal.dynamicloading.IXBannerAd** {*;}
-keep class xlfj.internal.dynamicloading.IXNativeAd** {*;}
-keep class xlfj.internal.dynamicloading.AdSize {*;}
-keep class xlfj.internal.view.MediaView {*;}
