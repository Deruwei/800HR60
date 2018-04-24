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
######################################################################
#shareSDK混淆设置
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
##########################################################################
##########################################################################
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
#百度地图
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**
#友盟
-keep class com.umeng.commonsdk.** {*;}
# ProGuard configurations for NetworkBench Lens
-keep class com.networkbench.** { *; }
-dontwarn com.networkbench.**
-keep class okhttp3.** { *;}
-dontwarn okhttp3.**
-keepattributes Exceptions, Signature, InnerClasses
-keepattributes SourceFile,LineNumberTable
# End NetworkBench Lens
