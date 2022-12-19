package utility.helper;

import common.ExtentTestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

public class AssertionsUtil {

    private final SoftAssert assertion = new SoftAssert();
    private static final Logger logger
            = LoggerFactory.getLogger(AssertionsUtil.class);

    /**
     * Library method to soft assert equals and print allure report [AssertionsUtil]
     *
     * @param left (Any) Expected item to compare
     * @param right (Any) Actual item to compare
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertEquals(Object left, Object right, String description) {
        this.logger.debug("assertEquals - Start");
        boolean blnCompare = false;
        if(left == right){
            blnCompare = true;
        }
        String status;
        String desc;
        if (blnCompare)
            status = "PASSED" ;
        else
            status = "FAILED";
        if(description==null||description.equals("")){
            desc = "Comparing the item '"+left+"'";
        }
        else{
            desc = description;
        }

       assertion.assertTrue(blnCompare, desc);
        ExtentTestManager.printAssertion("Assert equals", status, desc, left, right, AssertionsUtil.class);
        logger.debug("assertEquals - End");
        return blnCompare;
    }

    /**
     * Library method to soft assert contains and print allure report [AssertionsUtil]
     *
     * @param left (Any) Expected item to compare
     * @param right (Any) Actual item to compare
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertContains(String left, String right, String description) {
        logger.debug("assertContains - Start");
        boolean blnCompare = left.contains(right);
        String status = "FAILED";
        String desc = description;
        if(blnCompare)
        {
            status="PASSED";
        }
        if(description==null||description.equals("")){
            desc = "Comparing the item '"+left+"'";
        }

        assertion.assertTrue(blnCompare, description);
        ExtentTestManager.printAssertion("Assert contains", status, desc, left, right, AssertionsUtil.class);
        logger.debug("assertContains - End");
        return blnCompare;
    }

    /**
     * Library method to soft assert does not contains and print allure report [AssertionsUtil]
     *
     * @param left (Any) Expected item to compare
     * @param right (Any) Actual item to compare
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertDoesNotContains(String left, String right, String description) {
        logger.debug("assertDoesNotContains - Start");
        boolean blnCompare = !left.contains(right);
        String status = "FAILED";
        String desc = description;
        if(blnCompare)
        {
            status="PASSED";
        }
        if(description==null||description.equals("")){
            desc = "Comparing the item '"+left+"'";
        }

        assertion.assertTrue(blnCompare, description);
        ExtentTestManager.printAssertion("Assert contains", status, desc, left, right, AssertionsUtil.class);
        logger.debug("assertDoesNotContains - End");
        return blnCompare;
    }

    /**
     * Library method to soft assert not equals and print allure report [AssertionsUtil]
     *
     * @param left (Any) Expected item to compare
     * @param right (Any) Actual item to compare
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertNotEquals(Object left, Object right, String description) {
        logger.debug("assertNotEquals - Start");
        boolean blnCompare = (left != right);
        String status = "FAILED";
        String desc = description;
        if(blnCompare)
        {
            status="PASSED";
        }
        if(description==null||description.equals("")){
            desc = "Comparing the item '"+left+"'";
        }

        assertion.assertTrue(blnCompare, description);
        ExtentTestManager.printAssertion("Assert not equals", status, desc, left, right, AssertionsUtil.class);
        logger.debug("assertNotEquals - End");
        return blnCompare;
    }

    /**
     * Library method to close the soft assertion [AssertionsUtil]
     */
    public void closeAssert() {
        logger.debug("closeAssert - Start");
        this.assertion.assertAll();
        logger.debug("closeAssert - End");
    }

    /**
     * Library method to soft assert notNull and print allure report [AssertionsUtil]
     *
     * @param obj (Boolean) Expected item should not null
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertNotNull(Object obj, String description) {
        logger.debug("assertNotNull - Start");
        boolean blnCompare = (obj != null);
        String status = "FAILED";
        String desc = description;
        if(blnCompare)
        {
            status="PASSED";
        }
        if(description==null||description.equals("")){
            desc = "Comparing the item '"+obj+"'";
        }
        assertion.assertTrue(blnCompare, desc);
        ExtentTestManager.printAssertion("Assert null", status, desc, obj, null, AssertionsUtil.class);
        logger.debug("assertNotNull - End");
        return blnCompare;
    }

    /**
     * Library method to soft assert assertTrue and print allure report [AssertionsUtil]
     *
     * @param condition (Boolean) Expected item to compare
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertTrue(Boolean condition, String description) {
        logger.debug("assertTrue - Start");
        String status = "FAILED";
        String desc = description;
        if(condition)
        {
            status="PASSED";
        }
        if(description==null||description.equals("")){
            desc = "Result should be true '"+condition+"'";
        }
        assertion.assertTrue(condition, desc);
        ExtentTestManager.printAssertion("Assert true", status, desc, condition, true, AssertionsUtil.class);
        logger.debug("assertTrue - End");
        return condition;
    }

    /**
     * Library method to soft assert assertFalse and print allure report [AssertionsUtil]
     *
     * @param condition (Boolean) Expected item to compare
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertFalse(Boolean condition, String description) {
        logger.debug("assertFalse - Start");
        String status = "FAILED";
        String desc = description;
        if(!condition)
        {
            status="PASSED";
        }
        if(description==null||description.equals("")){
            desc = "Result should be true '"+condition+"'";
        }
        assertion.assertTrue(!condition, desc);
        ExtentTestManager.printAssertion("Assert false", status, desc, condition, false, AssertionsUtil.class);
        logger.debug("assertFalse - End");
        return !condition;
    }

    /**
     * Library method to soft assert null and print allure report [AssertionsUtil]
     *
     * @param obj (Boolean) Expected item should be null
     * @param description (String) Description to print. If null it will print default message
     * @return (Boolean) assertion status
     */
    public Boolean assertNull(Object obj, String description) {
        logger.debug("assertNull - Start");
        boolean blnCompare = (obj == null);
        String status = "FAILED";
        String desc = description;
        if(blnCompare)
        {
            status="PASSED";
        }
        if(description==null||description.equals("")){
            desc = "Comparing the item '"+obj+"'";
        }
        assertion.assertTrue(blnCompare);
        ExtentTestManager.printAssertion("Assert null", status, desc, obj, null, AssertionsUtil.class);
        logger.debug("assertNull - End");
        return blnCompare;
    }
}
