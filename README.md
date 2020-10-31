# Draftsman

###### Powered by TabooLib 5.0
###### authors: [枫溪] Ray_Hughes

## 原理解释
本插件操作了SkillAPI的Flag功能进行判断达到效果

包括MythicMobs部分也使用了SkillAPI的Flag

所以SkillAPI为必须依赖 [可使用免费版]

### 配置解释
注: 非关键内容可以不写 不写则为默认[减少文件行数]
```yaml
Groups:
  测试技能:
    #以下内容删除则默认为0
    #change 额外偏移不跟随实体方向
    changeX: 0
    changeY: 0
    changeZ: 0
    #rotate 旋转角
    rotateX: 0
    rotateY: 0
    rotateZ: 0
    #extra 额外偏移跟随实体方向
    extraX: 0
    extraY: 0
    extraZ: 0
    #图片地址
    path: "test.png"
    #长宽大小
    width: 5
    hight: 5
    #透明度
    alpha: 1
    #跟随玩家视角移动?
    follow: false
    #发光?
    glow: false
    #锁定玩家视角?
    lock: false
  #你也可以这样写成这样的缩写
  lona乱舞:
    path: "test.png"
    lock: true
```

### 应用方式
In `SkillAPI`
```yaml
#请在技能内给目标施加Flag
Flag内容为: "draft-[Type]-[Independent]-[Groups]"
info:
  draft: "识别表头"
  Type: 
  - "类型 分为"
  - "place [放置在实体位置上]"
  - "follow [跟随实体移动]"
  Independent: "图片是否唯一 false 是不唯一 true是唯一"
  Groups: "对应配置文件中的Groups"
Test: "draft-place-false-测试技能"
```

In `MythicMobs`
```yaml
SkeletalKnight:
  Type: WITHER_SKELETON
  Display: '&aBkm016'
  Health: 10
  Damage: 8
  Skills:
  - draftsman{type=place;independent=false;group=剑舞;time=100} @LivingInRadius{r=10} ~onTimer:10
  #格式同skillapi Time为持续的tick
  #选择器是释放图片的位置XD 祝你使用愉快
```
