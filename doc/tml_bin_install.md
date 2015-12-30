# Install libTML-java  binary package {#tml_bin_install}	

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

**Note:** The Windows binary installer doesn't change the Windows path environment.


## Windows installation content ##
The installation destination folder contains

- **bin** dynamic link library files (C- API & JNI) 
	- **win32**	dynamic link libraries for 32 bit systems
	- **x64**	dynamic link libraries for 64 bit systems
- **include** include files for C- development
- **lib** lib files for C- development
	- **win32**	lib files for 32 bit C- development
	- **x64**	lib files for 64 bit C- development
- **java** java source files
	- **com\tmlsidex\jni**	JNI binding of the C- API functions
	- **com\tmlsidex\sidex** classes to simplify the usage of SIDEX- API
	- **com\tmlsidex\tml** classes to simplify the usage of TML- API
- **pascal** pascal source files
	- **examples**	Lazarus example using the TML Messaging Suite libraries
	- **graphics**	component icons
	- **src**	component source files
	- **packages\Lazarus**	Lazarus component source files
	- **packages\C++ Builder**	C++ Builder component source files
	- **packages\Delphi**	Delphi component source files

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


- shared libraries for the Java Native Interface installed to folder **/usr/lib**

<a name="OsxLink"></a>
## OS-X Disk Image ##

## Installation steps: ###

 - Download the TML Messaging Suite disk image file (.dmg) onto your OS-X target system
 - Mount the disk image file
 - Copy shared libraries:
   - on a 32 bit environment
   		-  copy ***)** the directory content of **/Volumes/libtml/usr/lib32** to the folder **/usr/lib**
   - on a 64 bit environment
   		-  copy ***)** the directory content of **/Volumes/libtml/usr/lib64** to the folder **/usr/lib**

***)**

	cp /Volumes/libtml/usr/lib32/* /usr/lib
respectively

	cp /Volumes/libtml/usr/lib64/* /usr/lib
on the command shell out of "superuser mode". 

## OS-X installation content ##
The mounted TML Messaging Suite disk image file contains

- **use** shared library files (C- API & JNI) 
	- **lib32**	shared library files for 32 bit environment
	- **lib64**	shared library files for 64 bit environment
- **include** include files for C- development
- **java** java source files
	- **com\tmlsidex\jni**	JNI binding of the C- API functions
	- **com\tmlsidex\sidex** classes to simplify the usage of SIDEX- API
	- **com\tmlsidex\tml** classes to simplify the usage of TML- API
- **pascal** pascal source files
	- **examples**	Lazarus example using the TML Messaging Suite libraries
	- **graphics**	component icons
	- **src**	component source files
	- **packages\Lazarus**	Lazarus component source files

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


- shared libraries for the Java Native Interface installed to folder **/usr/lib**

