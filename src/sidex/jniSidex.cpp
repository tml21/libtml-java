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
 *  Homepage:
 *    http://www.libtml.org
 *
 *  For professional support contact us:
 *
 *    wobe-systems GmbH
 *    support@libtml.org
 *
 * Contributors:
 *    wobe-systems GmbH
 */
 
#include <stdio.h>
#include <string.h>
#include <jni.h>
#include "jniSidex.h"

#include <sidex.h>

//////////////////////
// General:
JNIEXPORT void Java_com_tmlsidex_jni_Sidex_NativeSidexGetCopyright(JNIEnv* env, jobject javaThis, jobject sValue, jintArray iLength){
  jint* intArr = (env)->GetIntArrayElements(iLength, 0);
  char* cValue = NULL;

  SIDEX_INT32 strLength;

  sidex_Get_Copyright(&cValue, &strLength);
  jclass clazz = env->GetObjectClass (sValue);
  jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
  if (mid != 0){
    jstring _jstring = env->NewStringUTF ((const char *) cValue);
    env->CallObjectMethod (sValue, mid, _jstring);
    env->DeleteLocalRef(_jstring);
  }
  intArr[0] = strLength;
  env->ReleaseIntArrayElements(iLength, intArr, 0);
}

JNIEXPORT void Java_com_tmlsidex_jni_Sidex_NativeSidexGetVersion(JNIEnv* env, jobject javaThis, jintArray apiVer, jintArray libVer, jobject verStr){
  jint* apiVerArr = (env)->GetIntArrayElements(apiVer, 0);
  jint* libVerArr = (env)->GetIntArrayElements(libVer, 0);
  char* cValue = NULL;

  SIDEX_INT32 iApiVer;
  SIDEX_INT32 iLibVer;

  sidex_Get_Version(&iApiVer, &iLibVer, &cValue);
  jclass clazz = env->GetObjectClass (verStr);
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

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexSetPassword(JNIEnv* env, jobject javaThis, jstring pUserName, jstring pPassWord){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cUserName = (env)->GetStringUTFChars(pUserName, 0);
  const char *cUserPSW = (env)->GetStringUTFChars(pPassWord, 0);

  iRet = sidex_Set_Password(cUserName, cUserPSW);

  env->ReleaseStringUTFChars(pUserName, cUserName);
  env->ReleaseStringUTFChars(pPassWord, cUserPSW);

  return (jint)iRet;
}

///////////////////////
// SIDEX document:
JNIEXPORT void Java_com_tmlsidex_jni_Sidex_NativeSidexClear(JNIEnv* env, jobject javaThis, jlong sHandle){
  sidex_Clear((SIDEX_HANDLE)sHandle);
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexCreate(JNIEnv* env, jobject javaThis, jstring docname, jlongArray jHandle){

  const char *cDoc = (env)->GetStringUTFChars(docname, 0);

  jlong* longArr = (env)->GetLongArrayElements(jHandle, 0);
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  SIDEX_HANDLE sHandle;
  iRet = sidex_Create(cDoc, &sHandle);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sHandle;
  }
  env->ReleaseLongArrayElements(jHandle, longArr, 0);
  env->ReleaseStringUTFChars(docname, cDoc);

  return (jint)iRet;
}

JNIEXPORT void Java_com_tmlsidex_jni_Sidex_NativeSidexFree(JNIEnv* env, jobject javaThis, jlong sHandle){
  sidex_Free((SIDEX_HANDLE*)&sHandle);
}

// sidex_Free_Content is not necessary on JAVA

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexGetContent(JNIEnv* env, jobject javaThis, jlong sHandle, jobject sContent, jintArray iLength){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;

  jint* intArr = (env)->GetIntArrayElements(iLength, 0);
  char* cValue = NULL;

  SIDEX_INT32 strLength;

  iRet = sidex_Get_Content(sHandle, &cValue, &strLength);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (sContent);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cValue);
      env->CallObjectMethod (sContent, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
    sidex_Free_Content(cValue);
    intArr[0] = strLength;
  }
  env->ReleaseIntArrayElements(iLength, intArr, 0);


  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexGetContentLength(JNIEnv* env, jobject javaThis, jlong sHandle, jintArray iLength){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;

  jint* intArr = (env)->GetIntArrayElements(iLength, 0);

  SIDEX_INT32 strLength;

  iRet = sidex_Get_Content_Length(sHandle, &strLength);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = strLength;
  }
  env->ReleaseIntArrayElements(iLength, intArr, 0);

  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexGetDocumentName(JNIEnv* env, jobject javaThis, jlong sHandle, jobject sName){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;

  char* cValue = NULL;

  iRet = sidex_Get_DocumentName(sHandle, &cValue);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (sName);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cValue);
      env->CallObjectMethod (sName, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
  }
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexLoadContent(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sPath){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cPath = (env)->GetStringUTFChars(sPath, 0);

  iRet = sidex_Load_Content((SIDEX_HANDLE)sHandle, cPath);
  env->ReleaseStringUTFChars(sPath, cPath);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexMerge(JNIEnv* env, jobject javaThis, jlong sBaseHandle, jlong sMergeHandle, jboolean bOverwrite, jstring sGroup, jstring sKey){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);

  SIDEX_BOOL doOwerwrite;
  (bOverwrite == JNI_TRUE)? doOwerwrite = SIDEX_TRUE: doOwerwrite = SIDEX_FALSE;
  iRet = sidex_Merge((SIDEX_HANDLE)sBaseHandle, (SIDEX_HANDLE)sMergeHandle, doOwerwrite, (char*)cGroup, (char*)cKey);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}


JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexSaveContent(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sPath){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cPath = (env)->GetStringUTFChars(sPath, 0);

  iRet = sidex_Save_Content((SIDEX_HANDLE)sHandle, cPath);
  env->ReleaseStringUTFChars(sPath, cPath);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexSetContent(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sContent){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;

  const char *cContent = (env)->GetStringUTFChars(sContent, 0);
  iRet = sidex_Set_Content(sHandle, (char*)cContent);
  env->ReleaseStringUTFChars(sContent, cContent);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexSetDocumentName(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sName){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;

  const char *cName = (env)->GetStringUTFChars(sName, 0);
  iRet = sidex_Set_DocumentName(sHandle, cName);
  env->ReleaseStringUTFChars(sName, cName);
  return (jint)iRet;
}

////////////////////////////////////////////////
// SIDEX document / Group/Key management
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexDeleteKey(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_DeleteKey(sHandle, (char*)cGroup, (char*)cKey);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexDeleteGroup(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  iRet = sidex_DeleteGroup(sHandle, (char*)cGroup);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexGetGroups(JNIEnv* env, jobject javaThis, jlong sHandle, jlongArray variant){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  jlong* longArr = (env)->GetLongArrayElements(variant, 0);
  SIDEX_VARIANT sVariant;
  iRet = sidex_GetGroups(sHandle, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseLongArrayElements(variant, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexGetKeys(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jlongArray variant){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  jlong* longArr = (env)->GetLongArrayElements(variant, 0);
  SIDEX_VARIANT sVariant;
  iRet = sidex_GetKeys(sHandle, (char*)cGroup, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseLongArrayElements(variant, longArr, 0);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  return (jint)iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexHasGroup(JNIEnv* env, jobject javaThis, jlong shandle, jstring sGroup){
  SIDEX_BOOL bVal;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  bVal = sidex_HasGroup(shandle, (char*) cGroup);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  return (jboolean) bVal;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexHasKey(JNIEnv* env, jobject javaThis, jlong shandle, jstring sGroup, jstring sKey){
  SIDEX_BOOL bVal;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  bVal = sidex_HasKey(shandle, (char*) cGroup, (char*)cKey);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jboolean) bVal;
}

////////////////////////////////////////////////
// SIDEX document / Read/Write values
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexBinaryLength(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jintArray iLength){
  SIDEX_INT32 iRet;

  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jint* intArr = (env)->GetIntArrayElements(iLength, 0);

  SIDEX_INT32 strLength;

  iRet = sidex_Binary_Length(sHandle, (char*)cGroup, (char*)cKey, &strLength);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = strLength;
  }
  env->ReleaseIntArrayElements(iLength, intArr, 0);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexBinaryRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jobject sValue, jintArray iLength){
  SIDEX_INT32 iRet;

  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jint* intArr = (env)->GetIntArrayElements(iLength, 0);

  SIDEX_INT32 strLength;
  unsigned char* binVar;

  iRet = sidex_Binary_Read(sHandle, (char*)cGroup, (char*)cKey, &binVar, &strLength);
  if (SIDEX_SUCCESS == iRet){
    jint capacity = (jint)(env)->GetDirectBufferCapacity(sValue);
    unsigned char* dBuf = (unsigned char*) (env)->GetDirectBufferAddress(sValue);
    if (capacity >= strLength){
      memcpy (dBuf, binVar, strLength);
      intArr[0] = strLength;
    }
    else{
      memcpy (dBuf, binVar, capacity);
      intArr[0] = capacity;
    }
    sidex_Free_Binary_ReadString(binVar);
  }
  env->ReleaseIntArrayElements(iLength, intArr, 0);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexBinaryWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jbyteArray value, jint iSize){
  SIDEX_INT32 iRet;

  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jbyte* bufferPtr = (env)->GetByteArrayElements(value, NULL);

  iRet = sidex_Binary_Write(sHandle, (char*)cGroup, (char*)cKey, (unsigned char*)bufferPtr, iSize);

  env->ReleaseByteArrayElements(value, bufferPtr, 0);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexBooleanRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jbooleanArray value){
  SIDEX_INT32 iRet;

  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jboolean* boolArr = (env)->GetBooleanArrayElements(value, 0);

  SIDEX_BOOL  bVar;

  iRet = sidex_Boolean_Read(sHandle, (char*)cGroup, (char*)cKey, &bVar);
  if (SIDEX_SUCCESS == iRet){
    boolArr[0] = (SIDEX_TRUE == bVar);
  }
  env->ReleaseBooleanArrayElements(value, boolArr, 0);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexBooleanWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jboolean value){
  SIDEX_INT32 iRet;

  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);

  SIDEX_BOOL bValue;
  (value == JNI_TRUE)? bValue = SIDEX_TRUE: bValue = SIDEX_FALSE;
  iRet = sidex_Boolean_Write(sHandle, (char*)cGroup, (char*)cKey, bValue);

  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexDateTimeRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jlong* longArr = (env)->GetLongArrayElements(value, 0);
  char* cValue = NULL;

  SIDEX_VARIANT sVariant;

  iRet = sidex_DateTime_Read(sHandle, (char*)cGroup, (char*)cKey, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseLongArrayElements(value, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexDateTimeWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_DateTime_Write(sHandle, (char*)cGroup, (char*)cKey, value);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexDictRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jlong* longArr = (env)->GetLongArrayElements(value, 0);
  char* cValue = NULL;

  SIDEX_VARIANT sVariant;

  iRet = sidex_Dict_Read(sHandle, (char*)cGroup, (char*)cKey, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseLongArrayElements(value, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexDictWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Dict_Write(sHandle, (char*)cGroup, (char*)cKey, value);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexFloatRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jdoubleArray value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jdouble* doubleArr = (env)->GetDoubleArrayElements(value, 0);
  char* cValue = NULL;

  SIDEX_DOUBLE sVariant;

  iRet = sidex_Float_Read(sHandle, (char*)cGroup, (char*)cKey, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    doubleArr[0] = sVariant;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseDoubleArrayElements(value, doubleArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexFloatWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jdouble value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Float_Write(sHandle, (char*)cGroup, (char*)cKey, value);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

// sidex_Free_Binary_ReadString  s not necessary on JAVA
// sidex_Free_ReadString is not necessary on JAVA

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexIntegerRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jlong* longArr = (env)->GetLongArrayElements(value, 0);
  char* cValue = NULL;

  SIDEX_INT64 sVariant;

  iRet = sidex_Integer_Read(sHandle, (char*)cGroup, (char*)cKey, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseLongArrayElements(value, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexIntegerWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Integer_Write(sHandle, (char*)cGroup, (char*)cKey, value);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexListRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jlong* longArr = (env)->GetLongArrayElements(value, 0);
  char* cValue = NULL;

  SIDEX_VARIANT sVariant;

  iRet = sidex_List_Read(sHandle, (char*)cGroup, (char*)cKey, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseLongArrayElements(value, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexListWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_List_Write(sHandle, (char*)cGroup, (char*)cKey, value);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexNoneWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_None_Write(sHandle, (char*)cGroup, (char*)cKey);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexStringLength(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jintArray iLength){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jint* intArr = (env)->GetIntArrayElements(iLength, 0);
  char* cValue = NULL;

  SIDEX_INT32 strLength;

  iRet = sidex_String_Length(sHandle, (char*)cGroup, (char*)cKey, &strLength);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = strLength;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseIntArrayElements(iLength, intArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexStringRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jobject sValue, jintArray iLength){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jint* intArr = (env)->GetIntArrayElements(iLength, 0);
  char* cValue = NULL;

  SIDEX_INT32 strLength;

  iRet = sidex_String_Read(sHandle, (char*)cGroup, (char*)cKey, &cValue, &strLength);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (sValue);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cValue);
      env->CallObjectMethod (sValue, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
    sidex_Free_ReadString(cValue);
    intArr[0] = strLength;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseIntArrayElements(iLength, intArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexStringWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jstring sValue){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  const char *cValue = (env)->GetStringUTFChars(sValue, 0);
  iRet = sidex_String_Write(sHandle, (char*)cGroup, (char*)cKey, cValue);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseStringUTFChars(sValue, cValue);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexTableRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jlong* longArr = (env)->GetLongArrayElements(value, 0);
  char* cValue = NULL;

  SIDEX_VARIANT sVariant;

  iRet = sidex_Table_Read(sHandle, (char*)cGroup, (char*)cKey, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseLongArrayElements(value, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexTableWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Table_Write(sHandle, (char*)cGroup, (char*)cKey, value);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray variant){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  jlong* longArr = (env)->GetLongArrayElements(variant, 0);
  char* cValue = NULL;

  SIDEX_VARIANT sVariant;

  iRet = sidex_Variant_Read(sHandle, (char*)cGroup, (char*)cKey, &sVariant);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sVariant;
  }
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseLongArrayElements(variant, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong variant){
  SIDEX_INT32 iRet = SIDEX_SUCCESS;
  const char *cGroup = (env)->GetStringUTFChars(sGroup, 0);
  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Variant_Write(sHandle, (char*)cGroup, (char*)cKey, variant);
  env->ReleaseStringUTFChars(sGroup, cGroup);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint)iRet;
}

////////////////////////////////////////////////
// SIDEX datatypes / General variant functions
JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantCopy(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_VARIANT iCopy;
  iCopy = sidex_Variant_Copy(variant);
  return (jlong)iCopy;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDecRef(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_INT32 iRet;
  iRet = sidex_Variant_DecRef(variant);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantGetType(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_DATA_TYPE iType;
  iType = sidex_Variant_GetType(variant);
  return (jint)iType;
}

JNIEXPORT void Java_com_tmlsidex_jni_Sidex_NativeSidexVariantIncRef(JNIEnv* env, jobject javaThis, jlong variant){
  sidex_Variant_IncRef(variant);
  return;
}

////////////////////////////////////////////////
// Simple data types / Integer
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsInteger(JNIEnv* env, jobject javaThis, jlong variant, jlongArray value){
  SIDEX_INT32 iRet;
  SIDEX_INT64 sValue;

  jlong* longArr = (env)->GetLongArrayElements(value, 0);

  iRet = sidex_Variant_As_Integer(variant, &sValue);
  if (SIDEX_SUCCESS == iRet){
    longArr[0] = sValue;
  }

  env->ReleaseLongArrayElements(value, longArr, 0);
  return (jint)iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantIntegerCheck(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_Integer_Check(variant);
  return (jboolean) bVal;
}

JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewInteger(JNIEnv* env, jobject javaThis, jlong value){
  SIDEX_VARIANT variant;
  variant = sidex_Variant_New_Integer(value);
  return (jlong)variant;
}

////////////////////////////////////////////////
// Simple data types / None
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNoneCheck(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_None_Check(variant);
  return (jboolean) bVal;
}

JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewNone(JNIEnv* env, jobject javaThis){
  SIDEX_VARIANT variant;
  variant = sidex_Variant_New_None();
  return (jlong)variant;
}

////////////////////////////////////////////////
// Simple data types / Boolean
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsBoolean(JNIEnv* env, jobject javaThis, jlong variant, jbooleanArray value){
  SIDEX_INT32 iRet;
  SIDEX_BOOL sValue;

  jboolean* boolArr = (env)->GetBooleanArrayElements(value, 0);

  iRet = sidex_Variant_As_Boolean(variant, &sValue);
  if (SIDEX_SUCCESS == iRet){
    boolArr[0] = (SIDEX_TRUE == sValue);
  }

  env->ReleaseBooleanArrayElements(value, boolArr, 0);
  return (jint)iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantBooleanCheck(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_Boolean_Check(variant);
  return (jboolean) bVal;
}

JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewBoolean(JNIEnv* env, jobject javaThis, jboolean value){
  SIDEX_VARIANT variant;
  SIDEX_BOOL bValue;
  (value == JNI_TRUE)? bValue = SIDEX_TRUE: bValue = SIDEX_FALSE;
  variant = sidex_Variant_New_Boolean(bValue);
  return (jlong)variant;
}

////////////////////////////////////////////////
// Simple data types / Float
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsFloat(JNIEnv* env, jobject javaThis, jlong variant, jdoubleArray value){
  SIDEX_INT32 iRet;
  SIDEX_DOUBLE sValue;

  jdouble* doubleArr = (env)->GetDoubleArrayElements(value, 0);

  iRet = sidex_Variant_As_Float(variant, &sValue);
  if (SIDEX_SUCCESS == iRet){
    doubleArr[0] = sValue;
  }

  env->ReleaseDoubleArrayElements(value, doubleArr, 0);
  return (jint)iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantFloatCheck(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_Float_Check(variant);
  return (jboolean) bVal;
}

JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewFloat(JNIEnv* env, jobject javaThis, jdouble value){
  SIDEX_VARIANT variant;
  variant = sidex_Variant_New_Float(value);
  return (jlong)variant;
}

////////////////////////////////////////////////
// Simple data types / String
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsString(JNIEnv* env, jobject javaThis, jlong variant, jobject value, jintArray length){
  SIDEX_INT32 iRet;
  char* cValue = NULL;

  jint* intArr = (env)->GetIntArrayElements(length, 0);
  SIDEX_INT32 strLength;

  iRet = sidex_Variant_As_String(variant, &cValue, &strLength);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (value);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cValue);
      env->CallObjectMethod (value, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
    intArr[0] = strLength;
  }
  env->ReleaseIntArrayElements(length, intArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsStringLength(JNIEnv* env, jobject javaThis, jlong variant, jintArray length){
  SIDEX_INT32 iRet;

  jint* intArr = (env)->GetIntArrayElements(length, 0);

  SIDEX_INT32 strLength;

  iRet = sidex_Variant_As_String_Length(variant, &strLength);
  intArr[0] = strLength;
  env->ReleaseIntArrayElements(length, intArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewString(JNIEnv* env, jobject javaThis, jstring value, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT sVariant;
  const char *cValue = (env)->GetStringUTFChars(value, 0);
  jlong* longArr = (env)->GetLongArrayElements(variant, 0);

  iRet = sidex_Variant_New_String((char*)cValue, &sVariant);
  longArr[0] = sVariant;

  env->ReleaseLongArrayElements(variant, longArr, 0);
  env->ReleaseStringUTFChars(value, cValue);
  return (jint)iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantStringCheck(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_String_Check(variant);
  return (jboolean) bVal;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantStringGetFormat(JNIEnv* env, jobject javaThis, jlong variant, jobject value){
  SIDEX_INT32 iRet;
  const char * cValue = NULL;

  iRet = sidex_Variant_String_GetFormat(variant, &cValue);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (value);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cValue);
      env->CallObjectMethod (value, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
  }
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantStringSetFormat(JNIEnv* env, jobject javaThis, jlong variant, jstring value){
  SIDEX_INT32 iRet;
  const char *cFormat = (env)->GetStringUTFChars(value, 0);

  iRet = sidex_Variant_String_SetFormat(variant, cFormat);

  env->ReleaseStringUTFChars(value, cFormat);
  return (jint)iRet;
}

////////////////////////////////////////////////
// Simple data types / Binary
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsBinary(JNIEnv* env, jobject javaThis, jlong variant, jobject value, jintArray length){
  SIDEX_INT32 iRet;
  unsigned char* cValue = NULL;

  jint* intArr = (env)->GetIntArrayElements(length, 0);
  SIDEX_INT32 strLength;
  unsigned char* binVar;

  iRet = sidex_Variant_As_Binary(variant, &binVar, &strLength);
  if (SIDEX_SUCCESS == iRet){
    jint capacity = (jint)(env)->GetDirectBufferCapacity(value);
    unsigned char* dBuf = (unsigned char*) (env)->GetDirectBufferAddress(value);
    if (capacity >= strLength){
      memcpy (dBuf, binVar, strLength);
      intArr[0] = strLength;
    }
    else{
      memcpy (dBuf, binVar, capacity);
      intArr[0] = capacity;
    }
  }
  env->ReleaseIntArrayElements(length, intArr, 0);
  return (jint)iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsBinaryLength(JNIEnv* env, jobject javaThis, jlong variant, jintArray length){
  SIDEX_INT32 iRet;

  jint* intArr = (env)->GetIntArrayElements(length, 0);

  SIDEX_INT32 strLength;

  iRet = sidex_Variant_As_Binary_Length(variant, &strLength);
  if (SIDEX_SUCCESS == iRet){
    intArr[0] = strLength;
  }
  env->ReleaseIntArrayElements(length, intArr, 0);
  return (jint)iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantBinaryCheck(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_Binary_Check(variant);
  return (jboolean) bVal;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewBinary(JNIEnv* env, jobject javaThis, jbyteArray value, jint length, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT sVariant;
  jbyte* bufferPtr = (env)->GetByteArrayElements(value, NULL);
  jlong* longArr = (env)->GetLongArrayElements(variant, 0);

  iRet = sidex_Variant_New_Binary((unsigned char*)bufferPtr, length, &sVariant);
  longArr[0] = sVariant;

  env->ReleaseLongArrayElements(variant, longArr, 0);
  env->ReleaseByteArrayElements(value, bufferPtr, 0);
  return (jint)iRet;
}

////////////////////////////////////////////////
// Simple data types / DateTime
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsDateTime(JNIEnv* env, jobject javaThis, jlong variant, jobject value){
  SIDEX_INT32 iRet;
  char* cValue = NULL;

  iRet = sidex_Variant_As_DateTime(variant, &cValue);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (value);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cValue);
      env->CallObjectMethod (value, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
  }
  return (jint)iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDateTimeCheck(JNIEnv* env, jobject javaThis, jlong variant){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_DateTime_Check(variant);
  return (jboolean) bVal;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewDateTime(JNIEnv* env, jobject javaThis, jstring value, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT sVariant;
  const char *cValue = (env)->GetStringUTFChars(value, 0);
  jlong* longArr = (env)->GetLongArrayElements(variant, 0);

  iRet = sidex_Variant_New_DateTime((char*)cValue, &sVariant);
  longArr[0] = sVariant;

  env->ReleaseLongArrayElements(variant, longArr, 0);
  env->ReleaseStringUTFChars(value, cValue);
  return (jint)iRet;
}

////////////////////////////////////////////////
// Comntainer data types / List
JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListAppend(JNIEnv* env, jobject javaThis, jlong sList, jlong value, jintArray pos){
  SIDEX_INT32 iRet;
  SIDEX_INT32 iPos;

  jint* cPos = (env)->GetIntArrayElements(pos, 0);

  iRet = sidex_Variant_List_Append(sList, value, &iPos);
  if (SIDEX_SUCCESS == iRet){
    cPos[0] = iPos;
  }

  env->ReleaseIntArrayElements(pos, cPos, 0);
  return (jint) iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListCheck(JNIEnv* env, jobject javaThis, jlong sList){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_List_Check(sList);
  return (jboolean) bVal;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListClear(JNIEnv* env, jobject javaThis, jlong sList){
  SIDEX_INT32 iRet;
  iRet = sidex_Variant_List_Clear(sList);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListDeleteItem(JNIEnv* env, jobject javaThis, jlong sList, jint pos){
  SIDEX_INT32 iRet;
  iRet = sidex_Variant_List_DeleteItem(sList, pos);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListGet(JNIEnv* env, jobject javaThis, jlong sList, jint index, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT retVariant;

  jlong* cVariant = (env)->GetLongArrayElements(variant, 0);

  iRet = sidex_Variant_List_Get(sList, index, &retVariant);
  if (SIDEX_SUCCESS == iRet){
    cVariant[0] = retVariant;
  }
  env->ReleaseLongArrayElements(variant, cVariant, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListInsert(JNIEnv* env, jobject javaThis, jlong sList, jlong value, jint pos){
  SIDEX_INT32 iRet;

  iRet = sidex_Variant_List_Insert(sList, value, pos);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListSet(JNIEnv* env, jobject javaThis, jlong sList, jlong value, jint pos){
  SIDEX_INT32 iRet;

  iRet = sidex_Variant_List_Set(sList, value, pos);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListSize(JNIEnv* env, jobject javaThis, jlong sList, jintArray size){
  SIDEX_INT32 iRet;
  SIDEX_INT32 iSize;

  jint* cSize = (env)->GetIntArrayElements(size, 0);

  iRet = sidex_Variant_List_Size(sList, &iSize);
  if (SIDEX_SUCCESS == iRet){
    cSize[0] = iSize;
  }

  env->ReleaseIntArrayElements(size, cSize, 0);
  return (jint) iRet;
}

JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewList(JNIEnv* env, jobject javaThis){
  SIDEX_VARIANT variant;
  variant = sidex_Variant_New_List();
  return (jlong)variant;
}

////////////////////////////////////////////////
// Comntainer data types / Dict
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictCheck(JNIEnv* env, jobject javaThis, jlong sDict){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_Dict_Check(sDict);
  return (jboolean) bVal;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictClear(JNIEnv* env, jobject javaThis, jlong sDict){
  SIDEX_INT32 iRet;
  iRet = sidex_Variant_Dict_Clear(sDict);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictDelete(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey){
  SIDEX_INT32 iRet;

  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Variant_Dict_Delete(sDict, (char*)cKey);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictFirst(JNIEnv* env, jobject javaThis, jlong sDict){
  SIDEX_INT32 iRet;
  iRet = sidex_Variant_Dict_First(sDict);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictGet(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT retVariant;

  jlong* cVariant = (env)->GetLongArrayElements(variant, 0);

  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Variant_Dict_Get(sDict,  (char*)cKey, &retVariant);
  if (SIDEX_SUCCESS == iRet){
    cVariant[0] = retVariant;
  }
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseLongArrayElements(variant, cVariant, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictHasKey(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey, jbooleanArray bRet){
  SIDEX_INT32 iRet;
  SIDEX_BOOL  retValue;

  jboolean* cRet = (env)->GetBooleanArrayElements(bRet, 0);

  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Variant_Dict_HasKey(sDict,  (char*)cKey, &retValue);
  if (SIDEX_SUCCESS == iRet){
    cRet[0] = (SIDEX_TRUE == retValue);
  }
  env->ReleaseStringUTFChars(sKey, cKey);
  env->ReleaseBooleanArrayElements(bRet, cRet, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictKeys(JNIEnv* env, jobject javaThis, jlong sDict, jlongArray keys){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT retKeys;

  jlong* cKeys = (env)->GetLongArrayElements(keys, 0);

  iRet = sidex_Variant_Dict_Keys(sDict, &retKeys);
  if (SIDEX_SUCCESS == iRet){
    cKeys[0] = retKeys;
  }
  env->ReleaseLongArrayElements(keys, cKeys, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictNext(JNIEnv* env, jobject javaThis, jlong sDict, jobject sKey, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT retVariant;
  char* cValue = NULL;

  jlong* cVariant = (env)->GetLongArrayElements(variant, 0);

  iRet = sidex_Variant_Dict_Next(sDict, &cValue, &retVariant);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (sKey);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cValue);
      env->CallObjectMethod (sKey, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
    cVariant[0] = retVariant;
  }
  env->ReleaseLongArrayElements(variant, cVariant, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictSet(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey, jlong value){
  SIDEX_INT32 iRet;

  const char *cKey = (env)->GetStringUTFChars(sKey, 0);
  iRet = sidex_Variant_Dict_Set(sDict,  (char*)cKey, value);
  env->ReleaseStringUTFChars(sKey, cKey);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictSize(JNIEnv* env, jobject javaThis, jlong sDict, jintArray size){
  SIDEX_INT32 iRet;
  SIDEX_INT32 iSize;

  jint* cSize = (env)->GetIntArrayElements(size, 0);

  iRet = sidex_Variant_Dict_Size(sDict, &iSize);
  if (SIDEX_SUCCESS == iRet){
    cSize[0] = iSize;
  }

  env->ReleaseIntArrayElements(size, cSize, 0);
  return (jint) iRet;
}

JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewDict(JNIEnv* env, jobject javaThis){
  SIDEX_VARIANT variant;
  variant = sidex_Variant_New_Dict();
  return (jlong)variant;
}

JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewDictBySize(JNIEnv* env, jobject javaThis, jint iSize){
  SIDEX_VARIANT variant;
  variant = sidex_Variant_New_DictBySize(iSize);
  return (jlong)variant;
}

////////////////////////////////////////////////
// Comntainer data types / Table
JNIEXPORT jlong Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewTable(JNIEnv* env, jobject javaThis){
  SIDEX_VARIANT variant;
  variant = sidex_Variant_New_Table();
  return (jlong)variant;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableAddColumn(JNIEnv* env, jobject javaThis, jlong sTable, jstring sColumn){
  SIDEX_INT32 iRet;

  const char *cColumn = (env)->GetStringUTFChars(sColumn, 0);
  iRet = sidex_Variant_Table_AddColumn(sTable,  (char*)cColumn);
  env->ReleaseStringUTFChars(sColumn, cColumn);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableAddRow(JNIEnv* env, jobject javaThis, jlong sTable, jintArray iRowIdx){
  SIDEX_INT32 iRet;

  SIDEX_INT32 iRow;

  jint* cRow = (env)->GetIntArrayElements(iRowIdx, 0);

  iRet = sidex_Variant_Table_AddRow(sTable,  &iRow);
  if (SIDEX_SUCCESS == iRet){
    cRow[0] = iRow;
  }

  env->ReleaseIntArrayElements(iRowIdx, cRow, 0);
  return (jint) iRet;
}

JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableCheck(JNIEnv* env, jobject javaThis, jlong sTable){
  SIDEX_BOOL bVal;
  bVal = sidex_Variant_Table_Check(sTable);
  return (jboolean) bVal;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableColumnNames(JNIEnv* env, jobject javaThis, jlong sTable, jlongArray varNames){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT retNames;

  jlong* cNames = (env)->GetLongArrayElements(varNames, 0);

  iRet = sidex_Variant_Table_ColumnNames(sTable, &retNames);
  if (SIDEX_SUCCESS == iRet){
    cNames[0] = retNames;
  }
  env->ReleaseLongArrayElements(varNames, cNames, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableColumns(JNIEnv* env, jobject javaThis, jlong sTable, jintArray iColumns){
  SIDEX_INT32 iRet;
  SIDEX_INT32 retColumns;

  jint* cColumns = (env)->GetIntArrayElements(iColumns, 0);

  iRet = sidex_Variant_Table_Columns(sTable, &retColumns);
  if (SIDEX_SUCCESS == iRet){
    cColumns[0] = retColumns;
  }
  env->ReleaseIntArrayElements(iColumns, cColumns, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableDeleteColumn(JNIEnv* env, jobject javaThis, jlong sTable, jstring sColumn){
  SIDEX_INT32 iRet;

  const char *cColumn = (env)->GetStringUTFChars(sColumn, 0);
  iRet = sidex_Variant_Table_DeleteColumn(sTable,  (char*)cColumn);
  env->ReleaseStringUTFChars(sColumn, cColumn);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableDeleteRow(JNIEnv* env, jobject javaThis, jlong sTable, jint iRowIdx){
  SIDEX_INT32 iRet;

  iRet = sidex_Variant_Table_DeleteRow(sTable, iRowIdx);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableDeleteRows(JNIEnv* env, jobject javaThis, jlong sTable){
  SIDEX_INT32 iRet;

  iRet = sidex_Variant_Table_DeleteRows(sTable);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableGetColumnName(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jobject sColumn){
  SIDEX_INT32 iRet;
  char* cColumn = NULL;

  iRet = sidex_Variant_Table_GetColumnName(sTable, index, &cColumn);
  if (SIDEX_SUCCESS == iRet){
    jclass clazz = env->GetObjectClass (sColumn);
    jmethodID mid = env->GetMethodID (clazz, "append", "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
    if (mid != 0){
      jstring _jstring = env->NewStringUTF ((const char *) cColumn);
      env->CallObjectMethod (sColumn, mid, _jstring);
      env->DeleteLocalRef(_jstring);
    }
  }
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableGetField(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jstring sColumnName, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT retVariant;
  char* cColumn = NULL;

  const char *cColumnName = (env)->GetStringUTFChars(sColumnName, 0);
  jlong* cVariant = (env)->GetLongArrayElements(variant, 0);
  iRet = sidex_Variant_Table_GetField(sTable, index, (char*)cColumnName, &retVariant);
  if (SIDEX_SUCCESS == iRet){
    cVariant[0] = retVariant;
  }
  env->ReleaseStringUTFChars(sColumnName, cColumn);
  env->ReleaseLongArrayElements(variant, cVariant, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableGetRow(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jlongArray variant){
  SIDEX_INT32 iRet;
  SIDEX_VARIANT retVariant;
  char* cColumn = NULL;

  jlong* cVariant = (env)->GetLongArrayElements(variant, 0);
  iRet = sidex_Variant_Table_GetRow(sTable, index, &retVariant);
  if (SIDEX_SUCCESS == iRet){
    cVariant[0] = retVariant;
  }
  env->ReleaseLongArrayElements(variant, cVariant, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableHasColumn(JNIEnv* env, jobject javaThis, jlong sTable, jstring sColumnName, jbooleanArray bRet){
  SIDEX_INT32 iRet;
  SIDEX_BOOL  retValue;

  jboolean* cRet = (env)->GetBooleanArrayElements(bRet, 0);

  const char *cColumnName = (env)->GetStringUTFChars(sColumnName, 0);
  iRet = sidex_Variant_Table_HasColumn(sTable,  (char*)cColumnName, &retValue);
  if (SIDEX_SUCCESS == iRet){
    cRet[0] = (SIDEX_TRUE == retValue);
  }
  env->ReleaseStringUTFChars(sColumnName, cColumnName);
  env->ReleaseBooleanArrayElements(bRet, cRet, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableRows(JNIEnv* env, jobject javaThis, jlong sTable, jintArray iRows){
  SIDEX_INT32 iRet;
  SIDEX_INT32 retRows;

  jint* cRows = (env)->GetIntArrayElements(iRows, 0);

  iRet = sidex_Variant_Table_Rows(sTable, &retRows);
  if (SIDEX_SUCCESS == iRet){
    cRows[0] = retRows;
  }
  env->ReleaseIntArrayElements(iRows, cRows, 0);
  return (jint) iRet;
}

JNIEXPORT jint Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableSetField(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jstring sColumnName, jlong variant){
  SIDEX_INT32 iRet;

  const char *cColumnName = (env)->GetStringUTFChars(sColumnName, 0);
  iRet = sidex_Variant_Table_SetField(sTable, index, (char*)cColumnName, variant);
  env->ReleaseStringUTFChars(sColumnName, cColumnName);
  return (jint) iRet;
}






