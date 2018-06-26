# FrameX
[![CircleCI](https://circleci.com/gh/tweag/asterius/tree/master.svg?style=shield)](https://circleci.com/gh/neutronest/framex/tree/master)
[![Join the chat at https://gitter.im/framex-member/Lobby](https://badges.gitter.im/framex-member/Lobby.svg)](https://gitter.im/framex-member/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)



# Introduction



# Use Guide



load a data frame from two-dimensional list / vector with header



```scala
import com.framex.core.{FrameX}
val ll = List(List(1, 2, 3, 4, 5),
      List("A", "B", "C", "D", "E"),
      List("2015-01-10", "2017-08-22", "2016-03-03", "2011-02-02", "2017-02-12"),
      List("Tom", "Axiba Warning", "Dong Chao", "Zhang zhi hao", "zzz"),
      List("Man", "Woman", "Woman", "Man", "Man"))
val columnNames = List("id", "word", "date", "name", "gender")
val df = FrameX(ll, columnNames)
```

it will look's like:

```
| id       | word        | date                 | name                    | gender
--------------------------------------------------------------------------------------------
 | ExInt(1) | ExString(A) | ExString(2015-01-10) | ExString(Tom)           | ExString(Man)
 | ExInt(2) | ExString(B) | ExString(2017-08-22) | ExString(Axiba Warning) | ExString(Woman)
 | ExInt(3) | ExString(C) | ExString(2016-03-03) | ExString(Dong Chao)     | ExString(Woman)
 | ExInt(4) | ExString(D) | ExString(2011-02-02) | ExString(Zhang zhi hao) | ExString(Man)
 | ExInt(5) | ExString(E) | ExString(2017-02-12) | ExString(zzz)           | ExString(Man)
```









# Dev status

Release 0.1.0 features (Todo): 

- [x] basic Series structure design
- [x] basic DataFrame structrure design
- IO api from: List / Matrix / csv / json / Nd4j
  - [x] from list
  - [x] from matrix
  - [ ] from csv
  - [ ] from json
  - [ ] from nd4j matrix
  - [ ] to list
  - [ ] to matrix
  - [ ] to csv
  - [ ] to json
  - [ ] to nd4j matrix
- Index features:
  - [x] Framex.head : return first n rows
  - [x] Framex.tail: return last n rows
  - [x] Framex.loc :
  - [ ] Framex.insert :
  - [ ] Framex.where
  - [ ] Framex.query
- Functional application
  - [ ] Framex.applyFunc
  - [x] Framex.applyMap
  - [x] Framex.aggregate
  - [x] Framex.groupby
- Binary Ops
  - [ ] add
  - [ ] sub
  - [ ] mul
  - [ ] div
  - [ ] radd
  - [ ] rsub
  - [ ] rmul
  - [ ] rdiv
  - [ ] pow
  - [ ] rpow
  - [ ] floordiv (Integer division)
  - [ ] rfloordiv
  - [ ] dot
  - [ ] lt
  - [ ] gt
  - [ ] le
  - [ ] ge
  - [ ] eq
- Stats
  - [ ] abs
  - [ ] all
  - [ ] clip
  - [ ] clip_lower
  - [ ] clip_upper
  - [x] max
  - [x] mean
  - [x] median
  - [x] sum
  - [ ] std
  - [ ] var
- Miss data handler
  - [ ] dropna
  - [ ] fillna
  - [ ] replace
- DB-like Ops
  - [ ] append
  - [ ] assign
  - [ ] join
  - [ ] merge
  - [ ] update