# MacOS:
#   $ brew install gcc swig
# Debian-based Linux:
#   $ apt-get -y install build-essential gfortran swig ant openjdk-11-jdk-headless

CC = gcc
FC = gfortran
LIB_DEPENDENCIES = -lgfortran

SRC = src
TARGET = target
DIST = $(TARGET)/generated-sources/dist
BUILD = build

CFLAGS_DEBUG = -fPIC -Wall -g
CFLAGS_RELEASE = -fPIC -Wall -O3
CFLAGS = $(CFLAGS_RELEASE)
FFLAGS_DEBUG = -fPIC -Wall -fexceptions -g
FFLAGS_RELEASE = -fPIC -Wall -fexceptions -O3
FFLAGS = $(FFLAGS_RELEASE)

LIB_NAME = liblbfgsb_wrapper.so
JNI_ARCH = $(shell uname -s | tr '[:upper:]' '[:lower:]')

ifeq ($(JNI_ARCH),darwin)
  CELLAR_GCC_LIB_DIR = -L $(shell find /usr/local/Cellar -name libgfortran.dylib -exec dirname {} +)
else
  CELLAR_GCC_LIB_DIR =
endif

LDFLAGS_linux = -s -Wl,--version-script=$(SRC)/lbfgsb_wrapper.map
LDFLAGS_darwin = -Wl
LDFLAGS = ${LDFLAGS_${JNI_ARCH}}

JAVA_HOME = $(shell java -XshowSettings:properties -version 2>&1 > /dev/null | grep 'java.home' | cut -s -d ' ' -f 7)
JNI_ARCH_DIR = $(JAVA_HOME)/include/$(JNI_ARCH)

all: $(DIST)

init:
	rm -rf $(BUILD) $(DIST) $(TARGET)
	mkdir -p $(BUILD) $(DIST) $(TARGET)

$(DIST): init $(DIST)/$(LIB_NAME) $(DIST)/test_prog

$(DIST)/$(LIB_NAME): $(BUILD)/lbfgsb_wrapper.o $(BUILD)/solver.o  $(BUILD)/blas.o $(BUILD)/linpack.o $(BUILD)/timer.o $(SRC)/lbfgsb_wrapper.i
	mkdir -p $(DIST)/src/lbfgsb/jniwrapper
	swig -java -package lbfgsb.jniwrapper -outdir $(DIST)/src/lbfgsb/jniwrapper $(SRC)/lbfgsb_wrapper.i
	$(CC) -I $(JAVA_HOME)/include -I $(JNI_ARCH_DIR) -fPIC -c $(SRC)/lbfgsb_wrapper_wrap.c -o $(BUILD)/lbfgsb_wrapper_wrap.o #JAVA_HOME environment variable has to be set
	$(CC) $(LDFLAGS) $(CELLAR_GCC_LIB_DIR) -shared -o $(DIST)/$(LIB_NAME) $(BUILD)/lbfgsb_wrapper_wrap.o $(BUILD)/lbfgsb_wrapper.o $(BUILD)/solver.o $(BUILD)/blas.o $(BUILD)/linpack.o $(BUILD)/timer.o -lm $(LIB_DEPENDENCIES)

$(DIST)/test_prog: $(BUILD)/test_prog.o $(BUILD)/lbfgsb_wrapper.o $(BUILD)/solver.o $(BUILD)/blas.o $(BUILD)/linpack.o $(BUILD)/timer.o
	$(CC) $(CELLAR_GCC_LIB_DIR) -o $(DIST)/test_prog $(BUILD)/test_prog.o $(BUILD)/lbfgsb_wrapper.o $(BUILD)/solver.o $(BUILD)/blas.o $(BUILD)/linpack.o $(BUILD)/timer.o -lm $(LIB_DEPENDENCIES)

$(BUILD)/test_prog.o: $(SRC)/test_prog.c
	$(CC) $(CFLAGS) -c $(SRC)/test_prog.c -o $(BUILD)/test_prog.o

$(BUILD)/lbfgsb_wrapper.o: $(SRC)/lbfgsb_wrapper.c $(SRC)/lbfgsb_wrapper.h
	$(CC) $(CFLAGS) -c $(SRC)/lbfgsb_wrapper.c -o $(BUILD)/lbfgsb_wrapper.o

$(BUILD)/solver.o: $(SRC)/fortran_src/solver.f90
	$(FC) $(FFLAGS) -c $(SRC)/fortran_src/solver.f90 -o $(BUILD)/solver.o

$(BUILD)/blas.o: $(SRC)/fortran_src/blas.f90
	$(FC) $(FFLAGS) -c $(SRC)/fortran_src/blas.f90 -o $(BUILD)/blas.o

$(BUILD)/linpack.o: $(SRC)/fortran_src/linpack.f90
	$(FC) $(FFLAGS) -c $(SRC)/fortran_src/linpack.f90 -o $(BUILD)/linpack.o

$(BUILD)/timer.o: $(SRC)/fortran_src/timer.f90
	$(FC) $(FFLAGS) -c $(SRC)/fortran_src/timer.f90 -o $(BUILD)/timer.o

clean:
	rm -rf $(BUILD) $(DIST) target
	rm -f $(SRC)/fortran_src/iterate.dat
	rm -f $(SRC)/lbfgsb_wrapper_wrap.c
