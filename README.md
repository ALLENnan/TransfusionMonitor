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

###其他
import sun.audio.AudioPlayer;   
import sun.audio.AudioStream;   
这两个包报错则修改Forbidden reference为Worning   

![tip](https://github.com/ALLENnan/TransfusionMonitor/blob/master/image/tip.png)
