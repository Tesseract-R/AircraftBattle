# AircraftBattle

### 已修改部分：

- 修改菜单的中文乱码为英文

- 设置敌机的飞行高度为0-300 

  - 实现原理：在Airplane.fly()里设置pY的坐标范围

- 关闭了友伤（敌机不会打到敌机、双人模式不会打到队友）

  - 实现原理：分离玩家与电脑发出的子弹类型，玩家的子弹带有标签“player”，电脑的子弹带有标签“computer”，然后在Airplane.hit(Bullet b)中先判断击中的子弹类型是什么

- 减慢敌机子弹速度 由3降为1

  - 实现原理：

    ```java
    //发射子弹
    if ((p.getRandomIntNum(0, 300))==2)  {
       if(p.bullettype==nmlBullet) {
          Bullet b2=new Bullet(p.pX+p.pWidth/2-3,p.pY+p.pHeight,13,13,nmlBullet);
          b2.speed=-1;
          bulletsList.add(b2);
       }
    }
    ```

- 敌机不要和角色抢夺补剂资源

  - 实现原理：

    - 逻辑层面：Airplane.hit(Accessory a) 里先判断飞机的类型是不是玩家if(controlled)，这样敌机碰到补给不会加血；
    - 表现层面：Battlefield.gameControl里也加上限定飞机类型是不是玩家，是的话再让补给消失；

    
---

### 待修改部分：

- 减慢敌机速度
- 设置画面边界，或者从左端出去可以自右端回来，不然可以卡bug
- 缩小敌机的运动范围

### 高阶-新增特性

-	根据通关时间血量最后显示得分
-	敌机的子弹可以斜射
-	开局可选择不同机型：比如有单发机型（伤害高），双发机型（伤害低）等
-	每个机型对应有一个充能槽，命中敌机可充能
-	能量槽充能后可以发动终极技能，每个机型对应一个自己的终极技能
-	关卡选择
-	受到伤害时屏幕闪红或抖动
