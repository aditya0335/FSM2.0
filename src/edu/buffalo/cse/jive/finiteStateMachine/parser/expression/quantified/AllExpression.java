package edu.buffalo.jive.finiteStateMachine.parser.expression.quantified;
import edu.buffalo.cse.jive.finiteStateMachine.models.Context;
import edu.buffalo.cse.jive.finiteStateMachine.parser.expression.core.VariableExpression;
import edu.buffalo.cse.jive.finiteStateMachine.parser.expression.expression.Expression;
import java.util.ArrayList;
import java.util.List;

public class AllExpression extends QuantifiedVariable {
	
	Expression exp;  //Object of Expression	
	String quantifiedVariableName;	
	int value;
	List<Integer> variableList=new ArrayList<Integer>();	
	int from=-1;  //Starting of Loop	
	int to=-1;  //Ending of Loop
	
	VariableExpression variableExpression;

	public AllExpression(String quantifiedVariableName,int from,int to,Expression exp)
	{
		this.quantifiedVariableName=quantifiedVariableName;
		this.from=from;
		this.to=to;
		this.exp=exp;
	
	}
	
	public AllExpression(String quantifiedVaraibleName,VariableExpression variableExpression,Expression exp)
	{
		this.quantifiedVariableName=quantifiedVaraibleName;		
		this.variableExpression=variableExpression;
		this.exp=exp;
		
	}
	
	public Object getValue(String qvars) {
		return QuantifiedVarsMap.get(qvars);
	}

	@Override
	public Boolean evaluate(Context context) {
		// TODO Auto-generated method stub
		if(from==-1)
		{

			variableExpression.evaluate(context);
			String listString = (String) variableExpression.getValue();
			System.out.println("AllExpression.listString= "+listString);
			String[] listValue = listString.split(" |\\]|\\[");
			for(String test:listValue) {
				System.out.println("AllExpression.test= "+test);
			}
			
			QuantifiedVarsMap.put(this.quantifiedVariableName,listValue[0]);
			
			for(String qvar:listValue) {
				if(!(qvar.equals(" ") || qvar.equals("")))
					{Boolean currentResult;
					System.out.println("AllExpression.qvar= "+qvar);
					QuantifiedVarsMap.replace(quantifiedVariableName,Integer.parseInt(qvar));
					currentResult = exp.evaluate(context);				
					if(currentResult==false) {return false;}
			}}
		}
		else {
			QuantifiedVarsMap.put(this.quantifiedVariableName, this.from);
			for(int i=from;i<=to;i++) {
				Boolean currentResult;
				QuantifiedVarsMap.replace(quantifiedVariableName,i);
				currentResult = exp.evaluate(context);
				if(currentResult==false) {return false;}
			}
		}
		QuantifiedVarsMap.remove(quantifiedVariableName);
		return true;
	}


}
