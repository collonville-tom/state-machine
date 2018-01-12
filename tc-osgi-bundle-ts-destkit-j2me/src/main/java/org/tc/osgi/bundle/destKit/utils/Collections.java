package destKit.utils;

public class Collections {
	public static Object detect(Collection c, Predicate p) {
		Iterator it = c.iterator();
		for (; it.hasNext();) {
			Object e = it.next();
			if (p.evaluate(e))
				return e;
		}
		return null;
	}

	public static Collection select(Collection c, Predicate p) {
		Collection res = null;
		try {
			res = (Collection) c.getClass().newInstance();
			Iterator it = c.iterator();
			for (; it.hasNext();) {
				Object e = it.next();
				if (p.evaluate(e))
					res.add(e);
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return res;
	}

	public static Collection reject(Collection c, Predicate p) {
		Collection res = null;
		try {
			res = (Collection) c.getClass().newInstance();
			Iterator it = c.iterator();
			for (; it.hasNext();) {
				Object e = it.next();
				if (!p.evaluate(e))
					res.add(e);
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return res;
	}

	public static Collection collect(Collection c, Transformer t) {
		Collection res = null;
		try {
			res = (Collection) c.getClass().newInstance();
			Iterator it = c.iterator();
			for (; it.hasNext();)
				t.evaluate(res, it.next());
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return res;
	}
}
