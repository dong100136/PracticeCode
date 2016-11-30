from pandas import *
import numpy
import matplotlib.pyplot as plt
import pylab
data = read_csv('data.txt',sep='#',names=('id','name','year','rating','countries'))
data2  = data.groupby('countries')