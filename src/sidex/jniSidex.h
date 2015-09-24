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
 
/* Header for class jniSidex */

#include <jni.h>

#ifndef _Included_jniSidexJNI
#define _Included_jniSidexJNI
#ifdef __cplusplus
extern "C" {
#endif

//////////////////////
// General:
JNIEXPORT void    Java_com_tmlsidex_jni_Sidex_NativeSidexGetCopyright(JNIEnv* env, jobject javaThis, jobject sValue, jintArray iLength);
JNIEXPORT void    Java_com_tmlsidex_jni_Sidex_NativeSidexGetVersion(JNIEnv* env, jobject javaThis, jintArray apiVer, jintArray libVer, jobject verStr);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexSetPassword(JNIEnv* env, jobject javaThis, jstring pUserName, jstring pPassWord);

///////////////////////
// SIDEX document:
JNIEXPORT void    Java_com_tmlsidex_jni_Sidex_NativeSidexClear(JNIEnv* env, jobject javaThis, jlong sHandle);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexCreate(JNIEnv* env, jobject javaThis, jstring docname, jlongArray jHandle);
JNIEXPORT void    Java_com_tmlsidex_jni_Sidex_NativeSidexFree(JNIEnv* env, jobject javaThis, jlong sHandle);
// sidex_Free_Content is not necessary on JAVA
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexGetContent(JNIEnv* env, jobject javaThis, jlong sHandle, jobject sContent, jintArray iLength);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexGetContentLength(JNIEnv* env, jobject javaThis, jlong sHandle, jintArray iLength);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexGetDocumentName(JNIEnv* env, jobject javaThis, jlong sHandle, jobject sName);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexLoadContent(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sPath);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexMerge(JNIEnv* env, jobject javaThis, jlong sBaseHandle, jlong sMergeHandle, jboolean bOverwrite, jstring sGroup, jstring sKey);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexSaveContent(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sPath);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexSetContent(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sContent);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexSetDocumentName(JNIEnv* env, jobject javaThis, jlong sHandle, jstring  sName);

////////////////////////////////////////////////
// SIDEX document / Group/Key management
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexDeleteGroup(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexDeleteKey(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexGetGroups(JNIEnv* env, jobject javaThis, jlong sHandle, jlongArray variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexGetKeys(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jlongArray variant);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexHasGroup(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexHasKey(JNIEnv* env, jobject javaThis, jlong shandle, jstring sGroup, jstring sKey);

////////////////////////////////////////////////
// SIDEX document / Read/Write values
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexBinaryLength(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jintArray iLength);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexBinaryRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jobject sValue, jintArray iLength);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexBinaryWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jbyteArray value, jint iSize);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexBooleanRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jbooleanArray value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexBooleanWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jboolean value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexDateTimeRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexDateTimeWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexDictRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexDictWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexFloatRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jdoubleArray value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexFloatWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jdouble value);
// sidex_Free_Binary_ReadString is not necessary on JAVA
// sidex_Free_ReadString is not necessary on JAVA
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexIntegerRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexIntegerWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexListRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexListWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexNoneWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexStringLength(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jintArray iLength);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexStringRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jobject sValue, jintArray iLength);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexStringWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jstring sValue);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexTableRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexTableWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong value);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantRead(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlongArray variant);
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantWrite(JNIEnv* env, jobject javaThis, jlong sHandle, jstring sGroup, jstring sKey, jlong variant);

////////////////////////////////////////////////
// SIDEX datatypes / General variant functions
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantCopy(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDecRef(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantGetType(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT void     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantIncRef(JNIEnv* env, jobject javaThis, jlong variant);

////////////////////////////////////////////////
// Simple data types / Integer
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsInteger(JNIEnv* env, jobject javaThis, jlong variant, jlongArray value);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantIntegerCheck(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewInteger(JNIEnv* env, jobject javaThis, jlong value);

////////////////////////////////////////////////
// Simple data types / None
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNoneCheck(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewNone(JNIEnv* env, jobject javaThis);

////////////////////////////////////////////////
// Simple data types / Boolean
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsBoolean(JNIEnv* env, jobject javaThis, jlong variant, jbooleanArray value);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantBooleanCheck(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewBoolean(JNIEnv* env, jobject javaThis, jboolean value);

////////////////////////////////////////////////
// Simple data types / Float
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsFloat(JNIEnv* env, jobject javaThis, jlong variant, jdoubleArray value);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantFloatCheck(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewFloat(JNIEnv* env, jobject javaThis, jdouble value);

////////////////////////////////////////////////
// Simple data types / String
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsString(JNIEnv* env, jobject javaThis, jlong variant, jobject value, jintArray length);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsStringLength(JNIEnv* env, jobject javaThis, jlong variant, jintArray length);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewString(JNIEnv* env, jobject javaThis, jstring value, jlongArray variant);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantStringCheck(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantStringGetFormat(JNIEnv* env, jobject javaThis, jlong variant, jobject value);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantStringSetFormat(JNIEnv* env, jobject javaThis, jlong variant, jstring value);

////////////////////////////////////////////////
// Simple data types / Binary
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsBinary(JNIEnv* env, jobject javaThis, jlong variant, jobject value, jintArray length);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsBinaryLength(JNIEnv* env, jobject javaThis, jlong variant, jintArray length);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantBinaryCheck(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewBinary(JNIEnv* env, jobject javaThis, jbyteArray value, jint length, jlongArray variant);

////////////////////////////////////////////////
// Simple data types / DateTime
JNIEXPORT jint    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantAsDateTime(JNIEnv* env, jobject javaThis, jlong variant, jobject value);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDateTimeCheck(JNIEnv* env, jobject javaThis, jlong variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewDateTime(JNIEnv* env, jobject javaThis, jstring value, jlongArray variant);

////////////////////////////////////////////////
// Comntainer data types / List
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListAppend(JNIEnv* env, jobject javaThis, jlong sList, jlong value, jintArray pos);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListCheck(JNIEnv* env, jobject javaThis, jlong sList);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListClear(JNIEnv* env, jobject javaThis, jlong sList);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListDeleteItem(JNIEnv* env, jobject javaThis, jlong sList, jint pos);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListGet(JNIEnv* env, jobject javaThis, jlong sList, jint index, jlongArray variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListInsert(JNIEnv* env, jobject javaThis, jlong sList, jlong value, jint pos);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListSet(JNIEnv* env, jobject javaThis, jlong sList, jlong value, jint pos);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantListSize(JNIEnv* env, jobject javaThis, jlong sList, jintArray size);
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewList(JNIEnv* env, jobject javaThis);

////////////////////////////////////////////////
// Comntainer data types / Dict
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictCheck(JNIEnv* env, jobject javaThis, jlong sDict);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictClear(JNIEnv* env, jobject javaThis, jlong sDict);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictDelete(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictFirst(JNIEnv* env, jobject javaThis, jlong sDict);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictGet(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey, jlongArray variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictHasKey(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey, jbooleanArray bRet);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictKeys(JNIEnv* env, jobject javaThis, jlong sDict, jlongArray keys);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictNext(JNIEnv* env, jobject javaThis, jlong sDict, jobject sKey, jlongArray variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictSet(JNIEnv* env, jobject javaThis, jlong sDict, jstring sKey, jlong value);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantDictSize(JNIEnv* env, jobject javaThis, jlong sDict, jintArray size);
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewDict(JNIEnv* env, jobject javaThis);
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewDictBySize(JNIEnv* env, jobject javaThis, jint iSize);

////////////////////////////////////////////////
// Comntainer data types / Table
JNIEXPORT jlong    Java_com_tmlsidex_jni_Sidex_NativeSidexVariantNewTable(JNIEnv* env, jobject javaThis);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableAddColumn(JNIEnv* env, jobject javaThis, jlong sTable, jstring sColumn);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableAddRow(JNIEnv* env, jobject javaThis, jlong sTable, jintArray iRowIdx);
JNIEXPORT jboolean Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableCheck(JNIEnv* env, jobject javaThis, jlong sTable);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableColumnNames(JNIEnv* env, jobject javaThis, jlong sTable, jlongArray varNames);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableColumns(JNIEnv* env, jobject javaThis, jlong sTable, jintArray iColumns);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableDeleteColumn(JNIEnv* env, jobject javaThis, jlong sTable, jstring sColumn);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableDeleteRow(JNIEnv* env, jobject javaThis, jlong sTable, jint iRowIdx);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableDeleteRows(JNIEnv* env, jobject javaThis, jlong sTable);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableGetColumnName(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jobject sColumn);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableGetField(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jstring sColumnName, jlongArray variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableGetRow(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jlongArray variant);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableHasColumn(JNIEnv* env, jobject javaThis, jlong sTable, jstring sColumnName, jbooleanArray bRet);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableRows(JNIEnv* env, jobject javaThis, jlong sTable, jintArray iRows);
JNIEXPORT jint     Java_com_tmlsidex_jni_Sidex_NativeSidexVariantTableSetField(JNIEnv* env, jobject javaThis, jlong sTable, jint index, jstring sColumnName, jlong variant);


#ifdef __cplusplus
}
#endif
#endif
