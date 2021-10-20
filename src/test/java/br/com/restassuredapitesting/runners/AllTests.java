package br.com.restassuredapitesting.runners;


import br.com.restassuredapitesting.ClasseDeTeste;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(br.com.restassuredapitesting.suites.AllTests.class)
@Suite.SuiteClasses({
        ClasseDeTeste.class
})
public class AllTests {
}
