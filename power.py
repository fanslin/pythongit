#!/usr/bin/python
# coding=utf-8
# from abstest import my_abs来导入my_abs()函数
# x 的 n 次方

def power(x,n = 2):
	s = 1
	while n > 0:
		n = n - 1
		s = s * x
	return s
