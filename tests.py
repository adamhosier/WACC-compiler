from subprocess import Popen, call, PIPE, STDOUT
import os

numtests = 0
numpasses = 0

def run_test(f):
    global numtests, numpasses;
    print("Testing: {0}".format(path))
    p = Popen(["java", "-classpath", "../lib/antlr-4.4-complete.jar:.", 
        "Main", "run"], stdin=PIPE, stdout=PIPE, stderr=STDOUT);
          
    output, err = p.communicate(f.read())
    exit = p.returncode

    numtests += 1
    if exit == 0:
         numpasses += 1
    else:
        print(output.decode("utf-8"))
        print("======== TEST {0} {1} ========".format(
            numtests, "PASSED" if exit == 0 else "FAILED"))
        print()
        print("Return code: {0}".format(p.returncode))
        print()
        print()


# compile
print("Running make")
r = call(["make"], stdout=PIPE, stderr=STDOUT)

# bail on build failure
if r != 0:
    print("Make failed")
    sys.exit(-1)

# work in java bin directory
os.chdir("bin")

testdir = "../examples/valid/expressions"

for subdir, dirs, files in os.walk(testdir):
    for f in files:
        path = os.path.join(subdir, f)
        with open(path, 'rb') as testfile:
            run_test(testfile)


print()
print("======== TEST RESULTS ========")
print("Tests passed ({0}/{1})".format(numpasses, numtests))
