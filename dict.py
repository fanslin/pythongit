#!/usr/bin/python
# coding=utf-8
d = {'A': 95, 'B':75, 'C':85}
s = d['A']
print (s)
d['D'] = 65
print(d.get('B'))
address = d.get("address", "address is Not Exit")
print(address)

s = set([1,2, 3, 4, 2, 4, 1])
print(s)
