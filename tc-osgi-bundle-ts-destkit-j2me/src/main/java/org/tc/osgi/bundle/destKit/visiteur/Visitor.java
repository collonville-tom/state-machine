package destKit.visiteur;

import destKit.metamodel.FiniteStateMachine;

public abstract class Visitor {

	public abstract void visit(FiniteStateMachine fsm);

}
