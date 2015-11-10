from subprocess import Popen, call, PIPE, STDOUT
import os

numtests = 0
numpasses = 0

def run_test(f):
    print("Testing: {0}".format(path))
    p = Popen(["java", "-classpath", "../lib/antlr-4.4-complete.jar:.", 
        "Main", "run"], stdin=PIPE, stdout=PIPE, stderr=STDOUT);
          
    output, err = p.communicate(f.read())
    print(output.decode("utf-8"))

# compile
print("Running make")
r = call(["make"], stdout=PIPE, stderr=STDOUT)

# bail on build failure
if r != 0:
    print("Make failed")
    sys.exit(-1)

# work in java bin directory
os.chdir("bin")

testdir = "../examples/valid"

for subdir, dirs, files in os.walk(testdir):
    for f in files:
        numtests += 1
        path = os.path.join(subdir, f)
        with open(path, 'rb') as testfile:
            run_test(testfile)


print()
print("======== TEST RESULTS ========")
print("Tests passed ({0}/{1})".format(numpasses, numtests))

