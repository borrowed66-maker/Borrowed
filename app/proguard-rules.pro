# Add project specific ProGuard rules here.
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class io.github.jan-tennert.supabase.** { *; }
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**