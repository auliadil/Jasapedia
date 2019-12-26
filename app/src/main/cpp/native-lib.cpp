//
// Created by Adil on 2019-12-26.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jint JNICALL
Java_id_ac_ui_cs_mobileprogramming_muhammadauliaadil_jasapedia_AddBookingActivity_calculationBookingCost(
        JNIEnv* env,
        jobject /* this */,
        jint a,
        jint b) {
    return (a*b);
}
