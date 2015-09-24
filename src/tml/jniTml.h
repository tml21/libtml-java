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

/* Header for class jniTml */
 
#include <jni.h>
#include <tmlCore.h>

#ifndef _Included_jniTmlJNI
#define _Included_jniTmlJNI
#ifdef __cplusplus
extern "C" {
#endif

struct tmlData{
  JNIEnv* env;
  jobject thisObj;
};

struct callbackData{
  JavaVM *jvm;
  jobject callback;
  char*   callbackName;
  jobject cbData;
};

//////////////////////
// Callback handling:
void handleCustomCallback (TML_COMMAND_ID_TYPE iCMD, TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData);
void handleOnCmdDeleteCallback (TML_COMMAND_ID_TYPE iCMD, TML_POINTER pCmdData, TML_POINTER pCBData);
void handleCmdDispatchCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData);
void handleCmdProgressCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData, TML_INT32 iProgress);
void handleStatusReplyCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData, TML_INT32 iType, TML_CTSTR *sMsg);
TML_INT32 handleRecStreamDldBlockCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBDataDld, TML_POINTER buffer, TML_INT32 bytesRead, TML_INT64 totalBytesRead, TML_INT64 streamSize);
void handleRecStreamDldFinishCallback (TML_STREAM_ID_TYPE iID, TML_INT32 errCode, TML_POINTER pCBDataDldFinish);
void handleSndStreamCloseCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData);
TML_INT64 handleSndStreamGetPositionCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData);
TML_INT64 handleSndStreamGetSizeCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData);
void handleSndStreamOnErrorCallback (TML_STREAM_ID_TYPE iID, TML_INT32 iError, TML_POINTER pCBData);
TML_INT32 handleSndStreamReadCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData, TML_POINTER buffer, TML_INT32 count, TML_INT32 *bytesRead);
TML_INT32 handleSndStreamSeekCallback (TML_STREAM_ID_TYPE iID, TML_POINTER pCBData, TML_INT64 seekPosition, tmlSeekOrigin seekOrigin);
TML_INT32 handleSndStreamWriteCallback (TML_STREAM_ID_TYPE iID,  TML_POINTER buffer, TML_INT32 count, TML_POINTER pCBData);
void handleCommandReadyCallback (TML_COMMAND_HANDLE cmdHandle, TML_POINTER pCBData);
void handleOnEvtErrorCallback (TML_CTSTR *sProfile, TML_CTSTR *sHost, TML_CTSTR *sPort, TML_COMMAND_ID_TYPE iCMD, TML_INT32 iErrorCode, TML_POINTER pCBData);
TML_BOOL handleOnPeerRegistrationCallback (TML_BOOL bSubscribe, TML_CTSTR *sHost, TML_CTSTR *sPort, TML_POINTER pCBData);
TML_INT32 handleOnPopulateCallback (TML_CTSTR *sProfile, TML_POINTER pCBData);
void handleOnEvtQueueOverflowCallback (TML_CTSTR *sProfile, TML_COMMAND_ID_TYPE iCMD, TML_POINTER pCBData);TML_INT32 handleOnBalCalculationCallback (TML_INT32 iCountOfDestinations, TML_COMMAND_HANDLE *listenerBusyStateArray, TML_POINTER pCBData, TML_INT32 *iNextListenerIndex);
TML_INT32 handleOnBalBusyStatusRequestCallback (TML_COMMAND_HANDLE tmlhandle, TML_POINTER pCBData);

/////////////////////
// helper:
char* getIDString(jlong iID);
void addRegisterData(char* sCoreHandleString, char* profile, SIDEX_VARIANT* registerDict);
SIDEX_VARIANT fetchRegisterDict(char* sCoreHandleString, char* profile, SIDEX_VARIANT registerDict);
void setRegisteredCallbackData (char* sCoreHandleString, char* profile, const char* regType, SIDEX_INT64 iVal, SIDEX_VARIANT registerDict);
void freeRegisteredCallbackData (JNIEnv* env, char* sCoreHandleString, char* profile, const char* regType, SIDEX_VARIANT registerDict);
void freeRegisteredData(JNIEnv* env, char* sCoreHandleString, char* profile, SIDEX_VARIANT registerDict);
void freeAllRegisteredData(JNIEnv* env, char* sCoreHandleString, SIDEX_VARIANT registerDict);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////
// General:
JNIEXPORT void    Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetCopyright(JNIEnv* env, jobject javaThis, jobject sValue, jintArray iLength);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetLoggingValue(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iLogValue);
JNIEXPORT void    Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetVersion(JNIEnv* env, jobject javaThis, jintArray apiVer, jintArray libVer, jobject verStr);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetLoggingValue(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iLogValue);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetPassword(JNIEnv* env, jobject javaThis, jstring pUserName, jstring pPassWord);

///////////////////////
// TMLCore:
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreClose(JNIEnv* env, jobject javaThis, jlong coreHandle);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreGeneralDeregistration(JNIEnv* env, jobject javaThis, jlong coreHandle);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreOpen(JNIEnv* env, jobject javaThis, jlongArray coreHandle, jint iLogValue);

///////////////////////////////////////////////
//TMLCore / Listener management
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetListenerEnabled(JNIEnv* env, jobject javaThis, jlong coreHandle, jbooleanArray bEnable);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetListenerIP(JNIEnv* env, jobject javaThis, jlong coreHandle, jobject sIP);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreGetListenerPort(JNIEnv* env, jobject javaThis, jlong coreHandle, jobject sPort);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetListenerEnabled(JNIEnv* env, jobject javaThis, jlong coreHandle, jboolean bEnable);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetListenerIP(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sIP);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCoreSetListenerPort(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sPort);

///////////////////////////////////////////////
//TMLCore / Profile management
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileGetRegistered(JNIEnv* env, jobject javaThis, jlong coreHandle, jlongArray profiles);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileGetRegisteredCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iSize);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileGetRegisterState(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jbooleanArray bRegistered);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileRegister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileRegisterCmd(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jint cmdID, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileSetOnCustomDispatch(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileSetOnDeleteCmd(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlProfileUnregister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile);

///////////////////////////////////////////////
//TMLCore / Sending commands
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSendAsyncMessage(JNIEnv* env, jobject javaThis,
                                                                          jlong coreHandle,  jlong cmdHandle, jstring sProfile, jstring sIP, jstring sPort, jlong timeout);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSendAsyncProgressReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint progress);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSendAsyncStatusReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint iType, jstring sStatus);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSendSyncMessage(JNIEnv* env, jobject javaThis,
                                                                         jlong coreHandle,  jlong cmdHandle, jstring sProfile, jstring sIP, jstring sPort, jlong timeout);

///////////////////////////////////////////////
//TMLCore / Event handling
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtGetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iCount);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtGetMaxQueuedEventMessages(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iMaximum);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtGetSubscribedMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jlongArray subscriptions);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSendMessage(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong cmdHandle, jstring sProfile);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSendSubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSendUnsubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iCount);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetMaxQueuedEventMessages(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iMaximum);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnError(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnPeerRegister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnPopulate(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSetOnQueueOverflow(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtSubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtUnsubscribeAllMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlEvtUnsubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort);


///////////////////////////////////////////////
//TMLCore / Load balancing
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalGetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jintArray iCount);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalGetSubscribedMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jlongArray subscriptions);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSendAsyncMessage(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong cmdHandle, jstring sProfile, jlong iTimeout);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSendSubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSendSyncMessage(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong cmdHandle, jstring sProfile, jlong iTimeout);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSendUnsubscriptionRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sSourceHost, jstring sSourcePort, jstring sDestHost, jstring sDestPort, long timeout);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSetMaxConnectionFailCount(JNIEnv* env, jobject javaThis, jlong coreHandle, jint iCount);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnBusyStatusRequest(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnCalculation(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnPeerRegister(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSetOnPopulate(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring profile, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalSubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalUnsubscribeAllMessageDestinations(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlBalUnsubscribeMessageDestination(JNIEnv* env, jobject javaThis, jlong coreHandle, jstring sProfile, jstring sHost, jstring sPort);

///////////////////////////////////////////////
//TMLCore / Stream communication
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamClose(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jboolean bRetainOpen);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamDownloadData(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jint buffersize,
                                                                            jobject callerClassDld, jstring sCBNameDld, jobject pCBDataDld,
                                                                            jobject callerClassFinish, jstring sCBNameFinish, jobject pCBDataFinish);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamGetPosition(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jlongArray rposition);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamGetSize(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jlongArray rsize);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamOpen(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jstring sProfile, jstring sIP, jstring sPort);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamRead(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject buffer, jint count, jintArray bytesRead);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamReadBuffer(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject buffer, jint count);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamSeek(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jlong seekPos, jint origin);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlRecStreamWrite(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jbyteArray buffer, jint count);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamClose(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamOpen(JNIEnv* env, jobject javaThis, jlong coreHandle, jlongArray iID, jstring sProfile, jstring sIP, jstring sPort);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterClose(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterGetPosition(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterGetSize(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterOnError(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterRead(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterSeek(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlSndStreamRegisterWrite(JNIEnv* env, jobject javaThis, jlong coreHandle, jlong iID, jobject callerClass, jstring sCBName, jobject pCBData);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////
// TML commands:
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdAcquireSidexHandle(JNIEnv* env, jobject javaThis, jlong cmdHandle, jlongArray sidexHandle);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdCreate(JNIEnv* env, jobject javaThis, jlongArray cmdHandle);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdFree(JNIEnv* env, jobject javaThis, jlong cmdHandle);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdReleaseSidexHandle(JNIEnv* env, jobject javaThis, jlong cmdHandle);

///////////////////////////////////////////////
//TML commands / Command message callbacks
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisterCommandReady(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisterProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisterStatusReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject callerClass, jstring sCBName, jobject pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisteredCommandReady(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobjectArray callerClass, jobject sCBName, jobjectArray pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisteredStatusReply(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobjectArray callerClass, jobject sCBName, jobjectArray pCBData);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdRegisteredProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobjectArray callerClass, jobject sCBName, jobjectArray pCBData);

///////////////////////////////////////////////
// TML commands / Accessing header information
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetCommand(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray cmdID);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetCreationTime(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject time);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetError(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray error);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetErrorMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject msg);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetMode(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray mode);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray progress);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetReplyMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jobject msg);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetReplyType(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray type);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderGetState(JNIEnv* env, jobject javaThis, jlong cmdHandle, jintArray state);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetCommand(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint cmdID);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetError(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint error);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetErrorMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jstring msg);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetMode(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint mode);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetProgress(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint progress);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetReplyMessage(JNIEnv* env, jobject javaThis, jlong cmdHandle, jstring msg);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetReplyType(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint type);
JNIEXPORT jint    Java_com_tmlsidex_jni_Tml_NativeTmlCmdHeaderSetState(JNIEnv* env, jobject javaThis, jlong cmdHandle, jint state);


#ifdef __cplusplus
}
#endif
#endif
