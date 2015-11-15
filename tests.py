from subprocess import Popen, call, PIPE, STDOUT
import os

numtests = 0
numpasses = 0

def run_test(path, expectedErrorCode):
    global numtests, numpasses 
    print("Testing: {0}".format(path))
    p = Popen(["./compile", path], stdin=PIPE, stdout=PIPE, stderr=STDOUT);
          
    output, err = p.communicate()
    exit = p.returncode

    numtests += 1
    if exit == expectedErrorCode or exit == expectedErrorCode - 100:
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

validtestdir = "examples/valid/while"
invalidtestdir = "examples/invalid"

print("========== VALID TESTS ==========")
for subdir, dirs, files in os.walk(validtestdir):
    for f in files:
        path = os.path.join(subdir, f)
        _, extension = os.path.splitext(path)
        if(extension == ".wacc"):
            run_test(path, 0)

print("========== INVALID TESTS ==========")
for subdir, dirs, files in os.walk(invalidtestdir):
    for f in files:
        path = os.path.join(subdir, f)
        _, extension = os.path.splitext(path)
        if(extension == ".wacc"):
            run_test(path, 200)


print()
print("========== TEST RESULTS ==========")
print("Tests passed ({0}/{1})".format(numpasses, numtests))

