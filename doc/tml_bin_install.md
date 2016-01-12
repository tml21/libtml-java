# Install libTML-java binary package {#tml_bin_install}	

Currently the following libTML-java binary packages are tested:

- [Windows](#WinLink)
- [debian libtml-java](#debianLink)
- [OS-X](#OsxLink)
- [freeBSD libtml-java](#freeBSDLink)


<a name="WinLink"></a>
## Windows installer (32/64 bit) ##

Download the TML Messaging Suite Windows binary installer and launch it on your Windows target system. It supports both, 32 bit and 64 bit Windows systems.

Using the wizard based installer you are able to

- navigate to the next setup wizard page using the “Next” button.
- navigate to the previous setup wizard page using the “Back” button.
- terminate the setup wizard session by using the “Cancel” button.

### Installation step "Welcome page"###
Welcome & introduction of TML Messaging Suite installer.
### Installation step "License agreement page"###
License agreement of TML Messaging Suite.
### Installation step "Destination folder page"###
Select the installation destination folder.
### Installation step "Ready to start installation page"###
Click `"Next"` to start the installation.
### Installation step "Installation progress page"###
Installation progress information.
### Installation step "Complete page"###
Now the TML Messaging Suite is ready to be used.


## Windows installation content ##
The installation destination folder contains

- **bin** dynamic link library files (C API & JNI) 
	- **win32**	dynamic link libraries for 32 bit systems
	- **x64**	dynamic link libraries for 64 bit systems
- **include** include files for C/C++ development
- **lib** lib files for C/C++ development
	- **win32**	lib files for 32 bit C/C++ development
	- **x64**	lib files for 64 bit C/C++ development
- **java** java source files
	- **com\\tmlsidex\\jni**	JNI binding of the C API functions
	- **com\\tmlsidex\\sidex** classes to simplify the usage of SIDEX API
	- **com\\tmlsidex\\tml** classes to simplify the usage of TML API
- **pascal** pascal source files
	- **examples**	Lazarus example using the TML Messaging Suite libraries
	- **graphics**	component icons
	- **src**	component source files
	- **packages\\Lazarus**	Lazarus component source files
	- **packages\\C++ Builder**	C++ Builder component source files
	- **packages\\Delphi**	Delphi component source files

Furthermore the libtml-java dynamic link library files are installed into the system folder

- **Windows**
	- **System32** (Windows 32 bit & Windows 64 bit target platform)
	- **SysWOW64** (Windows 64 bit target platform)

**Note:** The Windows binary installer doesn't change the Windows path environment.

[The libTML-java binary package installation is finished now. Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="debianLink"></a>
## Debian package dependencies  ##

The libTML-java debian package depends on the  libTML-c debian package.

So install the libTML-c debian package first.

## libTML-java debian package ##
Download the libTML-java package onto your target system.

Install the package using shell command


	sudo dpkg -i /PATH/TO/PACKAGE/PACKAGENAME.deb 

## libTML-java debian package installation content ##
The libTML-java debian package installation contains


- shared libraries for the Java Native Interface installed to folder `"/usr/lib"` 


[The libTML-java binary package installation is finished now. Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------


<a name="OsxLink"></a>
## OS-X Disk Image ##

 - Download the TML Messaging Suite disk image file (.dmg) onto your OS-X target system
 - Mount the disk image file

## OS-X installation content ##
The mounted TML Messaging Suite disk image file contains

- `"usr"` shared library files (C API & JNI)
	- `"lib32"`	shared library files for 32 bit environment
	- `"lib64"`	shared library files for 64 bit environment
- `"include"` include files for C/C++ development
- `"java"` java source files
	- `"com/tmlsidex/jni"`	JNI binding of the C API functions
	- `"com/tmlsidex/sidex"` classes to simplify the usage of SIDEX API
	- `"com/tmlsidex/tml"` classes to simplify the usage of TML API
- `"pascal"` pascal source files
	- `"examples"`	Lazarus example using the TML Messaging Suite libraries
	- `"graphics"`	component icons
	- `"src"`	component source files
	- `"packages/Lazarus"`	Lazarus component source files

##Copy shared library files##

- on a 32 bit environment
	- copy **1)** the directory content of `"/Volumes/libtml/usr/lib32"` to the folder `"/usr/lib"`

- on a 64 bit environment
	- copy **2)** the directory content of `"/Volumes/libtml/usr/lib64"` to the folder `"/usr/lib"`


**1)** on the command shell out of "superuser mode":

	cp /Volumes/libtml/usr/lib32/* /usr/lib


**2)** on the command shell out of "superuser mode":

	cp /Volumes/libtml/usr/lib64/* /usr/lib

##System Integrity Protection - using OS X 10.11 (El Capitan)##

If the System Integrity Protection is enabled (default on El Capitan), root is not permitted to copy to `"/usr/lib"`.
    
To disable System Integrity Protection, you must boot to Recovery OS and run the csrutil(1) command from the Terminal.<br>[See:Configuring System Integrity Protection](https://developer.apple.com/library/mac/documentation/Security/Conceptual/System_Integrity_Protection_Guide/ConfiguringSystemIntegrityProtection/ConfiguringSystemIntegrityProtection.html).

- Boot to Recovery OS by restarting your machine and holding down the Command and R keys at startup.
- Launch Terminal from the Utilities menu.
- Enter the following command:

		$ csrutil disable

After disabling System Integrity Protection on a machine, a reboot is required.    



[The libTML-java binary package installation is finished now. Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="freeBSDLink"></a>
## freeBSD package dependencies  ##

The libTML-java freeBSD package depends on the  libTML-c freeBSD package.

So install the libTML-c freeBSD package first.

## libTML-java freeBSD package ##
Download the libTML-java freeBSD package onto your target system.

Install the package using shell command


	pkg install /PATH/TO/PACKAGE/PACKAGENAME 

## libTML-java freeBSD package installation content ##
The libTML-java freeBSD package installation contains


- shared libraries for the Java Native Interface installed to folder  `"/usr/lib"`

[The libTML-java binary package installation is finished now. Continue reading chapter "JNI" of this documentation, building the Java archive (JAR).](#javaLink) 
<br><br>

----------

<a name="javaLink"></a>
## JNI ##
The Java Native Interface consists of a `"Java part"` and a `"C part"`. 

- `"java Part"`  - is a Java class with native methods calling a C library
- `"C part"` - is a library written in C that implements the native methods calling "other C library" API's. In our case the "other C libraries" are the content of the libTML-c binary package.


The libTML-java binary package installs the `"C part"` of the JNI.

- jniSidex11 library
- jniTml11 library

The `"java Part"` of the JNI has to be build explicitly out of our JNI Java source files using the *Java Development Kit* (JDK).
 

### The JNI source files ###

- **Windows:** In case of a Windows binary package installation, you find the JNI Java sources in the destination subfolder `"/java"`
- **OS-X:** In case of an OS-X binary package installation, you find the JNI Java sources in the mounted disk image subfolder `"/java"`. Copy it onto your target system.
- **Debian or freeBSD:** In case of Debian or freeBSD you can  [download the JNI Java sources from GitHub](https://github.com/tml21/libtml-java). Download the ZIP archive and extract the subfolder `"/java"` onto your target system.
 
### creating a JAR ###
Running a Java program using package of Java classes it is usual to bind them into a Java archive (JAR). If you don't already have a *JDK*, [download and install](http://www.oracle.com/technetwork/java/javase/downloads/index.html) it appropriately. Keep your platform (x86 or x64) in mind. 

The following description is a way on how to build a Java Archive (JAR) out of our JNI Java sources. 

- Change into the directory `/java/com/tmlsidex/` and create a new folder, for example  'class'. 
- Generate a text file with the pathnames of all java files that exist in the subfolders of the present directory. 
- Compile all java files using that text file writing the output into the recently created 'class' folder. 
- Create the JAR file named tmlSidex.jar.


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
