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

	  protected int selectDirection() throws TerminationException{
		//Evaluate left move
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
		return dir;
	  }
	  
	//   protected boolean changeDirection() throws TerminationException {
	// 	    // evaluate left move
	// 	    var.setValue(num - modifier);
	// 	    ObjectiveValue left = objFun.evaluate(vector);

	// 	    // evaluate right move
	// 	    var.setValue(num + modifier);
	// 	    ObjectiveValue right = objFun.evaluate(vector);

	// 	    // find the best direction
	// 	    boolean leftBetter = left.betterThan(initial);
	// 	    boolean rightBetter = right.betterThan(initial);
	// 	    if (leftBetter) {
	// 	      dir = -1;
	// 	    } else if (rightBetter) {
	// 	      dir = 1;
	// 	    } else {
	// 	      dir = 0;
	// 	    }

	// 	    // set num and the variable according to the best edge
	// 	    num += dir * modifier;
	// 	    var.setValue(num);

	// 	    // set last and next objective values
	// 	    last = initial;
	// 	    if (dir == -1) {
	// 	      next = left;
	// 	    } else if (dir == 1) {
	// 	      next = right;
	// 	    } else if (dir == 0) {
	// 	      next = initial;
	// 	    }

	// 	    return dir != 0;
	// 	  }
	  
	@Override
	protected void performSearch() throws TerminationException {
		//initialize
		initial = objFun.evaluate(vector);
		modifier = 1;
	    num = var.getValue();
	    //dir = 0;
		
		//Figure out what direction peak is in
		dir = selectDirection();
		
		//Make initial change
		num += dir * modifier;
		var.setValue(num);
		next = objFun.evaluate(vector);
		last = initial;
		
		//Keep going in that direction until next is better than last --> Keep Climbing Hill
		while(next.betterThan(last)){
			last = next;
			num += dir * modifier;
			var.setValue(num);
			next = objFun.evaluate(vector);
			

			//Once next is not better than last --> Peak has been reached. Last has best value for this run.
			if(!next.betterThan(last)){
				num -= dir * modifier;
				var.setValue(num);
			}
		}


		if(changeDirection()) {
			//Keeps going until next is better than last --> Keep climbing Hill
			while (next.betterThan(last)) {
			      last = next;
			       // do hill climbing
			      
			      next = objFun.evaluate(vector);
			      //As soon as next is not better than last -> Get out of loop; last is the local optimum. (At peak of hill)
				  if (!next.betterThan(last)) {
			    	//   changeDirection();
			        //   num -= modifier * dir;
			        //   var.setValue(num);

			        }
			}
		
		}
		
	}

}
