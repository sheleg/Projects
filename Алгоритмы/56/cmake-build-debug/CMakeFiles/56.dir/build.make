# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.6

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
CMAKE_SOURCE_DIR = /Users/vladasheleg/Documents/Projects/С++/Algorithm/56

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/56.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/56.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/56.dir/flags.make

CMakeFiles/56.dir/main.cpp.o: CMakeFiles/56.dir/flags.make
CMakeFiles/56.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/vladasheleg/Documents/Projects/С++/Algorithm/56/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/56.dir/main.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/56.dir/main.cpp.o -c /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/main.cpp

CMakeFiles/56.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/56.dir/main.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/main.cpp > CMakeFiles/56.dir/main.cpp.i

CMakeFiles/56.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/56.dir/main.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/main.cpp -o CMakeFiles/56.dir/main.cpp.s

CMakeFiles/56.dir/main.cpp.o.requires:

.PHONY : CMakeFiles/56.dir/main.cpp.o.requires

CMakeFiles/56.dir/main.cpp.o.provides: CMakeFiles/56.dir/main.cpp.o.requires
	$(MAKE) -f CMakeFiles/56.dir/build.make CMakeFiles/56.dir/main.cpp.o.provides.build
.PHONY : CMakeFiles/56.dir/main.cpp.o.provides

CMakeFiles/56.dir/main.cpp.o.provides.build: CMakeFiles/56.dir/main.cpp.o


# Object files for target 56
56_OBJECTS = \
"CMakeFiles/56.dir/main.cpp.o"

# External object files for target 56
56_EXTERNAL_OBJECTS =

56: CMakeFiles/56.dir/main.cpp.o
56: CMakeFiles/56.dir/build.make
56: CMakeFiles/56.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/vladasheleg/Documents/Projects/С++/Algorithm/56/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable 56"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/56.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/56.dir/build: 56

.PHONY : CMakeFiles/56.dir/build

CMakeFiles/56.dir/requires: CMakeFiles/56.dir/main.cpp.o.requires

.PHONY : CMakeFiles/56.dir/requires

CMakeFiles/56.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/56.dir/cmake_clean.cmake
.PHONY : CMakeFiles/56.dir/clean

CMakeFiles/56.dir/depend:
	cd /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/vladasheleg/Documents/Projects/С++/Algorithm/56 /Users/vladasheleg/Documents/Projects/С++/Algorithm/56 /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/cmake-build-debug /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/cmake-build-debug /Users/vladasheleg/Documents/Projects/С++/Algorithm/56/cmake-build-debug/CMakeFiles/56.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/56.dir/depend

