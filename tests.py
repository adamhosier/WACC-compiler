from subprocess import Popen, call, PIPE, STDOUT
import os

numtests = 0
numpasses = 0

def run_test(path, expectedExit):
    global numtests, numpasses 

    numtests += 1

    fname = os.path.splitext(os.path.basename(path))[0]

    print("Testing: {0}".format(path))
    
    # compile
    p = Popen(["./compile", path], stdin=PIPE, stdout=PIPE, stderr=STDOUT);
    p.communicate()
    if(p.returncode != 0):
        print("TEST {0} FAILED: COULD NOT COMPILE".format(numtests))
        return

    # assemble
    p = Popen(["arm-linux-gnueabi-gcc", "-o", fname, "-mcpu=arm1176jzf-s",
        "-mtune=arm1176jzf-s", fname + ".s"], stdin=PIPE, stdout=PIPE, 
        stderr=STDOUT)
    p.communicate() 
    if(p.returncode != 0):
        print("TEST {0} FAILED: COULD NOT ASSEMBLE")
        os.remove(fname + ".s")
        return

    # run
    p = Popen(["qemu-arm", "-L", "/usr/arm-linux-gnueabi/", fname], stdin=PIPE,
            stdout=PIPE, stderr=STDOUT) 
    output, err = p.communicate()

    # format output
    output = output.decode("utf-8")
    output = "#empty#" if output == "" else output

    exit = p.returncode

    if exit == expectedExit or expectedExit == "#n/a#":
        numpasses += 1
    else:
        print("======== TEST {0} FAILED ========".format(numtests))
        print()
        print("Output: ")
        print(output)
        print("Expected exit:\t{0}".format(expectedExit))
        print("But got:\t{0}".format(exit))
        print()

    # clean up
    os.remove(fname)
    os.remove(fname + ".s")


# compile
print("Running make")
r = call(["make"], stdout=PIPE, stderr=STDOUT)

# bail on build failure
if r != 0:
    print("Make failed")
    sys.exit(-1)

testdir = "examples/valid/IO"

print("========== RUNNING TESTS ==========")
for subdir, dirs, files in os.walk(testdir):
    for f in files:
        path = os.path.join(subdir, f)
        _, extension = os.path.splitext(path)
        if(extension == ".wacc"):
            expectedExit = "#n/a#"
            with open(path, "r") as fi:
                readExit = False
                for i, line in enumerate(fi):
                    line = line.strip()
                    if line == "begin": break
                    if readExit:
                        expectedExit = int(line[2:])
                        readExit = False
                    if line == "# Exit:": readExit = True
            run_test(path, expectedExit)

print()
print("========== TEST RESULTS ==========")
print("Tests passed ({0}/{1})".format(numpasses, numtests))

