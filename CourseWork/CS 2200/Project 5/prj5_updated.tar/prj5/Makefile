# Makefile for Project 5 - Cache : Fall 2015

SUBMIT = cachesim.h cachesim.c cachesim_driver.c Makefile
CXXFLAGS := -g -Wall -std=c99 -lm -pedantic-errors
CXX=gcc

all: cachesim

cachesim: cachesim.o cachesim_driver.o
	$(CXX) -o cachesim cachesim.o cachesim_driver.o $(CXXFLAGS)

clean:
	rm -f cachesim *.o

submit: clean
	tar zcvf prj5-submit.tar.gz $(SUBMIT)
