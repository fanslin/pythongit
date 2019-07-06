#!/usr/bin/python
# coding=utf-8
n = 1
while n <= 100:
	if n > 10:
		break
	# print(n)
	n = n + 1
print('END')


m = 0
while m < 10:
	m = m + 1
	if m % 2 == 0:
		continue
	print(m)