package com.zyj.springboot_test.test.groovy

String scriptDir = new File(getClass().protectionDomain.codeSource.location.path).parent
def p = ~/.*\.jar/
new File("$scriptDir/").eachFileMatch(p){ f ->
    this.class.classLoader.rootLoader.addURL(f.toURL())
}
this.class.classLoader.rootLoader.addURL(new File(scriptDir).toURL())

def test = Class.forName("com.zyj.springboot_test.test.groovy.TestByGroovy")
test.hello()
