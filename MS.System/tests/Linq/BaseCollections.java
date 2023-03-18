package Linq;

import java.util.ArrayList;

import System.TupleT2;
import System.Linq.Enumerable;

public class BaseCollections
{
	private static TupleT2<Integer, String> makeTuple(int id, String info)
	{
		return new TupleT2<>(id, info);
	}
	
	public static Enumerable<TupleT2<Integer, String>> AlphabetNumbers()
	{
		ArrayList<TupleT2<Integer, String>> result = new ArrayList<>();

		result.add(makeTuple(3, "c"));
		result.add(makeTuple(2, "d"));
		result.add(makeTuple(2, "b"));
		result.add(makeTuple(1, "a"));
		result.add(makeTuple(7, "g"));
		result.add(makeTuple(26, "z"));
		result.add(makeTuple(25, "y"));
		result.add(makeTuple(25, "r"));
		result.add(makeTuple(24, "x"));

		return new Enumerable<>(result);
	}
}
