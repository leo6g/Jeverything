纯java实现桌面端文件快速搜索，功能丰富，JAVA版Everything

快速搜索文件，局域网分享、快传，全搞定。

##### 功能特性:

- 快速索引目录
- 快速搜索
- 实时更新文件变动
- 文件分享，支持提取码方式
- 极少资源占用
- 移动端访问
- 操作便利
- 无需安装，直接启动
- 删除即走，无任何残留
- 跨平台运行（待完善）
- ........

##### 环境要求：

windows，jdk 1.8+

##### 下载地址：

如电脑上已配置java 运行环境,可以单独下载everything.exe
https://gitee.com/polarlb/everything/attach_files/833578/download/Everything.exe

如电脑没有java运行环境，可以下载包含jre的版本
https://gitee.com/polarlb/everything/attach_files/833604/download/Everything-jre-windows.zip

##### 运行：

直接双击运行

##### 界面展示：

![输入图片说明](https://images.gitee.com/uploads/images/2021/0916/171734_97ed967a_9742464.png "image-20210916160538213.png")

##### 使用说明：

初始登陆用户为admin，密码：123456 ，登陆后可自行修改密码。

输入框置空状态下，显示最近一天变动文件列表，可筛选。

默认搜索是前置匹配，可在设置里修改匹配类型。

双击文件记录，可直接打开文件。

##### 高级搜索

path:D:\dir1\*  搜索路径匹配D:\dir1\*的文件

file:key1 key2 !key3 搜索文件名包含key1 key2 但不包含key3的文件

| 或者条件，取左右并集结果

可以结合使用

file:key1 key2 !key3 path:D:\dir1\* | file:key1 key2 !key3 path:D:\dir2\* 

说明：搜索D:\dir1\文件夹下的 包含key1 key2 但不包含key3的文件 和 D:\dir2\文件夹下的 包含key1 key2 但不包含key3的文件

file:key1 key2 !key3 在单独使用时 可以省略 file: 字符




