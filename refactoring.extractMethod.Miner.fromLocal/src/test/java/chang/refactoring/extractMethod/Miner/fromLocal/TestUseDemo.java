package chang.refactoring.extractMethod.Miner.fromLocal;

import org.junit.Test;


import baseMain.test004;
import junit.framework.Assert;

public class TestUseDemo {

	@Test
	public void testUseDemo(){
		String repository = "C:\\Users\\chang\\Desktop\\refactoringDemo-02";

		try {
			test004.main2(new String[] {repository});
			Assert.assertEquals("测试通过", 1, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertEquals("测试失败", 1, 2);

		}
	}
}
