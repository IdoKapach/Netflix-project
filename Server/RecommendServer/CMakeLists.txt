cmake_minimum_required(VERSION 3.14)
project(Targil3)

# set and enforce C++ standard (Gtest requires C++14)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Enable testing
enable_testing()

# include the headers under the include directory
include_directories(include)

# add on option for compiling with coverage flags
option(COVERAGE "Enable coverage reporting" OFF)

if(COVERAGE)
    message(STATUS "Building with coverage information")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} --coverage")
    set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} --coverage")
endif()

# Add subdirectories
add_subdirectory(src)
add_subdirectory(test)

# Add pthread specifically for targil2
target_link_libraries(targil2 pthread)
