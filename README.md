# moonrover
## 源码结构：<br/>
ground/GroundControlCenter:模拟地面控制站<br/>
ground/LocationCalculator:地面站定时线程，进行打印结果<br/>

metrics/BaseMetrics:上报信息基类<br/>
metrics/Channel:信道类，模拟2秒延迟<br/>
metrics/CycleMetrics:定时上报消息类<br/>
metrics/TurnMetrics:转向上报消息类<br/>

rover/Rover:模拟月球车<br/>
rover/ReportCalculator:月球车定时线程，进行数据上报<br/>

Config:配置类 <br/>
Env:模拟仿真环境类 <br/>
FileUtil:生成随机路径文件类 <br/>
Main:main类 <br/>


## 路径文件格式: <br/>
title: rover_path_#{id}</br>
里面每一行是一个上报数据</br>
对有5个数字的行：<br/>
30000 67 502.397923753683 440.82904834572685 7</br>
每个字段的意义为：时间戳，方向，x坐标,y坐标,速度</br>
(其中方向为前进方向与x轴的夹角，所以坐标的递推式为：</br>
locationX = Math.cos(direction * Math.PI / 180) * speed  + locationX </br>
locationY = Math.sin(direction * Math.PI / 180) * speed  + locationY </br>
)

对有2个数字的行：<br/>
30575 -7<br/>
分别为时间戳和转向角(fixDirection)，定时上报出现转向的概率为1/10，如果出现了，则下一个direction = direction - fixDirection</br>




## 输出结果格式<br/>
GGC report, current time = 7509===============================</br>
rover 2:report time = 5050,locationX = 551.7992733099272,locationY = 696.4544952771031,direction = 202.0,speed = 8.0,calculateX = 533.5597125228894,calculateY = 689.0852343714253</br>
rover 5:report time = 5433,locationX = 436.7550941674851,locationY = 286.1842329657171,direction = 67.0,speed = 9.0,calculateX = 444.05551457217865,calculateY = 303.38294564762253</br>
rover 1:report time = 5139,locationX = 806.2721272857573,locationY = 22.106690163261693,direction = 355.0,speed = 9.0,calculateX = 827.5209601960543,calculateY = 20.24765817045414</br>
rover 4:report time = 5414,locationX = 465.84581297520924,locationY = 410.9405370767373,direction = 238.0,speed = 15.0,calculateX = 449.1931000966808,calculateY = 384.29062565502164</br>
rover 3:report time = 5292,locationX = 391.86359120976607,locationY = 1033.5361442845024,direction = 125.0,speed = 12.0,calculateX = 376.60416369708287,calculateY = 1055.3288652707668</br>
GGC report end===============================</br>
输出结果中每一行为月球车的信息，
report time:上报时间戳(为模拟多个月球车并行，月球车启动有一个500ms以内的随机delay，所以上报时间并不一致)<br/> 
locationX:x坐标<br/>
locationY:y坐标 <br/>
direction:方向 <br/>
speed:速度 <br/>
calculateX:计算得到当前x坐标<br/> 
calculateY:计算得到当前y坐标<br/>
