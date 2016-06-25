# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/andbrain/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-dontshrink
-verbose



-dontwarn org.**

-dontwarn javax.**
-dontwarn java.lang.management.**
-dontwarn android.support.**
-dontwarn com.google.**
-dontwarn com.android.**
-dontwarn oauth.signpost.**
-dontwarn twitter4j.**
-dontwarn javax.annotation.**
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep class javax.** { *; }
-keepclassmembers class javax.** {*;}
-keep class com.google.** { *; }
-keepclassmembers class com.google.** {*;}
-keep class android.** { *; }
-keepclassmembers class android.** {*;}

-keep class javax.crypto.** { *; }
-keepclassmembers class javax.crypto.** {*;}
-dontwarn javax.crypto.**


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.google.code.linkedinapi.**

-keep class javax.**  { *; }
-keep class org.**  { *; }
-keep class twitter4j.**  { *; }
-keep class java.lang.management.**  { *; }
-keep class com.google.code.**  { *; }
-keep class oauth.signpost.**  { *; }

-keepclassmembers public class com.google.code.linkedinapi.client.impl.LinkedInApiXppClient {
     public <init>(java.lang.String, java.lang.String);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class andbrain.** { *; }
-keepclassmembers class andbrain.** {*;}
-dontwarn andbrain.**


-dontnote
-keep class javax.annotation.processing.** { *; }
-keepclassmembers class javax.annotation.processing.** {*;}
-dontwarn javax.annotation.processing.**
-dontwarn android.support.**
-ignorewarnings


