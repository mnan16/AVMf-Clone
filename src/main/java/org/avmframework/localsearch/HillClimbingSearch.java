package org.avmframework.localsearch;

import org.avmframework.TerminationException;
import org.avmframework.objective.ObjectiveValue;

public class HillClimbingSearch extends LocalSearch {

	  protected ObjectiveValue initial;
	  protected ObjectiveValue last;
	  protected ObjectiveValue next;
	  protected int modifier;
	  protected int num;
	  protected int dir;
	  
	  protected boolean changeDirection() throws TerminationException {
		    // evaluate left move
		    var.setValue(num - modifier);
		    ObjectiveValue left = objFun.evaluate(vector);

		    // evaluate right move
		    var.setValue(num + modifier);
		    ObjectiveValue right = objFun.evaluate(vector);

		    // find the best direction
		    boolean leftBetter = left.betterThan(initial);
		    boolean rightBetter = right.betterThan(initial);
		    if (leftBetter) {
		      dir = -1;
		    } else if (rightBetter) {
		      dir = 1;
		    } else {
		      dir = 0;
		    }

		    // set num and the variable according to the best edge
		    num += dir * modifier;
		    var.setValue(num);

		    // set last and next objective values
		    last = initial;
		    if (dir == -1) {
		      next = left;
		    } else if (dir == 1) {
		      next = right;
		    } else if (dir == 0) {
		      next = initial;
		    }

		    return dir != 0;
		  }
	  
	@Override
	protected void performSearch() throws TerminationException {
		//initialize
		initial = objFun.evaluate(vector);
		modifier = 1;
	    num = var.getValue();
	    dir = 0;
		if(changeDirection()) {
			while (next.betterThan(last)) {
			      last = next;
			       // do hill climbing
			      
			      next = objFun.evaluate(vector);
			      if (!next.betterThan(last)) {
			    	  changeDirection();
			          num -= modifier * dir;
			          var.setValue(num);
			        }
			}
		
		}
		
	}

}
