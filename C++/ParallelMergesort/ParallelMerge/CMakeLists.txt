cmake_minimum_required(VERSION 2.8)

SET(CMAKE_CXX_FLAGS "-std=c++0x")

project(ParallelMerge)
add_executable(${PROJECT_NAME} "main.cpp")
