# Install libTML-java from source{#tml_install}

The libTML-java library is intended to be used on multiple platforms. CMake is used to simplify building, installing or creating project files for a preferred development environment and platform. All instructions in this section require to install [CMake](http://www.cmake.org/).
You can download CMake here: [http://www.cmake.org/download/](http://www.cmake.org/download/)

All instructions require the sources of the [libTML-java library from GitHub](https://github.com/tml21/libtml-java). Download the ZIP archive and extract the files on your target or build system.

Currently the following target systems are tested:

- [Android Cross Build](#AndroidLink)
- [freeBSD x86](#freeBSDLink)
- [Linux ARM](#linuxArmLink)
- [Linux x86](#LinuxLink)
- [OS X](#OsxLink)
- [Windows](#WinLink)
	- [Build with MinGW](#MinGWLink)
		- [Build with MinGW32](#MinGWLink32)
		- [Build with MinGW64](#MinGWLink64)
	- [Build with Visual Studio](#WinVCLink) 

<br>

----------

## Library dependencies ##

To build libTML-java various libraries need to be present. Refer to the documentation of the particular library for installation.

#### libTML-c ####

See [Installation guide for libTML-c](http://libtml.org/docs/libtml-c-html/md_tml_install.html).

### Set library dependencies manually ###

By default cmake finds the library and include paths automatically. If the libraries are not installed in the default location, the **CMakeLists.txt** in the root directory of libTML-java has to be modified. These modifications are usually necessary on Windows. 
Find the intended build target in the **CMakeLists.txt** file and modify the directories.

Set include directories:

	set(JNIINCLUDE "C:\\Program Files\\Java\\jdk1.7.0_71\\include")
	set(JNIINCLUDE_PLATFORM "C:\\Program Files\\Java\\jdk1.7.0_71\\include\\win32")
    set(TMLINCLUDE "X:\\workdir\\libtml-c\\sidex\\src"
                   "X:\\workdir\\libtml-c\\common\\src"
                   "X:\\workdir\\libtml-c\\tml\\src")

 
Set library directories:

	LINK_DIRECTORIES("X:\\workdir\\libtml-c\\build\\win_x86-32\\sidex\\Release"
                     "X:\\workdir\\libtml-c\\build\\win_x86-32\\tml\\Release")

<br>

----------

<a name="WinLink"></a>
## Build libTML-java on Windows ##

To build libTML-java on Windows either Visual Studio or MinGW can be uses. 

- [Build with MinGW](#MinGWLink)
	- [Build MinGW32](#MinGWLink32)
	- [Build MinGW64](#MinGWLink64)
	- [Build with Visual Studio](#WinVCLink)


<a name="MinGWLink"></a>
### Build with MinGW ###

> The compiled libraries require the MinGW run time libraries.

Download the MinGW Installer from [http://www.mingw.org/](http://www.mingw.org/). After installation start the `guimain.exe` and mark the required packages:

#### win32 #####

- MinGW / MinGW Base System "`mingw32-base`"
- MinGW / MinGW Base System "`mingw32-gcc-g++`"
- MSYS / MSYS Base System "`msys-base`"

Apply changes to install the packages.
Open the MSys console (`<MinGW Installdir>\msys\1.0\msys.bat`) and go to libtml-java/src of the libTML-java files.

#### win64 #####

To compile 64 bit libraries with MinGW a 64 bit compiler is required. After installing MinGW for 32 bit you need to download the 64 bit MinGW package from [**here**](https://code.google.com/p/mingw-builds/downloads/detail?name=x86_64-mingw32-gcc-4.7.0-release-c,c%2b%2b,fortran-sjlj.zip&can=2&q=) 
Extract the package but do not overwrite the previous 32 bit MinGW installation.

 Use the `win_mingw64.cmake` tool chain to set the compiler path for win64 binary build.
 (see [Build libTML-java win64 with MinGW](#MinGWLink64))

<br>

----------

<a name="MinGWLink32"></a>
## Build libTML-java win32 with MinGW ##

To build libTML-java win32 binaries on Windows with MinGW, the settings for the tool chain have to be adjusted. CMake needs the information where to find win32 compiler. Edit the tool chain file `win_mingw32.cmake`. The file is located in libtml-java/src.

* Set the cross compilers and tool chain utilities in `win_mingw32.cmake`.
	- Root path to search on the file system for the win32 compilers and tool chain utilities e.g.<br>`set(MINGW_BIN_DIRECTORY C:/mingw/bin)`

- Open MSYS development console (`<MinGW Installdir>\msys\1.0\msys.bat`) and go to the libtml-java/src directory.
 
- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the make file<br>`cmake .. -DCMAKE_TOOLCHAIN_FILE=win_mingw32.cmake -G "Unix Makefiles" -DTML_TARGET_PLATFORM=win_x86-32_mingw`

> The CMake cache file `CMakeCache.txt` is storing parameters from a previous build. If you create the binaries for a different platform you need to delete `CMakeCache.txt` and the `CMakeFiles` directory in order to create a clean new set of project files.

- Build with cmake<br>`cmake --build . --clean-first`

	- The binaries are located in:
		- `build\win_x86-32_mingw\sidex\jniSidex11.dll`
		- `build\win_x86-32_mingw\tml\jniTml11.dll`

[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="MinGWLink64"></a>
## Build libTML-java win64 with MinGW ##

To build libTML-java win64 binaries on Windows with MinGW, the settings for the tool chain have to be adjusted. CMake needs the information where to find win64 compiler. Edit the tool chain file `win_mingw64.cmake`. The file is located in libtml-java/src.

* Set the cross compilers and tool chain utilities in `win_mingw64.cmake`.
	- Root path to search on the file system for the win64 compilers and tool chain utilities e.g.<br>`set(MINGW_BIN_DIRECTORY C:/mingw64/bin)`

- Open MSYS development console (`<MinGW Installdir>\msys\1.0\msys.bat`) and go to the libtml-java/src directory.
 
- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the make file<br>`cmake .. -DCMAKE_TOOLCHAIN_FILE=win_mingw64.cmake -G "Unix Makefiles" -DTML_TARGET_PLATFORM=win_x86-64_mingw`

> The CMake cache file `CMakeCache.txt` is storing parameters from a previous build. If you create the binaries for a different platform you need to delete `CMakeCache.txt` and the `CMakeFiles` directory in order to create a clean new set of project files.

- Build with cmake<br>`cmake --build . --clean-first`

	- the binaries are located in:
		- `build\win_x86-64_mingw\sidex\jniSidex11.dll`
		- `build\win_x86-64_mingw\tml\jniTml11.dll`

[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="WinVCLink"></a>
## Build libTML-java on Windows with Visual Studio ##

- Open a windows command prompt and go to the libtml-java/src directory.

- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the Visual Studio Project files. If you intend to use a different version of Visual Studio, call `cmake --help` and find the matching generator.
	-	for win32 binary build:<br>`cmake .. -G "Visual Studio 10 2010" -DTML_TARGET_PLATFORM=win_x86-32`
	-	for x64 binary build:<br>`cmake .. -G "Visual Studio 10 2010 Win64" -DTML_TARGET_PLATFORM=win_x86-64`

> The CMake cache file `CMakeCache.txt` is storing parameters from a previous build. If you create the binaries for a different platform you need to delete `CMakeCache.txt` and the `CMakeFiles` directory in order to create a clean new set of project files.

- Build with cmake<br>`cmake --build . --clean-first --config Release`

- Depending on the `TML_TARGET_PLATFORM` parameter the binaries are located in:

	* win_x86-32:
		- `build\win_x86-32\sidex\Release\jniSidex11.dll`
		- `build\win_x86-32\sidex\Release\jniSidex11.lib`
		- `build\win_x86-32\tml\Release\jniTml11.dll`
		- `build\win_x86-32\tml\Release\jniTml11.lib`
	* win_x86-64:
	    - `build\win_x86-64\sidex\Release\jniSidex11.dll`
	    - `build\win_x86-64\sidex\Release\jniSidex11.lib`
		- `build\win_x86-64\tml\Release\jniTml11.dll`
		- `build\win_x86-64\tml\Release\jniTml11.lib`


[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="LinuxLink"></a>
## Build libTML-java on LINUX x86 ##

- Open a terminal and go to the libtml-java/src directory.

- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the make file
	-	for 32 bit binary build:<br>`cmake .. -G "Unix Makefiles" -DTML_TARGET_PLATFORM=linux_x86-32`
	-	for 64 bit binary build:<br>`cmake .. -G "Unix Makefiles" -DTML_TARGET_PLATFORM=linux_x86-64`

> The CMake cache file `CMakeCache.txt` is storing parameters from a previous build. If you create the binaries for a different platform you need to delete `CMakeCache.txt` and the `CMakeFiles` directory in order to create a clean new set of project files.

- Build with cmake<br>`cmake --build . --clean-first`

- Depending on the `TML_TARGET_PLATFORM` parameter the binaries are located in:
	* linux_x86-32
		- `build\linux_x86-32\sidex\libjniSidex11.so`
		- `build\linux_x86-32\tml\libjniTml11.so`
	* linux_x86-64
		- `build\linux_x86-64\sidex\libjniSidex11.so`
		- `build\linux_x86-64\tml\libjniTml11.so`


- Install the libraries (superuser required)<br/>`make install`<br/>or<br/>`sudo make install`


[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------


<a name="OsxLink"></a>
## Build libTML-java on OS X ##

- Open a terminal and go to the libtml-java/src directory.

- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the make file<br>
	-	for 32 bit binary build:<br>`cmake .. -G "Unix Makefiles" -DTML_TARGET_PLATFORM=osx_x86-32`
	-	for 64 bit binary build:<br>`cmake .. -G "Unix Makefiles" -DTML_TARGET_PLATFORM=osx_x86-64`

> The CMake cache file `CMakeCache.txt` is storing parameters from a previous build. If you create the binaries for a different platform you need to delete `CMakeCache.txt` and the `CMakeFiles` directory in order to create a clean new set of project files.

- Build with cmake<br>`cmake --build . --clean-first`

- Depending on the `TML_TARGET_PLATFORM` parameter the binaries are located in:
	- osx_x86-32
		- `build\osx_x86-32\sidex\libjniSidex11.dylib`
		- `build\osx_x86-32\tml\libjniTml11.dylib`
	- osx_x86-64
		- `build\osx_x86_64\sidex\libjniSidex11.dylib`
		- `build\osx_x86_64\tml\libjniTml11.dylib`


- Install the libraries (superuser required)<br/>`make install`<br/>or<br/>`sudo make install`


[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="linuxArmLink"></a>
## Build libTML-java on LINUX ARM ##

- Open a terminal and go to the libtml-java/src directory.<br>

- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the makefile<br>`cmake .. -G "Unix Makefiles" -DTML_TARGET_PLATFORM=linux_arm32`

- Build with cmake<br>`cmake --build . --clean-first`

- the binaries are located in:
	- `build\linux_arm32\sidex\libjniSidex11.so`
	- `build\linux_arm32\tml\libjniTml11.so`


- Install the libraries (superuser required)<br/>`make install`<br/>or<br/>`sudo make install`


[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="AndroidLink"></a>
## libTML-java cross build for Android ##

To build libTML-java for Android with CMAKE, the settings for the tool chain have to be adjusted. CMake needs the information where to find the Android cross compiler. Edit the tool chain file `android_arm32.cmake`. The file is located in libtml-java/src.

The [Android NDK](https://developer.android.com/ndk/index.html) is required for cross compilation. Depending on the intended target platform you need to download and install the matching version. The Android NDK can be downloaded from [https://developer.android.com/ndk/downloads](https://developer.android.com/ndk/downloads/index.html).

>The [required libraries](#RequiredLibs) have to be build as well. Some of the libraries do not include instructions on how to build on Android. The `3rdParty` directory of the libTML-c source distribution contains additional files and instructions in` AndroidBuild-ReadMe.md` to fill this gap. Depending on the version of the library additional modifications may be required.

A cross compile for Android on Windows requires [MinGW](http://www.mingw.org/) with the MSYS packages.

- Set the cross compilers and tool chain utilities in `android_arm32.cmake`.
	-	 Root path to search on the file system for the cross compilers and tool chain utilities e.g.
		`set(CMAKE_FIND_ROOT_PATH t:/android/standalone-20/arm-linux-androideabi-4.6/arm-linux-androideabi/bin )`
	-	 Path of C cross compiler e.g. 
		`set(CMAKE_C_COMPILER t:/android/standalone-20/arm-linux-androideabi-4.6/bin/arm-linux-androideabi-gcc.exe )`
	-	 Path of C++ cross compiler e.g. 
		`set(CMAKE_CXX_COMPILER t:/android/standalone-20/arm-linux-androideabi-4.6/bin/arm-linux-androideabi-g++.exe )`
<br><br>
- Open MSYS development console (`<MinGW Installdir>\msys\1.0\msys.bat`) and go to the libtml-java/src directory.

- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the makefile<br>`cmake .. -DCMAKE_TOOLCHAIN_FILE=android_arm32.cmake -G "Unix Makefiles" -DTML_TARGET_PLATFORM=android_arm32`

- Build with cmake<br>`cmake --build . --clean-first`

- the binaries are located in:
	- `build\android_arm32\sidex\libjniSidex11.so`
	- `build\android_arm32\tml\libjniTml11.so`


[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="freeBSDLink"></a>
## Build libTML-java on freeBSD x86 ##

- Open a terminal and go to the libtml-java/src directory.

- Create a build directory.<br/>`mkdir build`

- go to the build directory<br/>`cd build`

- Generate the make file
	-	for 32 bit binary build:<br>`cmake .. -G "Unix Makefiles" -DTML_TARGET_PLATFORM=freeBSD_x86-32`
	-	for 64 bit binary build:<br>`cmake .. -G "Unix Makefiles" -DTML_TARGET_PLATFORM=freeBSD_x86-64`

> The CMake cache file `CMakeCache.txt` is storing parameters from a previous build. If you create the binaries for different platform you need to delete `CMakeCache.txt` and the `CMakeFiles` directory in order to create a clean new set of project files.

- Build with cmake<br>`cmake --build . --clean-first`

- Depending on the `TML_TARGET_PLATFORM` parameter the binaries are located in:
	* freeBSD_x86-32
		- `build\freeBSD_x86-32\sidex\libjniSidex11.so`
		- `build\freeBSD_x86-32\tml\libjniTml11.so`
	* freeBSD_x86-64
		- `build\freeBSD_x86-64\sidex\libjniSidex11.so`
		- `build\freeBSD_x86-64\tml\libjniTml11.so`


- Install the libraries (superuser required)<br/>`make install`<br/>or<br/>`sudo make install`


[Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="javaLink"></a>
## JNI ##
The Java Native Interface consists of a `"Java part"` and a `"C part"`:

- `"java Part"`  - is a Java class with native methods calling a C library
- `"C part"` - is a library written in C that implements the native methods calling "other C library" API's. In our case the "other C libraries" are the content of libTML-c.


The libTML-java build description above leads to the `"C part"` of the JNI (native libraries):

- jniSidex11 library
- jniTml11 library

The `"java Part"` of the JNI has to be build explicitly out of our JNI Java source files using the *Java Development Kit* (JDK).
 

### The JNI source files ###

You find the JNI Java sources in the subfolder `"/java"` of the libTML-java source repository.
 
### creating a JAR ###
Running a Java program using package of Java classes it is usual to bind them into a Java archive (JAR). If you don't already have a *JDK*, [download and install](http://www.oracle.com/technetwork/java/javase/downloads/index.html) it appropriately. Keep your platform (x86 or x64) in mind. 

The following description is a way on how to build a Java Archive (JAR) out of our JNI Java sources. 

- Change into the directory `/java/com/tmlsidex/` and create a new folder, for example  'class'. 
- Generate a text file with the pathnames of all java files that exist in the subfolders of the present directory. 
- Compile all java files using that text file writing the output into the recently created 'class' folder. 
- Create the JNI Java Archive / JAR file named **tmlSidex.jar**.


*Example on linux and OS-X platforms*:

	mkdir class
	find . -name "*.java" > targetList.txt
	javac -cp class -d class @targetList.txt
	cd class
	jar cf tmlSidex.jar com


*Example on windows:*

	mkdir class
	dir /s /B *.java > targetList.txt
	javac -cp class -d class @targetList.txt
	cd class
	jar cf tmlSidex.jar com


> If the pathname contains blanks, you have to edit the text file **targetList.txt**, set each line lines into **quotes** and replace **backslashes to slashes** before using the javac compiler.


Please read the documentation [Introduction to libTML-java](\ref tml_intro) to get information of how to use the library.