<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="manufacturer" type="ua.epam.dereza.shop.bean.Manufacturer" required="true" %>

<c:set var="checkedManufacturers" value="${formBean.manufacturers}" />

<label class="checkbox">
	<input type="checkbox" name="manufacturers" value="${manufacturer.name}"
		<c:forEach items="${checkedManufacturers}" var="checkedMnf">
			<c:if test="${checkedMnf == manufacturer.name}">
				checked
			</c:if>
		</c:forEach>
	>${manufacturer.name}	
</label>