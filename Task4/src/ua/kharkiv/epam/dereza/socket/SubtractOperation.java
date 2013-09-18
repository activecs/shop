package ua.kharkiv.epam.dereza.socket;

import java.util.List;

public class SubtractOperation extends Operation{

	@Override
	protected String calculate(List<String> operands) {
		String result = "";
		Double res = null;
		for(Double operand : parseOperandsValues(operands)){
			if(res == null){
				res = operand;
			}else{
				res -= operand;
			}
		}

		for(String operand : parseOperandsNames(operands)){
			result += (operand + "-");
		}

		result = result.substring(0, result.lastIndexOf("-")) + "=" + res;

		return result;
	}

}
