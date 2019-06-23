# SuperMicro IPMI 授权生成器
SuperMicro 超微主板 IPMI 高级功能授权解锁 Key 生成工具，使用 PHP 编写

超微的主板自带的 IPMI 有些高级功能是需要授权才能解锁的，比如在线更新 BIOS，RAID 管理等等，但是我找半天也不知道这个 Key 在哪……

在咕鸽上找了一下，看到一篇逆向超微 IPMI 源码的文章，大概摸清了它的授权验证原理：

1. 将 IPMI BMC MAC 地址作为 16 进制字串转为二进制
2. 使用 HMAC + SHA1 散列算法计算出 Key
3. 取前 24 位作为授权密钥

我寻思那挺简单啊……于是就用 PHP 给它写了个生成工具出来。

在线版：`https://tql.ink/ipmi/?mac=<你的 MAC>`

## 使用方法

使用方法很简单：

```php
<?php
$supermicro = new Supermicro();
echo $supermicro->getLicense("这里填写你的 IPMI MAC 地址");
```

其实可以更简单，命令行执行：

```bash
echo -n '你的 MAC 地址' | xxd -r -p | openssl dgst -sha1 -mac HMAC -macopt hexkey:8544E3B47ECA58F9583043F8 | awk '{print $2}' | cut -c 1-24
```

那还有必要写这个工具吗？有！为什么呢？因为无聊……

![img](https://i.imgur.com/IjI8nY5.gif)

## BMC MAC 地址在哪查看？

登录你的主板 IPMI，首页就能看到 BMC MAC 地址

## 开源协议

本项目使用 MIT 协议开源
