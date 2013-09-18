package ua.kharkiv.epam.dereza.socket;

import java.util.List;

public class MultiplyOperation extends Operation{

	@Override
	protected String calculate(List<String> operands) {
		String result = "";
		Double res = 1.;
		for(Double operand : parseOperandsValues(operands)){
			res*=operand;
		}

		for(String operand : parseOperandsNames(operands)){
			result += (operand + "*");
		}

		result = result.substring(0, result.lastIndexOf("*")) + "=" + res;

		return result;
	}
}
