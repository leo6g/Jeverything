快速搜索文件工具，类似Everything工具,功能丰富，运行丝滑。极速搜索文件，支持高级搜索，局域网分享、快传、文件在线预览、音视频播放、推流等功能，全搞定。
同时可部署到服务端实现远程文件管理。JAVA版Everything

##### 功能特性:

- 快速索引目录,实时更新文件变动
- 快速搜索文件，文件夹,支持高级搜索
- 图片文档在线预览,音视频在线播放
- http推流
- 支持索引网络文件(SMB)
- 文件在线分享，支持提取码方式
- 极少资源占用,运行丝滑
- 多终端访问(HTTP)
- 操作便利
- 无需安装，一键启动,删除即走，无任何残留
- 全平台运行支持

##### 环境要求：

全平台，jdk 1.8+

##### 发行版下载：

######Windows特制版
如Win电脑上已有java运行环境并配置JAVA_HOME环境变量,可以单独下载Windows特制版

[JEverything.zip]

如电脑没有java运行环境，可以下载包含jre的版本

[JEverything_JRE_windows.zip]

Window,MAC,Linux平台都可使用jar包运行

[JEverything.jar]

##### 安装运行：

Windows特制版，直接双击运行

jar运行

```
java -jar -Dserver.port=8082 -Xms256m -Xmx256m JEverything.jar
```



##### 界面展示：

![1699930678792](D:\workgitee\Jeverything\README.assets\1699930678792.png)
---------------
![1699930849360](D:\workgitee\Jeverything\README.assets\1699930849360.png)
---------------
![1699931976545](D:\workgitee\Jeverything\README.assets\1699931976545.png)

##### 使用说明：

###### 首次启动

首次启动，可进行系统初始化设置。

![1699930678792](D:\workgitee\Jeverything\README.assets\1699930678792.png)

主界面，默认显示最近一天变动文件列表，可通过文件类型筛选不同类型文件变动。双击文件记录，可直接打开本地文件。双击远程文件可在线预览


##### 

![1699930849360](D:\workgitee\Jeverything\README.assets\1699930849360.png)



###### 设置

基本设置

![1699931135884](D:\workgitee\Jeverything\README.assets\1699931135884.png)

可通过添加 文件后缀 来自定义索引，建议索引后，文件类型为其它文件

![1699931248511](D:\workgitee\Jeverything\README.assets\1699931248511.png)

建立索引可选择是否监听文件变动，更改后实时生效，监听机制只需消耗很小（可忽略不计）的计算机资源，就可以监听全部文件变动。

图片预览

![1699931927037](D:\workgitee\Jeverything\README.assets\1699931927037.png)

视频播放

![1699931976545](D:\workgitee\Jeverything\README.assets\1699931976545.png)

音频播放

![1699932014761](D:\workgitee\Jeverything\README.assets\1699932014761.png)

音频后台播放

![1699932031613](D:\workgitee\Jeverything\README.assets\1699932031613.png)



###### 普通搜索

搜文件:搜索同时包含key1,key2,但不包含key3的文件名

key1 key2 !key3  

搜文件夹: 搜索文件夹名包含key的文件夹下的所有文件

path:*key*  

搜全部: * 

###### 高级搜索

文件，文件夹搜索可以结合使用，来更进一步的筛选文件

file:key1 key2 !key3 path:D:\dir1\\*



多个筛选条件的结果  可以使用 | 来合并结果集

file:key1 key2 !key3 path:D:\dir1\\* | file:key1 key2 !key3 path:D:\dir2\\* 

说明：搜索D:\dir1\文件夹下的 包含key1 key2 但不包含key3的文件 和 D:\dir2\文件夹下的 包含key1 key2 但不包含key3的文件

##### Note:
首次启动，可全盘扫描文件。



