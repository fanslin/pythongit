#!/usr/bin/python
# coding=utf-8
# from abstest import my_abs来导入my_abs()函数
def my_abs(x):
    if not isinstance(x, (int, float)):
        raise TypeError('bad operand type')
    if x >= 0:
        return x
    else:
        return -x
