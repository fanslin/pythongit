#!/usr/bin/python
# -*- coding: utf-8 -*-
height = 1.82
weight = 74.5
bmi = weight/(height*2)
print('BMI = %.2f' %bmi)
if bmi < 18.5:
    print('低于18.5：','过轻')
elif bmi >= 18.5 and bmi < 25:
    print('18.5-25：','正常')
elif bmi >= 25 and bmi < 28:
    print('25-28：','过重')
elif bmi >= 28 and bmi < 32:
    print('28-32：','肥胖')
else:
    print('高于32：','严重肥胖')
