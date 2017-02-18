#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/bitmap.h>
#include <android/log.h>

#define  LOG_TAG    "libplasma"


#ifndef eprintf
#define  eprintf(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
/*#define eprintf(...) __android_log_print(ANDROID_LOG_ERROR,"@",__VA_ARGS__)*/
#endif

#define RGB565_R(p) ((((p) & 0xF800) >> 11) << 3)
#define RGB565_G(p) ((((p) & 0x7E0 ) >> 5)  << 2)
#define RGB565_B(p) ( ((p) & 0x1F  )        << 3)
#define MAKE_RGB565(r,g,b) ((((r) >> 3) << 11) | (((g) >> 2) << 5) | ((b) >> 3))

#define RGBA_A(p) (((p) & 0xFF000000) >> 24)
#define RGBA_R(p) (((p) & 0x00FF0000) >> 16)
#define RGBA_G(p) (((p) & 0x0000FF00) >>  8)
#define RGBA_B(p)  ((p) & 0x000000FF)
#define MAKE_RGBA(r,g,b,a) (((a) << 24) | ((r) << 16) | ((g) << 8) | (b))





JNIEXPORT void JNICALL
Java_com_ck_ind_finddir_toolkits_ImageTools_rotateBitmap(JNIEnv *env, jobject instance,
                                                         jobject zBitmap) {

    // TODO
    JNIEnv J = *env;

    if (zBitmap == NULL) {
//        eprintf("bitmap is null\n");
        return;
    }

    // Get bitmap info
    AndroidBitmapInfo info;
    memset(&info, 0, sizeof(info));
    AndroidBitmap_getInfo(env, zBitmap, &info);
    // Check format, only RGB565 & RGBA are supported
    if (info.width <= 0 || info.height <= 0 ||
        (info.format != ANDROID_BITMAP_FORMAT_RGB_565 && info.format != ANDROID_BITMAP_FORMAT_RGBA_8888)) {
        eprintf("invalid bitmap\n");
        J->ThrowNew(env, J->FindClass(env, "java/io/IOException"), "invalid bitmap");
        return;
    }

    // Lock the bitmap to get the buffer
    void * pixels = NULL;
    int res = AndroidBitmap_lockPixels(env, zBitmap, &pixels);
    if (pixels == NULL) {
        eprintf("fail to lock bitmap: %d\n", res);
        J->ThrowNew(env, J->FindClass(env, "java/io/IOException"), "fail to open bitmap");
        return;
    }

    eprintf("Effect: %dx%d, %d\n", info.width, info.height, info.format);

    int x = 0, y = 0;
    // From top to bottom
    for (y = 0; y < info.height; ++y) {
        // From left to right
        for (x = 0; x < info.width; ++x) {
            int a = 0, r = 0, g = 0, b = 0;
            void *pixel = NULL;
            // Get each pixel by format
            if (info.format == ANDROID_BITMAP_FORMAT_RGB_565) {
                pixel = ((uint16_t *)pixels) + y * info.width + x;
                uint16_t v = *(uint16_t *)pixel;
                r = RGB565_R(v);
                g = RGB565_G(v);
                b = RGB565_B(v);
            } else {// RGBA
                pixel = ((uint32_t *)pixels) + y * info.width + x;
                uint32_t v = *(uint32_t *)pixel;
                a = RGBA_A(v);
                r = RGBA_R(v);
                g = RGBA_G(v);
                b = RGBA_B(v);
            }

            // Grayscale
            int gray = (r * 38 + g * 75 + b * 15) >> 7;

            // Write the pixel back
            if (info.format == ANDROID_BITMAP_FORMAT_RGB_565) {
                *((uint16_t *)pixel) = MAKE_RGB565(gray, gray, gray);
            } else {// RGBA
                *((uint32_t *)pixel) = MAKE_RGBA(gray, gray, gray, a);
            }
        }
    }
    AndroidBitmap_unlockPixels(env, zBitmap);

}

/*
JNIEXPORT jstring JNICALL
Java_com_ck_ind_finddir_toolkits_ImageTools_getMsgFromJni2(JNIEnv *env, jobject instance) {

    // TODO
    const char* abc = "okok";

    return (*env)->NewStringUTF(env, abc);
}*/
