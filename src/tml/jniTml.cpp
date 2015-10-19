/* 
 *  TML/SIDEX:  A BEEP based TML/SIDEX communication and data
 *  serialization library.
 *  Copyright (C) 2015 wobe-systems GmbH
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this program; if not, write to the Free
 *  Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 *  
 *  You may find a copy of the license under this software is released
 *  at COPYING file. This is LGPL software: you are welcome to develop
 *  proprietary applications using this library without any royalty or
 *  fee but returning back any change, improvement or addition in the
 *  form of source code, project image, documentation patches, etc.
 *
 *  For commercial support on build UJO enabled solutions contact us:
 *          
 *      Postal address:
 *         wobe-systems GmbH
 *         Wittland 2-4
 *         24109 Kiel
 *         Germany
 *
 *      Email address:
 *         info@tml-software.com - http://www.tml-software.com
 */
 
 #ifndef LINUX
#include <Windows.h>
#endif // LINUX
#include <stdio.h>
#include <string.h>
#include "jniTml.h"

#include <sidex.h>

#define  REG_DUMMY_KEY (char*)"DUMMY_KEY"

#define  REG_PROFILE_TYPE_CMD "CMD"
#define  REG_PROFILE_TYPE_CUSTOM "CUSTOM"
#define  REG_PROFILE_TYPE_DELETE "DELETE"

#define  REG_PROFILE_TYPE_EVT_ERROR "EVT_ERROR"
#define  REG_PROFILE_TYPE_EVT_PEER "EVT_PEER"
#define  REG_PROFILE_TYPE_EVT_POPULATE "EVT_POPULATE"
#define  REG_PROFILE_TYPE_EVT_OVERFLOW "EVT_OVERFLOW"

#define  REG_PROFILE_TYPE_BAL_PEER "BAL_PEER"
#define  REG_PROFILE_TYPE_BAL_POPULATE "BAL_POPULATE"
#define  REG_PROFILE_TYPE_BAL_BUSYSTATUSREQ "BAL_BUSYSTATUSREQ"
#define  REG_PROFILE_TYPE_BAL_CALCULATION "BAL_CALCULATION"

#define  REG_CMD_CMD_READY "CMD_READY"
#define  REG_CMD_CMD_PROGRESS "CMD_PROGRESS"
#define  REG_CMD_CMD_STAT_RPLY "CMD_STAT_RPLY"

#define  REG_SNDSTM_CLOSE "SNDSTM_CLOSE"
#define  REG_SNDSTM_GETPOS "SNDSTM_GETPOS"
#define  REG_SNDSTM_GETSIZE "SNDSTM_GETSIZE"
#define  REG_SNDSTM_ONERROR "SNDSTM_ONERROR"
#define  REG_SNDSTM_READ "SNDSTM_READ"
#define  REG_SNDSTM_SEEK "SNDSTM_SEEK"
#define  REG_SNDSTM_WRITE "SNDSTM_WRITE"

#define  REG_RECSTM_DLDBLK "RECSTM_DLD_BLK"
#define  REG_RECSTM_DLDFINISH "RECSTM_DLD_FINISH"

//////////////////////
// Callback handling:
SIDEX_VARIANT m_profileRegistrationInfo = SIDEX_HANDLE_TYPE_NULL;
SIDEX_VARIANT m_commandRegistrationInfo = SIDEX_HANDLE_TYPE_NULL;
SIDEX_VARIANT m_sndStrmRegistrationInfo = SIDEX_HANDLE_TYPE_NULL;
SIDEX_VARIANT m_recStrmRegistrationInfo = SIDEX_HANDLE_TYPE_NULL;

//////////////////////
// Callback handling:
void handleCustomCallback (TML_COMMAND_ID_TYPE iCMD, TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData){

  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID
    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(IJLjava/lang/Object;)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, iCMD, tmlhandle, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

void handleOnCmdDeleteCallback (TML_COMMAND_ID_TYPE iCMD, TML_POINTER pCmdData, TML_POINTER pCBData){
  callbackData* pCmdDataStruct = (callbackData*) pCmdData;
  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(ILjava/lang/Object;Ljava/lang/Object;)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, iCMD,  pCmdDataStruct->cbData, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

void handleCmdDispatchCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData){

  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, tmlhandle, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

void handleCmdProgressCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData, TML_INT32 iProgress){

  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;I)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, tmlhandle, pCBDataStruct->cbData, iProgress);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

void handleStatusReplyCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData, TML_INT32 iType, TML_CTSTR *sMsg){

  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;ILjava/lang/String;)V");
    if (NULL != midCallBack){
      jstring jMsg = (env)->NewStringUTF((const char*)sMsg);
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, tmlhandle, pCBDataStruct->cbData, iType, jMsg);
      env->DeleteLocalRef(jMsg);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

TML_INT32 handleRecStreamDldBlockCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBDataDld, TML_POINTER buffer, TML_INT32 bytesRead, TML_INT64 totalBytesRead, TML_INT64 streamSize){

  callbackData* pCBDataStruct = (callbackData*) pCBDataDld;
  TML_INT32 iRet = -1;

  if(pCBDataStruct->jvm == NULL)
    return iRet;
  if(pCBDataStruct->callback == NULL)
    return iRet;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return iRet;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;[BIJJ)I");
    if (NULL != midCallBack){
      jbyte* byteArr = (jbyte*) buffer;
      jbyteArray result=(env)->NewByteArray(bytesRead);

      (env)->SetByteArrayRegion(result, 0, bytesRead, byteArr);

      iRet = (env)->CallIntMethod(pCBDataStruct->callback, midCallBack, iID, pCBDataStruct->cbData, result, bytesRead, totalBytesRead, streamSize);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return iRet;
}

void handleRecStreamDldFinishCallback (TML_STREAM_ID_TYPE iID, TML_INT32 errCode, TML_POINTER pCBDataDldFinish){

  callbackData* pCBDataStruct = (callbackData*) pCBDataDldFinish;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JILjava/lang/Object;)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, iID, errCode, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

void handleSndStreamCloseCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData){

  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, iID, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

TML_INT64 handleSndStreamGetPositionCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData){

  callbackData* pCBDataStruct = (callbackData*) pCBData;
  TML_INT64 lRet = -1;

  if(pCBDataStruct->jvm == NULL)
    return lRet;
  if(pCBDataStruct->callback == NULL)
    return lRet;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return lRet;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;)J");
    if (NULL != midCallBack){
      lRet = (env)->CallLongMethod(pCBDataStruct->callback, midCallBack, iID, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return lRet;
}

TML_INT64 handleSndStreamGetSizeCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData){

  callbackData* pCBDataStruct = (callbackData*) pCBData;
  TML_INT64 lRet = -1;

  if(pCBDataStruct->jvm == NULL)
    return lRet;
  if(pCBDataStruct->callback == NULL)
    return lRet;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return lRet;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;)J");
    if (NULL != midCallBack){
      lRet = (env)->CallLongMethod(pCBDataStruct->callback, midCallBack, iID, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return lRet;
}


void handleSndStreamOnErrorCallback (TML_STREAM_ID_TYPE iID, TML_INT32 iError, TML_POINTER pCBData){

  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JILjava/lang/Object;)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, iID, iError, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

TML_INT32 handleSndStreamReadCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData, TML_POINTER buffer, TML_INT32 count, TML_INT32 *bytesRead){

  TML_INT32 iRet = -1;
  TML_INT32 iBytesRead = -1;
  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return iRet;
  if(pCBDataStruct->callback == NULL)
    return iRet;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return iRet;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;[BI)I");
    if (NULL != midCallBack){
      ////////////////////////////
      // Buffer to write to:
      jbyteArray bArr = (env)->NewByteArray(count+1);

      iBytesRead = (env)->CallIntMethod(pCBDataStruct->callback, midCallBack, iID, pCBDataStruct->cbData, bArr, count);
      if (-1 != iBytesRead){
        iRet = 0;
        jbyte* byteBuffer = (env)->GetByteArrayElements(bArr, NULL);
        ////////////////////////////
         // Buffer to write to:
        *bytesRead = iBytesRead;
        unsigned char* pBuffer = (unsigned char*)buffer;
        memcpy (pBuffer, byteBuffer, iBytesRead);

        env->ReleaseByteArrayElements(bArr, byteBuffer, 0);
      }
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return iRet;
}

TML_INT32 handleSndStreamSeekCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData, TML_INT64 seekPosition, tmlSeekOrigin seekOrigin){

  TML_INT32 iRet = -1;
  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return iRet;
  if(pCBDataStruct->callback == NULL)
    return iRet;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return iRet;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;JI)I");
    if (NULL != midCallBack){
      iRet = (env)->CallIntMethod(pCBDataStruct->callback, midCallBack, iID, pCBDataStruct->cbData, seekPosition, seekOrigin);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return iRet;
}


TML_INT32 handleSndStreamWriteCallback (TML_STREAM_ID_TYPE iID,  TML_POINTER buffer, TML_INT32 count, TML_POINTER pCBData){

  TML_INT32 iRet = -1;
  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return iRet;
  if(pCBDataStruct->callback == NULL)
    return iRet;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return iRet;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(J[BILjava/lang/Object;)I");
    if (NULL != midCallBack){
      ////////////////////////////
      // Buffer to write to:
      jbyte* byteArr = (jbyte*) buffer;
      jbyteArray result=(env)->NewByteArray(count);

      (env)->SetByteArrayRegion(result, 0, count, byteArr);

      iRet = (env)->CallIntMethod(pCBDataStruct->callback, midCallBack, iID, result, count, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return iRet;
}

void handleCommandReadyCallback (TML_COMMAND_HANDLE cmdHandle, TML_POINTER pCBData){

  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;)V");
    if (NULL != midCallBack){
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, cmdHandle, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

void handleOnEvtErrorCallback (TML_CTSTR *sProfile, TML_CTSTR *sHost, TML_CTSTR *sPort, TML_COMMAND_ID_TYPE iCMD, TML_INT32 iErrorCode, TML_POINTER pCBData){
  callbackData* pCBDataStruct = (callbackData*) pCBData;

  if(pCBDataStruct->jvm == NULL)
    return ;
  if(pCBDataStruct->callback == NULL)
    return ;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return ;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/Object;)V");
    if (NULL != midCallBack){
      jstring jProfile = (env)->NewStringUTF((const char*)sProfile);
      jstring jHost = (env)->NewStringUTF((const char*)sHost);
      jstring jPort = (env)->NewStringUTF((const char*)sPort);
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, jProfile, jHost, jPort, iCMD, iErrorCode, pCBDataStruct->cbData);
      env->DeleteLocalRef(jProfile);
      env->DeleteLocalRef(jHost);
      env->DeleteLocalRef(jPort);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

TML_BOOL handleOnPeerRegistrationCallback (TML_BOOL bSubscribe, TML_CTSTR *sHost, TML_CTSTR *sPort, TML_POINTER pCBData){
  callbackData* pCBDataStruct = (callbackData*) pCBData;
  TML_BOOL bRet = TML_FALSE;

  if(pCBDataStruct->jvm == NULL)
    return TML_FALSE;
  if(pCBDataStruct->callback == NULL)
    return TML_FALSE;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return TML_FALSE;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Z");
    if (NULL != midCallBack){
      jstring jHost = (env)->NewStringUTF((const char*)sHost);
      jstring jPort = (env)->NewStringUTF((const char*)sPort);
      bRet = (env)->CallBooleanMethod(pCBDataStruct->callback, midCallBack, bSubscribe, jHost, jPort, pCBDataStruct->cbData);
      env->DeleteLocalRef(jHost);
      env->DeleteLocalRef(jPort);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return bRet;
}

TML_INT32 handleOnPopulateCallback (TML_CTSTR *sProfile, TML_POINTER pCBData){
  callbackData* pCBDataStruct = (callbackData*) pCBData;
  TML_BOOL iRet = TML_SUCCESS;

  if(pCBDataStruct->jvm == NULL)
    return TML_ERR_COMMON;
  if(pCBDataStruct->callback == NULL)
    return TML_ERR_COMMON;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return TML_ERR_COMMON;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(Ljava/lang/String;Ljava/lang/Object;)I");
    if (NULL != midCallBack){
      jstring jProfile = (env)->NewStringUTF((const char*)sProfile);
      iRet = (env)->CallIntMethod(pCBDataStruct->callback, midCallBack, jProfile, pCBDataStruct->cbData);
      env->DeleteLocalRef(jProfile);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return iRet;
}

void handleOnEvtQueueOverflowCallback (TML_CTSTR *sProfile, TML_COMMAND_ID_TYPE iCMD, TML_POINTER pCBData){
  callbackData* pCBDataStruct = (callbackData*) pCBData;
  TML_BOOL iRet = TML_SUCCESS;

  if(pCBDataStruct->jvm == NULL)
    return;
  if(pCBDataStruct->callback == NULL)
    return;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(Ljava/lang/String;ILjava/lang/Object;)V");
    if (NULL != midCallBack){
      jstring jProfile = (env)->NewStringUTF((const char*)sProfile);
      (env)->CallVoidMethod(pCBDataStruct->callback, midCallBack, jProfile, iCMD, pCBDataStruct->cbData);
      env->DeleteLocalRef(jProfile);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
}

TML_INT32 handleOnBalCalculationCallback (TML_INT32 iCountOfDestinations, TML_COMMAND_HANDLE *listenerBusyStateArray, TML_POINTER pCBData, TML_INT32 *iNextListenerIndex){
  callbackData* pCBDataStruct = (callbackData*) pCBData;
  TML_INT32 iRet = TML_ERR_INFORMATION_UNDEFINED;

  if(pCBDataStruct->jvm == NULL)
    return TML_ERR_COMMON;
  if(pCBDataStruct->callback == NULL)
    return TML_ERR_COMMON;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return TML_ERR_COMMON;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(I[JLjava/lang/Object;[I)I");
    if (NULL != midCallBack){
      jintArray iArr = (env)->NewIntArray(1);

      jlongArray lArr = NULL;
      jlong* longBuffer = NULL;
      if (0 < iCountOfDestinations){
        jlong* longBuffer = new jlong(iCountOfDestinations);
        lArr = (env)->NewLongArray(iCountOfDestinations);
        for (int i = 0; i < iCountOfDestinations; ++i){
          longBuffer[i] = listenerBusyStateArray[i];
        }
        (env)->SetLongArrayRegion(lArr, 0, iCountOfDestinations, longBuffer);
      }
      iRet = (env)->CallIntMethod(pCBDataStruct->callback, midCallBack, iCountOfDestinations, lArr, pCBDataStruct->cbData, iArr);
      if (TML_SUCCESS == iRet){
        jint* intBuffer = new jint(1);
        (env)->GetIntArrayRegion(iArr, 0, 1, intBuffer);
        *iNextListenerIndex = intBuffer[0];
        // delete intBuffer; //- Kein Delete - fuehrt zu Exception
      }
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return iRet;
}

TML_INT32 handleOnBalBusyStatusRequestCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData){
  callbackData* pCBDataStruct = (callbackData*) pCBData;
  TML_BOOL iRet = TML_ERR_INFORMATION_UNDEFINED;

  if(pCBDataStruct->jvm == NULL)
    return TML_ERR_COMMON;
  if(pCBDataStruct->callback == NULL)
    return TML_ERR_COMMON;

  JNIEnv *env = NULL;
  jint res = 0;
  bool bHaveToDetatch = false;
  JavaVM* jvm = pCBDataStruct->jvm;
  // Determine if current thread is already attached:
  if (jvm->GetEnv((void **) &env, JNI_VERSION_1_6) == JNI_EDETACHED) {
#ifdef ANDROID
    res = jvm->AttachCurrentThread((JNIEnv **)&env, NULL);
#else // ANDROID
    res = jvm->AttachCurrentThread((void **)&env, NULL);
#endif // ANDROID

    if(res < 0)
    {
      fprintf(stderr, "Attach VM Thread failed\n");
      return TML_ERR_COMMON;
    }
    bHaveToDetatch = true;
  }

  // Get a class reference for this object
  jclass thisClass = (env)->GetObjectClass(pCBDataStruct->callback);
  if (NULL != thisClass){
    jmethodID midCallBack = (env)->GetMethodID(thisClass, pCBDataStruct->callbackName, "(JLjava/lang/Object;)I");
    if (NULL != midCallBack){
      iRet = (env)->CallIntMethod(pCBDataStruct->callback, midCallBack, tmlhandle, pCBDataStruct->cbData);
    }
  }
  if (bHaveToDetatch){
    jint ret = jvm->DetachCurrentThread();
  }
  return iRet;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Helper functions
char* getIDString(jlong iID){
  static char sIDString[128];
  long long iVal = (long long) iID;
#if defined(LINUX) || defined (MINGW_BUILD)
  sprintf (sIDString, "%lld", iVal);
#else // LINUX
    sprintf_s (sIDString, 128, "%lld", iVal);
#endif // LINUX
  return sIDString;
}

char* getCmdIDString(jint iCMD){
  static char sIDString[128];
#if defined(LINUX) || defined (MINGW_BUILD)
  sprintf (sIDString, "%s%d", REG_PROFILE_TYPE_CMD, iCMD);
#else // LINUX
    sprintf_s (sIDString, 128, "%s%d", REG_PROFILE_TYPE_CMD, iCMD);
#endif // LINUX
  return sIDString;
}

void addRegisterData(char* sCoreHandleString, char* profile, SIDEX_VARIANT* registerDict){
  if (TML_HANDLE_TYPE_NULL == *registerDict){
    *registerDict = sidex_Variant_New_Dict();
    //tml_log(0xFFFFFFFF, "addRegisterData", "sidex_Variant_New_Dict", "registerDict", "");
  }
  SIDEX_VARIANT thisCoreInfoDict;
  if (SIDEX_SUCCESS != sidex_Variant_Dict_Get(*registerDict, sCoreHandleString, &thisCoreInfoDict)){
    thisCoreInfoDict = sidex_Variant_New_Dict();
    //tml_log(0xFFFFFFFF, "addRegisterData", "sidex_Variant_New_Dict", "thisCoreInfoDict", "");
    sidex_Variant_Dict_Set(*registerDict, sCoreHandleString, thisCoreInfoDict);
    sidex_Variant_DecRef(thisCoreInfoDict);
  }
  SIDEX_VARIANT thisProfileInfoDict;
  if (SIDEX_SUCCESS != sidex_Variant_Dict_Get(thisCoreInfoDict, profile, &thisProfileInfoDict)){
    //tml_log(0xFFFFFFFF, "addRegisterData", "sidex_Variant_New_Dict", "thisProfileInfoDict", "");
    thisProfileInfoDict = sidex_Variant_New_Dict();
    //tml_log(0xFFFFFFFF, "addRegisterData", "sidex_Variant_Dict_Set", profile, "");
    sidex_Variant_Dict_Set(thisCoreInfoDict, profile, thisProfileInfoDict);
    sidex_Variant_DecRef(thisProfileInfoDict);
  }
}

SIDEX_VARIANT fetchRegisterDict(char* sCoreHandleString, char* profile, SIDEX_VARIANT registerDict){
  
  SIDEX_VARIANT thisProfileInfoDict = SIDEX_HANDLE_TYPE_NULL;

  if (TML_HANDLE_TYPE_NULL != registerDict){
    SIDEX_VARIANT thisCoreInfoDict;
    if (SIDEX_SUCCESS == sidex_Variant_Dict_Get(registerDict, sCoreHandleString, &thisCoreInfoDict)){
      if (SIDEX_SUCCESS == sidex_Variant_Dict_Get(thisCoreInfoDict, profile, &thisProfileInfoDict)){
        //tml_log(0xFFFFFFFF, "fetchRegisterDict", "sidex_Variant_Dict_Get", "thisProfileInfoDict", "");
      }
    }
  }
  return thisProfileInfoDict;
}

void setRegisteredCallbackData (char* sCoreHandleString, char* profile, const char* regType, SIDEX_INT64 iVal, SIDEX_VARIANT registerDict){
  SIDEX_VARIANT thisProfileInfoDict = fetchRegisterDict(sCoreHandleString, profile, registerDict);
  if (TML_HANDLE_TYPE_NULL != thisProfileInfoDict){
    //tml_logI(0xFFFFFFFF, "setRegisteredCallbackData", "sidex_Variant_New_Integer",  profile, iVal);
    SIDEX_VARIANT thisRegisterEntry = sidex_Variant_New_Integer (iVal);
    //tml_log(0xFFFFFFFF, "setRegisteredCallbackData", "sidex_Variant_Dict_Set",  profile, regType);
    sidex_Variant_Dict_Set(thisProfileInfoDict, (char*) regType, thisRegisterEntry);
    // Integer Variant only in the dict:
    sidex_Variant_DecRef(thisRegisterEntry);
  }
}

void freeRegisteredCallbackData (JNIEnv* env, char* sCoreHandleString, char* profile, const char* regType, SIDEX_VARIANT registerDict){
  //tml_log(0xFFFFFFFF, "freeRegisteredCallbackData", "", "", "");

  SIDEX_VARIANT thisProfileInfoDict = fetchRegisterDict(sCoreHandleString, profile, registerDict);
  if (TML_HANDLE_TYPE_NULL != thisProfileInfoDict){
    SIDEX_VARIANT thisRegisterEntry;
    //tml_log(0xFFFFFFFF, "freeRegisteredCallbackData", "sidex_Variant_Dict_Get",  profile, regType);
    if (SIDEX_SUCCESS == sidex_Variant_Dict_Get(thisProfileInfoDict, (char*)regType, &thisRegisterEntry)){
      SIDEX_INT64 iRegValue = SIDEX_HANDLE_TYPE_NULL;
      if (SIDEX_SUCCESS == sidex_Variant_As_Integer (thisRegisterEntry, &iRegValue)){
        //tml_logI(0xFFFFFFFF, "freeRegisteredCallbackData", "sidex_Variant_As_Integer",  "", iRegValue);
        callbackData* pCBData = (callbackData*) iRegValue;

        if (TML_HANDLE_TYPE_NULL != pCBData){
          callbackData* pData = (callbackData*) pCBData;
          (env)->DeleteGlobalRef(pData->callback);
          (env)->DeleteGlobalRef(pData->cbData);
          delete (pData->callbackName);
          delete pData;
        }
        //tml_log(0xFFFFFFFF, "freeRegisteredCallbackData", "sidex_Variant_Dict_Delete",  regType, "");
        sidex_Variant_Dict_Delete (thisProfileInfoDict, (char*) regType);
      }
    }
  }
}

void freeRegisteredData(JNIEnv* env, char* sCoreHandleString, char* profile, SIDEX_VARIANT registerDict){
  //tml_log(0xFFFFFFFF, "freeRegisteredData", profile, "", "");

  if (TML_HANDLE_TYPE_NULL != registerDict){
    SIDEX_VARIANT thisCoreInfoDict;
    if (SIDEX_SUCCESS == sidex_Variant_Dict_Get(registerDict, sCoreHandleString, &thisCoreInfoDict)){
      SIDEX_VARIANT thisProfileInfoDict;
      if (SIDEX_SUCCESS == sidex_Variant_Dict_Get(thisCoreInfoDict, profile, &thisProfileInfoDict)){
        SIDEX_INT32 iSize;
        sidex_Variant_Dict_Size(thisProfileInfoDict, &iSize);
        if (0 < iSize){
          SIDEX_VARIANT keys;
          SIDEX_INT32   iRet;
          iRet = sidex_Variant_Dict_Keys(thisProfileInfoDict, &keys);
          for (int i = 0; i < iSize && SIDEX_SUCCESS == iRet; ++i){
            SIDEX_VARIANT item;
            iRet = sidex_Variant_List_Get(keys, i, &item);
            if (SIDEX_SUCCESS == iRet){
              char* sKey;
              SIDEX_INT32 length;
              iRet = sidex_Variant_As_String(item, &sKey, &length);
              //tml_log(0xFFFFFFFF, "freeRegisteredData", "freeRegisteredCallbackData", profile,  sKey);
              freeRegisteredCallbackData (env, sCoreHandleString, profile, sKey, registerDict);
            }
          }
          if (keys){
            sidex_Variant_DecRef (keys);
          }
        }
        sidex_Variant_Dict_Delete(thisCoreInfoDict, profile);
      }
    }
  }
}

void freeAllRegisteredData(JNIEnv* env, char* sCoreHandleString, SIDEX_VARIANT registerDict){
  //tml_log(0xFFFFFFFF, "freeAllRegisteredData", "", "", "");

  if (TML_HANDLE_TYPE_NULL != registerDict){
    SIDEX_VARIANT thisCoreInfoDict;
    if (SIDEX_SUCCESS == sidex_Variant_Dict_Get(registerDict, sCoreHandleString, &thisCoreInfoDict)){
      SIDEX_INT32 iSize;
      sidex_Variant_Dict_Size(thisCoreInfoDict, &iSize);
      if (0 < iSize){
        SIDEX_VARIANT keys;
        SIDEX_INT32   iRet;
        iRet = sidex_Variant_Dict_Keys(thisCoreInfoDict, &keys);
        for (int i = 0; i < iSize && SIDEX_SUCCESS == iRet; ++i){
          SIDEX_VARIANT item;
          iRet = sidex_Variant_List_Get(keys, i, &item);
          if (SIDEX_SUCCESS == iRet){
            char* sKey;
            SIDEX_INT32 length;
            iRet = sidex_Variant_As_String(item, &sKey, &length);
            freeRegisteredData (env, sCoreHandleString, sKey, registerDict);
          }
        }
        if (keys){
          sidex_Variant_DecRef (keys);
        }
      }
      SIDEX_INT32 iRet = sidex_Variant_Dict_Delete(registerDict, sCoreHandleString);
      //tml_logI(0xFFFFFFFF, "freeAllRegisteredData", "sidex_Variant_Dict_Delete", sCoreHandleString,  iRet);
    }
  }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        General
JNIEXPORT void Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetCopyright(JNIEnv* env, jobject javaThis, jobject sValue, jintArray iLength){
  TML_INT32 iRet = TML_SUCCESS;
  jint* intArr   = (env)->GetIntArrayElements(iLength, 0);
  char* cValue   = NULL;

  jclass clazz = env->GetObjectClass (sValue);

  TML_INT32 strLength;

  tml_Core_Get_Copyright(&cValue, &strLength);
  clazz = env->GetObjectClass (sValue);
  jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
  if (mid != 0){
    jstring _jstring = env->NewStringUTF ((const char *) cValue);
    env->CallObjectMethod (sValue, mid, _jstring);
    env->DeleteLocalRef(_jstring);
  }
  intArr[0] = strLength;
  env->ReleaseIntArrayElements(iLength, intArr, 0);
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetLoggingValue(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iLogValue){
  TML_INT32 iRet = TML_SUCCESS;

  jint* intArr = (env)->GetIntArrayElements(iLogValue, 0);
  TML_INT32 tempLogValue;
  iRet = tml_Core_Get_LoggingValue(coreHandle, &tempLogValue);
  intArr[0] = tempLogValue;
  env->ReleaseIntArrayElements(iLogValue, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT void Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetVersion(JNIEnv* env, jobject javaThis, jintArray apiVer, jintArray libVer, jobject verStr){
  TML_INT32 iRet  = TML_SUCCESS;
  jint* apiVerArr = (env)->GetIntArrayElements(apiVer, 0);
  jint* libVerArr = (env)->GetIntArrayElements(libVer, 0);
  char* cValue    = NULL;

  jclass clazz = env->GetObjectClass (verStr);

  TML_INT32 iApiVer;
  TML_INT32 iLibVer;

  tml_Core_Get_Version(&iApiVer, &iLibVer, &cValue);
  clazz = env->GetObjectClass (verStr);
  jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
  if (mid != 0){
    jstring _jstring = env->NewStringUTF ((const char *) cValue);
    env->CallObjectMethod (verStr, mid, _jstring);
    env->DeleteLocalRef(_jstring);
  }
  apiVerArr[0] = iApiVer;
  libVerArr[0] = iLibVer;
  env->ReleaseIntArrayElements(apiVer, apiVerArr, 0);
  env->ReleaseIntArrayElements(libVer, libVerArr, 0);
}

JNIEXPORT jint  Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetLoggingValue(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iLogValue){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Core_Set_LoggingValue(coreHandle, iLogValue);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetPassword(JNIEnv* env, jobject javaThis, jstring pUserName, jstring pPassWord){
  TML_INT32 iRet        = TML_SUCCESS;
  const char *cUserName = (env)->GetStringUTFChars(pUserName, 0);
  const char *cUserPSW  = (env)->GetStringUTFChars(pPassWord, 0);

  iRet = tml_Core_Set_Password(cUserName, cUserPSW);

  env->ReleaseStringUTFChars(pUserName, cUserName);
  env->ReleaseStringUTFChars(pPassWord, cUserPSW);

  return (jint)iRet;
}

///////////////////////
// TMLCore
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreClose(JNIEnv* env, jobject javaThis, jlong coreHandle){
  TML_INT32 iRet = TML_SUCCESS;

  freeAllRegisteredData(env, getIDString(coreHandle), m_profileRegistrationInfo);
  freeAllRegisteredData(env, REG_DUMMY_KEY, m_commandRegistrationInfo);
  freeAllRegisteredData(env, REG_DUMMY_KEY, m_sndStrmRegistrationInfo);
  freeAllRegisteredData(env, REG_DUMMY_KEY, m_recStrmRegistrationInfo);
  

  TML_CORE_HANDLE handle = (TML_CORE_HANDLE) coreHandle;
  iRet = tml_Core_Close((TML_CORE_HANDLE*)&handle);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreGeneralDeregistration(JNIEnv* env, jobject javaThis, jlong coreHandle){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Core_GeneralDeregistration((TML_CORE_HANDLE)coreHandle);

  freeAllRegisteredData(env, getIDString(coreHandle), m_profileRegistrationInfo);
  freeAllRegisteredData(env, REG_DUMMY_KEY, m_commandRegistrationInfo);
  freeAllRegisteredData(env, REG_DUMMY_KEY, m_sndStrmRegistrationInfo);
  freeAllRegisteredData(env, REG_DUMMY_KEY, m_recStrmRegistrationInfo);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreOpen(JNIEnv* env, jobject javaThis, jlongArray coreHandle, jint iLog){
  jlong* longArr = (env)->GetLongArrayElements(coreHandle, 0);
  TML_INT32 iRet = TML_SUCCESS;
  TML_CORE_HANDLE tempCoreHandle;
  iRet = tml_Core_Open(&tempCoreHandle, (TML_INT32)iLog);
  if (TML_SUCCESS == iRet){
    longArr[0] = tempCoreHandle;
  }
  (env)->ReleaseLongArrayElements(coreHandle, longArr, 0);

  return (jint)iRet;
}

///////////////////////////////////////////////
//TMLCore / Listener management
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetListenerEnabled(JNIEnv* env, jobject javaThis, jlong coreHandle, jbooleanArray bEnable){
  TML_INT32 iRet = TML_SUCCESS;
  TML_BOOL  tmpEnable;

  jboolean* boolArr = (env)->GetBooleanArrayElements(bEnable, 0);

  iRet = tml_Core_Get_ListenerEnabled (coreHandle, &tmpEnable);
  if (SIDEX_SUCCESS == iRet){
    boolArr[0] = (TML_TRUE == tmpEnable);
  }
  env->ReleaseBooleanArrayElements(bEnable, boolArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetListenerIP(JNIEnv* env, jobject javaThis, jlong coreHandle, jobject sIP){
  TML_INT32 iRet = TML_SUCCESS;
  char*     tempIP;

  iRet = tml_Core_Get_ListenerIP (coreHandle, &tempIP);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (sIP);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) tempIP);
      env->CallObjectMethod (sIP, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetListenerPort(JNIEnv* env, jobject javaThis, jlong coreHandle, jobject sPort){
  TML_INT32 iRet = TML_SUCCESS;
  char*     tempPort;

  iRet = tml_Core_Get_ListenerPort (coreHandle, &tempPort);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (sPort);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) tempPort);
      env->CallObjectMethod (sPort, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetListenerEnabled(JNIEnv* env, jobject javaThis, jlong coreHandle, jboolean bEnable){
  TML_INT32 iRet = TML_SUCCESS;

  TML_BOOL doEnable;
  (bEnable == JNI_TRUE)? doEnable = TML_TRUE: doEnable = TML_FALSE;
  iRet = tml_Core_Set_ListenerEnabled (coreHandle, doEnable);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetListenerIP(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sIP){
  TML_INT32 iRet     = TML_SUCCESS;
  const char* tempIP = (env)->GetStringUTFChars(sIP, 0);

  iRet = tml_Core_Set_ListenerIP (coreHandle, (char*)tempIP);
  (env)->ReleaseStringUTFChars(sIP, tempIP);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetListenerPort(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sPort){
  TML_INT32 iRet       = TML_SUCCESS;
  const char *tempPort = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_Core_Set_ListenerPort (coreHandle, (char*)tempPort);
  (env)->ReleaseStringUTFChars(sPort, tempPort);

  return (jint)iRet;
}

///////////////////////////////////////////////
//TMLCore / Profile management
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileGetRegistered(JNIEnv* env, jobject javaThis, jlong coreHandle, jlongArray profiles) {
  TML_INT32     iRet = TML_SUCCESS;
  SIDEX_VARIANT tempProfiles;

  jlong* longArr = (env)->GetLongArrayElements(profiles, 0);
  iRet = tml_Profile_Get_Registered (coreHandle, &tempProfiles);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = tempProfiles;
  }
  env->ReleaseLongArrayElements(profiles, longArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileGetRegisteredCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iSize) {
  TML_INT32     iRet = TML_SUCCESS;
  TML_INT32     tempSize;

  jint* intArr = (env)->GetIntArrayElements(iSize, 0);
  iRet = tml_Profile_Get_Registered_Count (coreHandle, &tempSize);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempSize;
  }
  env->ReleaseIntArrayElements(iSize, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileGetRegisterState(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jbooleanArray bRegistered){
  TML_INT32     iRet = TML_SUCCESS;
  TML_BOOL      tempBoolean;

  jboolean* boolArr = (env)->GetBooleanArrayElements(bRegistered, 0);
  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  iRet = tml_Profile_Get_RegisterState (coreHandle, (char*) tempProfile, &tempBoolean);
  if (SIDEX_SUCCESS == iRet){
    boolArr[0] = (TML_TRUE == tempBoolean);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);
  env->ReleaseBooleanArrayElements(bRegistered, boolArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileRegister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile) {
  TML_INT32 iRet          = TML_SUCCESS;
  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  iRet = tml_Profile_Register (coreHandle, tempProfile);
  addRegisterData(getIDString(coreHandle), (char*) tempProfile, &m_profileRegistrationInfo);

  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileRegisterCmd(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jint cmdID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  const char* regType = getCmdIDString(cmdID);

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Profile_Register_Cmd (coreHandle, tempProfile, cmdID, handleCmdDispatchCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Profile_Register_Cmd (coreHandle, tempProfile, cmdID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }

  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileSetOnCustomDispatch(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData) {
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  const char* regType = REG_PROFILE_TYPE_CUSTOM;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Profile_Set_OnCustomDispatch (coreHandle, tempProfile, handleCustomCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Profile_Set_OnCustomDispatch (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileSetOnDeleteCmd(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  const char* regType = REG_PROFILE_TYPE_DELETE;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Profile_Set_OnDeleteCmd (coreHandle, tempProfile, handleOnCmdDeleteCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Profile_Set_OnDeleteCmd (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlProfileUnregister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile) {
  TML_INT32 iRet          = TML_SUCCESS;
  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  iRet = tml_Profile_Unregister (coreHandle, tempProfile);

  freeRegisteredData(env, getIDString(coreHandle), (char*) tempProfile, m_profileRegistrationInfo);

  (env)->ReleaseStringUTFChars(profile, tempProfile);


  return (jint)iRet;
}

///////////////////////////////////////////////
//TMLCore / Sending commands
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSendAsyncMessage(JNIEnv* env, jobject javaThis,
                                          jlong coreHandle,  jlong cmdHandle, jstring sProfile, jstring sIP, jstring sPort, jlong timeout) {
  TML_INT32 iRet      = TML_SUCCESS;
  const char *profile = (env)->GetStringUTFChars(sProfile, 0);
  const char *ip      = (env)->GetStringUTFChars(sIP, 0);
  const char *port    = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_Send_AsyncMessage(coreHandle, cmdHandle, profile, ip, port, (TML_UINT32)timeout);

  env->ReleaseStringUTFChars(sPort, port);
  env->ReleaseStringUTFChars(sIP, ip);
  env->ReleaseStringUTFChars(sProfile, profile);
  
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSendAsyncProgressReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint progress){
  TML_INT32 iRet      = TML_SUCCESS;

  iRet = tml_Send_AsyncProgressReply(cmdHandle, progress);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSendAsyncStatusReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint iType, jstring sStatus){
  TML_INT32 iRet      = TML_SUCCESS;
  const char *tmpStatus = (env)->GetStringUTFChars(sStatus, 0);

  iRet = tml_Send_AsyncStatusReply(cmdHandle, iType, (char*)tmpStatus);
  env->ReleaseStringUTFChars(sStatus, tmpStatus);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSendSyncMessage(JNIEnv* env, jobject javaThis,
                                          jlong coreHandle,  jlong cmdHandle, jstring sProfile, jstring sIP, jstring sPort, jlong timeout) {
  TML_INT32 iRet      = TML_SUCCESS;
  const char *profile = (env)->GetStringUTFChars(sProfile, 0);
  const char *ip      = (env)->GetStringUTFChars(sIP, 0);
  const char *port    = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_Send_SyncMessage(coreHandle, cmdHandle, profile, ip, port, (TML_UINT32)timeout);

  env->ReleaseStringUTFChars(sPort, port);
  env->ReleaseStringUTFChars(sIP, ip);
  env->ReleaseStringUTFChars(sProfile, profile);
  
  return (jint)iRet;
}

///////////////////////////////////////////////
//TMLCore / Event handling
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtGetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iCount){
  TML_INT32     iRet = TML_SUCCESS;
  TML_UINT32    tempSize;

  jint* intArr = (env)->GetIntArrayElements(iCount, 0);
  iRet = tml_Evt_Get_MaxConnectionFailCount (coreHandle, &tempSize);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempSize;
  }
  env->ReleaseIntArrayElements(iCount, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtGetMaxQueuedEventMessages(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iMaximum){
  TML_INT32     iRet = TML_SUCCESS;
  TML_UINT32    tempSize;

  jint* intArr = (env)->GetIntArrayElements(iMaximum, 0);
  iRet = tml_Evt_Get_MaxQueuedEventMessages (coreHandle, &tempSize);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempSize;
  }
  env->ReleaseIntArrayElements(iMaximum, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtGetSubscribedMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jlongArray subscriptions){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);

  jlong* longArr = (env)->GetLongArrayElements(subscriptions, 0);
  SIDEX_VARIANT varSubscriptions;
  iRet = tml_Evt_Get_Subscribed_MessageDestinations((TML_CORE_HANDLE)coreHandle, tmpProfile, &varSubscriptions);
  if (TML_SUCCESS == iRet){
    longArr[0] = varSubscriptions;
  }
  (env)->ReleaseLongArrayElements(subscriptions, longArr, 0);
  env->ReleaseStringUTFChars(sProfile, tmpProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSendMessage(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong cmdHandle, jstring sProfile){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);

  iRet = tml_Evt_Send_Message((TML_CORE_HANDLE)coreHandle, cmdHandle, tmpProfile);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSendSubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpSourceHost = (env)->GetStringUTFChars(sSourceHost, 0);
  const char *tmpSourcePort = (env)->GetStringUTFChars(sSourcePort, 0);
  const char *tmpDestHost = (env)->GetStringUTFChars(sDestHost, 0);
  const char *tmpDestPort = (env)->GetStringUTFChars(sDestPort, 0);

  iRet = tml_Evt_Send_SubscriptionRequest((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpSourceHost, tmpSourcePort, tmpDestHost, tmpDestPort, timeout);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sSourceHost, tmpSourceHost);
  env->ReleaseStringUTFChars(sSourcePort, tmpSourcePort);
  env->ReleaseStringUTFChars(sDestHost, tmpDestHost);
  env->ReleaseStringUTFChars(sDestPort, tmpDestPort);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSendUnsubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpSourceHost = (env)->GetStringUTFChars(sSourceHost, 0);
  const char *tmpSourcePort = (env)->GetStringUTFChars(sSourcePort, 0);
  const char *tmpDestHost = (env)->GetStringUTFChars(sDestHost, 0);
  const char *tmpDestPort = (env)->GetStringUTFChars(sDestPort, 0);

  iRet = tml_Evt_Send_UnsubscriptionRequest((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpSourceHost, tmpSourcePort, tmpDestHost, tmpDestPort, timeout);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sSourceHost, tmpSourceHost);
  env->ReleaseStringUTFChars(sSourcePort, tmpSourcePort);
  env->ReleaseStringUTFChars(sDestHost, tmpDestHost);
  env->ReleaseStringUTFChars(sDestPort, tmpDestPort);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iCount){
  TML_INT32     iRet = TML_SUCCESS;

  iRet = tml_Evt_Set_MaxConnectionFailCount (coreHandle, iCount);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetMaxQueuedEventMessages(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iMaximum){
  TML_INT32     iRet = TML_SUCCESS;

  iRet = tml_Evt_Set_MaxQueuedEventMessages (coreHandle, iMaximum);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnError(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  const char* regType = REG_PROFILE_TYPE_EVT_ERROR;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Evt_Set_OnError (coreHandle, tempProfile, handleOnEvtErrorCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Evt_Set_OnError (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnPeerRegister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  // Free Callbackdata:
  const char* regType = REG_PROFILE_TYPE_EVT_PEER;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Evt_Set_OnPeerRegister (coreHandle, tempProfile, handleOnPeerRegistrationCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Evt_Set_OnPeerRegister (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnPopulate(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  // Free Callbackdata:
  const char* regType = REG_PROFILE_TYPE_EVT_POPULATE;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Evt_Set_OnPopulate (coreHandle, tempProfile, handleOnPopulateCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Evt_Set_OnPopulate (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnQueueOverflow(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  // Free Callbackdata:
  const char* regType = REG_PROFILE_TYPE_EVT_OVERFLOW;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Evt_Set_OnQueueOverflow (coreHandle, tempProfile, handleOnEvtQueueOverflowCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Evt_Set_OnQueueOverflow (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtSubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpHost = (env)->GetStringUTFChars(sHost, 0);
  const char *tmpPort = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_Evt_Subscribe_MessageDestination((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpHost, tmpPort);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sHost, tmpHost);
  env->ReleaseStringUTFChars(sPort, tmpPort);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtUnsubscribeAllMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);

  iRet = tml_Evt_Unsubscribe_All_MessageDestinations((TML_CORE_HANDLE)coreHandle, tmpProfile);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlEvtUnsubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpHost = (env)->GetStringUTFChars(sHost, 0);
  const char *tmpPort = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_Evt_Unsubscribe_MessageDestination((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpHost, tmpPort);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sHost, tmpHost);
  env->ReleaseStringUTFChars(sPort, tmpPort);

  return (jint)iRet;
}

///////////////////////////////////////////////
//TMLCore / Load balancing
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalGetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iCount){
  TML_INT32     iRet = TML_SUCCESS;
  TML_UINT32    tempSize;

  jint* intArr = (env)->GetIntArrayElements(iCount, 0);
  iRet = tml_Bal_Get_MaxConnectionFailCount (coreHandle, &tempSize);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempSize;
  }
  env->ReleaseIntArrayElements(iCount, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalGetSubscribedMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jlongArray subscriptions){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);

  jlong* longArr = (env)->GetLongArrayElements(subscriptions, 0);
  SIDEX_VARIANT varSubscriptions;
  iRet = tml_Bal_Get_Subscribed_MessageDestinations((TML_CORE_HANDLE)coreHandle, tmpProfile, &varSubscriptions);
  if (TML_SUCCESS == iRet){
    longArr[0] = varSubscriptions;
  }
  (env)->ReleaseLongArrayElements(subscriptions, longArr, 0);
  env->ReleaseStringUTFChars(sProfile, tmpProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSendAsyncMessage(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong cmdHandle, jstring sProfile, jlong iTimeout){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);

  iRet = tml_Bal_Send_AsyncMessage((TML_CORE_HANDLE)coreHandle, cmdHandle, tmpProfile, (TML_UINT32)iTimeout);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSendSubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpSourceHost = (env)->GetStringUTFChars(sSourceHost, 0);
  const char *tmpSourcePort = (env)->GetStringUTFChars(sSourcePort, 0);
  const char *tmpDestHost = (env)->GetStringUTFChars(sDestHost, 0);
  const char *tmpDestPort = (env)->GetStringUTFChars(sDestPort, 0);

  iRet = tml_Bal_Send_SubscriptionRequest((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpSourceHost, tmpSourcePort, tmpDestHost, tmpDestPort, timeout);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sSourceHost, tmpSourceHost);
  env->ReleaseStringUTFChars(sSourcePort, tmpSourcePort);
  env->ReleaseStringUTFChars(sDestHost, tmpDestHost);
  env->ReleaseStringUTFChars(sDestPort, tmpDestPort);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSendSyncMessage(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong cmdHandle, jstring sProfile, jlong iTimeout){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);

  iRet = tml_Bal_Send_SyncMessage((TML_CORE_HANDLE)coreHandle, cmdHandle, tmpProfile, (TML_UINT32)iTimeout);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSendUnsubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpSourceHost = (env)->GetStringUTFChars(sSourceHost, 0);
  const char *tmpSourcePort = (env)->GetStringUTFChars(sSourcePort, 0);
  const char *tmpDestHost = (env)->GetStringUTFChars(sDestHost, 0);
  const char *tmpDestPort = (env)->GetStringUTFChars(sDestPort, 0);

  iRet = tml_Bal_Send_UnsubscriptionRequest((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpSourceHost, tmpSourcePort, tmpDestHost, tmpDestPort, timeout);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sSourceHost, tmpSourceHost);
  env->ReleaseStringUTFChars(sSourcePort, tmpSourcePort);
  env->ReleaseStringUTFChars(sDestHost, tmpDestHost);
  env->ReleaseStringUTFChars(sDestPort, tmpDestPort);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iCount){
  TML_INT32     iRet = TML_SUCCESS;

  iRet = tml_Bal_Set_MaxConnectionFailCount (coreHandle, iCount);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnPeerRegister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  // Free Callbackdata:
  const char* regType = REG_PROFILE_TYPE_BAL_PEER;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Bal_Set_OnPeerRegister (coreHandle, tempProfile, handleOnPeerRegistrationCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Bal_Set_OnPeerRegister (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnPopulate(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  // Free Callbackdata:
  const char* regType = REG_PROFILE_TYPE_BAL_POPULATE;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Bal_Set_OnPopulate (coreHandle, tempProfile, handleOnPopulateCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Bal_Set_OnPopulate (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnBusyStatusRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  // Free Callbackdata:
  const char* regType = REG_PROFILE_TYPE_BAL_BUSYSTATUSREQ;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Bal_Set_OnBusyStatusRequest (coreHandle, tempProfile, handleOnBalBusyStatusRequestCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Bal_Set_OnBusyStatusRequest (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnCalculation(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  const char *tempProfile = (env)->GetStringUTFChars(profile, 0);

  // Free Callbackdata:
  const char* regType = REG_PROFILE_TYPE_BAL_CALCULATION;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Bal_Set_OnCalculation (coreHandle, tempProfile, handleOnBalCalculationCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
    // Remember the registration:
    setRegisteredCallbackData (getIDString(coreHandle), (char*)tempProfile, regType, (SIDEX_INT64)pData, m_profileRegistrationInfo);
  }
  else{
    iRet = tml_Bal_Set_OnCalculation (coreHandle, tempProfile, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, getIDString(coreHandle), (char*)tempProfile, regType, m_profileRegistrationInfo);
  }
  (env)->ReleaseStringUTFChars(profile, tempProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalSubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpHost = (env)->GetStringUTFChars(sHost, 0);
  const char *tmpPort = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_Bal_Subscribe_MessageDestination((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpHost, tmpPort);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sHost, tmpHost);
  env->ReleaseStringUTFChars(sPort, tmpPort);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalUnsubscribeAllMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);

  iRet = tml_Bal_Unsubscribe_All_MessageDestinations((TML_CORE_HANDLE)coreHandle, tmpProfile);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlBalUnsubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort){
  TML_INT32 iRet = TML_SUCCESS;
  const char *tmpProfile = (env)->GetStringUTFChars(sProfile, 0);
  const char *tmpHost = (env)->GetStringUTFChars(sHost, 0);
  const char *tmpPort = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_Bal_Unsubscribe_MessageDestination((TML_CORE_HANDLE)coreHandle, tmpProfile, tmpHost, tmpPort);

  env->ReleaseStringUTFChars(sProfile, tmpProfile);
  env->ReleaseStringUTFChars(sHost, tmpHost);
  env->ReleaseStringUTFChars(sPort, tmpPort);

  return (jint)iRet;
}

///////////////////////////////////////////////
//TMLCore / Stream communication
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamClose(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jboolean bRetainOpen){
  TML_INT32     iRet = TML_SUCCESS;

  TML_BOOL doRetainOpen;
  (bRetainOpen == JNI_TRUE)? doRetainOpen = TML_TRUE: doRetainOpen = TML_FALSE;
  iRet = tml_RecStream_Close (coreHandle, iID, doRetainOpen);
  
  freeRegisteredData(env, REG_DUMMY_KEY, getIDString(iID), m_recStrmRegistrationInfo);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamDownloadData(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jint buffersize,
                                                                            jobject callerClassDld, jstring sCBNameDld, jobject pCBDataDld,
                                                                            jobject callerClassFinish, jstring sCBNameFinish, jobject pCBDataFinish){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regTypeDldBlock  = REG_RECSTM_DLDBLK;
  const char* regTypeDldFinish = REG_RECSTM_DLDFINISH;
  if (NULL != callerClassDld &&  NULL != callerClassFinish){
    int iLength;
    JavaVM* tempJvm;
    jobject tempCallback;

    ////////////
    // Dld:
    const char *tempCallbackNameDld = (env)->GetStringUTFChars(sCBNameDld, 0);

    iLength = strlen(tempCallbackNameDld);
    char *callbackNameDld = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackNameDld, tempCallbackNameDld);
#else // LINUX
    strcpy_s (callbackNameDld, iLength+1, tempCallbackNameDld);
#endif // LINUX  

    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClassDld);

    callbackData* pDataDld = new callbackData();
    pDataDld->callbackName = callbackNameDld;
    pDataDld->jvm = tempJvm;
    pDataDld->callback = tempCallback;

    jobject objDld = (env)->NewGlobalRef(pCBDataDld);
    pDataDld->cbData = objDld;

    (env)->ReleaseStringUTFChars(sCBNameDld, tempCallbackNameDld);

    //////////
    // Finish:
    const char *tempCallbackNameFinish = (env)->GetStringUTFChars(sCBNameFinish, 0);

    iLength = strlen(tempCallbackNameFinish);
    char *callbackNameFinish = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackNameFinish, tempCallbackNameFinish);
#else // LINUX
    strcpy_s (callbackNameFinish, iLength+1, tempCallbackNameFinish);
#endif // LINUX  

    //(env)->GetJavaVM(&tempJvm); Bereits gemacht
    tempCallback = (env)->NewGlobalRef(callerClassFinish);

    callbackData* pDataFinish = new callbackData();
    pDataFinish->callbackName = callbackNameFinish;
    pDataFinish->jvm = tempJvm;
    pDataFinish->callback = tempCallback;

    jobject objFinish = (env)->NewGlobalRef(pCBDataFinish);
    pDataFinish->cbData = objFinish;

    (env)->ReleaseStringUTFChars(sCBNameDld, tempCallbackNameDld);

    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regTypeDldBlock, m_recStrmRegistrationInfo);
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regTypeDldFinish, m_recStrmRegistrationInfo);

    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_recStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regTypeDldBlock, (SIDEX_INT64)pDataDld, m_recStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regTypeDldFinish, (SIDEX_INT64)pDataFinish, m_recStrmRegistrationInfo);

    iRet = tml_RecStream_DownloadData (coreHandle, iID, buffersize, handleRecStreamDldBlockCallback, (TML_POINTER)pDataDld, handleRecStreamDldFinishCallback, (TML_POINTER)pDataFinish);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamGetPosition(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jlongArray rposition){
  TML_INT32     iRet = TML_SUCCESS;

  jlong* longArr = (env)->GetLongArrayElements(rposition, 0);
  TML_INT64  tmpPosition;
  iRet = tml_RecStream_GetPosition (coreHandle, iID, &tmpPosition);
  if (TML_SUCCESS == iRet){
    longArr[0] = tmpPosition;
  }
  (env)->ReleaseLongArrayElements(rposition, longArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamGetSize(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jlongArray rsize){
  TML_INT32     iRet = TML_SUCCESS;

  jlong* longArr = (env)->GetLongArrayElements(rsize, 0);
  TML_INT64  tmpSize;
  iRet = tml_RecStream_GetSize (coreHandle, iID, &tmpSize);
  if (TML_SUCCESS == iRet){
    longArr[0] = tmpSize;
  }
  (env)->ReleaseLongArrayElements(rsize, longArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamOpen(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jstring sProfile, jstring sIP, jstring sPort){
  TML_INT32     iRet = TML_SUCCESS;

  const char *profile = (env)->GetStringUTFChars(sProfile, 0);
  const char *ip      = (env)->GetStringUTFChars(sIP, 0);
  const char *port    = (env)->GetStringUTFChars(sPort, 0);

  iRet = tml_RecStream_Open (coreHandle, iID, profile, ip, port);

  env->ReleaseStringUTFChars(sPort, port);
  env->ReleaseStringUTFChars(sIP, ip);
  env->ReleaseStringUTFChars(sProfile, profile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamRead(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject buffer, jint count, jintArray bytesRead){
  TML_INT32 iRet = TML_SUCCESS;

  SIDEX_INT32    tmpBytesRead;

  unsigned char* binVar = new unsigned char[count];

  jint* intArr = (env)->GetIntArrayElements(bytesRead, 0);
  iRet = tml_RecStream_Read (coreHandle, iID, binVar, count, &tmpBytesRead);
  if (SIDEX_SUCCESS == iRet){
    jint capacity = (jint)(env)->GetDirectBufferCapacity(buffer);
    unsigned char* dBuf = (unsigned char*) (env)->GetDirectBufferAddress(buffer);
    if (capacity >= tmpBytesRead){
      memcpy (dBuf, binVar, tmpBytesRead);
      intArr[0] = tmpBytesRead;
    }
    else{
      memcpy (dBuf, binVar, capacity);
      intArr[0] = capacity;
    }
  }
  env->ReleaseIntArrayElements(bytesRead, intArr, 0);
  delete binVar;
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamReadBuffer(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject buffer, jint count){
  TML_INT32 iRet = TML_SUCCESS;

  unsigned char* binVar = new unsigned char[count];

  iRet = tml_RecStream_ReadBuffer (coreHandle, iID, binVar, count);
  if (SIDEX_SUCCESS == iRet){
    jlong capacity = (env)->GetDirectBufferCapacity(buffer);
    unsigned char* dBuf = (unsigned char*) (env)->GetDirectBufferAddress(buffer);
    memcpy (dBuf, binVar, count);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamSeek(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jlong seekPos, jint origin){
  TML_INT32     iRet = TML_SUCCESS;

  iRet = tml_RecStream_Seek (coreHandle, iID, seekPos, (TML_SEEK_ENUM)origin);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamWrite(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jbyteArray buffer, jint count){
  TML_INT32     iRet = TML_SUCCESS;

  jbyte* bufferPtr = (env)->GetByteArrayElements(buffer, NULL);

  iRet = tml_RecStream_Write (coreHandle, iID, (TML_POINTER)bufferPtr, count);

  env->ReleaseByteArrayElements(buffer, bufferPtr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamClose(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID){
  TML_INT32     iRet = TML_SUCCESS;

  iRet = tml_SndStream_Close (coreHandle, iID);
  freeRegisteredData(env, REG_DUMMY_KEY, getIDString(iID), m_sndStrmRegistrationInfo);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamOpen(JNIEnv* env, jobject javaThis, jlong coreHandle, jlongArray iID, jstring sProfile, jstring sIP, jstring sPort){
  TML_INT32     iRet = TML_SUCCESS;
  jlong* longArr = (env)->GetLongArrayElements(iID, 0);
  const char *profile = (env)->GetStringUTFChars(sProfile, 0);
  const char *ip      = (env)->GetStringUTFChars(sIP, 0);
  const char *port    = (env)->GetStringUTFChars(sPort, 0);

  TML_INT64  tmpID;
  iRet = tml_SndStream_Open (coreHandle, &tmpID, profile, ip, port);
  if (TML_SUCCESS == iRet){
    longArr[0] = tmpID;
  }
  (env)->ReleaseLongArrayElements(iID, longArr, 0);

  env->ReleaseStringUTFChars(sPort, port);
  env->ReleaseStringUTFChars(sIP, ip);
  env->ReleaseStringUTFChars(sProfile, profile);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterClose(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regType = REG_SNDSTM_CLOSE;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_SndStream_Register_Close (coreHandle, iID, handleSndStreamCloseCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_sndStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regType, (SIDEX_INT64)pData, m_sndStrmRegistrationInfo);
  }
  else{
    iRet = tml_SndStream_Register_Close (coreHandle, iID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterGetPosition(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regType = REG_SNDSTM_GETPOS;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_SndStream_Register_GetPosition (coreHandle, iID, handleSndStreamGetPositionCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_sndStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regType, (SIDEX_INT64)pData, m_sndStrmRegistrationInfo);
  }
  else{
    iRet = tml_SndStream_Register_GetPosition (coreHandle, iID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterGetSize(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regType = REG_SNDSTM_GETSIZE;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_SndStream_Register_GetSize (coreHandle, iID, handleSndStreamGetSizeCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_sndStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regType, (SIDEX_INT64)pData, m_sndStrmRegistrationInfo);
  }
  else{
    iRet = tml_SndStream_Register_GetSize (coreHandle, iID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterOnError(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regType = REG_SNDSTM_ONERROR;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_SndStream_Register_OnError (coreHandle, iID, handleSndStreamOnErrorCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_sndStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regType, (SIDEX_INT64)pData, m_sndStrmRegistrationInfo);
  }
  else{
    iRet = tml_SndStream_Register_OnError (coreHandle, iID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterRead(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regType = REG_SNDSTM_READ;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_SndStream_Register_Read (coreHandle, iID, handleSndStreamReadCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_sndStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regType, (SIDEX_INT64)pData, m_sndStrmRegistrationInfo);
  }
  else{
    iRet = tml_SndStream_Register_Read (coreHandle, iID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
  }

  return (jint)iRet;}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterSeek(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regType = REG_SNDSTM_SEEK;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_SndStream_Register_Seek (coreHandle, iID, handleSndStreamSeekCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_sndStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regType, (SIDEX_INT64)pData, m_sndStrmRegistrationInfo);
  }
  else{
    iRet = tml_SndStream_Register_Seek (coreHandle, iID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterWrite(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData){
  TML_INT32 iRet = TML_SUCCESS;

  // Free Callbackdata:
  const char* regType = "SNDSTM_WRITE";

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_SndStream_Register_Write (coreHandle, iID, handleSndStreamWriteCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(iID), &m_sndStrmRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(iID), regType, (SIDEX_INT64)pData, m_sndStrmRegistrationInfo);
  }
  else{
    iRet = tml_SndStream_Register_Seek (coreHandle, iID, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(iID), regType, m_sndStrmRegistrationInfo);
  }

  return (jint)iRet;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////
// TML commands:
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdAcquireSidexHandle(JNIEnv* env, jobject javaThis, jlong cmdHandle, jlongArray sidexHandle){
  TML_INT32 iRet = TML_SUCCESS;
  jlong* longArr = (env)->GetLongArrayElements(sidexHandle, 0);
  SIDEX_HANDLE sidex;
  iRet = tml_Cmd_Acquire_Sidex_Handle((TML_COMMAND_HANDLE)cmdHandle, &sidex);
  if (TML_SUCCESS == iRet){
    longArr[0] = sidex;
  }
  (env)->ReleaseLongArrayElements(sidexHandle, longArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdCreate(JNIEnv* env, jobject javaThis, jlongArray cmdHandle){
  TML_INT32 iRet = TML_SUCCESS;
  jlong* longArr = (env)->GetLongArrayElements(cmdHandle, 0);
  TML_COMMAND_HANDLE cmd;
  iRet = tml_Cmd_Create(&cmd);
  if (TML_SUCCESS == iRet){
    longArr[0] = cmd;
  }
  (env)->ReleaseLongArrayElements(cmdHandle, longArr, 0);
  
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdFree(JNIEnv* env, jobject javaThis, jlong cmdHandleValue){
  TML_INT32 iRet = TML_SUCCESS;

  TML_COMMAND_HANDLE cmdHandle = (TML_COMMAND_HANDLE) cmdHandleValue;
  iRet = tml_Cmd_Free(&cmdHandle);
  freeRegisteredData(env, REG_DUMMY_KEY, getIDString(cmdHandle), m_commandRegistrationInfo);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdReleaseSidexHandle(JNIEnv* env, jobject javaThis, jlong cmdHandle){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Cmd_Release_Sidex_Handle((TML_COMMAND_HANDLE)cmdHandle);

  return (jint)iRet;
}

///////////////////////////////////////////////
//TML commands / Command message callbacks
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisterCommandReady(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject callerClass, jstring sCBName, jobject pCBData) {
  TML_INT32 iRet = TML_SUCCESS;

  const char* regType = REG_CMD_CMD_READY;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Cmd_Register_CommandReady(cmdHandle, handleCommandReadyCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(cmdHandle), regType, m_commandRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(cmdHandle), &m_commandRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(cmdHandle), regType, (SIDEX_INT64)pData, m_commandRegistrationInfo);
  }
  else{
    iRet = tml_Cmd_Register_CommandReady(cmdHandle, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(cmdHandle), regType, m_commandRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisterProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject callerClass, jstring sCBName, jobject pCBData) {
  TML_INT32 iRet = TML_SUCCESS;

  const char* regType = REG_CMD_CMD_PROGRESS;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);
    int iLength;

    iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Cmd_Register_Progress(cmdHandle, handleCmdProgressCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(cmdHandle), regType, m_commandRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(cmdHandle), &m_commandRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(cmdHandle), regType, (SIDEX_INT64)pData, m_commandRegistrationInfo);
  }
  else{
    iRet = tml_Cmd_Register_Progress(cmdHandle, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(cmdHandle), regType, m_commandRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisterStatusReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject callerClass, jstring sCBName, jobject pCBData) {
  TML_INT32 iRet = TML_SUCCESS;

  const char* regType = REG_CMD_CMD_STAT_RPLY;

  if (NULL != callerClass){
    const char *tempCallbackName = (env)->GetStringUTFChars(sCBName, 0);

    int iLength = strlen(tempCallbackName);
    char *callbackName = new char[iLength+1];
#if defined(LINUX) || defined (MINGW_BUILD)
    strcpy (callbackName, tempCallbackName);
#else // LINUX
    strcpy_s (callbackName, iLength+1, tempCallbackName);
#endif // LINUX  

    JavaVM* tempJvm;
    jobject tempCallback;
    (env)->GetJavaVM(&tempJvm);
    tempCallback = (env)->NewGlobalRef(callerClass);

    callbackData* pData = new callbackData();
    pData->callbackName = callbackName;
    pData->jvm = tempJvm;
    pData->callback = tempCallback;

    jobject obj = (env)->NewGlobalRef(pCBData);
    pData->cbData = obj;

    (env)->ReleaseStringUTFChars(sCBName, tempCallbackName);
    iRet = tml_Cmd_Register_StatusReply(cmdHandle, handleStatusReplyCallback, (TML_POINTER)pData);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(cmdHandle), regType, m_commandRegistrationInfo);
    // Remember the registration:
    addRegisterData(REG_DUMMY_KEY, getIDString(cmdHandle), &m_commandRegistrationInfo);
    setRegisteredCallbackData (REG_DUMMY_KEY, getIDString(cmdHandle), regType, (SIDEX_INT64)pData, m_commandRegistrationInfo);
  }
  else{
    iRet = tml_Cmd_Register_StatusReply(cmdHandle, NULL, NULL);
    // Free the old registration:
    freeRegisteredCallbackData (env, REG_DUMMY_KEY, getIDString(cmdHandle), regType, m_commandRegistrationInfo);
  }

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisteredCommandReady(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobjectArray callerClass, jobject sCBName, jobjectArray pCBData) {
  TML_INT32 iRet = TML_SUCCESS;

  TML_ON_COMMAND_READY_CB_FUNC pRetCBFunc;
  TML_POINTER                 pRetCBData;

  iRet = tml_Cmd_Registered_CommandReady(cmdHandle, &pRetCBFunc, &pRetCBData);

  if (TML_SUCCESS == iRet && NULL != pRetCBFunc){
    // pData->callbackName :
    callbackData* pData = (callbackData*) pRetCBData;
    jclass clazz = env->GetObjectClass (sCBName);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) pData->callbackName);
      env->CallObjectMethod (sCBName, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
    //pData->callback;
    (env)->SetObjectArrayElement(callerClass, 0, pData->callback);
    //pData->cbData;
    (env)->SetObjectArrayElement(pCBData, 0, pData->cbData);
  }
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisteredStatusReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobjectArray callerClass, jobject sCBName, jobjectArray pCBData) {
  TML_INT32 iRet = TML_SUCCESS;

  TML_ON_STATUS_REPLY_CB_FUNC pRetCBFunc;
  TML_POINTER                 pRetCBData;

  iRet = tml_Cmd_Registered_StatusReply(cmdHandle, &pRetCBFunc, &pRetCBData);

  if (TML_SUCCESS == iRet && NULL != pRetCBFunc){
    // pData->callbackName :
    callbackData* pData = (callbackData*) pRetCBData;
    jclass clazz = env->GetObjectClass (sCBName);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) pData->callbackName);
      env->CallObjectMethod (sCBName, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
    //pData->callback;
    (env)->SetObjectArrayElement(callerClass, 0, pData->callback);
    //pData->cbData;
    (env)->SetObjectArrayElement(pCBData, 0, pData->cbData);
  }
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisteredProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobjectArray callerClass, jobject sCBName, jobjectArray pCBData) {
  TML_INT32 iRet = TML_SUCCESS;

  TML_ON_PROGRESS_REPLY_CB_FUNC pRetCBFunc;
  TML_POINTER                 pRetCBData;

  iRet = tml_Cmd_Registered_Progress(cmdHandle, &pRetCBFunc, &pRetCBData);

  if (TML_SUCCESS == iRet && NULL != pRetCBFunc){
    // pData->callbackName :
    callbackData* pData = (callbackData*) pRetCBData;
    jclass clazz = env->GetObjectClass (sCBName);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) pData->callbackName);
      env->CallObjectMethod (sCBName, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
    //pData->callback;
    (env)->SetObjectArrayElement(callerClass, 0, pData->callback);
    //pData->cbData;
    (env)->SetObjectArrayElement(pCBData, 0, pData->cbData);
  }
  return (jint)iRet;
}

///////////////////////////////////////////////
// TML commands / Accessing header information
JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetCommand(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray cmdID){
  TML_INT32           iRet = TML_SUCCESS;
  TML_COMMAND_ID_TYPE tempCmdID;

  jint* intArr = (env)->GetIntArrayElements(cmdID, 0);
  iRet = tml_Cmd_Header_GetCommand (cmdHandle, &tempCmdID);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempCmdID;
  }
  env->ReleaseIntArrayElements(cmdID, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetCreationTime(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject time){
  TML_INT32 iRet = TML_SUCCESS;
  char* cValue   = NULL;

  jclass clazz = env->GetObjectClass (time);

  iRet = tml_Cmd_Header_GetCreationTime (cmdHandle, &cValue);
  clazz = env->GetObjectClass (time);
  jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
  if (mid != 0){
    jstring _jstring = env->NewStringUTF ((const char *) cValue);
    env->CallObjectMethod (time, mid, _jstring);
    env->DeleteLocalRef(_jstring);
  }
  if (NULL != cValue){
    sidex_Free_ReadString(cValue);
  }
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetError(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray error){
  TML_INT32 iRet = TML_SUCCESS;
  TML_INT32 tempError;

  jint* intArr = (env)->GetIntArrayElements(error, 0);
  iRet = tml_Cmd_Header_GetError (cmdHandle, &tempError);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempError;
  }
  env->ReleaseIntArrayElements(error, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetErrorMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject msg){
  TML_INT32 iRet = TML_SUCCESS;
  char* cValue   = NULL;

  jclass clazz = env->GetObjectClass (msg);

  TML_INT32 strLength;

  iRet = tml_Cmd_Header_GetErrorMessage(cmdHandle, &cValue, &strLength);
  clazz = env->GetObjectClass (msg);
  jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
  if (mid != 0){
    jstring _jstring = env->NewStringUTF ((const char *) cValue);
    env->CallObjectMethod (msg, mid, _jstring);
    env->DeleteLocalRef(_jstring);
  }
  if (NULL != cValue){
    sidex_Free_ReadString(cValue);
  }
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetMode(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray mode){
  TML_INT32 iRet = TML_SUCCESS;
  TML_INT32 tempMode;

  jint* intArr = (env)->GetIntArrayElements(mode, 0);
  iRet = tml_Cmd_Header_GetMode(cmdHandle, &tempMode);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempMode;
  }
  env->ReleaseIntArrayElements(mode, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray progress){
  TML_INT32 iRet = TML_SUCCESS;
  TML_INT32 tempProgress;

  jint* intArr = (env)->GetIntArrayElements(progress, 0);
  iRet = tml_Cmd_Header_GetProgress (cmdHandle, &tempProgress);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempProgress;
  }
  env->ReleaseIntArrayElements(progress, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetReplyMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject msg){
  TML_INT32 iRet = TML_SUCCESS;
  char* cValue   = NULL;

  jclass clazz = env->GetObjectClass (msg);

  TML_INT32 strLength;

  iRet = tml_Cmd_Header_GetReplyMessage (cmdHandle, &cValue, &strLength);
  clazz = env->GetObjectClass (msg);
  jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
  if (mid != 0){
    jstring _jstring = env->NewStringUTF ((const char *) cValue);
    env->CallObjectMethod (msg, mid, _jstring);
    env->DeleteLocalRef(_jstring);
  }
  if (NULL != cValue){
    sidex_Free_ReadString(cValue);
  }
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetReplyType(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray type){
  TML_INT32 iRet = TML_SUCCESS;
  TML_INT32 tempType;

  jint* intArr = (env)->GetIntArrayElements(type, 0);
  iRet = tml_Cmd_Header_GetReplyType (cmdHandle, &tempType);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempType;
  }
  env->ReleaseIntArrayElements(type, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetState(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray state){
  TML_INT32 iRet = TML_SUCCESS;
  TML_INT32 tempState;

  jint* intArr = (env)->GetIntArrayElements(state, 0);
  iRet = tml_Cmd_Header_GetState (cmdHandle, &tempState);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = tempState;
  }
  env->ReleaseIntArrayElements(state, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetCommand(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint cmdID){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Cmd_Header_SetCommand (cmdHandle, cmdID);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetError(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint error){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Cmd_Header_SetError (cmdHandle, error);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetErrorMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jstring msg){
  TML_INT32 iRet = TML_SUCCESS;
  const char* tempMsg = (env)->GetStringUTFChars(msg, 0);

  int iMsgLength = strlen(tempMsg);

  iRet = tml_Cmd_Header_SetErrorMessage (cmdHandle, (char*)tempMsg, iMsgLength);
  (env)->ReleaseStringUTFChars(msg, tempMsg);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetMode(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint mode){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Cmd_Header_SetMode (cmdHandle, mode);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint progress){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Cmd_Header_SetProgress (cmdHandle, progress);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetReplyMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jstring msg){
  TML_INT32 iRet = TML_SUCCESS;
  const char* tempMsg = (env)->GetStringUTFChars(msg, 0);

  int iMsgLength = strlen(tempMsg);

  iRet = tml_Cmd_Header_SetReplyMessage (cmdHandle, (char*)tempMsg, iMsgLength);
  (env)->ReleaseStringUTFChars(msg, tempMsg);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetReplyType(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint type){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Cmd_Header_SetReplyType (cmdHandle, type);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetState(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint state){
  TML_INT32 iRet = TML_SUCCESS;
  iRet = tml_Cmd_Header_SetState (cmdHandle, state);
  
  return (jint)iRet;
}



