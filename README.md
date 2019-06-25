# SuperMicro IPMI 授权生成器
SuperMicro 超微主板 IPMI 高级功能授权解锁 Key 生成工具，使用多种语言编写

超微的主板自带的 IPMI 有些高级功能是需要授权才能解锁的，比如在线更新 BIOS，RAID 管理等等，但是我找半天也不知道这个 Key 在哪……

在咕鸽上找了一下，看到一篇逆向超微 IPMI 源码的文章，大概摸清了它的授权验证原理：

1. 将 IPMI BMC MAC 地址作为 16 进制字串转为二进制
2. 使用 HMAC + SHA1 散列算法计算出 Key
3. 取前 24 位作为授权密钥

我寻思那挺简单啊……于是就给它写了个生成工具出来。

在线版：https://tql.ink/ipmi/

API 调用：`https://tql.ink/ipmi/api.php?mac=<你的 BMC MAC>`

## 使用方法

你可以直接使用网页版，最方便，也可以自己下载下来编译运行玩

先 `git clone` 下来，然后选择一个你喜欢的语言，如果需要编译就编译一下，脚本语言例如 php python 可以直接运行

然后执行方法：

```
命令 <你的 BMC MAC>
```

例如

```
java main 0c:c4:7a:3e:2f:de
```

PHP 类引用的方法：

```php
<?php
include('supermicro.php');
$supermicro = new Supermicro();
echo $supermicro->getLicense("这里填写你的 IPMI MAC 地址");
```

其实可以更简单，命令行执行：

```bash
echo -n '你的 MAC 地址' | xxd -r -p | openssl dgst -sha1 -mac HMAC -macopt hexkey:8544E3B47ECA58F9583043F8 | awk '{print $2}' | cut -c 1-24
```

那还有必要写这个工具吗？有！为什么呢？因为无聊……

![img](https://i.imgur.com/IjI8nY5.gif)

## 多语言

什么你不喜欢 PHP？那就试下其他语言吧，还有 Python、Go、Java、JavaScript 版本可选

JavaScript 版本是 [@muzea](https://github.com/muzea) 大佬做的，很惭愧，我只是做了个微小的 UI。

其他语言也是从咕鸽上搜索来东拼西凑做成的，凑合着看吧……

目前我就会这几种语言了，如果你会其他语言的话，可以尝试自行实现生成，然后欢迎提交 Pr~

## BMC MAC 地址在哪查看？

登录你的主板 IPMI，首页就能看到 BMC MAC 地址

## 怎么激活？

登录 IPMI，找到下拉栏的 Other（其他），然后 Activate License 这个页面

把生成出来的 License Key 填进去就行了

## 开源协议

本项目使用 MIT 协议开源
