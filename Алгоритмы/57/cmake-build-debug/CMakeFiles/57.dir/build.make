# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.7

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/57.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/57.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/57.dir/flags.make

CMakeFiles/57.dir/main.cpp.o: CMakeFiles/57.dir/flags.make
CMakeFiles/57.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/57.dir/main.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/57.dir/main.cpp.o -c /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/main.cpp

CMakeFiles/57.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/57.dir/main.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/main.cpp > CMakeFiles/57.dir/main.cpp.i

CMakeFiles/57.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/57.dir/main.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/main.cpp -o CMakeFiles/57.dir/main.cpp.s

CMakeFiles/57.dir/main.cpp.o.requires:

.PHONY : CMakeFiles/57.dir/main.cpp.o.requires

CMakeFiles/57.dir/main.cpp.o.provides: CMakeFiles/57.dir/main.cpp.o.requires
	$(MAKE) -f CMakeFiles/57.dir/build.make CMakeFiles/57.dir/main.cpp.o.provides.build
.PHONY : CMakeFiles/57.dir/main.cpp.o.provides

CMakeFiles/57.dir/main.cpp.o.provides.build: CMakeFiles/57.dir/main.cpp.o


# Object files for target 57
57_OBJECTS = \
"CMakeFiles/57.dir/main.cpp.o"

# External object files for target 57
57_EXTERNAL_OBJECTS =

57: CMakeFiles/57.dir/main.cpp.o
57: CMakeFiles/57.dir/build.make
57: CMakeFiles/57.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable 57"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/57.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/57.dir/build: 57

.PHONY : CMakeFiles/57.dir/build

CMakeFiles/57.dir/requires: CMakeFiles/57.dir/main.cpp.o.requires

.PHONY : CMakeFiles/57.dir/requires

CMakeFiles/57.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/57.dir/cmake_clean.cmake
.PHONY : CMakeFiles/57.dir/clean

CMakeFiles/57.dir/depend:
	cd /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57 /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57 /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/cmake-build-debug /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/cmake-build-debug /Users/vladasheleg/Documents/Git/Projects/Алгоритмы/57/cmake-build-debug/CMakeFiles/57.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/57.dir/depend

