#!/usr/bin/python
# coding=utf-8
names = ['a','b','c']
for name in names:
    print(name)

sum = 0
for x in [1,2,3,4,5]:
	sum = sum + x
print(sum)

sum = 0
for x in range(101):
	sum = sum + x
print(sum)

L = ['Bart', 'Lisa', 'Adam']
for x in L:
	print('Hello, %s!!!' %x)



d = {'a':'1', 'b':'2', 'c':'3'}
for key in d:
    print(key)
for value in d.values():
    print(value)

for k,v in d.items():
    print(k ,'=',v)

print([k + '=' + v for k,v in d.items()])

L = ['Hello', 'World', 18, 'Apple', None]
print([s.lower() for s in L if isinstance(s, str)])

#print("")
#for ch in 'ABC':
#    print(ch)

