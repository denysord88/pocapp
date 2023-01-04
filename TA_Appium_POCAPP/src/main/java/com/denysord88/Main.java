package com.denysord88;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        XmlSuite suite = new XmlSuite();
        suite.setName("TestNG XML created from Java Code");

        XmlTest test = new XmlTest(suite);
        test.setName("Test on iOS v16.1; Device name: iPhone 13 Pro Max;");

        List<XmlPackage> packages = new LinkedList<>();
        packages.add(new XmlPackage("tests.*"));
        packages.add(new XmlPackage("tests.*.*"));
        test.setXmlPackages(packages);

        TestNG runner = new TestNG();
        runner.setUseDefaultListeners(false);
        TestListenerAdapter tla = new TestListenerAdapter();
        runner.addListener(tla);

        List<XmlSuite> suiteFiles = new ArrayList<>();
        suiteFiles.add(suite);
        runner.setXmlSuites(suiteFiles);

        runner.run();
    }
}