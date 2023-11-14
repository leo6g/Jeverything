<p align="center">
    <br> <a href="README-EN.md">English</a>  | <a href="README.md">中文</a>
</p>
<p align="center">
    <em>Jeverything,search everything in your device.</em>
</p>

Fast File Search Tool, Similar to Everything, Feature-Rich, Smooth Performance. Lightning-fast file search, advanced search support, LAN sharing, fast file transfer, online file preview, audio/video playback, streaming, etc., all in one. Also deployable on the server for remote file management. JAVA version of Everything.

##### Features::

- Quickly index directories, real-time updates for file changes.
- Rapid file and folder search, supports advanced search.
- Online preview for images and documents, online playback for audio/video.
- HTTP streaming.
- Supports indexing network files (SMB).
- Online file sharing, supports password-protected sharing.
- Minimal resource usage, smooth performance.
- Accessible from multiple terminals (HTTP).
- Convenient operations.
- No installation required, one-click start, leaves no residue upon deletion.
- Cross-platform support.

#####  **System Requirements** ：

 All platforms, JDK 1.8+ 

#####  **Distribution Downloads** ：

###### **Windows custom Edition** 
 If Java is already installed on your Windows computer and JAVA_HOME environment variable is configured, you can download the standalone Windows special edition: 

[JEverything.zip] 

 If Java is not installed, you can download the version that includes JRE: 

[JEverything_JRE_windows.zip] 
###### **JAR** 
The JAR file can run on Windows, Mac, and Linux platforms

[JEverything.jar]

##### Installation and Execution:

For the Windows custom version, simply double-click to run.

To run the JAR file:
```
java -jar -Dserver.port=8082 -Xms256m -Xmx256m JEverything.jar
```


##### User Interface Showcase:

![1699930678792](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699930678792.png)
---------------
![1699930849360](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699930849360.png)
---------------
![1699931976545](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699931976545.png)

##### Instructions for Use：

###### Initial Startup

During the initial startup, you can configure system settings.

![1699930678792](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699930678792.png)

Main Interface: By default, it displays a list of files that have changed in the last day. You can filter different types of file changes by file type. Double-clicking on a file record allows you to directly open the local file. Double-clicking on a remote file enables online preview.


##### 

![1699930849360](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699930849360.png)



###### Settings

Basic Settings

![1699931135884](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699931135884.png)

You can customize the index by adding file extensions. It is recommended that after indexing, the file type be set to "Other Files."

![1699931248511](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699931248511.png)

Establishing an index allows you to choose whether to monitor file changes. Changes take effect in real-time after modification. The monitoring mechanism consumes minimal (almost negligible) computer resources to monitor all file changes.

Image preview

![1699931927037](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699931927037.png)

video play

![1699931976545](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699931976545.png)

audio play

![1699932014761](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699932014761.png)

audio play in background 

![1699932031613](https://github.com/leo6g/Jeverything/blob/master/README.assets/1699932031613.png)



###### Regular Search

Search for files: Search for files with both key1 and key2 but not key3 in the filename.

```
key1 key2 !key3  
```

Search for folders: Search for all files under folders whose names contain key.

```
path:*key*  
```

Search for all: *

###### Advanced Search

File and folder searches can be combined for more refined file filtering.

```
file:key1 key2 !key3 path:D:\dir1\*
```



Results from multiple filter conditions can be merged using |.

```
file:key1 key2 !key3 path:D:\dir1\* | file:key1 key2 !key3 path:D:\dir2\* 
```

Explanation: Search for files under the folder D:\dir1\ that contain key1 and key2 but not key3, and also search for files under the folder D:\dir2\ with the same criteria.

##### Note:
首次启动，可全盘扫描文件。

During the initial startup, you can perform a full disk scan for files.

