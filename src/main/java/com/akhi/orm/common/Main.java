/**---------------------------------------------------------------------*
 * filename     : Main.java
 * date         : Apr 26, 2013
 * Author       : akhilesh
 *
 * Changes      : See javadoc object description
 *----------------------------------------------------------------------*
 */
/**
 *
 */
package com.akhi.orm.common;

import com.akhi.orm.transactions.Manager;
import com.akhi.orm.transactions.ORMSession;

/*-----------------------------------------------------------------------*//**
 * 
 * @author
 *         akhilesh
 * @version %R%
 * 
 * <BR>
 *          <B>Revision History:</B><BR>
 * 
 *          <PRE>
 *     Date        | User  | Description
 *     ------------------------------------------------------------
 *     Apr 26, 2013 | akhilesh   | Original
 * </PRE>
 * 
 * @since JDK1.4.2_07
 * 
 */
/*-----------------------------------------------------------------------*/

public class Main {

	public static void main(String[] args) {
		System.out.println("My ORM");
		Manager manager = new Manager();
		ORMSession session = manager.getSession();
		Employee emp = new Employee("Akhilesh", "Developer");
		try {
			session.save(emp);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		manager.closeSession(session);
	}

}
