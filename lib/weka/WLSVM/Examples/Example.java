
/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    Example.java
 *    Copyright (C) 2005 Yasser EL-Manzalawy
 *
 */

/*
 * This exmaple deomnstrates how to use WLSVM classifier.
 */



import weka.classifiers.Evaluation;
import wlsvm.WLSVM;

public class Example {
	public static void main(String[] argv)throws Exception{
		if (argv.length < 1){
			System.out.println("Usage: Example <arff file>");
			System.exit(1);
		}
		
		String dataFile = argv[0];  // input arff file
		 		
		WLSVM lib = new WLSVM();
			
		String[] ops = {new String("-t"),
				dataFile,
				new String("-x"),		// 5 folds CV
				new String("5"),
				new String("-i"),		//
				//---------------
				new String("-S"),		// WLSVM options
				new String("0"),		// Classification problem
				new String("-K"),       // RBF kernel
				new String("2"),
				new String("-G"),       // gamma
				new String("1"),
				new String("-C"),       // C
				new String("7"),
				new String("-Z"),       // normalize input data
				new String("1"),
				new String("-M"),       // cache size in MB
				new String("100")				
				};
		
		System.out.println(Evaluation.evaluateModel(lib,ops));
	}
}
