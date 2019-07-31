# 1、Servlet抽取
### 1.1 Servlet抽取的目的

在下项目之前，首先要进行Servlet的抽取工作，因为如果还是按照以前学习Servlet的时候的方式来写，那么每一个功能都要对应一个Servlet（用户注册，用户登录...），这样会导致Servlet很多，因此我们应该对servlet进行抽取，使每一类功能对应一个Servlet,如用户的所有方法共用一个servlet，商品共用一个servlet，订单再共用一个servlet......
### 1.2 Servlet抽取的方法

首先，在Servlet内部有一个方法 service ，通过看它的源码，我们会发现，它主要是在获取前端请求之前判断前端是通过什么方法发起请求（get、post...），然后再调用对应的方法（doGet、doPost...）处理请求，我们也可以模拟这种方法，因为前台要请求一个类的时候是通过这个类的urlPatterns 来请求到这个类，请求到这个类之后，会先去执行它的service方法，这时候我们覆写它的service方法，然后再在请求的url中添加一个参数method=“方法名”，在service方法中我们获取对应的url中的method的值，然后通过反射调用对应的方法，说的可能不太清楚，下面画图说明：
![image](https://github.com/feihhh/store/raw/master/img4readme/servletHandle.png)
最后，我们在要执行的具体方法中，执行完具体的业务，返回一个字符串，这个字符串就是要跳转的网页（或者说是链接），然后在MyServlet中获取方法返回值，统一跳转页面。

# 2、项目模块
### 2.1 IndexServlet    ---  加载首页的相关操作

由于上面说了要进行Servlet抽取，所以要想访问首页，也必须要写一个参数method，例如method=index（http://localhost:8080/store/index?method=index ），然后在index方法中跳转到首页，但是这用操作太麻烦了，我们想的是希望访问首页的时候不输入method=？就可以到达首页，此时，我们可以在覆写的service方法中判断，如果url中的参数method为空，就让它等于index，这样只要没有输入method（http://localhost:8080/store/index ），就跳转到首页，也可以保证代码的健壮性，避免出现空指针异常。
其次，在上面的首页中可以看到，加载首页的时候同时要加载最新商品和热门商品，这时需要调用底层的相关业务查询，然后将查询的结果放在requset域对象中，具体流程如下图：
![image](https://github.com/feihhh/store/raw/master/img4readme/index.png)

### 2.2 UserServlet

#### 2.2.1 登录模块：

![image](https://github.com/feihhh/JavaWeb-/raw/master/img4Readme/login.png)

#### 2.2.2 注册模块：

![image](https://github.com/feihhh/JavaWeb-/raw/master/img4Readme/regist.png)

#### 2.2.3 激活模块：

请求的url:http://localhost:8080/store/user?method=activeUser&code=8728533DDBFE45FE8344A4A3A3C21F23

其中 user?method=activeUser 表示请求到UserServlet类里面的activeUser方法，code表是的是激活码（是一个随机不重复的字符串）

激活逻辑：

- 首先，通过url获取激活码
- 根据激活码获取用户信息
  - 如果用户信息为空，提示不存在该用户
  - 如果不为空，继续
- 判断用户状态是否已经激活了，如果已经激活，提示用户，否则继续
- 将数据库中的用户状态设置为激活状态，将激活码设置为空（因为激活码只会使用这一次）
- 激活成功，提示用户，可以登录了，并提供登录的超链接

![image](https://github.com/feihhh/JavaWeb-/raw/master/img4Readme/active.PNG)

#### 2.2.4 退出模块

将session中存储的用户信息删除，跳转到首页，在首页将用户登录状态变为未登录状态

未登录：

![image](https://github.com/feihhh/JavaWeb-/raw/master/img4Readme/exitStatus.png)

登录之后：

![image](https://github.com/feihhh/JavaWeb-/raw/master/img4Readme/loginStatus.PNG)

### 2.3 ProductServlet  商品模块

2.3.1 通过商品pid查询商品

2.3.1 通过页面和分类cid查询商品

以上者两部分流程类似，如下如：

![image](https://github.com/feihhh/JavaWeb-/raw/master/img4Readme/product.png)

## 2.4 CategoryServlet  商品分类模块

通过cid查询商品

流程和product的类似，调用底层对应的业务方法，最后将结果展示在界面

## 2.5 CartServlet  购物车模块

- 此模块实现的功能：
  - 首先，需要登录之后才能进入购物车模块（登录之后会将当前用户放在session域中）
  - 将某个商品加入购物车
  - 将某个商品从购物车中删除
  - 某个商品数量的增加一个
  - 某个商品的数量减少一个
  - 清空购物车

- 判断登录：
  - 从session中获取当前User对象，如果为空，表示没有登录，跳转到提示页面，让用户登录，并提供登录的超链接
- 商品加入购物车：
  - 首先，我是将购物车的信息存放在session中的，购物车中的信息以map的形式存放，其中key表示商品id，value为一个商品条目
  - 从session中获取购物车，并判断是否为空
    - 如果为空，表示当前购物车中还没有商品，直接new一个Map，然后将当前商品id，商品信息put进map里面
    - 如果不为空
      - 判断购物车中是否已经有当前商品了，如果有，只需要改变这个商品的数量以及钱数，如果没有，在把商品数量，钱数计算了，然后将商品id，商品条目信息put进Map
  - 最后将Map放在购物车对象中
  - 然后将购物车放进session域中，并设置session存活时间
- 购物车中删除商品
  - 从session中获取购物车信息
  - 获取Map集合
  - 通过前台传过来的pid（要删除的商品id），直接将其从Map中删除
  - 把Map放购物车中，把购物车放session 中
  - 返回购物车界面
- 商品数量加一
  - 获取前台传过来的pid（要加一个的商品id）
  - 从session中获取购物车，然后获取Map集合
  - 通过pid获取Map中对应的商品条目（pid对应的value）
  - 将它的数量加一，钱数增加一个商品的价格，购物车总数加一，总价钱加一个商品的钱数
  - 把Map放购物车中，把购物车放session中，
  - 返回购物车界面
- 商品数量减一：
  - 前面的步骤基本都一样
  - 中间判断对应pid的数量是否为1，如果是1，减一就没了，直接将pid对应的商品删除
  - 如果不是1，直接获取对应的商品条目，数量减一，小计的钱数减一个这个商品的钱数，在计算购物车的总价钱，购物车中商品总数减一
  - 后面的步骤也一样，最后返回购物车界面
- 清空购物车：
  - 从session中获取购物车信息
  - 将session中的购物车删除
  - 返回购物车界面

## 2.6 订单模块 - OrderServlet

- 实现的功能：
  - 在购物车中点击立即下单，生成一个订单信息的列表
  - 通过在界面填了地址，收件人等信息之后，点击确认订单，订单创建成功（但是不能支付）
  - 登录之后再购物车界面有超链接，查询所有订单，所有订单分页显示



readme未完待续...