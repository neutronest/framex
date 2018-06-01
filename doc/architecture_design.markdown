## FrameX Doc 

What is Framex

In many real-world situations, we met the requirement that "transform the algorithms to Java enviroments which implements by python ecosystem (such as numpy, pandas, scikit-learn) originally". We soon found a hard problem: no DataFrame foundations can be used in Java. 

In python, a DataFrame, which provided by pandas, is one of the most important basic foundations in data science.

It become a bridge between raw matrix aggregation , statistic analysis and machine learning models. 



DevRoadmap

Release 0.1.0 features (Todo): 

* basic Series structure design
* basic DataFrame structrure design
* IO api from: List / Matrix / csv / json / Nd4j
* Index features:
  * Framex.head : return first n rows
  * Framex.tail: return last n rows
  * Framex.loc :
  * Framex.insert :
  *  Framex.where
  * Framex.query
  * ​
* Function application
  * Framex.applyFunc
  * Framex.applyMap
  * Framex.aggregate
  * Framex.groupby
* Binary Ops
  * add
  * sub
  * mul
  * div
* Stats
  * abs
  * all
  * clip
  * clip_lower
  * clip_upper
  * max
  * mean
  * median
  * sum
  * std
  * var
* Miss data handler
  * dropna
  * fillna
  * replace
* DB-like Ops
  * append
  * assign
  * join
  * merge
  * update



TODO

Why framex

Framex is designed as a "pandas implementation in scala" in early time. It will support basic DataFrame operations such as  read from file, get data by row / column / index / axes. More advanced features will keep developed after first release published.

















Q1 目标

对标 Pandas, 实现基本的 Block, BlockManager, NDFrame, Series, DataFrame 中的基础功能，比如数据加载与存储，

一期先通过 SeqX 与 FrameX 的抽象，实现基础模块的简化版，可以为之后的扩展做准备。

基于基础模块支撑的上层功能: 

### 数据加载：

from ND4J

```scala
INDArray arr1 = Nd4j.create(new float[]{1,2,3,4},new int[]{2,2});
val fx1 = FrameX.fromNd4j(arr1)

```

from Compute.scala

```scala
val my2DArray: Tensor = Tensor(Array(Seq(1.0f, 2.0f, 3.0f), Seq(4.0f, 5.0f, 6.0f)))
val fx2 = FrameX.fromComputeScala(my2DArray)
```

from json

```scala
val jsonData: String = FrameX.fromJson("['foo': [1,2,3], 'bar': [4,5,6]]")
val fx3 = FrameX.fromJson(jsonData)
```

### 指定列名:

```scala
val jsonData: String = FrameX.fromJson("['foo': [1,2,3], 'bar': [4,5,6]]", columns=["A", "B"])
val fx3 = FrameX.fromJson(jsonData)


result:
	 A      B
1.  100.   400
2.  200.   500
3.  300.   600
```

### 索引：

行索引:

```
val jsonData: String = FrameX.fromJson("['foo': List[100,200,300], 'bar': [400,500,600]]", index=List[1,2,3])
val fx3 = FrameX.fromJson(jsonData)

result:
	foo    bar
1.  100.   400
2.  200.   500
3.  300.   600
```



列索引:

```scala
val jsonData: String = FrameX.fromJson("['foo': [1,2,3], 'bar': [4,5,6]]")
val fx3 = FrameX.fromJson(jsonData)
val fx4 = fx3[["bar", "foo"]]
```

### 数据 slicing

```

```

