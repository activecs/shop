package ua.kharkiv.epam.dereza.socket;

import java.util.List;

public class DivideOperation extends Operation{

	@Override
	protected String calculate(List<String> operands) {
		String result = "";
		Double res = null;
		for(Double operand : parseOperandsValues(operands)){
			if(res == null){
				res = operand;
			}
			res/=operand;
		}

		for(String operand : parseOperandsNames(operands)){
			result += (operand + "/");
		}

		result = result.substring(0, result.lastIndexOf("/")) + "=" + res;

		return result;
	}
}
