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

 # Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 # kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
 -keepclassmembers class kotlinx.serialization.json.** {
     *** Companion;
 }
 -keepclasseswithmembers class kotlinx.serialization.json.** {
     kotlinx.serialization.KSerializer serializer(...);
 }

 -keep interface com.kakao.sdk.**.*Api
 -keep class com.kakao.sdk.**.model.* { <fields>; }
 -keep class * extends com.google.gson.TypeAdapter

 # https://github.com/square/okhttp/pull/6792
 -dontwarn org.bouncycastle.jsse.**
 -dontwarn org.conscrypt.*
 -dontwarn org.openjsse.**
 -dontwarn org.slf4j.impl.StaticLoggerBinder

-dontwarn com.android.billingclient.api.Purchase
-dontwarn java.lang.invoke.StringConcatFactory