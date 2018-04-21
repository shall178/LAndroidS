All order:
获取：GET
    获取设备信息：                  (1 << 0)
        devName+devMem+devAvailbleMem
    获取WIFI扫描列表：              (1 << 1)
        close
        open+active/static+ip
        最多扫描15个，包括wifi名称和信号强度
    获取以太网的信息：               (1 << 2)
        close
        open+active/static+IP
    获取AP信息：                   (1 << 3)
        Open+apName+passWord
    获取HDMI 信息：                (1 << 4)
        resolution
    获取AUDIO信息：                (1 << 5)
        maxA+curO
    获取后台程序检测时间：           (1 << 6)
        time

    获取HDMI 支持的分辨率           7


设置： SET
1    WIFI：
        打开/关闭               o/c
        打开连接指定的wifi       a+wifiName+password
        设置动态 /静态IP             i+a/ipAddress
        例如：打开/关闭wifi： SET+1+o/c
             打开连接指定wifi： SET+2+a{wifiName+password}
             设置WIFI静态IP：SET+1+s+ipAddress

2    以太网：
        设置以太网动态/静态IP      eth+a/ip
        例如：SET2+ip

3    AP：
        打开/关闭           ap+o/c
        打开并且设置热点名称和密码  ap+name+password
        例如：
            打开/关闭热点：SET+3+o/c
            设置热点名称：SET+3+s{name+password}

4    HDMI：
        设置HDMI 分辨率  SET+4+resolution

5    AUDIO：
        设置AUDIO的大小  SET+5+num

6    后台程序时间：
        设置后台程序检测时间  SET+6+time










