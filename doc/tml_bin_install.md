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

### Installation step "Collecting information"###
Introductory information about TML Messaging Suite.
### Installation step "Preparing information"###
Allows changing  of the installation destination folder.
### Installation step "Installation"###
During this step the TML Messaging Suite will be installed to the chosen destination folder.
### Installation step "Finalizing installation"###
The TML Messaging Suite is ready to be used.


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
To use the libTML-java library in a project, the project needs to include two components. After the binary package installation you have the first one, the native libraries. The second component contains the JNI(Java Native Interface) functions calling particular native functions. To include the native libraries, they are set in the java library path during the call for execution. The JNI functions are included as an archive during both the compilation and the execution. 

### The JNI source files ###
 In case of a Windows binary package installation, you have already the JNI sources in the destination subfolder `"/java"`.
 
 In case of an OS-X binary package installation, you have already the JNI sources in the mounted disk image subfolder `"/java"`. Copy it onto your target system.

 In case of debian or freeBSD you can  [download the JNI sources from GitHub](https://github.com/tml21/libtml-java). Download the ZIP archive and extract the subfolder `"/java"` on your target system.
 
### creating a JAR ###
 If you don't already have a *Java Development Kit*, [download and install](http://www.oracle.com/technetwork/java/javase/downloads/index.html) it appropriately. Keep your platform (x86 or x64) in mind. The following description is one way on how to build a JAR. 
 First change into the directory /java/com/tmlsidex/ and create a new folder, for example named 'class'. Then generate a txt-file with the pathnames of all java-files that exist in the subfolders in your present directory. Afterwards compile all java-files with the help of the txt-file, writing the output into the recently created 'class'-folder. Eventually change into the 'class'-folder and create the JAR file. <br/>


*For example on linux and OS-X platforms*:

	mkdir class
	find . -name "*.java" > targetList.txt
	javac -cp class -d class @targetList.txt
	cd class
	jar cf tmlSidex.jar com


*on windows:*

	mkdir class
	dir /s /B *.java > targetList.txt
	javac -cp class -d class @targetList.txt
	cd class
	jar cf tmlSidex.jar com


> If the pathname contains blanks, you have to **edit targetList.txt**, **set lines into quotes** and **replace backslashes to slashes** before using javac.

