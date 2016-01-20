#  libTML:  A BEEP based Messaging Suite
#  Copyright (C) 2015 wobe-systems GmbH
#
#  This program is free software; you can redistribute it and/or
#  modify it under the terms of the GNU Lesser General Public License
#  as published by the Free Software Foundation; either version 2.1
#  of the License, or (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
#  Lesser General Public License for more details.
#
#  You should have received a copy of the GNU Lesser General Public
#  License along with this program; if not, write to the Free
#  Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
#  02111-1307 USA
#  
#  You may find a copy of the license under this software is released
#  at COPYING file. This is LGPL software: you are welcome to develop
#  proprietary applications using this library without any royalty or
#  fee but returning back any change, improvement or addition in the
#  form of source code, project image, documentation patches, etc.
#
#  Homepage:
#    http://www.libtml.org
#
#  For professional support contact us:
#
#    wobe-systems GmbH
#    support@libtml.org
#
# Contributors:
#    wobe-systems GmbH


#######################################################
## Source & Header
set  (TML_SOURCES_HEADER jniTml.h)
                    
set (TML_SOURCES  jniTml.cpp)
                   

source_group("Headerfiles" FILES ${TML_SOURCES_HEADER})
source_group("Sourcefiles" FILES ${TML_SOURCES})

#######################################################
## Windows 32 bit / MinGW build
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-32_mingw)
  set(SIDEXLIB sidex11.lib)
  set(TMLLIB tmlcore11.lib)
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DMINGW_BUILD)
  set(TARGET jniTml)
  ADD_DEFINITIONS ("-m32")
  set(CMAKE_CXX_FLAGS "-m32")
  
  set (INST_LIB_PATH ./lib)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-32_mingw)

#######################################################
## Windows 64 bit / MinGW build
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-64_mingw)
  set(SIDEXLIB sidex11_64.lib)
  set(TMLLIB tmlcore11_64.lib)
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DMINGW_BUILD)
  set(TARGET jniTml)
  ADD_DEFINITIONS ("-m64")
  set(CMAKE_CXX_FLAGS "-m64")
  
  set (INST_LIB_PATH ./lib)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-64_mingw)

#######################################################
## Windows 32 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-32)
  set(SIDEXLIB sidex11.lib)
  set(TMLLIB tmlcore11.lib)
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH ./lib)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-32)

#######################################################
## Windows 64 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-64)
  set(SIDEXLIB sidex11_64.lib)
  set(TMLLIB tmlcore11_64.lib)
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH ./lib)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-64)

#######################################################
## LINUX 32 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL linux_x86-32)
  set(SIDEXLIB libsidex11.so)
  set(TMLLIB libtmlcore11.so)

  ADD_DEFINITIONS ("-std=c++0x")
  ADD_DEFINITIONS ("-m32")
  set(CMAKE_CXX_FLAGS "-m32")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DGCC4)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH /usr/lib)
endif (${TML_TARGET_PLATFORM} STREQUAL linux_x86-32)

#######################################################
## LINUX 64 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL linux_x86-64)
  set(SIDEXLIB libsidex11_64.so)
  set(TMLLIB libtmlcore11_64.so)

  ADD_DEFINITIONS ("-std=c++0x")
  ADD_DEFINITIONS ("-m64")
  set(CMAKE_CXX_FLAGS "-m64")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DGCC4)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH /usr/lib)
endif (${TML_TARGET_PLATFORM} STREQUAL linux_x86-64)

#######################################################
## FreeBSD / 32 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-32)
  set(SIDEXLIB libsidex11.so)
  set(TMLLIB libtmlcore11.so)

  ADD_DEFINITIONS ("-std=c++0x")
  ADD_DEFINITIONS ("-m32")
  set(CMAKE_CXX_FLAGS "-m32")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DGCC4)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH ./lib)
endif (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-32)

#######################################################
## FreeBSD / 64 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-64)
  set(SIDEXLIB libsidex11_64.so)
  set(TMLLIB libtmlcore11_64.so)

  ADD_DEFINITIONS ("-std=c++0x")
  ADD_DEFINITIONS ("-m64")
  set(CMAKE_CXX_FLAGS "-m64")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DGCC4)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH ./lib)
endif (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-64)

#######################################################
## OSX 32 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL osx_x86-32)
  set(SIDEXLIB libsidex11.dylib)
  set(TMLLIB libtmlcore11.dylib)

  ADD_DEFINITIONS ("-std=c++11")
  ADD_DEFINITIONS ("-arch i386")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DOS_X)
  set(CMAKE_CXX_FLAGS "-arch i386")
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH /usr/local/lib)
endif (${TML_TARGET_PLATFORM} STREQUAL osx_x86-32)

#######################################################
## OSX 64 bit
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL osx_x86-64)
  set(SIDEXLIB libsidex11_64.dylib)
  set(TMLLIB libtmlcore11_64.dylib)

  ADD_DEFINITIONS ("-std=c++11")
  ADD_DEFINITIONS ("-arch x86_64")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DOS_X)
  set(CMAKE_CXX_FLAGS "-arch x86_64")
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH /usr/local/lib)
endif (${TML_TARGET_PLATFORM} STREQUAL osx_x86-64)

#######################################################
## ANDROID ARM32
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL android_arm32)
  set(SIDEXLIB sidex11.so)
  set(TMLLIB tmlcore11.so)
  ADD_DEFINITIONS ("-std=c++11")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DGCC4 -DANDROID)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH /usr/local/lib)
endif (${TML_TARGET_PLATFORM} STREQUAL android_arm32)

#######################################################
## LINUX ARM32
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL linux_arm32)
  set(SIDEXLIB libsidex11.so)
  set(TMLLIB libtmlcore11.so)

  ADD_DEFINITIONS ("-std=c++0x")
  set(PREPROCESSOR_DEF -DJNITML_EXPORTS -DLINUX -DGCC4)
  
  set(TARGET jniTml)
  
  set (INST_LIB_PATH /usr/lib)
endif (${TML_TARGET_PLATFORM} STREQUAL linux_arm32)
                    
#######################################################
## Concatenate with version
#######################################################
  set(TMLLIBNAME_LOCAL  ${TARGET}${TML_API_VERSION})
  set(SIDEXLIBNAME ${TMLLIBNAME_LOCAL} PARENT_SCOPE)

#######################################################
## The target is a shared library 
ADD_LIBRARY( ${TMLLIBNAME_LOCAL} SHARED ${TML_SOURCES_HEADER} ${TML_SOURCES})

#######################################################
## On MinGW build target prefix don't have to be lib:
#######################################################
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-32_mingw)
  set_target_properties(${TMLLIBNAME_LOCAL} PROPERTIES PREFIX "")
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-32_mingw)
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-64_mingw)
  set_target_properties(${TMLLIBNAME_LOCAL} PROPERTIES PREFIX "")
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-64_mingw)

#######################################################
## Include- directories:
if (SIDEX_INCLUDE_DIR)
  INCLUDE_DIRECTORIES (${SIDEX_INCLUDE_DIR})
endif()
if (TMLCORE_INCLUDE_DIR)
  INCLUDE_DIRECTORIES (${TMLCORE_INCLUDE_DIR})
endif()
INCLUDE_DIRECTORIES (${JNIINCLUDE})
INCLUDE_DIRECTORIES (${JNIINCLUDE_PLATFORM})
INCLUDE_DIRECTORIES (${TMLINCLUDE})
INCLUDE_DIRECTORIES ( . )

#######################################################
## Preprocessor- definitions:
ADD_DEFINITIONS( ${PREPROCESSOR_DEF})
              
#######################################################
## SIDEX library 
TARGET_LINK_LIBRARIES(${TMLLIBNAME_LOCAL} ${SIDEXLIB} )    
TARGET_LINK_LIBRARIES(${TMLLIBNAME_LOCAL} ${TMLLIB} )    

#######################################################
## Install
install (TARGETS ${TMLLIBNAME_LOCAL} DESTINATION ${INST_LIB_PATH})
