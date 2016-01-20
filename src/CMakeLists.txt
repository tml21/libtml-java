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

set(CMAKE_MACOSX_RPATH 1)
set( CMAKE_VERBOSE_MAKEFILE on )
#######################################################
## minimum required cmake version 
cmake_minimum_required(VERSION 3.1)

#######################################################
## set build parameter
#######################################################
SET (TML_TARGET_PLATFORM win_x86-64 CACHE STRING "set the build target")
#######################################################

set (TML_MAJOR_VERSION 1)
set (TML_MINOR_VERSION 2)
set (TML_MICRO_VERSION 0)
set (TML_API_VERSION 11)
include (InstallRequiredSystemLibraries)
set (CPACK_PACKAGE_VERSION_MAJOR "${TML_MAJOR_VERSION}")
set (CPACK_PACKAGE_VERSION_MINOR "${TML_MINOR_VERSION}")
set (CPACK_PACKAGE_VERSION_PATCH "${TML_MICRO_VERSION}")
set (PLATFORM_FOUND false)

set (CMAKE_PREFIX_PATH ${CMAKE_PREFIX_PATH} /usr)
set (CMAKE_PREFIX_PATH ${CMAKE_PREFIX_PATH} /usr/local)
#######################################################
## Windows 32 bit / MinGW build
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-32_mingw)
  set(PROJECT win_x86-32_mingw)

  set(JNIINCLUDE "T:\\java\\jdk1.7.0_71\\include")
  set(JNIINCLUDE_PLATFORM "T:\\java\\jdk1.7.0_71\\include\\win32")
  
  set(TMLINCLUDE "X:\\workdir\\libtml-c\\sidex\\src"
                 "X:\\workdir\\libtml-c\\common\\src"
                 "X:\\workdir\\libtml-c\\tml\\src")

  LINK_DIRECTORIES("X:\\workdir\\libtml-c\\build\\win_x86-32_mingw\\sidex"
                   "X:\\workdir\\libtml-c\\build\\win_x86-32_mingw\\tml")

  include (CPack)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .lib)

  set (PLATFORM_FOUND true)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-32_mingw)

#######################################################
## Windows 64bit / MinGW build
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-64_mingw)
  set(PROJECT win_x86-64_mingw)

  set(JNIINCLUDE "T:\\java\\jdk1.7.0_71\\include")
  set(JNIINCLUDE_PLATFORM "T:\\java\\jdk1.7.0_71\\include\\win32")
  
  set(TMLINCLUDE "X:\\workdir\\libtml-c\\sidex\\src"
                 "X:\\workdir\\libtml-c\\common\\src"
                 "X:\\workdir\\libtml-c\\tml\\src")

  LINK_DIRECTORIES("X:\\workdir\\libtml-c\\build\\win_x86-64_mingw\\sidex"
                   "X:\\workdir\\libtml-c\\build\\win_x86-64_mingw\\tml")

  include (CPack)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .lib)

  set (PLATFORM_FOUND true)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-64_mingw)

#######################################################
## Windows 32 bit
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-32)
  set(PROJECT win_x86-32)

  set(JNIINCLUDE "T:\\java\\jdk1.7.0_71\\include")
  set(JNIINCLUDE_PLATFORM "T:\\java\\jdk1.7.0_71\\include\\win32")
  
  set(TMLINCLUDE "X:\\workdir\\libtml-c\\sidex\\src"
                 "X:\\workdir\\libtml-c\\common\\src"
                 "X:\\workdir\\libtml-c\\tml\\src")

  LINK_DIRECTORIES("X:\\workdir\\libtml-c\\build\\win_x86-32\\sidex\\Release"
                   "X:\\workdir\\libtml-c\\build\\win_x86-32\\tml\\Release")
  
  include (CPack)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .lib)

  set (PLATFORM_FOUND true)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-32)

#######################################################
## Windows 64 bit
if (${TML_TARGET_PLATFORM} STREQUAL win_x86-64)
  set(PROJECT win_x86-64)
  
  set(JNIINCLUDE "T:\\java\\jdk1.7.0_71\\include")
  set(JNIINCLUDE_PLATFORM "T:\\java\\jdk1.7.0_71\\include\\win32")
  
  set(TMLINCLUDE "X:\\workdir\\libtml-c\\sidex\\src"
                 "X:\\workdir\\libtml-c\\common\\src"
                 "X:\\workdir\\libtml-c\\tml\\src")

  LINK_DIRECTORIES("X:\\workdir\\libtml-c\\build\\win_x86-64\\sidex\\Release"
                   "X:\\workdir\\libtml-c\\build\\win_x86-64\\tml\\Release")
  
  include (CPack)

  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .lib)

  set (PLATFORM_FOUND true)
endif (${TML_TARGET_PLATFORM} STREQUAL win_x86-64)

######################################################
## LINUX 32 bit
if (${TML_TARGET_PLATFORM} STREQUAL linux_x86-32)
  set(PROJECT linux_x86-32)
  
  set(JNIINCLUDE "/usr/local/include/java")
  set(JNIINCLUDE_PLATFORM "/usr/local/include/java/linux")
  
  set(TMLINCLUDE "/home/wobe/workdir/libtml-c/sidex/src"
                 "/home/wobe/workdir/libtml-c/common/src"
                 "/home/wobe/workdir/libtml-c/tml/src")

  LINK_DIRECTORIES("/home/wobe/workdir/libtml-c/build/linux_x86-32/sidex"
                   "/home/wobe/workdir/libtml-c/build/linux_x86-32/tml")
    
  set(CPACK_SET_DESTDIR ON)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .so)

  include (CPack)

  set (PLATFORM_FOUND true) 
endif (${TML_TARGET_PLATFORM} STREQUAL linux_x86-32)

######################################################
## LINUX 64 bit
if (${TML_TARGET_PLATFORM} STREQUAL linux_x86-64)
  set(PROJECT linux_x86-64)
  
  set(JNIINCLUDE "/usr/local/include/java")
  set(JNIINCLUDE_PLATFORM "/usr/local/include/java/linux")
  
  set(TMLINCLUDE "/home/wobe/workdir/libtml-c/sidex/src"
                 "/home/wobe/workdir/libtml-c/common/src"
                 "/home/wobe/workdir/libtml-c/tml/src")

  LINK_DIRECTORIES("/home/wobe/workdir/libtml-c/build/linux_x86-64/sidex"
                   "/home/wobe/workdir/libtml-c/build/linux_x86-64/tml")
  
  set(CPACK_SET_DESTDIR ON)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .so)

  include (CPack)

  set (PLATFORM_FOUND true) 
endif (${TML_TARGET_PLATFORM} STREQUAL linux_x86-64)

######################################################
## FreeBSD / 32 bit
if (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-32)
  set(PROJECT freeBSD_x86-32)
  
  set(JNIINCLUDE "/usr/local/openjdk7/include/")
  set(JNIINCLUDE_PLATFORM "/usr/local/openjdk7/include/freebsd")
  
  set(TMLINCLUDE "/root/workdir/libtml-c/sidex/src"
                 "/root/workdir/libtml-c/common/src"
                 "/root/workdir/libtml-c/tml/src")

  LINK_DIRECTORIES("/root/workdir/libtml-c/build/freeBSD_x86-32/sidex"
                   "/root/workdir/libtml-c/build/freeBSD_x86-32/tml")
    
  set(CPACK_SET_DESTDIR ON)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .so)

  include (CPack)

  set (PLATFORM_FOUND true) 
endif (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-32)

######################################################
## FreeBSD / 64 bit
if (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-64)
  set(PROJECT freeBSD_x86-64)
  
  set(JNIINCLUDE "/usr/local/openjdk7/include/")
  set(JNIINCLUDE_PLATFORM "/usr/local/openjdk7/include/freebsd")
  
  set(TMLINCLUDE "/root/workdir/libtml-c/sidex/src"
                 "/root/workdir/libtml-c/common/src"
                 "/root/workdir/libtml-c/tml/src")

  LINK_DIRECTORIES("/root/workdir/libtml-c/build/freeBSD_x86-64/sidex"
                   "/root/workdir/libtml-c/build/freeBSD_x86-64/tml")
  
  set(CPACK_SET_DESTDIR ON)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .so)

  include (CPack)

  set (PLATFORM_FOUND true) 
endif (${TML_TARGET_PLATFORM} STREQUAL freeBSD_x86-64)

#######################################################
## OSX 32 bit
if (${TML_TARGET_PLATFORM} STREQUAL osx_x86-32)
  set(PROJECT osx_x86-32)
  
  set(JNIINCLUDE "/usr/local/include/java")
  set(JNIINCLUDE_PLATFORM "/usr/local/include/java/linux")
  
  set(TMLINCLUDE "/Users/wobe/workdir/libtml-c/sidex/src"
                 "/Users/wobe/workdir/libtml-c/common/src"
                 "/Users/wobe/workdir/libtml-c/tml/src")

  LINK_DIRECTORIES("/Users/wobe/workdir/libtml-c/build/osx_x86-32/sidex"
                   "/Users/wobe/workdir/libtml-c/build/osx_x86-32/tml")

  set(CPACK_SET_DESTDIR ON)
  
  include (CPack)

  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .dylib)

  set (PLATFORM_FOUND true)
  endif (${TML_TARGET_PLATFORM} STREQUAL osx_x86-32)

#######################################################
## OSX 64 bit
if (${TML_TARGET_PLATFORM} STREQUAL osx_x86-64)
  set(PROJECT osx_x86-64)
  
  set(JNIINCLUDE "/usr/local/include/java")
  set(JNIINCLUDE_PLATFORM "/usr/local/include/java/linux")
  
  set(TMLINCLUDE "/Users/wobe/workdir/libtml-c/sidex/src"
                 "/Users/wobe/workdir/libtml-c/common/src"
                 "/Users/wobe/workdir/libtml-c/tml/src")

  LINK_DIRECTORIES("/Users/wobe/workdir/libtml-c/build/osx_x86-64/sidex"
                   "/Users/wobe/workdir/libtml-c/build/osx_x86-64/tml")
  
  set(CPACK_SET_DESTDIR ON)
  
  include (CPack)

  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .dylib)

  set (PLATFORM_FOUND true)
endif (${TML_TARGET_PLATFORM} STREQUAL osx_x86-64)

#######################################################
## ANDROID ARM32
if (${TML_TARGET_PLATFORM} STREQUAL android_arm32)
  set(PROJECT android_arm32)

  set(JNIINCLUDE "T:\\android-ndk-r10c\\platforms\\android-12\\arch-arm\\usr\\include")
  set(JNIINCLUDE_PLATFORM "")
  
  set(TMLINCLUDE "X:\\workdir\\libtml-c\\sidex\\src"
                 "X:\\workdir\\libtml-c\\common\\src"
                 "X:\\workdir\\libtml-c\\tml\\src")

  LINK_DIRECTORIES("X:\\workdir\\libtml-c\\build\\android_arm32\\sidex"
                   "X:\\workdir\\libtml-c\\build\\android_arm32\\tml")
  
  set(CPACK_SET_DESTDIR ON)
  
  include (CPack)

  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .so)

  set (PLATFORM_FOUND true)
endif (${TML_TARGET_PLATFORM} STREQUAL android_arm32)

#######################################################
## LINUX ARM32
if (${TML_TARGET_PLATFORM} STREQUAL linux_arm32)
  set(PROJECT linux_arm32)

  set(JNIINCLUDE "/usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt/include")
  set(JNIINCLUDE_PLATFORM "/usr/lib/jvm/jdk-8-oracle-arm-vfp-hflt/include/linux")
  
  set(TMLINCLUDE "/home/pi/workdir/libtml-c/sidex/src"
                 "/home/pi/workdir/libtml-c/common/src"
                 "/home/pi/workdir/libtml-c/tml/src")

  LINK_DIRECTORIES("/home/pi/workdir/libtml-c/build/linux_arm32/sidex"
                   "/home/pi/workdir/libtml-c/build/linux_arm32/tml")
    
  set(CPACK_SET_DESTDIR ON)
  
  set (CMAKE_FIND_LIBRARY_PREFIXES lib)
  set (CMAKE_FIND_LIBRARY_SUFFIXES .so)

  include (CPack)


  set (PLATFORM_FOUND true)
endif (${TML_TARGET_PLATFORM} STREQUAL linux_arm32)

if (${PLATFORM_FOUND} STREQUAL true)
  #######################################################
  ## Project / Solution- File:
  PROJECT( tml )
  
  #######################################################
  ## Try to find SIDEX install path automatically:
  find_library(SIDEX_LIB_PATH NAMES sidex11)
  if (SIDEX_LIB_PATH AND EXISTS "${SIDEX_LIB_PATH}")
    get_filename_component(SIDEX_LIB_PATH ${SIDEX_LIB_PATH} DIRECTORY)
  endif()
  find_library(SIDEX_LIB_PATH_64 NAMES sidex11_64)
  if (SIDEX_LIB_PATH_64 AND EXISTS "${SIDEX_LIB_PATH_64}")
    get_filename_component(SIDEX_LIB_PATH_64 ${SIDEX_LIB_PATH_64} DIRECTORY)
  endif()
  find_path(SIDEX_INCLUDE_DIR tml/sidex.h)
  if (SIDEX_INCLUDE_DIR AND EXISTS "${SIDEX_INCLUDE_DIR}/tml/sidex.h")
    set(SIDEX_INCLUDE_DIR ${SIDEX_INCLUDE_DIR}/tml)
  endif()
  message("-- ---------------------------------------------------------------")
  message("-- Automatic path detection:")
  message(STATUS "SIDEX_LIB_PATH:       " ${SIDEX_LIB_PATH})
  message(STATUS "SIDEX_LIB_PATH_64:    " ${SIDEX_LIB_PATH_64})
  message(STATUS "SIDEX_INCLUDE_DIR:    " ${SIDEX_INCLUDE_DIR})
  ## Try to find TMLCORE install path automatically:
  find_library(TMLCORE_LIB_PATH tmlcore11)
  if (TMLCORE_LIB_PATH AND EXISTS "${TMLCORE_LIB_PATH}")
    get_filename_component(TMLCORE_LIB_PATH ${TMLCORE_LIB_PATH} DIRECTORY)
  endif()
  find_library(TMLCORE_LIB_PATH_64 tmlcore11_64)
  if (TMLCORE_LIB_PATH_64 AND EXISTS "${TMLCORE_LIB_PATH_64}")
    get_filename_component(TMLCORE_LIB_PATH_64 ${TMLCORE_LIB_PATH_64} DIRECTORY)
  endif()
  find_path(TMLCORE_INCLUDE_DIR /tml/tmlCore.h)
  if (TMLCORE_INCLUDE_DIR AND EXISTS "${TMLCORE_INCLUDE_DIR}/tml/tmlCore.h")
    set(TMLCORE_INCLUDE_DIR ${TMLCORE_INCLUDE_DIR}/tml)
  endif()
  message(STATUS "TMLCORE_LIB_PATH:    " ${TMLCORE_LIB_PATH})
  message(STATUS "TMLCORE_LIB_PATH_64: " ${TMLCORE_LIB_PATH_64})
  message(STATUS "TMLCORE_INCLUDE_DIR: " ${TMLCORE_INCLUDE_DIR})
  message("-- If NOTFOUND- you have to set include/library directories manual")
  message("-- ---------------------------------------------------------------")

  #######################################################
  ## Source and destination directories:
  ADD_SUBDIRECTORY( sidex ${PROJECT}/sidex)
  ADD_SUBDIRECTORY( tml ${PROJECT}/tml)
  
else (${PLATFORM_FOUND} STREQUAL true)
  message( SEND_ERROR "Unknown target platform / please check \"TML_TARGET_PLATFORM\"" )
endif (${PLATFORM_FOUND} STREQUAL true)
