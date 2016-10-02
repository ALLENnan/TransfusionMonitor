# TransfusionMonitor
###项目简介
基于微处理的智能输液监控系统(广外创新实验室团队)，一款智能化的医疗设备，通过与点滴硬件端的连接，实现对输液的监控和控制；可以实时监控输液速度，智能调整输液速度以及输液结束前的智能提醒

###主界面  
图一  
![1](https://github.com/ALLENnan/TransfusionMonitor/blob/master/image/1.png)  
图二   
![2](https://github.com/ALLENnan/TransfusionMonitor/blob/master/image/2.png)  
图三   
![3](https://github.com/ALLENnan/TransfusionMonitor/blob/master/image/3.png)  
图四   
![4](https://github.com/ALLENnan/TransfusionMonitor/blob/master/image/4.png)  

###简介
图一为智能输液监控系统的主界面；本PC端软件可看作服务器，可以管理远端的多个点滴客户端系统，实时监控且可远程对液体点滴进行相应的控制。等待点滴客户端连接上服务器后，通过录入需要输液的瓶数，以及每瓶液体的体积等信息（图二），即可在此监控系统实时查看各病患（客户端）的输液速度以及剩余的输液瓶数及每瓶距离输完所需时间（图三），还可以实现远程更改客户端的输液速度的功能。当有病患的输液剩余时间达到安全提醒值时，系统会通过音响向监控室医护人员发出具体病患需要换液或者拔针的提醒。

###功能   
1、实时显示点滴客户端发出的点滴速度；   
2、如果点滴速度不在安全范围内，可以通过本系统输入合适的速度值进行远程控制；  
3、通过输入的药液体积，本系统可以根据点滴的速度来计算其余量，并显示药液大概用完的时间   
4、当系统预计点滴快用完时，系统会进行声音提示。   
5、当液体点滴下降至警戒线，服务器可以发出警报请求并停止点滴。   

###具体原理  
本PC端系统通过socket通信，基于TCP协议，等待远端点滴系统的WIFI模块发出连接， WIFI模块已经设置好此系统的IP地址和端口，当开启WIFI模块时，则通过路由器连上在同一个局域网的PC端系统，PC端系统此时会创建一个新线程与此点滴客户端系统进行收发数据的通信，内置程序会对数据进行处理并通过界面与用户进行交互。   
###实用价值  
此软件用java语言编写，具可移植性，可跨平台；  
此软件体积小，且占用空间很少；   
软件的编写条理清晰，思路分明；   
软件的稳定性好，可以准确地反应多个客户端发来的数据；  
软件的可扩展性强，例：可以继续开发，与医院的数据库系统进行连接，此时可直接获取患者账单和输液的瓶数以及每瓶药液的体积等信息，不用手动输入   
此软件操作简单且快捷，便于管理病患信息，适合医护人员的使用。   

###其他
import sun.audio.AudioPlayer;   
import sun.audio.AudioStream;   
这两个包报错则修改Forbidden reference为Worning   

![tip](https://github.com/ALLENnan/TransfusionMonitor/blob/master/image/tip.png)
